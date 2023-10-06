package br.senai.sc.capiplayvideo.video.service;

import br.senai.sc.capiplayvideo.messaging.Publisher;
import br.senai.sc.capiplayvideo.pesquisa.model.entity.Filtro;
import br.senai.sc.capiplayvideo.tag.model.entity.Tag;
import br.senai.sc.capiplayvideo.tag.service.TagService;
import br.senai.sc.capiplayvideo.exceptions.ObjetoInexistenteException;
import br.senai.sc.capiplayvideo.usuario.model.entity.Usuario;
import br.senai.sc.capiplayvideo.usuario.model.entity.UsuarioVisualizaVideo;
import br.senai.sc.capiplayvideo.usuario.service.UsuarioService;
import br.senai.sc.capiplayvideo.usuario.service.UsuarioVisualizaVideoService;
import br.senai.sc.capiplayvideo.video.amqp.VideoSalvoEvent;
import br.senai.sc.capiplayvideo.video.model.dto.VideoDTO;
import br.senai.sc.capiplayvideo.video.model.dto.VideoRetornoDTO;
import br.senai.sc.capiplayvideo.video.model.entity.Video;
import br.senai.sc.capiplayvideo.video.model.enums.ResolucaoEnum;
import br.senai.sc.capiplayvideo.video.model.projection.VideoMiniaturaProjection;
import br.senai.sc.capiplayvideo.video.repository.VideoRepository;
import br.senai.sc.capiplayvideo.video.utils.GeradorUuidUtils;

import jakarta.validation.Valid;

import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.jcodec.containers.mp4.MP4Util;
import org.jcodec.containers.mp4.boxes.Box;
import org.jcodec.containers.mp4.boxes.MovieHeaderBox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

import java.nio.file.Paths;

import java.util.List;
import java.util.ArrayList;

import static java.time.ZoneOffset.UTC;
import static java.util.Objects.isNull;


@Service
public class VideoService {

    private final VideoRepository repository;
    private final TagService tagService;
    private final UsuarioService usuarioService;
    private final UsuarioVisualizaVideoService visualizacaoService;
    private final Publisher publisher;

    @Value("${diretorioVideos}")
    private String diretorio;

    @Autowired
    public VideoService(VideoRepository repository,
                        TagService tagService,
                        UsuarioService usuarioService,
                        UsuarioVisualizaVideoService usuarioVisualizaVideoService,
                        Publisher publisher) {
        this.repository = repository;
        this.tagService = tagService;
        this.usuarioService = usuarioService;
        this.visualizacaoService = usuarioVisualizaVideoService;
        this.publisher = publisher;
    }

    public void salvar(@Valid VideoDTO videoDTO, String usuarioId) throws IOException {
        String uuid = GeradorUuidUtils.gerarUuid();
        String diretorioEsse = diretorio + uuid + File.separator;
        try {
            Path caminho = Paths.get(diretorioEsse);
            Files.createDirectories(caminho);
            Path arquivoTemporario = Files.createTempFile(caminho, "video_", ".mp4");
            videoDTO.video().transferTo(arquivoTemporario.toFile());
            MovieHeaderBox mvhd = findMovieHeader(arquivoTemporario.toFile());
            Long durationInSeconds = mvhd.getDuration() / mvhd.getTimescale();
            for (ResolucaoEnum resolucaoEnum : ResolucaoEnum.values()) {
                BufferedImage imagemRedimensionada = redimensionarImagem(
                        videoDTO.miniatura().getInputStream(),
                        resolucaoEnum.getLargura(),
                        resolucaoEnum.getAltura());
                arquivoTemporario = Files.createTempFile(caminho, "miniatura_" + resolucaoEnum + "_", ".png");
                ImageIO.write(imagemRedimensionada, "PNG", arquivoTemporario.toFile());
            }
            Usuario usuario = usuarioService.buscarUm(usuarioId);
            Video video = new Video(uuid, videoDTO, diretorioEsse, usuario, durationInSeconds);
            video.getTags().forEach(tagService::salvar);
            repository.save(video);
            this.publisher.publish(new VideoSalvoEvent(video.getUuid(), false)); // Manda para o RabbitMQ
        } catch (Exception e) {
            FileUtils.deleteDirectory(new File(diretorioEsse));
            throw e;
        }
    }

    private static MovieHeaderBox findMovieHeader(File f) throws IOException {
        for (Box box : MP4Util.createRefMovieFromFile(f).getBoxes()) {
            if (box instanceof MovieHeaderBox)
                return (MovieHeaderBox) box;
        }
        return null;
    }

    private BufferedImage redimensionarImagem(InputStream inputStream, int largura, int altura) throws IOException {
        BufferedImage sourceImage = ImageIO.read(inputStream);
        Image resizedImage = sourceImage.getScaledInstance(largura, altura, Image.SCALE_SMOOTH);
        BufferedImage bufferedResizedImage = new BufferedImage(largura, altura, BufferedImage.TYPE_INT_RGB);
        bufferedResizedImage.getGraphics().drawImage(resizedImage, 0, 0, null);
        return bufferedResizedImage;
    }

    public Page<VideoMiniaturaProjection> buscarTodos(Pageable pageable, String usuarioId, boolean shorts) {
        return repository.findAllByHistorico(pageable, usuarioId, shorts);
    }

    public List<VideoMiniaturaProjection> buscarPorCategoria(Pageable pageable, String categoria, String usuarioId, boolean shorts) {
        return repository.findAllByHistoricoByCategoria(pageable, usuarioId, categoria, shorts);
    }

    public VideoRetornoDTO buscarUm(String uuid, String uuidUsuario) {
        Usuario usuario = usuarioService.buscarUm(uuidUsuario);
        UsuarioVisualizaVideo historico =
                visualizacaoService.findByUsuarioUuidAndVideoUuid(uuidUsuario, uuid);
        Video video = repository.findById(uuid).get();
        if (isNull(historico)) historico = new UsuarioVisualizaVideo(usuario, video);
        historico.incrementarVisualizacao();
        historico.atualizarData();
        visualizacaoService.salvar(historico);
        video.incrementarVisualizacao();
        repository.save(video);
        return new VideoRetornoDTO(video);
    }

    public VideoRetornoDTO buscarShorts(String uuidUsuario) {
        Video video = repository.findOneByHistoricoByShort(uuidUsuario);
        if (isNull(video)) {
            video = repository.findShortByData(uuidUsuario, PageRequest.of(0, 1)).get(0);
        }
        Usuario usuario = usuarioService.buscarUm(uuidUsuario);
        UsuarioVisualizaVideo historico =
                visualizacaoService.findByUsuarioUuidAndVideoUuid(uuidUsuario, video.getUuid());
        if (isNull(historico)) historico = new UsuarioVisualizaVideo(usuario, new Video(video.getUuid()));
        historico.incrementarVisualizacao();
        historico.atualizarData();
        visualizacaoService.salvar(historico);
        video.incrementarVisualizacao();
        repository.save(video);
        return new VideoRetornoDTO(video);
    }

    public Video buscarUmVideo(String uuid) {
        return repository.findById(uuid).orElseThrow(ObjetoInexistenteException::new);
    }

    public void atualizarVideo(Video video) {
        repository.save(video);
    }

    public List<VideoMiniaturaProjection> filtrarVideos(String pesquisa, Filtro filtro, Pageable pageable, String usuarioId) {
        List<VideoMiniaturaProjection> videosFiltrados = repository.searchByFiltro(
                pesquisa,
                filtro.getFiltroDia(),
                filtro.getFiltroSemana(),
                filtro.getFiltroMes(),
                filtro.getFiltroAno(),
                filtro.getFiltroMenosDe5Min(),
                filtro.getFiltroEntre5E20Min(),
                filtro.getFiltroMaisDe20Min(),
                filtro.getFiltroVideo(),
                filtro.getFiltroShorts(),
                pageable);
        List<VideoMiniaturaProjection> videosNaoAssistidos = new ArrayList<>();
        Usuario usuario = usuarioService.buscarUm(usuarioId);
        for (VideoMiniaturaProjection video : videosFiltrados) {
            if (!usuario.getHistoricoVideo().contains(video)) {
                videosNaoAssistidos.add(video);
            }
        }
        return videosNaoAssistidos;
    }

    public List<VideoMiniaturaProjection> buscarUploads(Pageable pageable, String usuarioId) {
        return repository.findAllByUsuario_Uuid(usuarioId, pageable);
    }

    public List<VideoMiniaturaProjection> buscarHistorico(Pageable pageable, String usuarioId, Boolean shorts) {
        return repository.findAllHistoricoMinimizado(usuarioId, pageable, shorts);
    }

}
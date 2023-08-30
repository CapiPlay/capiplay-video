package br.senai.sc.capiplayvideo.video.service;

import br.senai.sc.capiplayvideo.messaging.Publisher;
import br.senai.sc.capiplayvideo.pesquisa.model.entity.Filtro;
import br.senai.sc.capiplayvideo.tag.service.TagService;
import br.senai.sc.capiplayvideo.exceptions.ObjetoInexistenteException;
import br.senai.sc.capiplayvideo.usuario.model.entity.Usuario;
import br.senai.sc.capiplayvideo.usuario.model.entity.UsuarioVisualizaVideo;
import br.senai.sc.capiplayvideo.usuario.service.UsuarioService;
import br.senai.sc.capiplayvideo.usuario.service.UsuarioVisualizaVideoService;
import br.senai.sc.capiplayvideo.video.amqp.VideoSalvoEvent;
import br.senai.sc.capiplayvideo.video.model.dto.VideoDTO;
import br.senai.sc.capiplayvideo.video.model.entity.Video;
import br.senai.sc.capiplayvideo.video.model.enums.ResolucaoEnum;
import br.senai.sc.capiplayvideo.video.model.projection.VideoMiniaturaProjection;
import br.senai.sc.capiplayvideo.video.model.projection.VideoProjection;
import br.senai.sc.capiplayvideo.video.repository.VideoRepository;
import br.senai.sc.capiplayvideo.video.utils.GeradorUuidUtils;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.jcodec.containers.mp4.MP4Util;
import org.jcodec.containers.mp4.boxes.Box;
import org.jcodec.containers.mp4.boxes.MovieHeaderBox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

import java.time.ZonedDateTime;
import java.util.List;
import java.time.LocalDate;
import java.util.ArrayList;

import static java.time.ZoneOffset.UTC;


@Service
public class VideoService {

    private final VideoRepository repository;
    private final TagService tagService;
    private final UsuarioService usuarioService;
    private final UsuarioVisualizaVideoService usuarioVisualizaVideoService;
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
        this.usuarioVisualizaVideoService = usuarioVisualizaVideoService;
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
            Video video = new Video(uuid, videoDTO, diretorioEsse, usuarioId, durationInSeconds);
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

    public List<VideoMiniaturaProjection> buscarTodos(Pageable pageable, String usuarioId) {
        return repository.findAllByHistorico(pageable, usuarioId);
    }

    public List<VideoMiniaturaProjection> buscarPorCategoria(Pageable pageable, String categoria, String usuarioId) {
        return repository.findAllByHistoricoByCategoria(pageable, usuarioId, categoria);
    }

    public VideoProjection buscarUm(String uuid, String uuidUsuario) {
        if (uuidUsuario == null) return repository.findByUuid(uuid).orElseThrow(ObjetoInexistenteException::new);
        Usuario usuario = usuarioService.buscarUm(uuidUsuario);
        UsuarioVisualizaVideo historico =
                usuarioVisualizaVideoService.findByUsuarioUuidAndVideoUuid(uuidUsuario, uuid);
        if (historico == null) {
            historico = new UsuarioVisualizaVideo(usuario, new Video(uuid));
            usuarioVisualizaVideoService.salvar(historico);
            usuario.getHistoricoVideo().add(historico);
            usuarioService.salvar(usuario);
        } else {
            historico.setQtdVisualizacoes(historico.getQtdVisualizacoes() + 1);
            historico.setDataVisualizacao(ZonedDateTime.now(UTC));
            usuarioVisualizaVideoService.salvar(historico);
        }
        return repository.findByUuid(uuid).orElseThrow(ObjetoInexistenteException::new);
    }

    public VideoProjection buscarShorts(String uuidUsuario) {
        if (uuidUsuario == null) return repository.findOneByHistoricoByShort(null);
        VideoProjection video = repository.findOneByHistoricoByShort(uuidUsuario);
        Usuario usuario = usuarioService.buscarUm(uuidUsuario);
        if (video == null) {
            VideoProjection videoR = repository.findShortByData(uuidUsuario, PageRequest.of(0, 1)).get(0);
            UsuarioVisualizaVideo historico = usuarioVisualizaVideoService.findByUsuarioUuidAndVideoUuid(uuidUsuario, videoR.getUuid());
            historico.setQtdVisualizacoes(historico.getQtdVisualizacoes() + 1);
            historico.setDataVisualizacao(ZonedDateTime.now(UTC));
            usuarioVisualizaVideoService.salvar(historico);
            return videoR;
        }
        UsuarioVisualizaVideo usuarioVisualizaVideo = new UsuarioVisualizaVideo(usuario, new Video(video.getUuid()));
        usuarioVisualizaVideoService.salvar(usuarioVisualizaVideo);
        usuario.getHistoricoVideo().add(usuarioVisualizaVideo);
        usuarioService.salvar(usuario);
        return video;
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

    public List<VideoMiniaturaProjection> buscarHistorico(Pageable pageable, String usuarioId) {
        return repository.findAllHistoricoMinimizado(usuarioId, pageable);
    }
}
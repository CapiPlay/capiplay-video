package br.senai.sc.capiplayvideo.video.service;

import br.senai.sc.capiplayvideo.categoria.model.enums.CategoriasEnum;
import br.senai.sc.capiplayvideo.categoria.service.CategoriaService;
import br.senai.sc.capiplayvideo.pesquisa.model.entity.Filtro;
import br.senai.sc.capiplayvideo.tag.service.TagService;
import br.senai.sc.capiplayvideo.exceptions.ObjetoInexistenteException;
import br.senai.sc.capiplayvideo.usuario.model.entity.Usuario;
import br.senai.sc.capiplayvideo.usuario.model.entity.UsuarioVisualizaVideo;
import br.senai.sc.capiplayvideo.usuario.service.UsuarioService;
import br.senai.sc.capiplayvideo.usuario.service.UsuarioVisualizaVideoService;
import br.senai.sc.capiplayvideo.video.model.dto.VideoDTO;
import br.senai.sc.capiplayvideo.categoria.model.entity.Categoria;
import br.senai.sc.capiplayvideo.video.model.entity.Video;
import br.senai.sc.capiplayvideo.video.model.enums.ResolucaoEnum;
import br.senai.sc.capiplayvideo.video.model.projection.VideoMiniaturaProjection;
import br.senai.sc.capiplayvideo.video.model.projection.VideoProjection;
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
import org.springframework.data.domain.PageImpl;
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
import java.time.LocalDate;
import java.util.ArrayList;


@Service
public class VideoService {

    private final VideoRepository repository;
    private final TagService tagService;
    private final CategoriaService categoriaService;
    private final UsuarioService usuarioService;
    private final UsuarioVisualizaVideoService usuarioVisualizaVideoService;

    @Value("${diretorioVideos}")
    private String diretorio;

    @Autowired
    public VideoService(VideoRepository repository,
                        TagService tagService,
                        CategoriaService categoriaService,
                        UsuarioService usuarioService,
                        UsuarioVisualizaVideoService usuarioVisualizaVideoService
    ) {
        this.repository = repository;
        this.tagService = tagService;
        this.categoriaService = categoriaService;
        this.usuarioService = usuarioService;
        this.usuarioVisualizaVideoService = usuarioVisualizaVideoService;
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
            categoriaService.salvar(video.getCategoria());
            repository.save(video);
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
        List<VideoMiniaturaProjection> videos = repository.findAllBy(pageable);
        List<VideoMiniaturaProjection> videosNaoAssistidos = new ArrayList<>();
        Usuario usuario = usuarioService.buscarUm(usuarioId);
        for (VideoMiniaturaProjection video : videos) {
            if(!usuario.getHistoricoVideo().contains(video)) {
                videosNaoAssistidos.add(video);
            }
        }
        return videosNaoAssistidos;
    }

    public List<VideoMiniaturaProjection> buscarPorCategoria(Pageable pageable, CategoriasEnum categoria, String usuarioId) {
        List<VideoMiniaturaProjection> videos = repository.findAllByCategoria_categoriaString(categoria.name(), pageable);
        List<VideoMiniaturaProjection> videosNaoAssistidos = new ArrayList<>();
        Usuario usuario = usuarioService.buscarUm(usuarioId);
        for (VideoMiniaturaProjection video : videos) {
            if(!usuario.getHistoricoVideo().contains(video)) {
                videosNaoAssistidos.add(video);
            }
        }
        return videosNaoAssistidos;
    }

    public VideoProjection buscarUm(String uuid, String uuidUsuario) {
        if (uuidUsuario == null) {
            return repository.findByUuid(uuid).orElseThrow(ObjetoInexistenteException::new);
        }
        Usuario usuario = usuarioService.buscarUm(uuidUsuario);
        UsuarioVisualizaVideo usuarioVisualizaVideo = new UsuarioVisualizaVideo(usuario, new Video(uuid));
        usuarioVisualizaVideoService.salvar(usuarioVisualizaVideo);
        usuario.getHistoricoVideo().add(usuarioVisualizaVideo);
        usuarioService.salvar(usuario);
        return repository.findByUuid(uuid).orElseThrow(ObjetoInexistenteException::new);
    }

    public VideoProjection buscarReels(String uuidUsuario) {
        List<Video> videos = repository.findAllByShortsIsTrue();
        if (uuidUsuario == null) {
            return repository.findByUuid(videos.get(Math.random() > 0.5 ? 0 : videos.size() - 1).getUuid()).get();
        }
        Usuario usuario = usuarioService.buscarUm(uuidUsuario);
        for (Video video : videos) {
            if (!usuario.getHistoricoVideo().contains(video)) {
                UsuarioVisualizaVideo usuarioVisualizaVideo = new UsuarioVisualizaVideo(usuario, video);
                usuarioVisualizaVideoService.salvar(usuarioVisualizaVideo);
                usuario.getHistoricoVideo().add(usuarioVisualizaVideo);
                usuarioService.salvar(usuario);
                return repository.findByUuid(video.getUuid()).get();
            }
        }
        return repository.findByUuid(videos.get(Math.random() > 0.5 ? 0 : videos.size() - 1).getUuid()).get();
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
            if(!usuario.getHistoricoVideo().contains(video)) {
                videosNaoAssistidos.add(video);
            }
        }
        return videosNaoAssistidos;
    }
}
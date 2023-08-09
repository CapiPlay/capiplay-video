package br.senai.sc.capiplayvideo.video.controller;

import br.senai.sc.capiplayvideo.categoria.model.entity.Categoria;
import br.senai.sc.capiplayvideo.pesquisa.model.dto.FiltroDTO;
import br.senai.sc.capiplayvideo.pesquisa.model.entity.Filtro;
import br.senai.sc.capiplayvideo.video.model.dto.VideoDTO;
import br.senai.sc.capiplayvideo.video.model.projection.VideoMiniaturaProjection;
import br.senai.sc.capiplayvideo.video.model.projection.VideoProjection;
import br.senai.sc.capiplayvideo.video.service.VideoService;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/video")
public class VideoController {

    private final VideoService service;

    @PostMapping("/criar")
    public ResponseEntity<Void> criar(
            @RequestParam("titulo") String titulo,
            @RequestParam("descricao") String descricao,
            @RequestParam("tags") List<String> tags,
            @RequestParam("categoria") String categoria,
            @RequestParam("ehReels") Boolean ehReels,
            @RequestParam("video") MultipartFile video,
            @RequestParam("miniatura") MultipartFile miniatura,
            @RequestParam("duracao") Long duracao,
            @RequestHeader("usuarioId") String usuarioId
    ) throws IOException {
        service.salvar(new VideoDTO(titulo, descricao, tags, categoria, ehReels, video, miniatura, duracao, usuarioId));
        return ResponseEntity.ok().build();
    }

    @GetMapping("/buscar-completo/{uuid}")
    public ResponseEntity<VideoProjection> buscarUm(@PathVariable String uuid, @RequestHeader(value = "usuarioId", required = false) String usuarioId) {
        return ResponseEntity.ok(service.buscarUm(uuid, usuarioId));
    }

    @GetMapping("/buscar-resumido")
    public Page<VideoMiniaturaProjection> buscarTodos(
            @RequestParam("size") int size,
            @RequestParam("page") int page) {
        return service.buscarTodos(PageRequest.of(page, size));
    }

    @GetMapping("/buscar-por-categoria")
    public Page<VideoMiniaturaProjection> buscarPorCategoria(
            @RequestParam("categoria") Categoria categoria,
            @RequestParam("size") int size,
            @RequestParam("page") int page) {
        return service.buscarPorCategoria(PageRequest.of(page, size), categoria);
    }

    @GetMapping("/buscar-reels")
    public ResponseEntity<VideoProjection> buscarReels(@RequestHeader(value = "usuarioId", required = false) String usuarioId) {
        return ResponseEntity.ok(service.buscarReels(usuarioId));
    }

    @GetMapping("/filtro/{pesquisa}")
    public ResponseEntity<Page<VideoMiniaturaProjection>> filtrarVideos(
            @PathVariable String pesquisa,
            @RequestParam Boolean filtroDia,
            @RequestParam Boolean filtroSemana,
            @RequestParam Boolean filtroMes,
            @RequestParam Boolean filtroAno,
            @RequestParam Boolean filtroMenosDe5Min,
            @RequestParam Boolean filtroEntre5E20Min,
            @RequestParam Boolean filtroMaisDe20Min,
            @RequestParam Boolean filtroVideo,
            @RequestParam Boolean filtroShorts,
            @RequestParam("page") int page,
            @RequestParam("size") int size) {

        Filtro filtro = new Filtro();
        BeanUtils.copyProperties(new FiltroDTO(filtroDia, filtroSemana, filtroMes, filtroAno, filtroMenosDe5Min, filtroEntre5E20Min, filtroMaisDe20Min, filtroVideo, filtroShorts), filtro);
        return ResponseEntity.ok(service.filtrarVideos(pesquisa, filtro, PageRequest.of(page, size)));
    }
}
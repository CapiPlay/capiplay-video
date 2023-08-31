package br.senai.sc.capiplayvideo.video.controller;

import br.senai.sc.capiplayvideo.pesquisa.model.dto.FiltroDTO;
import br.senai.sc.capiplayvideo.pesquisa.model.entity.Filtro;
import br.senai.sc.capiplayvideo.video.model.dto.VideoDTO;
import br.senai.sc.capiplayvideo.video.model.projection.VideoMiniaturaProjection;
import br.senai.sc.capiplayvideo.video.model.projection.VideoProjection;
import br.senai.sc.capiplayvideo.video.service.VideoService;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/video")
public class VideoController {

    private final VideoService service;

    @PostMapping("/criar")
    public ResponseEntity<Void> criar(
            @ModelAttribute VideoDTO videoDTO,
            @RequestHeader("usuarioId") String usuarioId
    ) throws IOException {
        service.salvar(videoDTO, usuarioId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/buscar-completo/{uuid}")
    public ResponseEntity<VideoProjection> buscarUm(
            @PathVariable String uuid,
            @RequestHeader(value = "usuarioId", required = false) String usuarioId
    ) {
        return ResponseEntity.ok(service.buscarUm(uuid, usuarioId));
    }

    @GetMapping("/buscar-historico")
    public ResponseEntity<List<VideoMiniaturaProjection>> buscarHistorico(
            @RequestParam("size") int size,
            @RequestParam("page") int page,
            @RequestHeader(value = "usuarioId", required = false) String usuarioId
    ) {
        return ResponseEntity.ok(service.buscarHistorico(PageRequest.of(page, size), usuarioId));
    }

    @GetMapping("/buscar-resumido")
    public List<VideoMiniaturaProjection> buscarTodos(
            @RequestParam("size") int size,
            @RequestParam("page") int page,
            @RequestParam("shorts") boolean shorts,
            @RequestHeader(value = "usuarioId", required = false) String usuarioId
    ) {
        return service.buscarTodos(PageRequest.of(page, size), usuarioId, shorts);
    }

    @GetMapping("/buscar-por-categoria")
    public List<VideoMiniaturaProjection> buscarPorCategoria(
            @RequestParam String categoria,
            @RequestParam("size") int size,
            @RequestParam("page") int page,
            @RequestHeader(value = "usuarioId", required = false) String usuarioId
    ) {
        return service.buscarPorCategoria(PageRequest.of(page, size), categoria, usuarioId);
    }

    @GetMapping("/buscar-shorts")
    public ResponseEntity<VideoProjection> buscarShorts(
            @RequestHeader(value = "usuarioId", required = false) String usuarioId
    ) {
        return ResponseEntity.ok(service.buscarShorts(usuarioId));
    }

    @GetMapping("/buscar-videos-canal")
    public List<VideoMiniaturaProjection> buscarUploads(
            @RequestParam("size") int size,
            @RequestParam("page") int page,
            @RequestParam("donoCanalId") String donoCanalId
    ) {
        return service.buscarUploads(PageRequest.of(page, size), donoCanalId);
    }

    @GetMapping("/filtro/{pesquisa}")
    public ResponseEntity<List<VideoMiniaturaProjection>> filtrarVideos(
            @PathVariable String pesquisa,
            @ModelAttribute FiltroDTO filtroDTO,
            @RequestParam("page") int page,
            @RequestParam("size") int size,
            @RequestHeader(value = "usuarioId", required = false) String usuarioId
    ) {
        Filtro filtro = new Filtro();
        BeanUtils.copyProperties(filtroDTO, filtro);
        return ResponseEntity.ok(service.filtrarVideos(pesquisa, filtro, PageRequest.of(page, size), usuarioId));
    }
}
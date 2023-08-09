package br.senai.sc.capiplayvideo.video.controller;

import br.senai.sc.capiplayvideo.categoria.model.entity.Categoria;
import br.senai.sc.capiplayvideo.pesquisa.model.dto.FiltroDTO;
import br.senai.sc.capiplayvideo.pesquisa.model.entity.Filtro;
import br.senai.sc.capiplayvideo.video.model.dto.VideoDTO;
import br.senai.sc.capiplayvideo.video.model.projection.VideoMiniaturaProjection;
import br.senai.sc.capiplayvideo.video.model.projection.VideoProjection;
import br.senai.sc.capiplayvideo.video.service.VideoService;
import jakarta.validation.Valid;
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

    @GetMapping("/buscar-resumido")
    public Page<VideoMiniaturaProjection> buscarTodos(
            @RequestParam("size") int size,
            @RequestParam("page") int page
    ) {
        return service.buscarTodos(PageRequest.of(page, size));
    }

    @GetMapping("/buscar-por-categoria")
    public Page<VideoMiniaturaProjection> buscarPorCategoria(
            @ModelAttribute Categoria categoria,
            @RequestParam("size") int size,
            @RequestParam("page") int page
    ) {
        return service.buscarPorCategoria(PageRequest.of(page, size), categoria);
    }

    @GetMapping("/buscar-reels")
    public ResponseEntity<VideoProjection> buscarReels(
            @RequestHeader(value = "usuarioId", required = false) String usuarioId
    ) {
        return ResponseEntity.ok(service.buscarReels(usuarioId));
    }

    @GetMapping("/filtro/{pesquisa}")
    public ResponseEntity<Page<VideoMiniaturaProjection>> filtrarVideos(
            @PathVariable String pesquisa,
            @ModelAttribute FiltroDTO filtroDTO,
            @RequestParam("page") int page,
            @RequestParam("size") int size
    ) {
        Filtro filtro = new Filtro();
        BeanUtils.copyProperties(filtroDTO, filtro);
        return ResponseEntity.ok(service.filtrarVideos(pesquisa, filtro, PageRequest.of(page, size)));
    }
}
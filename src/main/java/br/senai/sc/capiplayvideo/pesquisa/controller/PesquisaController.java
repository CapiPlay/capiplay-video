package br.senai.sc.capiplayvideo.pesquisa.controller;

import br.senai.sc.capiplayvideo.pesquisa.model.entity.Pesquisa;
import br.senai.sc.capiplayvideo.pesquisa.service.PesquisaService;
import br.senai.sc.capiplayvideo.video.model.projection.VideoMiniaturaProjection;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/video/pesquisa")
public class PesquisaController {

    private PesquisaService pesquisaService;

    @GetMapping("/{searchTerm}")
    public ResponseEntity<List<VideoMiniaturaProjection>> buscarVideos(
            @PathVariable String searchTerm,
            @RequestParam("shorts") boolean shorts,
            @RequestHeader(value = "usuarioId", required = false) String usuarioId
    ) {
        return ResponseEntity.ok(pesquisaService.buscarVideos(searchTerm, usuarioId, shorts));
    }


}

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
@RequestMapping("/api/pesquisa")
public class PesquisaController {

    private PesquisaService pesquisaService;

    // RequestHeader uuid
    @GetMapping("/{string}")
    public ResponseEntity<List<VideoMiniaturaProjection>> buscarVideos(
            @PathVariable String string,
            @RequestHeader String usuarioId
    ) {
        Pesquisa pesquisa = new Pesquisa(string);
        return ResponseEntity.ok(pesquisaService.buscarVideos(pesquisa, usuarioId));
    }


}

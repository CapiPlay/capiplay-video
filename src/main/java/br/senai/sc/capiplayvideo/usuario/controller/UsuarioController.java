package br.senai.sc.capiplayvideo.usuario.controller;

import br.senai.sc.capiplayvideo.usuario.model.projection.UsuarioHistoricoProjection;
import br.senai.sc.capiplayvideo.usuario.service.UsuarioService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/api/usuario")
public class UsuarioController {

    private UsuarioService service;

    @GetMapping("/get/historico/{uuid}")
    public ResponseEntity<List<UsuarioHistoricoProjection>> buscarHistorico(@PathVariable String uuid) {
        return ResponseEntity.ok(service.buscarHistorico(uuid));
    }
}

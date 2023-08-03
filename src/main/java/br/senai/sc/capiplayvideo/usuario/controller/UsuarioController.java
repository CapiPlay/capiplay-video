package br.senai.sc.capiplayvideo.usuario.controller;

import br.senai.sc.capiplayvideo.pesquisa.model.entity.Pesquisa;
import br.senai.sc.capiplayvideo.usuario.model.entity.Usuario;
import br.senai.sc.capiplayvideo.usuario.service.UsuarioService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@AllArgsConstructor
@CrossOrigin(origins = "*")
@RequestMapping("/api/usuario")
public class UsuarioController {

    private UsuarioService service;

    @GetMapping("/get/historico/{uuid}")
    public ResponseEntity<List<Pesquisa>> buscarHistorico(@PathVariable String uuid) {
        return ResponseEntity.ok(service.buscarHistoricoPesquisa(uuid));
    }
}

package br.senai.sc.capiplayvideo.video.utils;

import br.senai.sc.capiplayvideo.usuario.model.entity.Usuario;
import br.senai.sc.capiplayvideo.usuario.repository.UsuarioRepository;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class BancoUtil {

    private final UsuarioRepository repository;

    @PostConstruct
    public void init() {
        repository.save(new Usuario("1"));
    }

}

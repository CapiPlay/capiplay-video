package br.senai.sc.capiplayvideo.usuario.service;

import br.senai.sc.capiplayvideo.usuario.model.entity.UsuarioVisualizaVideo;
import br.senai.sc.capiplayvideo.usuario.repository.UsuarioVisualizaVideoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UsuarioVisualizaVideoService {

    private final UsuarioVisualizaVideoRepository repository;

    public void salvar(UsuarioVisualizaVideo usuarioVisualizaVideo) {
        repository.save(usuarioVisualizaVideo);
    }

    public UsuarioVisualizaVideo findByUsuarioUuidAndVideoUuid(String usuarioUuid, String videoUuid) {
        return repository.findByUsuarioUuidAndVideoUuid(usuarioUuid, videoUuid);
    }

}

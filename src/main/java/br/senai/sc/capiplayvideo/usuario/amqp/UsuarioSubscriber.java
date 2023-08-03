package br.senai.sc.capiplayvideo.usuario.amqp;

import br.senai.sc.capiplayvideo.usuario.amqp.events.UsuarioSalvoEvent;
import br.senai.sc.capiplayvideo.usuario.model.entity.Usuario;
import br.senai.sc.capiplayvideo.usuario.service.UsuarioService;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UsuarioSubscriber {

    private final UsuarioService usuarioService;

    @RabbitListener(queues = "usuarios.v1.usuario-salvo.video")
    public void on(UsuarioSalvoEvent usuarioSalvoEvent) {
        Usuario usuario = new Usuario(usuarioSalvoEvent.uuid());
        usuarioService.salvar(usuario);
    }
}
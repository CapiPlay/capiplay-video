package br.senai.sc.capiplayvideo.usuario.amqp;

import br.senai.sc.capiplayvideo.usuario.amqp.events.AnonimoSalvoEvent;
import br.senai.sc.capiplayvideo.usuario.amqp.events.UsuarioSalvoEvent;
import br.senai.sc.capiplayvideo.usuario.model.entity.Usuario;
import br.senai.sc.capiplayvideo.usuario.service.UsuarioService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UsuarioSubscriber {

    private final UsuarioService usuarioService;

//    @RabbitListener(queues = "usuarios.v1.usuario-salvo.video")
    public void on(UsuarioSalvoEvent event) {
        usuarioService.salvar(new Usuario(event.id()));
    }

//    @RabbitListener(queues = "usuarios.v1.anonimo-salvo.video")
    public void on(AnonimoSalvoEvent event) {
        usuarioService.salvar(new Usuario(event.id()));
    }
}
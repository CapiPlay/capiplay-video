package br.senai.sc.capiplayvideo.usuario.amqp;

import br.senai.sc.capiplayvideo.usuario.amqp.events.UsuarioSalvoEvent;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class UsuarioSubscriber {

    @RabbitListener(queues = "usuarios.v1.usuario-salvo.video")
    public void on(UsuarioSalvoEvent event) {
        // Salvar usuario no banco
        System.out.println("id: " + event.getId() + ", nome: " + event.getNome());
    }
}
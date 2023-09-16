package br.senai.sc.capiplayvideo.messaging;

import br.senai.sc.capiplayvideo.video.amqp.VideoSalvoEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class Publisher {

//    private final RabbitTemplate rabbitTemplate;

    private static final String EXCHANGE = "video-service";

    public void publish(VideoSalvoEvent event) {
        String routingKey = event.getClass().getSimpleName();
//        rabbitTemplate.convertAndSend(EXCHANGE, routingKey, event);
    }
}
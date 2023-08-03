package br.senai.sc.capiplayvideo.engajamento.amqp;

import br.senai.sc.capiplayvideo.engajamento.amqp.events.VideoAtualizadoEvent;
import br.senai.sc.capiplayvideo.video.model.entity.Video;
import br.senai.sc.capiplayvideo.video.service.VideoService;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EngajamentoSubscriber {

    private final VideoService videoService;

    @RabbitListener(queues = "engajamento.v1.video-atualizado.video")
    public void on(VideoAtualizadoEvent videoAtualizadoEvent) {
        Video video = videoService.buscarUmVideo(videoAtualizadoEvent.uuid());
        video.setCurtidas(videoAtualizadoEvent.curtidas());
        video.setVisualizacoes(videoAtualizadoEvent.visualizacoes());
        videoService.atualizarVideo(video);
    }

}

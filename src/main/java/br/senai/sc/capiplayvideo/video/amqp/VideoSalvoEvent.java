package br.senai.sc.capiplayvideo.video.amqp;

public record VideoSalvoEvent(
        String id,
        Boolean ehInativado
) { }

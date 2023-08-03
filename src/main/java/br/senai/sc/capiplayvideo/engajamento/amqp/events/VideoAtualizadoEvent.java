package br.senai.sc.capiplayvideo.engajamento.amqp.events;

public record VideoAtualizadoEvent(String uuid, Long curtidas, Long visualizacoes) {
}

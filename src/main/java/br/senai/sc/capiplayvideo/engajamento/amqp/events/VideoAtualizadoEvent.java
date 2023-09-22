package br.senai.sc.capiplayvideo.engajamento.amqp.events;

public record VideoAtualizadoEvent(
        String id,
        Long qtdCurtidas,
        Long qtdComentarios,
        Double pontuacao
        ) {
}

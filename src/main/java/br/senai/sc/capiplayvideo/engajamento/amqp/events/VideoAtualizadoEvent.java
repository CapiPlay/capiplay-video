package br.senai.sc.capiplayvideo.engajamento.amqp.events;

public record VideoAtualizadoEvent(
        String id,
        Long qtdCurtidas,
        Double pontuacao,
        Long qtdComentarios
) {
}

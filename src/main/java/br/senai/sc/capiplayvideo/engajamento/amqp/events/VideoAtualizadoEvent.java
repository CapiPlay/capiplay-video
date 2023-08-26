package br.senai.sc.capiplayvideo.engajamento.amqp.events;

public record VideoAtualizadoEvent(
        String id,
        Long qtdCurtidas,
        Long qtdVisualizacoes,
        Double pontuacao,
        Long qtdComentarios
) {
}

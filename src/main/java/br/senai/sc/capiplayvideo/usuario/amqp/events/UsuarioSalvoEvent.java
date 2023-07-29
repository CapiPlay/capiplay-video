package br.senai.sc.capiplayvideo.usuario.amqp.events;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioSalvoEvent {

    private String id;

    private String nome;
}
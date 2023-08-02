package br.senai.sc.capiplayvideo.pesquisa.model.entity;

import br.senai.sc.capiplayvideo.usuario.model.entity.Usuario;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Pesquisa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String pesquisa;

    @ManyToOne // dar update no sql para criar a coluna usuario_id
    private Usuario usuario;

    // ver a logica disso aqui para retornar o historico
    private LocalDateTime dataInsercao = LocalDateTime.now();

    public Pesquisa(String pesquisa) {
        this.pesquisa = pesquisa;
    }

}

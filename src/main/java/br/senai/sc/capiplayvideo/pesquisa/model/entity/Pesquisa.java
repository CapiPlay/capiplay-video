package br.senai.sc.capiplayvideo.pesquisa.model.entity;

import br.senai.sc.capiplayvideo.usuario.model.entity.Usuario;
import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    @JsonIgnore
    private Usuario usuario;

    private LocalDateTime dataInsercao = LocalDateTime.now();

    public Pesquisa(String pesquisa) {
        this.pesquisa = pesquisa;
    }

    public Pesquisa(String string, String usuarioId) {
        this.pesquisa = string;
        this.usuario = new Usuario(usuarioId);
    }
}

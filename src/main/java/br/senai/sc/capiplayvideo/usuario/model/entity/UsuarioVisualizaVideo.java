package br.senai.sc.capiplayvideo.usuario.model.entity;

import br.senai.sc.capiplayvideo.video.model.entity.Video;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Formula;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Objects;

import static java.time.ZoneOffset.UTC;
import static java.time.ZonedDateTime.now;
import static java.util.Objects.requireNonNullElse;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioVisualizaVideo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long uuid;

    @ManyToOne
    private Usuario usuario;

    @ManyToOne
    private Video video;

    private Integer qtdVisualizacoes;

    private ZonedDateTime dataVisualizacao;

    public UsuarioVisualizaVideo(Usuario usuario, Video video) {
        this.usuario = usuario;
        this.video = video;
        this.setDataVisualizacao(now(UTC));
        this.qtdVisualizacoes = 1;
    }
}

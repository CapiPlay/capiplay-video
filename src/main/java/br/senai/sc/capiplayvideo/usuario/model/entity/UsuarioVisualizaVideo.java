package br.senai.sc.capiplayvideo.usuario.model.entity;

import br.senai.sc.capiplayvideo.video.model.entity.Video;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

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

    private LocalDateTime dataVisualizacao = LocalDateTime.now();

    public UsuarioVisualizaVideo(Usuario usuario, Video video) {
        this.usuario = usuario;
        this.video = video;
        this.qtdVisualizacoes = 1;
    }
}

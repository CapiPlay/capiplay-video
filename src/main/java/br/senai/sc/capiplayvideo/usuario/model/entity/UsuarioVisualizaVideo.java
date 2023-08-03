package br.senai.sc.capiplayvideo.usuario.model.entity;

import br.senai.sc.capiplayvideo.video.model.entity.Video;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
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
    private String uuid;

    @ManyToOne
    private Usuario usuario;

    @ManyToOne
    private Video video;

    private LocalDateTime dataVisualizacao = LocalDateTime.now();

    public UsuarioVisualizaVideo(Usuario usuario, Video video) {
        this.usuario = usuario;
        this.video = video;
    }
}

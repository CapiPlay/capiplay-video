package br.senai.sc.capiplayvideo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.net.URL;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@AllArgsConstructor
public class Video {
    @Id
    private UUID uuid;
    private String titulo;
    private String descricao;
    private Integer visualizacoes;
    private URL video;
    private URL thumbnail;
    @OneToMany
    private List<Tag> listaTags;


    public Video() {
        this.uuid = UUID.randomUUID();
    }
}

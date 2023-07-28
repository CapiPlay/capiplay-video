package br.senai.sc.capiplayvideo.video.model.entity;

import br.senai.sc.capiplayvideo.categoria.model.entity.Categoria;
import br.senai.sc.capiplayvideo.tag.model.entity.Tag;
import br.senai.sc.capiplayvideo.video.model.dto.VideoDTO;
import br.senai.sc.capiplayvideo.video.utils.GeradorUuidUtils;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@Entity
@AllArgsConstructor
public class Video {

    @Id
    private String uuid;

    @Size(min = 3, max = 100)
    private String titulo;

    private String descricao;

    private String caminho;

    @ManyToMany
    private List<Tag> tags;

    @ManyToOne(cascade = CascadeType.PERSIST)
    private Categoria categoria;

    private Boolean ehReels;

    public Video() {
        this.uuid = GeradorUuidUtils.gerarUuid();
    }

    public Video(String uuid, VideoDTO videoDTO, String caminho) {
        this.uuid = uuid;
        this.titulo = videoDTO.titulo();
        this.descricao = videoDTO.descricao();
        this.caminho = caminho;
        this.ehReels = videoDTO.ehReels();
        this.tags = Tag.converterLista(videoDTO.tags());
        this.categoria = new Categoria(videoDTO.categoria());
    }

}
package br.senai.sc.capiplayvideo.video.model.entity;

import br.senai.sc.capiplayvideo.categoria.model.entity.Categoria;
import br.senai.sc.capiplayvideo.tag.model.entity.Tag;
import br.senai.sc.capiplayvideo.usuario.model.entity.Usuario;
import br.senai.sc.capiplayvideo.video.model.dto.VideoDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Video {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String uuid;

    @Size(min = 3, max = 100)
    private String titulo;

    private String descricao;

    private String caminho;

    private Double pontuacao;

    private LocalDate publicacao;

    @ManyToMany
    private List<Tag> tags;

    private Long duracao;

    @ManyToOne(cascade = CascadeType.PERSIST)
    private Categoria categoria;

    private Boolean shorts;

    private Long curtidas;

    private Long visualizacoes;

    @ManyToOne
    private Usuario usuario;

    private Boolean inativo = false;

    private Boolean restrito;

    public Video(String uuid, VideoDTO videoDTO, String caminho, String usuarioId, Long duracao) {
        this.uuid = uuid;
        this.titulo = videoDTO.titulo();
        this.descricao = videoDTO.descricao();
        this.caminho = caminho;
        this.shorts = videoDTO.shorts();
        this.tags = Tag.converterLista(videoDTO.tags());
        this.categoria = new Categoria(videoDTO.categoria());
        this.duracao = duracao;
        this.publicacao = LocalDate.now();
        this.restrito = videoDTO.restrito();
        this.usuario = new Usuario(usuarioId);
    }

    public Video(String uuid){
        this.uuid = uuid;
    }

}
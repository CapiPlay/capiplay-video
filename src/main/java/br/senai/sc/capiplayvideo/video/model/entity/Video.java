package br.senai.sc.capiplayvideo.video.model.entity;

import br.senai.sc.capiplayvideo.categoria.model.entity.Categoria;
import br.senai.sc.capiplayvideo.engajamento.amqp.events.VideoAtualizadoEvent;
import br.senai.sc.capiplayvideo.tag.model.entity.Tag;
import br.senai.sc.capiplayvideo.usuario.model.entity.Usuario;
import br.senai.sc.capiplayvideo.video.model.dto.VideoDTO;
import br.senai.sc.capiplayvideo.video.utils.GeradorUuidUtils;
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

    private LocalDate dataPublicacao;

    @ManyToMany
    private List<Tag> tags;

    private Long duracao;

    @ManyToOne(cascade = CascadeType.PERSIST)
    private Categoria categoria;

    private Boolean ehReels;

    private Long curtidas;

    private Long visualizacoes;

    @ManyToOne
    private Usuario usuario;

    private Boolean ehAtivo = true;

    public Video(String uuid, VideoDTO videoDTO, String caminho) {
        this.uuid = uuid;
        this.titulo = videoDTO.titulo();
        this.descricao = videoDTO.descricao();
        this.caminho = caminho;
        this.ehReels = videoDTO.ehReels();
        this.tags = Tag.converterLista(videoDTO.tags());
        this.categoria = new Categoria(videoDTO.categoria());
        this.duracao = videoDTO.duracao();
        this.dataPublicacao = LocalDate.now();
        this.usuario = new Usuario(videoDTO.usuarioId());
    }

    public Video(String uuid){
        this.uuid = uuid;
    }

}
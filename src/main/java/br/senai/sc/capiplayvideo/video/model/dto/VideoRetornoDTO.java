package br.senai.sc.capiplayvideo.video.model.dto;

import br.senai.sc.capiplayvideo.tag.model.entity.Tag;
import br.senai.sc.capiplayvideo.video.model.entity.Video;
import br.senai.sc.capiplayvideo.video.utils.DiretorioUtils;
import lombok.Data;

import java.util.List;

@Data
public class VideoRetornoDTO {

    private String uuid;

    private String titulo;

    private String descricao;

    private List<String> caminhos;

    private List<Tag> tags;

    private Long qtdComentarios;

    private String categoria;

    private Long visualizacoes;

    private Long curtidas;

    public VideoRetornoDTO(Video video) {
        this.uuid = video.getUuid();
        this.titulo = video.getTitulo();
        this.descricao = video.getDescricao();
        this.caminhos = DiretorioUtils.gerarListaStringArquivos(video.getCaminho());
        this.tags = video.getTags();
        this.qtdComentarios = video.getQtdComentarios();
        this.categoria = video.getCategoria();
        this.visualizacoes = video.getVisualizacoes();
        this.curtidas = video.getCurtidas();
    }
}

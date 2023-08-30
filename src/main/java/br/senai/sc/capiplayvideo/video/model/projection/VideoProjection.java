package br.senai.sc.capiplayvideo.video.model.projection;

import br.senai.sc.capiplayvideo.tag.model.entity.Tag;
import br.senai.sc.capiplayvideo.video.utils.DiretorioUtils;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;

public interface VideoProjection {

    String getUuid();

    String getTitulo();

    String getDescricao();

    @JsonIgnore
    String getCaminho();

    default List<String> getCaminhos() {
        return DiretorioUtils.gerarListaStringArquivos(getCaminho());
    }

    List<Tag> getTags();

    Long getQtdComentarios();

    String getCategoria();

    Long getVisualizacoes();

    Long getCurtidas();

}

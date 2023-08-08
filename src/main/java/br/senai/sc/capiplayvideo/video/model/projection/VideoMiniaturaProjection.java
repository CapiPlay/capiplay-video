package br.senai.sc.capiplayvideo.video.model.projection;

import br.senai.sc.capiplayvideo.video.utils.DiretorioUtils;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;

public interface VideoMiniaturaProjection {

    String getUuid();

    String getTitulo();

    Boolean getEhReels();

    Long getDuracao();

    @JsonIgnore
    String getCaminho();

    default List<String> getCaminhos() {
        return DiretorioUtils.gerarListaStringArquivos(getCaminho());
    }

}

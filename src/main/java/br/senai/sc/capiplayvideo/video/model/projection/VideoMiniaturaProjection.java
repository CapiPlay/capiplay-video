package br.senai.sc.capiplayvideo.video.model.projection;

import br.senai.sc.capiplayvideo.video.utils.DiretorioUtils;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDate;
import java.util.List;

public interface VideoMiniaturaProjection {

    String getUuid();

    String getTitulo();

    Boolean getShorts();

    Long getDuracao();

    LocalDate getPublicacao();

    @JsonIgnore
    String getCaminho();

    default List<String> getCaminhos() {
        return DiretorioUtils.gerarListaStringArquivos(getCaminho());
    }

}

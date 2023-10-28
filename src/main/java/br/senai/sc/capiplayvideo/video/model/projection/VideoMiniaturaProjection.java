package br.senai.sc.capiplayvideo.video.model.projection;

import br.senai.sc.capiplayvideo.usuario.model.entity.UsuarioProjection;
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

    Long getVisualizacoes();

    Long getCurtidas();

    String getCategoria();

    @JsonIgnore
    String getCaminho();

    UsuarioProjection getUsuario();

    default List<String> getCaminhos() {
        return DiretorioUtils.gerarListaStringArquivos(getCaminho());
    }

}

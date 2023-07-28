package br.senai.sc.capiplayvideo.usuario.model.projection;

import br.senai.sc.capiplayvideo.pesquisa.model.projection.PesquisaProjection;

import java.util.List;

public interface UsuarioHistoricoProjection {

    List<PesquisaProjection> getHistorico();

}

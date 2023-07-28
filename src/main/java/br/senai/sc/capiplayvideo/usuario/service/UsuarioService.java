package br.senai.sc.capiplayvideo.usuario.service;

import br.senai.sc.capiplayvideo.exceptions.ObjetoInexistenteException;
import br.senai.sc.capiplayvideo.pesquisa.model.entity.Pesquisa;
import br.senai.sc.capiplayvideo.pesquisa.model.projection.PesquisaProjection;
import br.senai.sc.capiplayvideo.usuario.model.entity.Usuario;
import br.senai.sc.capiplayvideo.usuario.model.projection.UsuarioHistoricoProjection;
import br.senai.sc.capiplayvideo.usuario.repository.UsuarioRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UsuarioService {

    private UsuarioRepository repository;

    public void salvar(Usuario usuario) {
        repository.save(usuario);
    }

    public List<UsuarioHistoricoProjection> buscarHistorico(String uuid) {
        Sort sort = Sort.by(Sort.Direction.DESC, "historico.id");
        List<UsuarioHistoricoProjection> lista = repository.findAllByUuid(uuid, sort);
//        if (!lista.isEmpty() && lista.get(0).getHistorico() != null) {
//            List<PesquisaProjection> historicoInvertido = new ArrayList<>(lista.get(0).getHistorico());
//            Collections.reverse(historicoInvertido);
//
//            lista.get(0).getHistorico().clear();
//            lista.get(0).getHistorico().addAll(historicoInvertido);
//            System.out.println(lista); // TÁ UMA MERDA ESSA BOSTA AQUI
//        }
        return lista;
    }

    public Usuario buscarUm(String uuid) {
        return repository.findById(uuid).orElseThrow(ObjetoInexistenteException::new);
    }

}

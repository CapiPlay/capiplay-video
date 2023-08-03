package br.senai.sc.capiplayvideo.usuario.service;

import br.senai.sc.capiplayvideo.exceptions.ObjetoInexistenteException;
import br.senai.sc.capiplayvideo.pesquisa.model.entity.Pesquisa;
import br.senai.sc.capiplayvideo.pesquisa.repository.PesquisaRepository;
import br.senai.sc.capiplayvideo.pesquisa.service.PesquisaService;
import br.senai.sc.capiplayvideo.usuario.model.entity.Usuario;
import br.senai.sc.capiplayvideo.usuario.repository.UsuarioRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@AllArgsConstructor
public class UsuarioService {

    private UsuarioRepository repository;
    private PesquisaRepository pesquisaRepository;

    public void salvar(Usuario usuario) {
        repository.save(usuario);
    }

    public List<Pesquisa> buscarHistoricoPesquisa(String uuid) {
        return pesquisaRepository.findAllByUsuario_Uuid(uuid, Sort.by(Sort.Direction.DESC, "dataInsercao"));
    }

    public Usuario buscarUm(String uuid) {
        return repository.findById(uuid).orElseThrow(ObjetoInexistenteException::new);
    }

}

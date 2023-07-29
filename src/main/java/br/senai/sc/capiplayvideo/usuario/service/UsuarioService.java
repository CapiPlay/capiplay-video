package br.senai.sc.capiplayvideo.usuario.service;

import br.senai.sc.capiplayvideo.exceptions.ObjetoInexistenteException;
import br.senai.sc.capiplayvideo.pesquisa.model.entity.Pesquisa;
import br.senai.sc.capiplayvideo.usuario.model.entity.Usuario;
import br.senai.sc.capiplayvideo.usuario.repository.UsuarioRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@AllArgsConstructor
public class UsuarioService {

    private UsuarioRepository repository;

    public void salvar(Usuario usuario) {
        repository.save(usuario);
    }

    public List<Usuario> buscarHistorico(String uuid) {
        Optional<Usuario> optionalUsuario = repository.findById(uuid);
        if (optionalUsuario.isPresent()) {
            Usuario usuario = optionalUsuario.get();
            List<Pesquisa> historico = usuario.getHistorico();
            Collections.reverse(historico);
            repository.save(usuario);
            return Collections.singletonList(usuario);
        } else {
            return Collections.emptyList();
        }
    }

    public Usuario buscarUm(String uuid) {
        return repository.findById(uuid).orElseThrow(ObjetoInexistenteException::new);
    }

}

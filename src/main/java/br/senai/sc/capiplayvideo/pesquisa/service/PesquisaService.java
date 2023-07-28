package br.senai.sc.capiplayvideo.pesquisa.service;

import br.senai.sc.capiplayvideo.pesquisa.model.entity.Pesquisa;
import br.senai.sc.capiplayvideo.pesquisa.repository.PesquisaRepository;
import br.senai.sc.capiplayvideo.usuario.model.entity.Usuario;
import br.senai.sc.capiplayvideo.usuario.repository.UsuarioRepository;
import br.senai.sc.capiplayvideo.usuario.service.UsuarioService;
import br.senai.sc.capiplayvideo.video.model.projection.VideoMiniaturaProjection;
import br.senai.sc.capiplayvideo.video.repository.VideoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PesquisaService {

    private PesquisaRepository repository;
    private VideoRepository videoRepository;
    private UsuarioService usuarioService;

    public List<VideoMiniaturaProjection> buscarVideos(Pesquisa pesquisa, String uuid) {
        Usuario usuario = usuarioService.buscarUm(uuid);
        usuario.getHistorico().add(pesquisa);
        System.out.println(usuario);
        repository.save(pesquisa);
        usuarioService.salvar(usuario);
        return videoRepository.searchBy(pesquisa.getPesquisa());
    }

}

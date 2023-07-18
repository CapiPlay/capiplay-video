package br.senai.sc.capiplayvideo.pesquisa.service;

import br.senai.sc.capiplayvideo.pesquisa.repository.PesquisaRepository;
import br.senai.sc.capiplayvideo.video.model.projection.VideoMiniaturaProjection;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PesquisaService {

    private PesquisaRepository pesquisaRepository;

    public List<VideoMiniaturaProjection> buscarVideos(String pesquisa) {
        return pesquisaRepository.searchBy(pesquisa);
    }

}
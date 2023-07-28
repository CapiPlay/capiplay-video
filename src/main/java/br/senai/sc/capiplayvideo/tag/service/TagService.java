package br.senai.sc.capiplayvideo.tag.service;

import br.senai.sc.capiplayvideo.tag.model.entity.Tag;
import br.senai.sc.capiplayvideo.tag.repository.TagRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TagService {

    private TagRepository repository;

    public void salvar(Tag tag) {
        repository.save(tag);
    }

}

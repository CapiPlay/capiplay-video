package br.senai.sc.capiplayvideo.tag.service;

import br.senai.sc.capiplayvideo.tag.model.entity.Tag;
import br.senai.sc.capiplayvideo.tag.repository.TagRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TagService {

    private TagRepository repository;

    public void salvar(Tag tag) {
        repository.save(tag);
    }

    public List<Tag> buscarTodos() {
        return repository.findAll();
    }

}

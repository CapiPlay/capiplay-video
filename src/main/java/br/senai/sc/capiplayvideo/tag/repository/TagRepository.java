package br.senai.sc.capiplayvideo.tag.repository;

import br.senai.sc.capiplayvideo.tag.model.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository extends JpaRepository<Tag, String> {
}

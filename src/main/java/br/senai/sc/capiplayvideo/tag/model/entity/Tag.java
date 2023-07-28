package br.senai.sc.capiplayvideo.tag.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Entity
@Data
@NoArgsConstructor
public class Tag {

    @Id
    @Size(min = 3)
    private String tag;

    public Tag(String tag) {
        this.tag = tag;
    }

    public static List<Tag> converterLista(List<String> tags) {
        return tags.stream().map(Tag::new).collect(Collectors.toList());
    }

}
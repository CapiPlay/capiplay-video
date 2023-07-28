package br.senai.sc.capiplayvideo.categoria.model.entity;

import br.senai.sc.capiplayvideo.categoria.model.enums.CategoriasEnum;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private CategoriasEnum categoria;

    private String categoriaString;

    public Categoria(String categoria) {
        this.categoria = CategoriasEnum.valueOf(categoria);
        this.categoriaString = categoria;
    }

}
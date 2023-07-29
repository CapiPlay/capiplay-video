package br.senai.sc.capiplayvideo.usuario.model.entity;

import br.senai.sc.capiplayvideo.pesquisa.model.entity.Pesquisa;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String uuid;

    @OneToMany
    private List<Pesquisa> historico;

}
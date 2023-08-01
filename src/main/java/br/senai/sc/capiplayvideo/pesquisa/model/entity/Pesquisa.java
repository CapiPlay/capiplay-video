package br.senai.sc.capiplayvideo.pesquisa.model.entity;

import br.senai.sc.capiplayvideo.usuario.model.entity.Usuario;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Pesquisa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String pesquisa;

    @OneToOne // Tem que fazer isso ainda, tanto a lógica quanto a mudança no banco de dados
    private Usuario usuario;

    public Pesquisa(String pesquisa) {
        this.pesquisa = pesquisa;
    }

}

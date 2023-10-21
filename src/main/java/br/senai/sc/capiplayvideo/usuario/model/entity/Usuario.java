package br.senai.sc.capiplayvideo.usuario.model.entity;

import br.senai.sc.capiplayvideo.pesquisa.model.entity.Pesquisa;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Usuario {

    @Id
    private String uuid;

    @OneToMany(mappedBy = "usuario")
    @JsonIgnore
    private List<Pesquisa> historicoPesquisa;

    @OneToMany(mappedBy = "usuario")
    @JsonIgnore
    private List<UsuarioVisualizaVideo> historicoVideo;

    public Usuario(String uuid) {
        this.uuid = uuid;
    }

}
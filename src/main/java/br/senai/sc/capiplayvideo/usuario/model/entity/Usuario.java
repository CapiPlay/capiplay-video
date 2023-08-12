package br.senai.sc.capiplayvideo.usuario.model.entity;

import br.senai.sc.capiplayvideo.pesquisa.model.entity.Pesquisa;
import br.senai.sc.capiplayvideo.video.model.entity.Video;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Usuario {

    @Id
    private String uuid;

    @OneToMany(mappedBy = "usuario")
    private List<Pesquisa> historicoPesquisa;

    @OneToMany(mappedBy = "usuario")
    private List<UsuarioVisualizaVideo> historicoVideo;

    public Usuario(String uuid) {
        this.uuid = uuid;
    }

}
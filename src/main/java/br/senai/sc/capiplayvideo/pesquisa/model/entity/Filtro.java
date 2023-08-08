package br.senai.sc.capiplayvideo.pesquisa.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Filtro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Boolean filtroDia;

    @Column(nullable = false)
    private Boolean filtroSemana;

    @Column(nullable = false)
    private Boolean filtroMes;

    @Column(nullable = false)
    private Boolean filtroAno;

    @Column(nullable = false)
    private Boolean filtroMenosDe5Min;

    @Column(nullable = false)
    private Boolean filtroEntre5E20Min;

    @Column(nullable = false)
    private Boolean filtroMaisDe20Min;

    @Column(nullable = false)
    private Boolean filtroVideo;

    @Column(nullable = false)
    private Boolean filtroShorts;

}

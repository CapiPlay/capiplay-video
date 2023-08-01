package br.senai.sc.capiplayvideo.engajamento.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Engajamento{

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String uuid;

}
package br.senai.sc.capiplayvideo.pesquisa.model.dto;

import jakarta.validation.constraints.NotBlank;

public record FiltroDTO(@NotBlank(message = "Nenhum filtroDia encontrado") Boolean filtroDia,
                        @NotBlank(message = "Nenhum filtroSemana encontrado") Boolean filtroSemana,
                        @NotBlank(message = "Nenhum filtroMes encontrado") Boolean filtroMes,
                        @NotBlank(message = "Nenhum filtroAno encontrado") Boolean filtroAno,
                        @NotBlank(message = "Nenhum filtroMenosDe5Min encontrado") Boolean filtroMenosDe5Min,
                        @NotBlank(message = "Nenhum filtroEntre5E20Min encontrado") Boolean filtroEntre5E20Min,
                        @NotBlank(message = "Nenhum filtroMaisDe20Min encontrado") Boolean filtroMaisDe20Min,
                        @NotBlank(message = "Nenhum filtroVideo encontrado") Boolean filtroVideo,
                        @NotBlank(message = "Nenhum filtroShorts encontrado") Boolean filtroShorts) {
}

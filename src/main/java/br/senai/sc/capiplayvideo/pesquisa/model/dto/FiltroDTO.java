package br.senai.sc.capiplayvideo.pesquisa.model.dto;

public record FiltroDTO(Boolean filtroDia,
                        Boolean filtroSemana,
                        Boolean filtroMes,
                        Boolean filtroAno,
                        Boolean filtroMenosDe5Min,
                        Boolean filtroEntre5E20Min,
                        Boolean filtroMaisDe20Min,
                        Boolean filtroVideo,
                        Boolean filtroShorts) {
}

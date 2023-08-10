
package br.senai.sc.capiplayvideo.video.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public record VideoDTO(@NotBlank(message = "Nenhum titulo enviado") String titulo,
                       @NotBlank(message = "Nenhuma descrição enviada") String descricao,
                       @NotBlank(message = "Nenhuma tag enviada") List<String> tags,
                       @NotBlank(message = "Nenhuma categoria enviada") String categoria,
                       @NotNull(message = "Nenhum shorts enviado") Boolean shorts,
                       @NotNull(message = "Nenhum video enviado") MultipartFile video,
                       @NotNull(message = "Nenhuma miniatura enviada") MultipartFile miniatura,
                       @NotNull(message = "Nenhum restrito enviado") Boolean restrito,
                       @NotBlank(message = "Nenhuma usuarioId enviado") String usuarioId) {
}

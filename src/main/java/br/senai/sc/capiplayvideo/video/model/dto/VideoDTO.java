
package br.senai.sc.capiplayvideo.video.model.dto;

import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

public record VideoDTO(String titulo,
                       String descricao,
                       List<String> tags,
                       String categoria,
                       Boolean ehReels,
                       MultipartFile video,
                       MultipartFile miniatura,
                       LocalDate dataPublicacao,
                       Long duracao,
                       String usuarioId) {
}

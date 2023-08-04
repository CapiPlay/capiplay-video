package br.senai.sc.capiplayvideo.usuario.repository;

import br.senai.sc.capiplayvideo.usuario.model.entity.UsuarioVisualizaVideo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioVisualizaVideoRepository extends JpaRepository<UsuarioVisualizaVideo, Long> {
}

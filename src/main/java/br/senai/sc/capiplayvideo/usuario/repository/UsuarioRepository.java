package br.senai.sc.capiplayvideo.usuario.repository;

import br.senai.sc.capiplayvideo.usuario.model.entity.Usuario;
import br.senai.sc.capiplayvideo.usuario.model.projection.UsuarioHistoricoProjection;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, String> {

    List<UsuarioHistoricoProjection> findAllByUuid(String uuid, Sort sort);

}
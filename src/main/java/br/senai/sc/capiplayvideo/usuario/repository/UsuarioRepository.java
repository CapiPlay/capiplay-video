package br.senai.sc.capiplayvideo.usuario.repository;

import br.senai.sc.capiplayvideo.usuario.model.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, String> {

}
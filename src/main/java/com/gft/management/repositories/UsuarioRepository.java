package com.gft.management.repositories;

import com.gft.management.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Usuario findByUsername(String email);

    Optional<Usuario> getOptionalUsuarioByUsername(String email);
}

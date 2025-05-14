package com.FinZenBack.ws.FinZenBack.repository;

import com.FinZenBack.ws.FinZenBack.models.Entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario,Long> {
    @Query("SELECT u FROM Usuario u WHERE u.numeroDocumento = :numeroDocumento")
    Optional<Usuario> findByNumeroDocumento(long numeroDocumento);

    @Query("SELECT u FROM Usuario u WHERE u.correo = :correo")
    Optional<Usuario> findByCorreo( String correo);

    @Query("SELECT u FROM Usuario u WHERE u.correo = :correo")
    boolean existsByCorreo( String correo);

    @Query("SELECT u FROM Usuario u WHERE u.nombreUsuario = :nombreUsuario")
    boolean existsByNombreUsuario( String nombreUsuario);
}

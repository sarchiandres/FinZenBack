package com.FinZenBack.ws.FinZenBack.repository;

import com.FinZenBack.ws.FinZenBack.models.Entities.Cuenta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CuentaRepository extends JpaRepository<Cuenta, Long> {
    @Query("SELECT c FROM Cuenta c WHERE c.usuario.idUsuario = :idUsuario")
    List<Cuenta> findByUsuario_IdUsuario(@Param("idUsuario") Long idUsuario);

    @Query("SELECT COUNT(c) > 0 FROM Cuenta c WHERE c.nombre = :nombre AND c.usuario.idUsuario = :idUsuario")
    boolean existsByNombreAndUsuarioId(@Param("nombre") String nombre, @Param("idUsuario") Long idUsuario);

    Optional<Cuenta> findByIdAndUsuarioCorreo(Long id, String correo);
}

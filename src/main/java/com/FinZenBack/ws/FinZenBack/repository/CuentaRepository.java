package com.FinZenBack.ws.FinZenBack.repository;

import com.FinZenBack.ws.FinZenBack.models.Entities.Cuenta;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface CuentaRepository extends JpaRepository<Cuenta, Long> {
    Optional<Cuenta> findByIdCuentaAndUsuarioCorreo(Long idCuenta, String correo);
    boolean existsByNombreAndUsuarioIdUsuario(String nombre, Long idUsuario);
    List<Cuenta> findByUsuarioIdUsuario(Long idUsuario);
}
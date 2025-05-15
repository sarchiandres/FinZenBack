package com.FinZenBack.ws.FinZenBack.repository;

import com.FinZenBack.ws.FinZenBack.models.Entities.Soporte;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SoporteRepository extends JpaRepository<Soporte, Long> {
    List<Soporte> findByUsuarioCorreo(String correo);
}
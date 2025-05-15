package com.FinZenBack.ws.FinZenBack.repository;

import com.FinZenBack.ws.FinZenBack.models.Entities.Informe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InformeRepository extends JpaRepository<Informe, Long> {
    List<Informe> findByUsuarioIdUsuario(Long idUsuario);
}
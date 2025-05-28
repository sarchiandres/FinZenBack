package com.FinZenBack.ws.FinZenBack.repository;

import com.FinZenBack.ws.FinZenBack.models.Entities.Presupuesto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PresupuestoRepository extends JpaRepository<Presupuesto, Long> {
    List<Presupuesto> findByCuentaIdCuenta(Long idCuenta);

    @Query("SELECT COUNT(p) > 0 FROM Presupuesto p WHERE p.nombre = :nombre AND p.cuenta.idCuenta = :idCuenta")
    boolean existsByNombreAndCuentaId(@Param("nombre") String nombre, @Param("idCuenta") Long idCuenta);
}
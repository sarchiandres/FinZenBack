package com.FinZenBack.ws.FinZenBack.Services;

import com.FinZenBack.ws.FinZenBack.models.DTO.IngresoDto;
import com.FinZenBack.ws.FinZenBack.models.Entities.Ingreso;
import com.FinZenBack.ws.FinZenBack.models.Entities.Presupuesto;
import com.FinZenBack.ws.FinZenBack.repository.IngresoRepository;
import com.FinZenBack.ws.FinZenBack.repository.PresupuestoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IngresoServices {

    private final IngresoRepository ingresoRepository;
    private final PresupuestoRepository presupuestoRepository;

    public IngresoServices(IngresoRepository ingresoRepository, PresupuestoRepository presupuestoRepository) {
        this.ingresoRepository = ingresoRepository;
        this.presupuestoRepository = presupuestoRepository;
    }

    public Ingreso createIngreso(IngresoDto dto) {
        Presupuesto presupuesto = presupuestoRepository.findById(dto.getIdPresupuesto())
                .orElseThrow(() -> new RuntimeException("Presupuesto no encontrado"));

        Ingreso ingreso = new Ingreso();
        ingreso.setPresupuesto(presupuesto);
        ingreso.setNombre(dto.getNombre());
        ingreso.setMonto(dto.getMonto());
        ingreso.setFecha(dto.getFecha());
        ingreso.setFuente(dto.getFuente());

        return ingresoRepository.save(ingreso);
    }

    public List<Ingreso> getIngresosByPresupuesto(Long idPresupuesto) {
        return ingresoRepository.findByPresupuestoIdPresupuesto(idPresupuesto);
    }

    public void deleteIngreso(Long idIngreso) {
        ingresoRepository.deleteById(idIngreso);
    }
}

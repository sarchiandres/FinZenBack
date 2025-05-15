package com.FinZenBack.ws.FinZenBack.Services;

import com.FinZenBack.ws.FinZenBack.models.DTO.IngresoDto;
import com.FinZenBack.ws.FinZenBack.models.Entities.Cuenta;
import com.FinZenBack.ws.FinZenBack.models.Entities.Ingreso;
import com.FinZenBack.ws.FinZenBack.models.Entities.Presupuesto;
import com.FinZenBack.ws.FinZenBack.repository.CuentaRepository;
import com.FinZenBack.ws.FinZenBack.repository.IngresoRepository;
import com.FinZenBack.ws.FinZenBack.repository.PresupuestoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class IngresoServices {
    private final IngresoRepository ingresoRepository;
    private final PresupuestoRepository presupuestoRepository;
    private final CuentaRepository cuentaRepository;

    public IngresoServices(IngresoRepository ingresoRepository, PresupuestoRepository presupuestoRepository,
                           CuentaRepository cuentaRepository) {
        this.ingresoRepository = ingresoRepository;
        this.presupuestoRepository = presupuestoRepository;
        this.cuentaRepository = cuentaRepository;
    }

    @Transactional
    public Ingreso createIngreso(IngresoDto dto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String correoUsuario = authentication.getName();

        Presupuesto presupuesto = presupuestoRepository.findById(dto.getIdPresupuesto())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Presupuesto no encontrado"));

        Cuenta cuenta = presupuesto.getCuenta();
        if (!cuentaRepository.findByIdCuentaAndUsuarioCorreo(cuenta.getIdCuenta(), correoUsuario).isPresent() &&
                !authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No tienes permiso para crear un ingreso en este presupuesto");
        }

        Ingreso ingreso = new Ingreso();
        ingreso.setPresupuesto(presupuesto);
        ingreso.setNombre(dto.getNombre());
        ingreso.setMonto(dto.getMonto());
        ingreso.setFecha(dto.getFecha());
        ingreso.setFuente(dto.getFuente());

        return ingresoRepository.save(ingreso);
    }

    @Transactional(readOnly = true)
    public List<Ingreso> getIngresosByPresupuesto(Long idPresupuesto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String correoUsuario = authentication.getName();

        Presupuesto presupuesto = presupuestoRepository.findById(idPresupuesto)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Presupuesto no encontrado"));

        Cuenta cuenta = presupuesto.getCuenta();
        if (!cuentaRepository.findByIdCuentaAndUsuarioCorreo(cuenta.getIdCuenta(), correoUsuario).isPresent() &&
                !authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No tienes permiso para ver los ingresos de este presupuesto");
        }

        return ingresoRepository.findByPresupuestoIdPresupuesto(idPresupuesto);
    }

    @Transactional
    public void deleteIngreso(Long idIngreso) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String correoUsuario = authentication.getName();

        Ingreso ingreso = ingresoRepository.findById(idIngreso)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Ingreso no encontrado"));

        Cuenta cuenta = ingreso.getPresupuesto().getCuenta();
        if (!cuentaRepository.findByIdCuentaAndUsuarioCorreo(cuenta.getIdCuenta(), correoUsuario).isPresent() &&
                !authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No tienes permiso para eliminar este ingreso");
        }

        ingresoRepository.deleteById(idIngreso);
    }
}
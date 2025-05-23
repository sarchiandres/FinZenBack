package com.FinZenBack.ws.FinZenBack.Services;

import com.FinZenBack.ws.FinZenBack.models.DTO.PresupuestoDto;
import com.FinZenBack.ws.FinZenBack.models.Entities.CategoriaPresupuesto;
import com.FinZenBack.ws.FinZenBack.models.Entities.Cuenta;
import com.FinZenBack.ws.FinZenBack.models.Entities.Presupuesto;
import com.FinZenBack.ws.FinZenBack.repository.CategoriaPresupuestoRepository;
import com.FinZenBack.ws.FinZenBack.repository.CuentaRepository;
import com.FinZenBack.ws.FinZenBack.repository.PresupuestoRepository;
import org.hibernate.Hibernate;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.List;

@Service
public class PresupuestoServices {
    private final PresupuestoRepository presupuestoRepository;
    private final CuentaRepository cuentaRepository;
    private final CategoriaPresupuestoRepository categoriaRepository;

    public PresupuestoServices(PresupuestoRepository presupuestoRepository, CuentaRepository cuentaRepository, CategoriaPresupuestoRepository categoriaRepository) {
        this.presupuestoRepository = presupuestoRepository;
        this.cuentaRepository = cuentaRepository;
        this.categoriaRepository = categoriaRepository;
    }

    @Transactional
    public Presupuesto createPresupuesto(PresupuestoDto presupuestoDto) {
        Cuenta cuenta = cuentaRepository.findById(presupuestoDto.getIdCuenta())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "La cuenta con ID " + presupuestoDto.getIdCuenta() + " no se encontró"));

        // Verificar permisos
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String correoAutenticado = authentication.getName();
        if (!correoAutenticado.equals(cuenta.getUsuario().getCorreo()) && !authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No tienes permiso para crear un presupuesto para esta cuenta");
        }

        // Validar unicidad
        if (presupuestoRepository.existsByNombreAndCuentaId(presupuestoDto.getNombre(), presupuestoDto.getIdCuenta())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Ya existe un presupuesto con el nombre " + presupuestoDto.getNombre() + " para esta cuenta");
        }

        // Validar monto libre
        if (cuenta.getMontoLibre().compareTo(presupuestoDto.getMontoAsignado()) < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El monto asignado excede el monto libre de la cuenta");
        }

        CategoriaPresupuesto categoria = null;
        if (presupuestoDto.getIdCategoria() != null) {
            categoria = categoriaRepository.findById(presupuestoDto.getIdCategoria())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "La categoría con ID " + presupuestoDto.getIdCategoria() + " no se encontró"));
        }

        Presupuesto miPresupuesto = new Presupuesto();
        miPresupuesto.setCuenta(cuenta);
        miPresupuesto.setNombre(presupuestoDto.getNombre());
        miPresupuesto.setMontoAsignado(presupuestoDto.getMontoAsignado());
        miPresupuesto.setCategoria(categoria);

        return presupuestoRepository.save(miPresupuesto);
    }

    @Transactional
    public Presupuesto updatePresupuesto(Long idPresupuesto, PresupuestoDto presupuestoDto) {
        Presupuesto miPresupuesto = presupuestoRepository.findById(idPresupuesto)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "El presupuesto con ID " + idPresupuesto + " no se encontró"));

        // Verificar permisos
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String correoAutenticado = authentication.getName();
        if (!correoAutenticado.equals(miPresupuesto.getCuenta().getUsuario().getCorreo()) && !authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No tienes permiso para modificar este presupuesto");
        }

        // Validar unicidad
        if (!miPresupuesto.getNombre().equals(presupuestoDto.getNombre()) &&
                presupuestoRepository.existsByNombreAndCuentaId(presupuestoDto.getNombre(), presupuestoDto.getIdCuenta())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Ya existe un presupuesto con el nombre " + presupuestoDto.getNombre() + " para esta cuenta");
        }

        // Validar monto libre
        Cuenta cuenta = cuentaRepository.findById(presupuestoDto.getIdCuenta())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "La cuenta con ID " + presupuestoDto.getIdCuenta() + " no se encontró"));
        BigDecimal montoLibreAjustado = cuenta.getMontoLibre().add(miPresupuesto.getMontoAsignado());
        if (montoLibreAjustado.compareTo(presupuestoDto.getMontoAsignado()) < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El nuevo monto asignado excede el monto libre de la cuenta");
        }

        CategoriaPresupuesto categoria = null;
        if (presupuestoDto.getIdCategoria() != null) {
            categoria = categoriaRepository.findById(presupuestoDto.getIdCategoria())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "La categoría con ID " + presupuestoDto.getIdCategoria() + " no se encontró"));
        }

        miPresupuesto.setCategoria(categoria);
        miPresupuesto.setCuenta(cuenta);
        miPresupuesto.setNombre(presupuestoDto.getNombre());
        miPresupuesto.setMontoAsignado(presupuestoDto.getMontoAsignado());

        return presupuestoRepository.save(miPresupuesto);
    }

    @Transactional(readOnly = true)
    public List<Presupuesto> getPresupuestos(Long idCuenta) {
        Cuenta cuenta = cuentaRepository.findById(idCuenta)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "La cuenta con ID " + idCuenta + " no se encontró"));

        // Verificar permisos
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String correoAutenticado = authentication.getName();
        if (!correoAutenticado.equals(cuenta.getUsuario().getCorreo()) && !authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No tienes permiso para ver los presupuestos de esta cuenta");
        }

        List<Presupuesto> presupuestos = presupuestoRepository.findByCuentaIdCuenta(idCuenta);
        presupuestos.forEach(p -> {
            if (p.getCategoria() != null) {
                Hibernate.initialize(p.getCategoria().getPresupuestos());
            }
        });
        return presupuestos;
    }

    @Transactional
    public void deletePresupuesto(Long idPresupuesto) {
        Presupuesto presupuesto = presupuestoRepository.findById(idPresupuesto)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "El presupuesto con ID " + idPresupuesto + " no se encontró"));

        // Verificar permisos
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String correoAutenticado = authentication.getName();
        if (!correoAutenticado.equals(presupuesto.getCuenta().getUsuario().getCorreo()) && !authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No tienes permiso para eliminar este presupuesto");
        }

        presupuestoRepository.deleteById(idPresupuesto);
    }
}
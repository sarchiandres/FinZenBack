package com.FinZenBack.ws.FinZenBack.Services;

import com.FinZenBack.ws.FinZenBack.models.DTO.GastoDto;
import com.FinZenBack.ws.FinZenBack.models.Entities.CategoriaGasto;
import com.FinZenBack.ws.FinZenBack.models.Entities.Cuenta;
import com.FinZenBack.ws.FinZenBack.models.Entities.Gasto;
import com.FinZenBack.ws.FinZenBack.models.Entities.Presupuesto;
import com.FinZenBack.ws.FinZenBack.repository.CategoriaGastoRepository;
import com.FinZenBack.ws.FinZenBack.repository.CuentaRepository;
import com.FinZenBack.ws.FinZenBack.repository.GastoRepository;
import com.FinZenBack.ws.FinZenBack.repository.PresupuestoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class GastoServices {
    private final GastoRepository gastoRepository;
    private final PresupuestoRepository presupuestoRepository;
    private final CategoriaGastoRepository categoriaGastoRepository;
    private final CuentaRepository cuentaRepository;

    public GastoServices(GastoRepository gastoRepository, PresupuestoRepository presupuestoRepository,
                         CategoriaGastoRepository categoriaGastoRepository, CuentaRepository cuentaRepository) {
        this.gastoRepository = gastoRepository;
        this.presupuestoRepository = presupuestoRepository;
        this.categoriaGastoRepository = categoriaGastoRepository;
        this.cuentaRepository = cuentaRepository;
    }

    @Transactional
    public Gasto createGasto(GastoDto gastoDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String correoUsuario = authentication.getName();

        Presupuesto presupuesto = null;
        if (gastoDto.getIdPresupuesto() != null) {
            presupuesto = presupuestoRepository.findById(gastoDto.getIdPresupuesto())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "El presupuesto con ID " + gastoDto.getIdPresupuesto() + " no se encontró"));

            Cuenta cuenta = presupuesto.getCuenta();
            if (!cuentaRepository.findByIdAndUsuarioCorreo(cuenta.getIdCuenta(), correoUsuario).isPresent() &&
                    !authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No tienes permiso para crear un gasto en este presupuesto");
            }

            if (gastoDto.getMonto().compareTo(presupuesto.getMontoAsignado()) > 0) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El monto del gasto excede el monto asignado al presupuesto");
            }
        }

        CategoriaGasto categoria = null;
        if (gastoDto.getIdCategoria() != null) {
            categoria = categoriaGastoRepository.findById(gastoDto.getIdCategoria())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "La categoría con ID " + gastoDto.getIdCategoria() + " no se encontró"));
        }

        Gasto gasto = new Gasto();
        gasto.setMonto(gastoDto.getMonto());
        gasto.setPresupuesto(presupuesto);
        gasto.setCategoria(categoria);
        gasto.setFecha(gastoDto.getFecha());
        gasto.setDescripcion(gastoDto.getDescripcion());

        return gastoRepository.save(gasto);
    }

    @Transactional(readOnly = true)
    public List<Gasto> getGasto(Long idPresupuesto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String correoUsuario = authentication.getName();

        Presupuesto presupuesto = presupuestoRepository.findById(idPresupuesto)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "El presupuesto con ID " + idPresupuesto + " no se encontró"));

        Cuenta cuenta = presupuesto.getCuenta();
        if (!cuentaRepository.findByIdAndUsuarioCorreo(cuenta.getIdCuenta(), correoUsuario).isPresent() &&
                !authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No tienes permiso para ver los gastos de este presupuesto");
        }

        return gastoRepository.findByPresupuestoIdPresupuesto(idPresupuesto);
    }

    @Transactional
    public Gasto updateGasto(Long id, GastoDto gastoDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String correoUsuario = authentication.getName();

        Gasto gasto = gastoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "El gasto con ID " + id + " no se encontró"));

        Presupuesto presupuestoActual = gasto.getPresupuesto();
        if (presupuestoActual != null) {
            Cuenta cuenta = presupuestoActual.getCuenta();
            if (!cuentaRepository.findByIdAndUsuarioCorreo(cuenta.getIdCuenta(), correoUsuario).isPresent() &&
                    !authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No tienes permiso para modificar este gasto");
            }
        }

        Presupuesto presupuesto = null;
        if (gastoDto.getIdPresupuesto() != null) {
            presupuesto = presupuestoRepository.findById(gastoDto.getIdPresupuesto())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "El presupuesto con ID " + gastoDto.getIdPresupuesto() + " no se encontró"));

            Cuenta cuenta = presupuesto.getCuenta();
            if (!cuentaRepository.findByIdAndUsuarioCorreo(cuenta.getIdCuenta(), correoUsuario).isPresent() &&
                    !authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No tienes permiso para asignar este presupuesto");
            }

            if (gastoDto.getMonto().compareTo(presupuesto.getMontoAsignado()) > 0) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El monto del gasto excede el monto asignado al presupuesto");
            }
        }

        CategoriaGasto categoria = null;
        if (gastoDto.getIdCategoria() != null) {
            categoria = categoriaGastoRepository.findById(gastoDto.getIdCategoria())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "La categoría con ID " + gastoDto.getIdCategoria() + " no se encontró"));
        }

        gasto.setMonto(gastoDto.getMonto());
        gasto.setPresupuesto(presupuesto);
        gasto.setCategoria(categoria);
        gasto.setFecha(gastoDto.getFecha());
        gasto.setDescripcion(gastoDto.getDescripcion());

        return gastoRepository.save(gasto);
    }

    @Transactional
    public void deleteGasto(Long idGasto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String correoUsuario = authentication.getName();

        Gasto gasto = gastoRepository.findById(idGasto)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "El gasto con ID " + idGasto + " no se encontró"));

        Presupuesto presupuesto = gasto.getPresupuesto();
        if (presupuesto != null) {
            Cuenta cuenta = presupuesto.getCuenta();
            if (!cuentaRepository.findByIdAndUsuarioCorreo(cuenta.getIdCuenta(), correoUsuario).isPresent() &&
                    !authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No tienes permiso para eliminar este gasto");
            }
        }

        gastoRepository.deleteById(idGasto);
    }
}
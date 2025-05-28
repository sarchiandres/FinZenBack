package com.FinZenBack.ws.FinZenBack.Services;

import com.FinZenBack.ws.FinZenBack.models.DTO.DeudaDto;
import com.FinZenBack.ws.FinZenBack.models.Entities.Cuenta;
import com.FinZenBack.ws.FinZenBack.models.Entities.Deuda;
import com.FinZenBack.ws.FinZenBack.repository.CuentaRepository;
import com.FinZenBack.ws.FinZenBack.repository.DeudaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class DeudaServices {
    private final DeudaRepository deudaRepository;
    private final CuentaRepository cuentaRepository;

    public DeudaServices(DeudaRepository deudaRepository, CuentaRepository cuentaRepository) {
        this.deudaRepository = deudaRepository;
        this.cuentaRepository = cuentaRepository;
    }

    @Transactional
    public Deuda createDeuda(DeudaDto deudaDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String correoUsuario = authentication.getName();

        Cuenta cuenta = cuentaRepository.findById(deudaDto.getIdCuenta())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cuenta no encontrada"));

        if (!cuentaRepository.findByIdCuentaAndUsuarioCorreo(cuenta.getIdCuenta(), correoUsuario).isPresent() &&
                !authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No tienes permiso para crear una deuda en esta cuenta");
        }

        if (deudaDto.getMonto().compareTo(deudaDto.getMontoPagado()) < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El monto pagado no puede ser mayor al monto de la deuda");
        }

        Deuda deuda = new Deuda();
        deuda.setCuenta(cuenta);
        deuda.setMonto(deudaDto.getMonto());
        deuda.setMontoPagado(deudaDto.getMontoPagado());
        deuda.setFechaVencimiento(deudaDto.getFechaVencimiento());
        deuda.setEstado(deudaDto.getEstado());

        return deudaRepository.save(deuda);
    }

    @Transactional(readOnly = true)
    public List<Deuda> getDeudas(Long idCuenta) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String correoUsuario = authentication.getName();

        Cuenta cuenta = cuentaRepository.findById(idCuenta)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cuenta no encontrada"));

        if (!cuentaRepository.findByIdCuentaAndUsuarioCorreo(cuenta.getIdCuenta(), correoUsuario).isPresent() &&
                !authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No tienes permiso para ver las deudas de esta cuenta");
        }

        return deudaRepository.findByCuentaIdCuenta(idCuenta);
    }

    @Transactional
    public Deuda updateDeuda(Long idDeuda, DeudaDto deudaDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String correoUsuario = authentication.getName();

        Deuda deuda = deudaRepository.findById(idDeuda)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Deuda no encontrada"));

        Cuenta cuentaActual = deuda.getCuenta();
        if (!cuentaRepository.findByIdCuentaAndUsuarioCorreo(cuentaActual.getIdCuenta(), correoUsuario).isPresent() &&
                !authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No tienes permiso para modificar esta deuda");
        }

        Cuenta cuenta = cuentaRepository.findById(deudaDto.getIdCuenta())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cuenta no encontrada"));

        if (!cuentaRepository.findByIdCuentaAndUsuarioCorreo(cuenta.getIdCuenta(), correoUsuario).isPresent() &&
                !authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No tienes permiso para asignar esta cuenta");
        }

        if (deudaDto.getMonto().compareTo(deudaDto.getMontoPagado()) < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El monto pagado no puede ser mayor al monto de la deuda");
        }

        deuda.setCuenta(cuenta);
        deuda.setMonto(deudaDto.getMonto());
        deuda.setMontoPagado(deudaDto.getMontoPagado());
        deuda.setFechaVencimiento(deudaDto.getFechaVencimiento());
        deuda.setEstado(deudaDto.getEstado());

        return deudaRepository.save(deuda);
    }

    @Transactional
    public void deleteDeuda(Long idDeuda) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String correoUsuario = authentication.getName();

        Deuda deuda = deudaRepository.findById(idDeuda)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Deuda no encontrada"));

        Cuenta cuenta = deuda.getCuenta();
        if (!cuentaRepository.findByIdCuentaAndUsuarioCorreo(cuenta.getIdCuenta(), correoUsuario).isPresent() &&
                !authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No tienes permiso para eliminar esta deuda");
        }

        deudaRepository.deleteById(idDeuda);
    }
}
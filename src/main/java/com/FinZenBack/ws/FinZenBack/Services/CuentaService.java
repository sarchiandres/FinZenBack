package com.FinZenBack.ws.FinZenBack.Services;

import com.FinZenBack.ws.FinZenBack.models.DTO.CuentaDto;
import com.FinZenBack.ws.FinZenBack.models.Entities.Cuenta;
import com.FinZenBack.ws.FinZenBack.models.Entities.Usuario;
import com.FinZenBack.ws.FinZenBack.repository.CuentaRepository;
import com.FinZenBack.ws.FinZenBack.repository.UsuarioRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class CuentaService {

    private final CuentaRepository cuentaRepository;
    private final UsuarioRepository usuarioRepository;

    public CuentaService(CuentaRepository cuentaRepository, UsuarioRepository usuarioRepository) {
        this.cuentaRepository = cuentaRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @Transactional
    public Cuenta createCuenta(CuentaDto cuentaDto) {
        // Validar idUsuario
        if (cuentaDto.getIdUsuario() == null) {
            throw new IllegalArgumentException("ID de usuario no proporcionado");
        }

        // Validar unicidad del nombre de la cuenta para el usuario
        if (cuentaRepository.existsByNombreAndUsuarioId(cuentaDto.getNombre(), cuentaDto.getIdUsuario())) {
            throw new IllegalArgumentException("Ya existe una cuenta con el nombre " + cuentaDto.getNombre() + " para este usuario");
        }

        // Validar monedaPredeterminada (puedes añadir más códigos según necesidad)
        if (!List.of("USD", "COP", "EUR").contains(cuentaDto.getMonedaPredeterminada())) {
            throw new IllegalArgumentException("Moneda predeterminada inválida: " + cuentaDto.getMonedaPredeterminada());
        }

        // Obtener usuario
        Usuario usuario = usuarioRepository.findById(cuentaDto.getIdUsuario())
                .orElseThrow(() -> new IllegalArgumentException("Usuario con ID " + cuentaDto.getIdUsuario() + " no encontrado"));

        // Verificar permisos
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String correoAutenticado = authentication.getName();
        if (!correoAutenticado.equals(usuario.getCorreo()) && !authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            throw new SecurityException("No tienes permiso para crear una cuenta para este usuario");
        }

        Cuenta cuenta = new Cuenta();
        cuenta.setNombre(cuentaDto.getNombre());
        cuenta.setMonedaPredeterminada(cuentaDto.getMonedaPredeterminada());
        cuenta.setMonto(cuentaDto.getMonto());
        cuenta.setMontoOcupado(BigDecimal.ZERO);
        cuenta.setMontoLibre(cuentaDto.getMonto()); // Inicialmente, montoLibre = monto
        cuenta.setUsuario(usuario);

        return cuentaRepository.save(cuenta);
    }

    @Transactional(readOnly = true)
    public List<Cuenta> getCuentasByUsuario(Long idUsuario) {
        // Validar usuario
        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new IllegalArgumentException("Usuario con ID " + idUsuario + " no encontrado"));

        // Verificar permisos
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String correoAutenticado = authentication.getName();
        if (!correoAutenticado.equals(usuario.getCorreo()) && !authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            throw new SecurityException("No tienes permiso para ver las cuentas de este usuario");
        }

        return cuentaRepository.findByUsuario_IdUsuario(idUsuario);
    }

    @Transactional
    public Cuenta updateCuenta(Long idCuenta, CuentaDto cuentaDto) {
        Cuenta cuenta = cuentaRepository.findById(idCuenta)
                .orElseThrow(() -> new IllegalArgumentException("Cuenta con ID " + idCuenta + " no encontrada"));

        // Verificar permisos
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String correoAutenticado = authentication.getName();
        if (!correoAutenticado.equals(cuenta.getUsuario().getCorreo()) && !authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            throw new SecurityException("No tienes permiso para modificar esta cuenta");
        }

        // Validar unicidad del nombre (si cambia)
        if (!cuenta.getNombre().equals(cuentaDto.getNombre()) &&
                cuentaRepository.existsByNombreAndUsuarioId(cuentaDto.getNombre(), cuenta.getUsuario().getIdUsuario())) {
            throw new IllegalArgumentException("Ya existe una cuenta con el nombre " + cuentaDto.getNombre() + " para este usuario");
        }

        // Validar monedaPredeterminada
        if (!List.of("USD", "COP", "EUR").contains(cuentaDto.getMonedaPredeterminada())) {
            throw new IllegalArgumentException("Moneda predeterminada inválida: " + cuentaDto.getMonedaPredeterminada());
        }

        // Actualizar campos
        cuenta.setNombre(cuentaDto.getNombre());
        cuenta.setMonedaPredeterminada(cuentaDto.getMonedaPredeterminada());
        if (cuentaDto.getMonto() != null) {
            cuenta.setMonto(cuentaDto.getMonto());
            // Recalcular montoLibre (monto - montoOcupado)
            cuenta.setMontoLibre(cuentaDto.getMonto().subtract(cuenta.getMontoOcupado()));
        }

        return cuentaRepository.save(cuenta);
    }

    @Transactional
    public void deleteCuenta(Long idCuenta) {
        Cuenta cuenta = cuentaRepository.findById(idCuenta)
                .orElseThrow(() -> new IllegalArgumentException("Cuenta con ID " + idCuenta + " no encontrada"));

        // Verificar permisos
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String correoAutenticado = authentication.getName();
        if (!correoAutenticado.equals(cuenta.getUsuario().getCorreo()) && !authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            throw new SecurityException("No tienes permiso para eliminar esta cuenta");
        }

        cuentaRepository.delete(cuenta);
    }
}
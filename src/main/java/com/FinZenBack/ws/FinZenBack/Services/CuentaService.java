package com.FinZenBack.ws.FinZenBack.Services;

import com.FinZenBack.ws.FinZenBack.models.DTO.CuentaDto;
import com.FinZenBack.ws.FinZenBack.models.Entities.Cuenta;
import com.FinZenBack.ws.FinZenBack.models.Entities.Usuario;
import com.FinZenBack.ws.FinZenBack.repository.CuentaRepository;
import com.FinZenBack.ws.FinZenBack.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class CuentaService {

    private final CuentaRepository cuentaRepository;
    private final UsuarioRepository usuarioRepository;

    public CuentaService(CuentaRepository cuentaRepository, UsuarioRepository usuarioRepository) {
        this.cuentaRepository = cuentaRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public Cuenta createCuenta(CuentaDto cuentaDto) {
        if (cuentaDto.getIdUsuario() == null) {
            throw new IllegalArgumentException("ID de usuario no proporcionado");
        }
        if (cuentaDto.getMonto() == null || cuentaDto.getMonto().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("El monto debe ser mayor o igual a cero");
        }

        Usuario usuario = usuarioRepository.findById(cuentaDto.getIdUsuario())
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

        Cuenta cuenta = new Cuenta();
        cuenta.setNombre(cuentaDto.getNombre());
        cuenta.setMonedaPredeterminada(cuentaDto.getMonedaPredeterminada());
        cuenta.setMonto(cuentaDto.getMonto());
        cuenta.setMontoOcupado(BigDecimal.ZERO);
        cuenta.setMontoLibre(cuentaDto.getMonto());
        cuenta.setUsuario(usuario);

        return cuentaRepository.save(cuenta);
    }


    // Método para obtener todas las cuentas de un usuario

    public List<Cuenta> getCuentasByUsuario(Long idUsuario) {
       return cuentaRepository.findByUsuario_IdUsuario(idUsuario);
    }

    // M    étodo para actualizar una cuenta
    public Cuenta updateCuenta(Long idCuenta, CuentaDto cuentaDto) {
        Cuenta cuenta = cuentaRepository.findById(idCuenta)
                .orElseThrow(() -> new RuntimeException("Cuenta no encontrada"));

        cuenta.setNombre(cuentaDto.getNombre());
        cuenta.setMonedaPredeterminada(cuentaDto.getMonedaPredeterminada());

        return cuentaRepository.save(cuenta);
    }

    // Método para eliminar una cuenta
    public void deleteCuenta(Long idCuenta) {
        if (!cuentaRepository.existsById(idCuenta)) {
            throw new RuntimeException("Cuenta no encontrada");
        }
        cuentaRepository.deleteById(idCuenta);
    }
}

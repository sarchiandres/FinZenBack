package com.FinZenBack.ws.FinZenBack.Services;

import com.FinZenBack.ws.FinZenBack.models.DTO.CuentaDto;
import com.FinZenBack.ws.FinZenBack.models.Entities.Cuenta;
import com.FinZenBack.ws.FinZenBack.models.Entities.Usuario;
import com.FinZenBack.ws.FinZenBack.repository.CuentaRepository;
import com.FinZenBack.ws.FinZenBack.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

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

    // Método para crear una nueva cuenta
    public Cuenta createCuenta(CuentaDto cuentaDto) {
        if (cuentaDto.getIdUsuario() == null) {
            throw new IllegalArgumentException("ID de usuario no proporcionado");
        }

        Usuario usuario = usuarioRepository.findById(cuentaDto.getIdUsuario())
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

        Cuenta cuenta = new Cuenta();
        cuenta.setNombre(cuentaDto.getNombre());
        cuenta.setMonedaPredeterminada(cuentaDto.getMonedaPredeterminada());
        cuenta.setUsuario(usuario);
        cuenta.setFechaCreacion(LocalDateTime.now());

        return cuentaRepository.save(cuenta);
    }


    // Método para obtener todas las cuentas de un usuario

    public List<Cuenta> getCuentasByUsuario(Long idUsuario) {
       return cuentaRepository.findByUsuario_IdUsuario(idUsuario);
    }

    // Método para actualizar una cuenta
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

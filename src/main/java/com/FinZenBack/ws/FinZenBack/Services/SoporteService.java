package com.FinZenBack.ws.FinZenBack.Services;

import com.FinZenBack.ws.FinZenBack.models.DTO.SoporteDto;
import com.FinZenBack.ws.FinZenBack.models.Entities.Soporte;
import com.FinZenBack.ws.FinZenBack.models.Entities.Usuario;
import com.FinZenBack.ws.FinZenBack.repository.SoporteRepository;
import com.FinZenBack.ws.FinZenBack.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SoporteService {

    private final SoporteRepository soporteRepository;
    private final UsuarioRepository usuarioRepository;

    public SoporteService(SoporteRepository soporteRepository, UsuarioRepository usuarioRepository) {
        this.soporteRepository = soporteRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public Soporte createSoporte(SoporteDto dto) {
        Soporte soporte = new Soporte();
        soporte.setAsunto(dto.getAsunto());
        soporte.setMensaje(dto.getMensaje());

        if (dto.getIdUsuario() != null) {
            Usuario usuario = usuarioRepository.findById(dto.getIdUsuario())
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
            soporte.setUsuario(usuario);
        }

        return soporteRepository.save(soporte);
    }

    public List<Soporte> getSoportes() {
        return soporteRepository.findAll();
    }

    public List<Soporte> getSoportesByUsuario(Long idUsuario) {
        return soporteRepository.findByUsuarioIdUsuario(idUsuario);
    }
}

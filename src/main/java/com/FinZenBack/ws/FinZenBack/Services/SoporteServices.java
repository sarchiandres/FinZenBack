package com.FinZenBack.ws.FinZenBack.Services;

import com.FinZenBack.ws.FinZenBack.models.DTO.SoporteDto;
import com.FinZenBack.ws.FinZenBack.models.Entities.Soporte;
import com.FinZenBack.ws.FinZenBack.models.Entities.Usuario;
import com.FinZenBack.ws.FinZenBack.repository.SoporteRepository;
import com.FinZenBack.ws.FinZenBack.repository.UsuarioRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class SoporteServices {
    private final SoporteRepository soporteRepository;
    private final UsuarioRepository usuarioRepository;

    public SoporteServices(SoporteRepository soporteRepository, UsuarioRepository usuarioRepository) {
        this.soporteRepository = soporteRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @Transactional
    public Soporte createSoporte(SoporteDto soporteDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String correoUsuario = authentication.getName();

        Usuario usuario = usuarioRepository.findByCorreo(correoUsuario)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));

        Soporte soporte = new Soporte();
        soporte.setUsuario(usuario);
        soporte.setAsunto(soporteDto.getAsunto());
        soporte.setMensaje(soporteDto.getMensaje());

        return soporteRepository.save(soporte);
    }

    @Transactional(readOnly = true)
    public List<Soporte> getSoportesByUsuario() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String correoUsuario = authentication.getName();

        return soporteRepository.findByUsuarioCorreo(correoUsuario);
    }

    @Transactional(readOnly = true)
    public List<Soporte> getAllSoportes() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Solo administradores pueden ver todos los tickets de soporte");
        }

        return soporteRepository.findAll();
    }
}
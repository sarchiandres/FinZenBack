package com.FinZenBack.ws.FinZenBack.Services;

import com.FinZenBack.ws.FinZenBack.models.DTO.InformeDto;
import com.FinZenBack.ws.FinZenBack.models.Entities.Informe;
import com.FinZenBack.ws.FinZenBack.models.Entities.Usuario;
import com.FinZenBack.ws.FinZenBack.repository.InformeRepository;
import com.FinZenBack.ws.FinZenBack.repository.UsuarioRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class InformeService {
    private final InformeRepository informeRepository;
    private final UsuarioRepository usuarioRepository;

    public InformeService(InformeRepository informeRepository, UsuarioRepository usuarioRepository) {
        this.informeRepository = informeRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @Transactional
    public Informe createInforme(InformeDto dto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String correoUsuario = authentication.getName();

        Usuario usuario = usuarioRepository.findById(dto.getIdUsuario())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));

        if (!usuario.getCorreo().equals(correoUsuario) &&
                !authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No tienes permiso para crear un informe para este usuario");
        }

        Informe informe = new Informe();
        informe.setTitulo(dto.getTitulo());
        informe.setContenido(dto.getContenido());
        informe.setUsuario(usuario);

        return informeRepository.save(informe);
    }

    @Transactional(readOnly = true)
    public List<Informe> getInformesByUsuario(Long idUsuario) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String correoUsuario = authentication.getName();

        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));

        if (!usuario.getCorreo().equals(correoUsuario) &&
                !authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No tienes permiso para ver los informes de este usuario");
        }

        return informeRepository.findByUsuarioIdUsuario(idUsuario);
    }

    @Transactional
    public Informe updateInforme(Long idInforme, InformeDto dto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String correoUsuario = authentication.getName();

        Informe informe = informeRepository.findById(idInforme)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Informe no encontrado"));

        if (!informe.getUsuario().getCorreo().equals(correoUsuario) &&
                !authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No tienes permiso para modificar este informe");
        }

        informe.setTitulo(dto.getTitulo());
        informe.setContenido(dto.getContenido());

        return informeRepository.save(informe);
    }

    @Transactional
    public void deleteInforme(Long idInforme) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String correoUsuario = authentication.getName();

        Informe informe = informeRepository.findById(idInforme)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Informe no encontrado"));

        if (!informe.getUsuario().getCorreo().equals(correoUsuario) &&
                !authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No tienes permiso para eliminar este informe");
        }

        informeRepository.deleteById(idInforme);
    }

    @Transactional
    public Informe generarInformeGeneral(Long idUsuario) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String correoUsuario = authentication.getName();

        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));

        if (!usuario.getCorreo().equals(correoUsuario) &&
                !authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No tienes permiso para generar un informe para este usuario");
        }

        // Call stored procedure (assumes a native query or JPA native query support)
        // This is a placeholder; actual implementation depends on your setup
        Informe informe = new Informe();
        informe.setTitulo("Informe General de " + usuario.getNombre());
        informe.setUsuario(usuario);
        // Set contenido from stored procedure result (e.g., via JDBC or native query)
        // For simplicity, assume it's called and sets contenido
        informe.setContenido("Contenido generado por GenerarInformeGeneral");

        return informeRepository.save(informe);
    }
}
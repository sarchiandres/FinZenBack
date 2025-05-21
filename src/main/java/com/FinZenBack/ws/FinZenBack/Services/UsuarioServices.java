package com.FinZenBack.ws.FinZenBack.Services;

import com.FinZenBack.ws.FinZenBack.payload.FinZenException;
import com.FinZenBack.ws.FinZenBack.models.DTO.UsuarioDto;
import com.FinZenBack.ws.FinZenBack.models.Entities.TipoUsuario;
import com.FinZenBack.ws.FinZenBack.models.Entities.Usuario;
import com.FinZenBack.ws.FinZenBack.repository.TipoUsuarioRepository;
import com.FinZenBack.ws.FinZenBack.repository.UsuarioRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class UsuarioServices {

    private final UsuarioRepository usuarioRepository;
    private final TipoUsuarioRepository tipoUsuarioRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UsuarioServices(
            UsuarioRepository usuarioRepository,
            TipoUsuarioRepository tipoUsuarioRepository,
            BCryptPasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.tipoUsuarioRepository = tipoUsuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Usuario createUsuario(UsuarioDto usuarioDTO) {
        // Validar unicidad
        if (usuarioRepository.existsByNumeroDocumento(usuarioDTO.getNumeroDocumento())) {
            throw new FinZenException("El usuario con documento " + usuarioDTO.getNumeroDocumento() + " ya existe");
        }
        if (usuarioRepository.existsByCorreo(usuarioDTO.getCorreo())) {
            throw new FinZenException("El usuario con correo " + usuarioDTO.getCorreo() + " ya existe");
        }
        if (usuarioRepository.existsByNombreUsuario(usuarioDTO.getNombreUsuario())) {
            throw new FinZenException("El nombre de usuario " + usuarioDTO.getNombreUsuario() + " ya existe");
        }

        // Validar rol
        String tipoUsuarioNombre = usuarioDTO.getTipoUsuario() != null ? usuarioDTO.getTipoUsuario().toUpperCase() : "USUARIO";
        if (!Arrays.asList("USUARIO", "ADMIN").contains(tipoUsuarioNombre)) {
            throw new FinZenException("Rol inválido: " + tipoUsuarioNombre + ". Debe ser 'USUARIO' o 'ADMIN'");
        }

        Usuario usuario = new Usuario();
        usuario.setNombre(usuarioDTO.getNombre());
        usuario.setCorreo(usuarioDTO.getCorreo());
        usuario.setContrasena(passwordEncoder.encode(usuarioDTO.getContrasena()));
        usuario.setNumeroDocumento(usuarioDTO.getNumeroDocumento());
        usuario.setPaisResidencia(usuarioDTO.getPaisResidencia());
        usuario.setIngresoMensual(usuarioDTO.getIngresoMensual() != null ? usuarioDTO.getIngresoMensual() : 0L);
        usuario.setMetaActual(usuarioDTO.getMetaActual() != null ? usuarioDTO.getMetaActual() : true);
        usuario.setNombreUsuario(usuarioDTO.getNombreUsuario());

        // Convertir tipoDocumento
        try {
            usuario.setTipoDocumento(Usuario.TipoDocumentoEnum.valueOf(usuarioDTO.getTipoDocumento()));
        } catch (IllegalArgumentException e) {
            throw new FinZenException("Tipo de documento inválido: " + usuarioDTO.getTipoDocumento());
        }

        // Convertir tipoPersona
        try {
            usuario.setTipoPersona(usuarioDTO.getTipoPersona() != null ?
                    Usuario.TipoPersonaEnum.valueOf(usuarioDTO.getTipoPersona()) :
                    Usuario.TipoPersonaEnum.personalizado);
        } catch (IllegalArgumentException e) {
            throw new FinZenException("Tipo de persona inválido: " + usuarioDTO.getTipoPersona());
        }

        // Asignar tipoUsuario
        TipoUsuario tipoUsuario = tipoUsuarioRepository.findByNombre(tipoUsuarioNombre)
                .orElseThrow(() -> new FinZenException("El tipo de usuario '" + tipoUsuarioNombre + "' no existe"));
        usuario.setTipoUsuario(tipoUsuario);

        return usuarioRepository.save(usuario);
    }

    public Usuario getUsuarioDocumento(Long documento) {
        return usuarioRepository.findByNumeroDocumento(documento)
                .orElseThrow(() -> new FinZenException("El usuario con documento " + documento + " no se encontró"));
    }

    public Usuario updateUsuario(Long documento, UsuarioDto usuarioDTO) {
        Usuario usuario = getUsuarioDocumento(documento);

        // Verificar unicidad
        if (!usuario.getCorreo().equals(usuarioDTO.getCorreo()) && usuarioRepository.existsByCorreo(usuarioDTO.getCorreo())) {
            throw new FinZenException("El correo " + usuarioDTO.getCorreo() + " ya está en uso");
        }
        if (!usuario.getNombreUsuario().equals(usuarioDTO.getNombreUsuario()) && usuarioRepository.existsByNombreUsuario(usuarioDTO.getNombreUsuario())) {
            throw new FinZenException("El nombre de usuario " + usuarioDTO.getNombreUsuario() + " ya está en uso");
        }

        // Actualizar campos
        usuario.setNombre(usuarioDTO.getNombre());
        usuario.setCorreo(usuarioDTO.getCorreo());
        if (usuarioDTO.getContrasena() != null && !usuarioDTO.getContrasena().isEmpty()) {
            usuario.setContrasena(passwordEncoder.encode(usuarioDTO.getContrasena()));
        }
        usuario.setNumeroDocumento(usuarioDTO.getNumeroDocumento());
        usuario.setPaisResidencia(usuarioDTO.getPaisResidencia());
        usuario.setIngresoMensual(usuarioDTO.getIngresoMensual() != null ? usuarioDTO.getIngresoMensual() : 0L);
        usuario.setMetaActual(usuarioDTO.getMetaActual() != null ? usuarioDTO.getMetaActual() : true);
        usuario.setNombreUsuario(usuarioDTO.getNombreUsuario());

        // Actualizar tipoDocumento
        try {
            usuario.setTipoDocumento(Usuario.TipoDocumentoEnum.valueOf(usuarioDTO.getTipoDocumento()));
        } catch (IllegalArgumentException e) {
            throw new FinZenException("Tipo de documento inválido: " + usuarioDTO.getTipoDocumento());
        }

        // Actualizar tipoPersona
        try {
            usuario.setTipoPersona(usuarioDTO.getTipoPersona() != null ?
                    Usuario.TipoPersonaEnum.valueOf(usuarioDTO.getTipoPersona()) :
                    Usuario.TipoPersonaEnum.personalizado);
        } catch (IllegalArgumentException e) {
            throw new FinZenException("Tipo de persona inválido: " + usuarioDTO.getTipoPersona());
        }

        // Actualizar tipoUsuario
        String tipoUsuarioNombre = usuarioDTO.getTipoUsuario() != null ? usuarioDTO.getTipoUsuario().toUpperCase() : usuario.getTipoUsuario().getNombre();
        if (!Arrays.asList("USUARIO", "ADMIN").contains(tipoUsuarioNombre)) {
            throw new FinZenException("Rol inválido: " + tipoUsuarioNombre + ". Debe ser 'USUARIO' o 'ADMIN'");
        }
        TipoUsuario tipoUsuario = tipoUsuarioRepository.findByNombre(tipoUsuarioNombre)
                .orElseThrow(() -> new FinZenException("El tipo de usuario '" + tipoUsuarioNombre + "' no existe"));
        usuario.setTipoUsuario(tipoUsuario);

        return usuarioRepository.save(usuario);
    }

    public String deleteUsuario(Long documento) {
        Usuario usuario = getUsuarioDocumento(documento);
        usuarioRepository.delete(usuario);
        return "Usuario eliminado";
    }

    public Usuario getUsuarioByCorreo(String correo) {
        return usuarioRepository.findByCorreo(correo)
                .orElseThrow(() -> new FinZenException("Usuario con correo " + correo + " no encontrado"));
    }
}

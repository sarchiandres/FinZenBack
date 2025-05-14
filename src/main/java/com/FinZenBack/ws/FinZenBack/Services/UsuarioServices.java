package com.FinZenBack.ws.FinZenBack.Services;

import com.FinZenBack.ws.FinZenBack.models.DTO.UsuarioDto;
import com.FinZenBack.ws.FinZenBack.models.Entities.TipoUsuario;
import com.FinZenBack.ws.FinZenBack.models.Entities.Usuario;
import com.FinZenBack.ws.FinZenBack.repository.TipoUsuarioRepository;
import com.FinZenBack.ws.FinZenBack.repository.UsuarioRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

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
        if (usuarioRepository.existsByNumeroDocumento(usuarioDTO.getNumeroDocumento())) {
            throw new RuntimeException("El usuario con documento " + usuarioDTO.getNumeroDocumento() + " ya existe");
        }
        if (usuarioRepository.existsByCorreo(usuarioDTO.getCorreo())) {
            throw new RuntimeException("El usuario con correo " + usuarioDTO.getCorreo() + " ya existe");
        }
        if (usuarioRepository.existsByNombreUsuario(usuarioDTO.getNombreUsuario())) {
            throw new RuntimeException("El nombre de usuario " + usuarioDTO.getNombreUsuario() + " ya existe");
        }

        Usuario usuario = new Usuario();
        usuario.setNombre(usuarioDTO.getNombre());
        usuario.setCorreo(usuarioDTO.getCorreo());
        usuario.setContrasena(passwordEncoder.encode(usuarioDTO.getContrasena())); // Encriptar contraseña
        usuario.setNumeroDocumento(usuarioDTO.getNumeroDocumento());
        usuario.setPaisResidencia(usuarioDTO.getPaisResidencia());
        usuario.setIngresoMensual(usuarioDTO.getIngresoMensual());
        usuario.setMetaActual(usuarioDTO.getMetaActual() != null ? usuarioDTO.getMetaActual() : true);
        usuario.setNombreUsuario(usuarioDTO.getNombreUsuario());

        // Convertir tipoDocumento
        try {
            usuario.setTipoDocumento(Usuario.TipoDocumentoEnum.valueOf(usuarioDTO.getTipoDocumento()));
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Tipo de documento inválido: " + usuarioDTO.getTipoDocumento());
        }

        // Convertir tipoPersona
        if (usuarioDTO.getTipoPersona() != null) {
            try {
                usuario.setTipoPersona(Usuario.TipoPersonaEnum.valueOf(usuarioDTO.getTipoPersona()));
            } catch (IllegalArgumentException e) {
                throw new RuntimeException("Tipo de persona inválido: " + usuarioDTO.getTipoPersona());
            }
        }

        // Asignar tipoUsuario
        String tipoUsuarioNombre = usuarioDTO.getTipoUsuario() != null ? usuarioDTO.getTipoUsuario() : "USUARIO";
        TipoUsuario tipoUsuario = tipoUsuarioRepository.findByNombre(tipoUsuarioNombre)
                .orElseThrow(() -> new RuntimeException("El tipo de usuario '" + tipoUsuarioNombre + "' no existe"));
        usuario.setTipoUsuario(tipoUsuario);

        return usuarioRepository.save(usuario);
    }

    public Usuario getUsuarioDocumento(Long documento) {
        return usuarioRepository.findByNumeroDocumento(documento)
                .orElseThrow(() -> new RuntimeException("El usuario con documento " + documento + " no se encontró"));
    }

    public Usuario updateUsuario(Long documento, UsuarioDto usuarioDTO) {
        Usuario usuario = getUsuarioDocumento(documento);

        // Verificar unicidad de correo y nombreUsuario (excepto para el mismo usuario)
        if (!usuario.getCorreo().equals(usuarioDTO.getCorreo()) && usuarioRepository.existsByCorreo(usuarioDTO.getCorreo())) {
            throw new RuntimeException("El correo " + usuarioDTO.getCorreo() + " ya está en uso");
        }
        if (!usuario.getNombreUsuario().equals(usuarioDTO.getNombreUsuario()) && usuarioRepository.existsByNombreUsuario(usuarioDTO.getNombreUsuario())) {
            throw new RuntimeException("El nombre de usuario " + usuarioDTO.getNombreUsuario() + " ya está en uso");
        }

        usuario.setNombre(usuarioDTO.getNombre());
        usuario.setCorreo(usuarioDTO.getCorreo());
        if (usuarioDTO.getContrasena() != null && !usuarioDTO.getContrasena().isEmpty()) {
            usuario.setContrasena(passwordEncoder.encode(usuarioDTO.getContrasena())); // Encriptar si se proporciona
        }
        usuario.setNumeroDocumento(usuarioDTO.getNumeroDocumento());
        usuario.setPaisResidencia(usuarioDTO.getPaisResidencia());
        usuario.setIngresoMensual(usuarioDTO.getIngresoMensual());
        usuario.setMetaActual(usuarioDTO.getMetaActual() != null ? usuarioDTO.getMetaActual() : true);
        usuario.setNombreUsuario(usuarioDTO.getNombreUsuario());

        // Actualizar tipoDocumento
        try {
            usuario.setTipoDocumento(Usuario.TipoDocumentoEnum.valueOf(usuarioDTO.getTipoDocumento()));
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Tipo de documento inválido: " + usuarioDTO.getTipoDocumento());
        }

        // Actualizar tipoPersona
        if (usuarioDTO.getTipoPersona() != null) {
            try {
                usuario.setTipoPersona(Usuario.TipoPersonaEnum.valueOf(usuarioDTO.getTipoPersona()));
            } catch (IllegalArgumentException e) {
                throw new RuntimeException("Tipo de persona inválido: " + usuarioDTO.getTipoPersona());
            }
        } else {
            usuario.setTipoPersona(null);
        }

        // Actualizar tipoUsuario
        if (usuarioDTO.getTipoUsuario() != null) {
            TipoUsuario tipoUsuario = tipoUsuarioRepository.findByNombre(usuarioDTO.getTipoUsuario())
                    .orElseThrow(() -> new RuntimeException("El tipo de usuario '" + usuarioDTO.getTipoUsuario() + "' no existe"));
            usuario.setTipoUsuario(tipoUsuario);
        }

        return usuarioRepository.save(usuario);
    }

    public String deleteUsuario(Long documento) {
        Usuario usuario = getUsuarioDocumento(documento);
        usuarioRepository.delete(usuario);
        return "Usuario eliminado";
    }

    public Usuario getUsuarioByCorreo(String correo) {
        return usuarioRepository.findByCorreo(correo)
                .orElseThrow(() -> new RuntimeException("Usuario con correo " + correo + " no encontrado"));
    }
}
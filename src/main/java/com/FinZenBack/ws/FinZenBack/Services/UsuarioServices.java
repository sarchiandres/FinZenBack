package com.FinZenBack.ws.FinZenBack.Services;

import com.FinZenBack.ws.FinZenBack.models.DTO.UsuarioDto;
import com.FinZenBack.ws.FinZenBack.models.Entities.Usuario;
import com.FinZenBack.ws.FinZenBack.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

@Service
public class UsuarioServices {

    private final  UsuarioRepository usuarioRepository;

    public UsuarioServices(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public Usuario createUsuario(UsuarioDto usuarioDTO) {
        if (usuarioRepository.findByNumeroDocumento(usuarioDTO.getNumeroDocumento()).isPresent()) {
            throw new RuntimeException("El usuario con documento " + usuarioDTO.getNumeroDocumento() + " ya existe");
        }

        Usuario usuario = new Usuario();
        usuario.setNombre(usuarioDTO.getNombre());
        usuario.setCorreo(usuarioDTO.getCorreo());
        usuario.setContrasena(usuarioDTO.getContrasena());
        usuario.setNumeroDocumento(usuarioDTO.getNumeroDocumento());
        usuario.setPaisResidencia(usuarioDTO.getPaisResidencia());
        usuario.setIngresoMensual(usuarioDTO.getIngresoMensual());
        usuario.setMetaActual(usuarioDTO.getMetaActual() != null ? usuarioDTO.getMetaActual() : true);
        usuario.setNombreUsuario(usuarioDTO.getNombreUsuario());

        // Convertir tipoDocumento de String a TipoDocumentoEnum
        try {
            usuario.setTipoDocumento(Usuario.TipoDocumentoEnum.valueOf(usuarioDTO.getTipoDocumento()));
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Tipo de documento inválido: " + usuarioDTO.getTipoDocumento());
        }

        // Convertir tipousuario de String a TipoUsuarioEnum (puede ser null)
        if (usuarioDTO.getTipousuario() != null) {
            try {
                usuario.setTipousuario(Usuario.TipoUsuarioEnum.valueOf(usuarioDTO.getTipousuario()));
            } catch (IllegalArgumentException e) {
                throw new RuntimeException("Tipo de usuario inválido: " + usuarioDTO.getTipousuario());
            }
        }

        return usuarioRepository.save(usuario);
    }

    public Usuario getUsuarioDocumento(long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("El usuario con documento " + id + " no se encontró"));
    }

    public Usuario updateUsuario(Long documento, UsuarioDto usuarioDTO) {
        // Obtener el usuario existente
        Usuario usuario = getUsuarioDocumento(documento);

        // Actualizar los campos del usuario
        usuario.setNombre(usuarioDTO.getNombre());
        usuario.setCorreo(usuarioDTO.getCorreo());
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

        // Actualizar tipousuario (puede ser null)
        if (usuarioDTO.getTipousuario() != null) {
            try {
                usuario.setTipousuario(Usuario.TipoUsuarioEnum.valueOf(usuarioDTO.getTipousuario()));
            } catch (IllegalArgumentException e) {
                throw new RuntimeException("Tipo de usuario inválido: " + usuarioDTO.getTipousuario());
            }
        } else {
            usuario.setTipousuario(null);
        }

        // Guardar el usuario actualizado
        return usuarioRepository.save(usuario);
    }

    public String deleteUsuario(long documento) {
        // Obtener el usuario para eliminar
        Usuario usuario = getUsuarioDocumento(documento);

        // Eliminar el usuario
        usuarioRepository.delete(usuario);

        return "Usuario eliminado";
    }
}
package com.FinZenBack.ws.FinZenBack.Services;

import com.FinZenBack.ws.FinZenBack.models.DTO.UsuarioDto;
import com.FinZenBack.ws.FinZenBack.models.Entities.TipoUsuario;
import com.FinZenBack.ws.FinZenBack.models.Entities.Usuario;
import com.FinZenBack.ws.FinZenBack.repository.TipoUsuarioRepository;
import com.FinZenBack.ws.FinZenBack.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

@Service
public class UsuarioServices {

    private final  UsuarioRepository usuarioRepository;
    private final TipoUsuarioRepository tipoUsuarioRepository;

    public UsuarioServices(UsuarioRepository usuarioRepository, TipoUsuarioRepository tipoUsuarioRepository) {
        this.usuarioRepository = usuarioRepository;
        this.tipoUsuarioRepository = tipoUsuarioRepository;
    }





    public Usuario createUsuario(UsuarioDto usuarioDTO) {
        if (usuarioRepository.findByNumeroDocumento(usuarioDTO.getNumeroDocumento()).isPresent()) {
            throw new RuntimeException("El usuario con documento " + usuarioDTO.getNumeroDocumento() + " ya existe");
        }if(usuarioRepository.findByCorreo(usuarioDTO.getCorreo()).isPresent()){
            throw new RuntimeException("El usuario con este correo ya exite ");
        }

        Usuario usuario = new Usuario();
        usuario.setNombre(usuarioDTO.getNombre());
        usuario.setCorreo(usuarioDTO.getCorreo());
        usuario.setNumeroDocumento(usuarioDTO.getNumeroDocumento());
        usuario.setPaisResidencia(usuarioDTO.getPaisResidencia());
        usuario.setIngresoMensual(usuarioDTO.getIngresoMensual());
        usuario.setMetaActual(usuarioDTO.getMetaActual() != null ? usuarioDTO.getMetaActual() : true);
        usuario.setNombreUsuario(usuarioDTO.getNombreUsuario());
        usuario.setContrasena(usuarioDTO.getContrasena());
        usuario.setNombre(usuarioDTO.getNombre());

        // Convertir tipoDocumento de String a TipoDocumentoEnum
        try {
            usuario.setTipoDocumento(Usuario.TipoDocumentoEnum.valueOf(usuarioDTO.getTipoDocumento()));
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Tipo de documento inválido: " + usuarioDTO.getTipoDocumento());
        }

        // Convertir tipousuario de String a TipoUsuarioEnum (puede ser null)
        if (usuarioDTO.getTipoPersona() != null) {
            try {
                usuario.setTipoPersona(Usuario.TipoPersonaEnum.valueOf(usuarioDTO.getTipoPersona()));
            } catch (IllegalArgumentException e) {
                throw new RuntimeException("Tipo de usuario inválido: " + usuarioDTO.getTipoPersona());
            }
        }

        TipoUsuario tipoUsuario = tipoUsuarioRepository.findByNombre("USUARIO")
                .orElseThrow(() -> new RuntimeException("El tipo de usuario 'USUARIO' no existe en la base de datos"));
        usuario.setTipoUsuario(tipoUsuario);


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
        usuario.setContrasena(usuarioDTO.getContrasena());
        usuario.setNombre(usuarioDTO.getNombre());

        if (usuarioDTO.getTipoPersona() != null) {
            try {
                usuario.setTipoPersona(Usuario.TipoPersonaEnum.valueOf(usuarioDTO.getTipoPersona()));
            } catch (IllegalArgumentException e) {
                throw new RuntimeException("Tipo de usuario inválido: " + usuarioDTO.getTipoPersona());
            }
        }

        // Actualizar tipoDocumento
        try {
            usuario.setTipoDocumento(Usuario.TipoDocumentoEnum.valueOf(usuarioDTO.getTipoDocumento()));
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Tipo de documento inválido: " + usuarioDTO.getTipoDocumento());
        }

        // Actualizar tipousuario (puede ser null)
        if (usuarioDTO.getTipoPersona() != null) {
            try {
                usuario.setTipoPersona(Usuario.TipoPersonaEnum.valueOf(usuarioDTO.getTipoPersona()));
            } catch (IllegalArgumentException e) {
                throw new RuntimeException("Tipo de usuario inválido: " + usuarioDTO.getTipoPersona());
            }
        } else {
            usuario.setTipoPersona(null);
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
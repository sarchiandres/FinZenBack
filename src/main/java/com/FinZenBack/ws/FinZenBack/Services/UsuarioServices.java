package com.FinZenBack.ws.FinZenBack.Services;

import com.FinZenBack.ws.FinZenBack.models.TipoDocumento;
import com.FinZenBack.ws.FinZenBack.models.TipoUsuario;
import com.FinZenBack.ws.FinZenBack.models.Usuario;
import com.FinZenBack.ws.FinZenBack.repository.TipoDocumentoRepository; // Importar el repositorio de TipoDocumento
import com.FinZenBack.ws.FinZenBack.repository.TipoUsuarioRepository; // Importar el repositorio de TipoUsuario
import com.FinZenBack.ws.FinZenBack.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

@Service
public class UsuarioServices {
    private final UsuarioRepository usuarioRepository;
    private final TipoUsuarioRepository tipoUsuarioRepository;
    private final TipoDocumentoRepository tipoDocumentoRepository; // Repositorio para TipoDocumento

    public UsuarioServices(UsuarioRepository usuarioRepository, TipoUsuarioRepository tipoUsuarioRepository, TipoDocumentoRepository tipoDocumentoRepository) {
        this.usuarioRepository = usuarioRepository;
        this.tipoUsuarioRepository = tipoUsuarioRepository;
        this.tipoDocumentoRepository = tipoDocumentoRepository;
    }

    public Usuario createUsuario(Usuario usuario) {
        // Verificar si el usuario con el mismo número de documento ya existe
        if (usuarioRepository.findByNumeroDocumento(usuario.getNumeroDocumento()).isPresent()) {
            throw new RuntimeException("El usuario con documento " + usuario.getNumeroDocumento() + " ya existe");
        }

        // Buscar el TipoUsuario por su ID
        TipoUsuario tipoUsuario = tipoUsuarioRepository.findById(usuario.getTipoUsuario().getId_tipousuario())
                .orElseThrow(() -> new RuntimeException("TipoUsuario con ID " + usuario.getTipoUsuario().getId_tipousuario() + " no encontrado"));

        // Buscar el TipoDocumento por su ID
        TipoDocumento tipoDocumento = tipoDocumentoRepository.findById(usuario.getTipoDocumento().getId_tipodocumento())
                .orElseThrow(() -> new RuntimeException("TipoDocumento con ID " + usuario.getTipoDocumento().getId_tipodocumento() + " no encontrado"));

        // Asignar el TipoUsuario y TipoDocumento al usuario
        usuario.setTipoUsuario(tipoUsuario);
        usuario.setTipoDocumento(tipoDocumento);

        // Guardar el usuario en el repositorio
        return usuarioRepository.save(usuario);
    }

    public Usuario getUsuarioDocumento(long documento) {
        return usuarioRepository.findByNumeroDocumento(documento)
                .orElseThrow(() -> new RuntimeException("El usuario con documento " + documento + " no se encontró"));
    }

    public Usuario updateUsuario(Long documento, Usuario usu) {
        // Obtener el usuario existente
        Usuario usuario = getUsuarioDocumento(documento);

        // Buscar el TipoUsuario a partir del ID
        TipoUsuario tipoUsuario = tipoUsuarioRepository.findById(usu.getTipoUsuario().getId_tipousuario())
                .orElseThrow(() -> new RuntimeException("TipoUsuario con ID " + usu.getTipoUsuario().getId_tipousuario() + " no encontrado"));

        // Buscar el TipoDocumento a partir del ID
        TipoDocumento tipoDocumento = tipoDocumentoRepository.findById(usu.getTipoDocumento().getId_tipodocumento())
                .orElseThrow(() -> new RuntimeException("TipoDocumento con ID " + usu.getTipoDocumento().getId_tipodocumento() + " no encontrado"));

        // Actualizar los campos del usuario
        usuario.setNombre(usu.getNombre());
        usuario.setApellido(usu.getApellido());
        usuario.setCorreo(usu.getCorreo());
        usuario.setTipoUsuario(tipoUsuario);
        usuario.setTipoDocumento(tipoDocumento);

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

package com.FinZenBack.ws.FinZenBack.Services;

import com.FinZenBack.ws.FinZenBack.models.DTO.UsuarioDto;
import com.FinZenBack.ws.FinZenBack.models.Entities.TipoDocumento;
import com.FinZenBack.ws.FinZenBack.models.Entities.TipoUsuario;
import com.FinZenBack.ws.FinZenBack.models.Entities.Usuario;
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

    public Usuario createUsuario(UsuarioDto usuarioDTO) {
        if (usuarioRepository.findByNumeroDocumento(usuarioDTO.getNumeroDocumento()).isPresent()) {
            throw new RuntimeException("El usuario con documento " + usuarioDTO.getNumeroDocumento() + " ya existe");
        }

        Usuario usuario = new Usuario();
        usuario.setNombre(usuarioDTO.getNombre());
        usuario.setApellido(usuarioDTO.getApellido());
        usuario.setCorreo(usuarioDTO.getCorreo());
        usuario.setContrasena(usuarioDTO.getContrasena());
        usuario.setNumeroDocumento(usuarioDTO.getNumeroDocumento());

        TipoUsuario tipoUsuario = new TipoUsuario();
        tipoUsuario.setId_tipousuario(usuarioDTO.getIdTipoUsuario());
        usuario.setTipoUsuario(tipoUsuario);

        TipoDocumento tipoDocumento = new TipoDocumento();
        tipoDocumento.setId_tipodocumento(usuarioDTO.getIdTipoDocumento());
        usuario.setTipoDocumento(tipoDocumento);

        return usuarioRepository.save(usuario);
    }

    public Usuario getUsuarioDocumento(long documento) {
        return usuarioRepository.findByNumeroDocumento(documento)
                .orElseThrow(() -> new RuntimeException("El usuario con documento " + documento + " no se encontró"));
    }

    public Usuario updateUsuario(Long documento, UsuarioDto usu) {
        // Obtener el usuario existente
        Usuario usuario = getUsuarioDocumento(documento);

        // Buscar el TipoUsuario a partir del ID
        TipoUsuario tipoUsuario = tipoUsuarioRepository.findById(usu.getIdTipoUsuario())
                .orElseThrow(() -> new RuntimeException("TipoUsuario con ID " + usu.getIdTipoUsuario() + " no encontrado"));

        // Buscar el TipoDocumento a partir del ID
        TipoDocumento tipoDocumento = tipoDocumentoRepository.findById(usu.getIdTipoDocumento())
                .orElseThrow(() -> new RuntimeException("TipoDocumento con ID " + usu.getIdTipoDocumento() + " no encontrado"));

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

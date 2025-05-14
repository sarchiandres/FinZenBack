package com.FinZenBack.ws.FinZenBack.security.jwt;

import com.FinZenBack.ws.FinZenBack.models.Entities.Usuario;
import com.FinZenBack.ws.FinZenBack.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String correo) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByCorreo(correo)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado con el correo: " + correo));

        String role = "ROLE_" + usuario.getTipoUsuario().getNombre().toUpperCase();
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role);

        return new org.springframework.security.core.userdetails.User(
                usuario.getCorreo(), // Usar correo como "username" en UserDetails
                usuario.getContrasena(),
                Collections.singletonList(authority));
    }
}
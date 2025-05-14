package com.FinZenBack.ws.FinZenBack.Services;

import com.FinZenBack.ws.FinZenBack.models.DTO.InformeDto;
import com.FinZenBack.ws.FinZenBack.models.Entities.Informe;
import com.FinZenBack.ws.FinZenBack.models.Entities.Usuario;
import com.FinZenBack.ws.FinZenBack.repository.InformeRepository;
import com.FinZenBack.ws.FinZenBack.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class InformeService {

    @Autowired
    private final InformeRepository informeRepository;

    @Autowired
    private final UsuarioRepository usuarioRepository;

    public InformeService(InformeRepository informeRepository, UsuarioRepository usuarioRepository) {
        this.informeRepository = informeRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public Informe createInforme(InformeDto dto) {
        Usuario usuario = usuarioRepository.findById(dto.getIdUsuario())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Informe informe = new Informe();
        informe.setTitulo(dto.getTitulo());
        informe.setContenido(dto.getContenido());
        informe.setFechaCreacion(LocalDateTime.now());
        informe.setUsuario(usuario);

        return informeRepository.save(informe);
    }

    public List<Informe> getInformesByUsuario(Long idUsuario) {
        return informeRepository.findByUsuarioIdUsuario(idUsuario);
    }

    public void deleteInforme(Long idInforme) {
        if (!informeRepository.existsById(idInforme)) {
            throw new RuntimeException("Informe no encontrado");
        }
        informeRepository.deleteById(idInforme);
    }

    public Informe updateInforme(Long idInforme, InformeDto dto) {
        Informe informe = informeRepository.findById(idInforme)
                .orElseThrow(() -> new RuntimeException("Informe no encontrado"));

        informe.setTitulo(dto.getTitulo());
        informe.setContenido(dto.getContenido());

        return informeRepository.save(informe);
    }
}

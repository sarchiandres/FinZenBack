package com.FinZenBack.ws.FinZenBack.Services;

import com.FinZenBack.ws.FinZenBack.models.DTO.MetaDto;
import com.FinZenBack.ws.FinZenBack.models.Entities.Cuenta;
import com.FinZenBack.ws.FinZenBack.models.Entities.Meta;
import com.FinZenBack.ws.FinZenBack.repository.CuentaRepository;
import com.FinZenBack.ws.FinZenBack.repository.MetaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class MetaServices {
    private final MetaRepository metaRepository;
    private final CuentaRepository cuentaRepository;

    public MetaServices(MetaRepository metaRepository, CuentaRepository cuentaRepository) {
        this.metaRepository = metaRepository;
        this.cuentaRepository = cuentaRepository;
    }

    @Transactional
    public Meta createMeta(MetaDto miMeta) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String correoUsuario = authentication.getName();

        Cuenta cuenta = cuentaRepository.findById(miMeta.getIdCuenta())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cuenta no encontrada"));

        if (!cuentaRepository.findByIdAndUsuarioCorreo(cuenta.getIdCuenta(), correoUsuario).isPresent() &&
                !authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No tienes permiso para crear una meta en esta cuenta");
        }

        if (miMeta.getFechaLimite().isBefore(miMeta.getFechaInicio())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La fecha límite debe ser posterior o igual a la fecha de inicio");
        }

        Meta meta = new Meta();
        meta.setTitulo(miMeta.getTitulo());
        meta.setDescripcion(miMeta.getDescripcion());
        meta.setFechaInicio(miMeta.getFechaInicio());
        meta.setFechaLimite(miMeta.getFechaLimite());
        meta.setEnProgreso(miMeta.getEnProgreso());
        meta.setEstado(miMeta.getEstado());
        meta.setValor(miMeta.getValor());
        meta.setCuenta(cuenta);

        return metaRepository.save(meta);
    }

    @Transactional(readOnly = true)
    public List<Meta> getMetasByIdCuenta(Long idCuenta) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String correoUsuario = authentication.getName();

        Cuenta cuenta = cuentaRepository.findById(idCuenta)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cuenta no encontrada"));

        if (!cuentaRepository.findByIdAndUsuarioCorreo(cuenta.getIdCuenta(), correoUsuario).isPresent() &&
                !authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No tienes permiso para ver las metas de esta cuenta");
        }

        return metaRepository.findByCuentaIdCuenta(idCuenta);
    }

    @Transactional
    public Meta updateMeta(Long idMeta, MetaDto miMeta) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String correoUsuario = authentication.getName();

        Meta meta = metaRepository.findById(idMeta)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Meta no encontrada"));

        Cuenta cuentaActual = meta.getCuenta();
        if (!cuentaRepository.findByIdAndUsuarioCorreo(cuentaActual.getIdCuenta(), correoUsuario).isPresent() &&
                !authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No tienes permiso para modificar esta meta");
        }

        Cuenta cuenta = cuentaRepository.findById(miMeta.getIdCuenta())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cuenta no encontrada"));

        if (!cuentaRepository.findByIdAndUsuarioCorreo(cuenta.getIdCuenta(), correoUsuario).isPresent() &&
                !authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No tienes permiso para asignar esta cuenta");
        }

        if (miMeta.getFechaLimite().isBefore(miMeta.getFechaInicio())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La fecha límite debe ser posterior o igual a la fecha de inicio");
        }

        meta.setTitulo(miMeta.getTitulo());
        meta.setDescripcion(miMeta.getDescripcion());
        meta.setFechaInicio(miMeta.getFechaInicio());
        meta.setFechaLimite(miMeta.getFechaLimite());
        meta.setEnProgreso(miMeta.getEnProgreso());
        meta.setEstado(miMeta.getEstado());
        meta.setValor(miMeta.getValor());
        meta.setCuenta(cuenta);

        return metaRepository.save(meta);
    }

    @Transactional
    public void deleteMeta(Long idMeta) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String correoUsuario = authentication.getName();

        Meta meta = metaRepository.findById(idMeta)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Meta no encontrada"));

        Cuenta cuenta = meta.getCuenta();
        if (!cuentaRepository.findByIdAndUsuarioCorreo(cuenta.getIdCuenta(), correoUsuario).isPresent() &&
                !authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No tienes permiso para eliminar esta meta");
        }

        metaRepository.deleteById(idMeta);
    }
}
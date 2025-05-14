package com.FinZenBack.ws.FinZenBack.Services;

import com.FinZenBack.ws.FinZenBack.models.DTO.MetaDto;
import com.FinZenBack.ws.FinZenBack.models.Entities.Cuenta;
import com.FinZenBack.ws.FinZenBack.models.Entities.Meta;
import com.FinZenBack.ws.FinZenBack.repository.CuentaRepository;
import com.FinZenBack.ws.FinZenBack.repository.MetaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MetaServices {
    private final MetaRepository metaRepository;
    private final CuentaRepository cuentaRepository;

    public MetaServices(MetaRepository metaRepository, CuentaRepository cuentaRepository) {
        this.metaRepository = metaRepository;
        this.cuentaRepository = cuentaRepository;

    }

    public Meta createMeta( MetaDto miMeta) {
        Cuenta cuenta = cuentaRepository.findById(miMeta.getIdCuenta())
                .orElseThrow(() -> new RuntimeException("Cuenta no encontrada"));


        Meta meta = new Meta();

        meta.setTitulo(miMeta.getTitulo());
        meta.setDescripcion(miMeta.getDescripcion());
        meta.setFechaInicio(miMeta.getFechaInicio());
        meta.setFechaLimite(miMeta.getFechaLimite());
        meta.setEnProgreso(miMeta.getEnProgreso());
        meta.setEstado(miMeta.getEstado());
        meta.setValor(miMeta.getValor());
        meta.setCuenta(cuenta); // Asignar usuario a la meta

        return metaRepository.save(meta);
    }
    public List<Meta> getMetasByIdCuenta(long id) {
        return metaRepository.findByCuentaIdCuenta(id);

    }

    public Meta updateMeta(long idMeta, MetaDto miMeta) {
        // Buscar la meta por ID
        Meta meta = metaRepository.findById(idMeta)
                .orElseThrow(() -> new RuntimeException("Meta no encontrada"));

        // Actualizar los campos de la meta
        meta.setTitulo(miMeta.getTitulo());
        meta.setDescripcion(miMeta.getDescripcion());
        meta.setFechaInicio(miMeta.getFechaInicio());
        meta.setFechaLimite(miMeta.getFechaLimite());
        meta.setEnProgreso(miMeta.getEnProgreso());
        meta.setEstado(miMeta.getEstado());
        meta.setValor(miMeta.getValor());

        // Guardar la meta actualizada
        return metaRepository.save(meta);
    }

    public void deleteMeta(long idMeta) {
        // Verificar si la meta existe
        if (!metaRepository.existsById(idMeta)) {
            throw new RuntimeException("Meta no encontrada");
        }

        // Eliminar la meta
        metaRepository.deleteById(idMeta);
    }
}

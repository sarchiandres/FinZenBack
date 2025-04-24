package services;

import models.Meta;
import org.springframework.stereotype.Service;
import repository.MetaRepository;

import java.util.List;
import java.util.Optional;

@Service
public class MetaService {

    private final MetaRepository metaRepository;

    public MetaService(MetaRepository metaRepository) {
        this.metaRepository = metaRepository;
    }

    public List<Meta> obtenerTodas() {
        return metaRepository.findAll();
    }

    public Optional<Meta> obtenerPorId(Long id) {
        return metaRepository.findById(id);
    }

    public Meta guardar(Meta meta) {
        return metaRepository.save(meta);
    }

    public void eliminar(Long id) {
        metaRepository.deleteById(id);
    }
}




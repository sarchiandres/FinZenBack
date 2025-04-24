package services;

import models.Cuenta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.CuentaRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CuentaService {

    @Autowired
    private CuentaRepository cuentaRepository;

    public List<Cuenta> obtenerTodas() {
        return cuentaRepository.findAll();
    }

    public Optional<Cuenta> obtenerPorId(int id_cuenta) {
        return cuentaRepository.findById(id_cuenta);
    }

    public Cuenta guardar(Cuenta cuenta) {
        return cuentaRepository.save(cuenta);
    }

    public void eliminar(int id) {
        cuentaRepository.deleteById(id);
    }

    public List<Cuenta> obtenerPorUsuario(Long idUsuario) {
        return cuentaRepository.findByIdUsuario(idUsuario);
    }
}

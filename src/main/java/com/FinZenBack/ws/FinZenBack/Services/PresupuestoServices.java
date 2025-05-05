package com.FinZenBack.ws.FinZenBack.Services;

import com.FinZenBack.ws.FinZenBack.models.DTO.PresupuestoDto;
import com.FinZenBack.ws.FinZenBack.models.Entities.CategoriaPresupuesto;
import com.FinZenBack.ws.FinZenBack.models.Entities.Cuenta;
import com.FinZenBack.ws.FinZenBack.models.Entities.Presupuesto;
import com.FinZenBack.ws.FinZenBack.repository.CategoriaPresupuestoRepository;
import com.FinZenBack.ws.FinZenBack.repository.CuentaRepository;
import com.FinZenBack.ws.FinZenBack.repository.PresupuestoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PresupuestoServices {
    private final PresupuestoRepository presupuestoRepository;
    private final CuentaRepository cuentaRepository;
    private final CategoriaPresupuestoRepository categoriaPrepository;

    public PresupuestoServices(PresupuestoRepository presupuestoRepository, CuentaRepository cuentaRepository, CategoriaPresupuestoRepository categoriaPrepository) {
        this.presupuestoRepository = presupuestoRepository;
        this.cuentaRepository = cuentaRepository;
        this.categoriaPrepository = categoriaPrepository;
    }

    public Presupuesto createPresupuesto(PresupuestoDto presupuestoDto){
        Cuenta cuenta = cuentaRepository.findById(presupuestoDto.getIdCuenta())
                .orElseThrow(()-> new RuntimeException("la cuenta no se encontro "));

        CategoriaPresupuesto categoria = categoriaPrepository.findById(presupuestoDto.getIdCategory())
                .orElseThrow(()->new RuntimeException("La cateforia no se encontro"));

        Presupuesto miPresupuesto = new Presupuesto();

        miPresupuesto.setCuenta(cuenta);
        miPresupuesto.setNombre(presupuestoDto.getNombre());
        miPresupuesto.setMontoAsignado(presupuestoDto.getMontoAsignado());
        miPresupuesto.setCategoria(categoria);

        return presupuestoRepository.save(miPresupuesto);
    }

    public Presupuesto updatePresupuesto (long idPresupuesto, PresupuestoDto presupuestoDto){
        Presupuesto miPresuspuesto  = presupuestoRepository.findById(idPresupuesto)
                .orElseThrow(()-> new RuntimeException("El presupuesto no se encontro "));
        CategoriaPresupuesto categoria = categoriaPrepository.findById(presupuestoDto.getIdCategory())
                .orElseThrow(()-> new RuntimeException("La categoria no se ha encontrado"));
        Cuenta cuenta = cuentaRepository.findById(presupuestoDto.getIdCuenta())
                .orElseThrow(()->new RuntimeException("La cuenta no se encontro"));

        miPresuspuesto.setCategoria(categoria);
        miPresuspuesto.setCuenta(cuenta);
        miPresuspuesto.setNombre(presupuestoDto.getNombre());
        miPresuspuesto.setMontoAsignado(presupuestoDto.getMontoAsignado());

        return presupuestoRepository.save(miPresuspuesto);
    }

    public List<Presupuesto> getPresupuestos(Long idCuenta){
        return  presupuestoRepository.findByCuentaIdCuenta(idCuenta);
    }

    public void deletePresupuesto(long idPresuspuesto){
        if (!presupuestoRepository.existsById(idPresuspuesto)) {
            throw new RuntimeException("presupuesto  no encontrada");
        }

        // Eliminar la meta
        presupuestoRepository.deleteById(idPresuspuesto);
    }
}

package com.FinZenBack.ws.FinZenBack.Services;

import com.FinZenBack.ws.FinZenBack.models.DTO.DeudaDto;
import com.FinZenBack.ws.FinZenBack.models.Entities.Cuenta;
import com.FinZenBack.ws.FinZenBack.models.Entities.Deuda;
import com.FinZenBack.ws.FinZenBack.models.Entities.Meta;
import com.FinZenBack.ws.FinZenBack.repository.CuentaRepository;
import com.FinZenBack.ws.FinZenBack.repository.DeudaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeudaServices {

    @Autowired
    private final DeudaRepository deudaRepository;
    private final CuentaRepository cuentaRepository;

    public DeudaServices(DeudaRepository deudaRepository, CuentaRepository cuentaRepository) {
        this.deudaRepository = deudaRepository;
        this.cuentaRepository = cuentaRepository;
    }

    public Deuda CreateDeuda(DeudaDto deudaDto){
        Cuenta cuenta = cuentaRepository.findById(deudaDto.getIdCuenta())
                .orElseThrow(() -> new RuntimeException("Cuenta no encontrada"));

        Deuda miDeuda = new Deuda();

        miDeuda.setMonto(deudaDto.getMonto());
        miDeuda.setEstado(deudaDto.getEstado());
        miDeuda.setFechaVencimineto(deudaDto.getFechaVencimiento());
        miDeuda.setMontoPagado(deudaDto.getMontoPagado());
        miDeuda.setCuenta(cuenta);// Asignar usuario a la meta

        return deudaRepository.save(miDeuda);
    }

    public List<Deuda> getDeudas(long idCuenta){
        return deudaRepository.findByCuentaIdCuenta(idCuenta);
    }

    public Deuda updateDeuda (long idDeuda , DeudaDto deudaDto){
        Deuda miDeuda = deudaRepository.findById(idDeuda)
                .orElseThrow(() -> new RuntimeException("Deuda no encontrada"));

        miDeuda.setMonto(deudaDto.getMonto());
        miDeuda.setEstado(deudaDto.getEstado());
        miDeuda.setFechaVencimineto(deudaDto.getFechaVencimiento());
        miDeuda.setMontoPagado(deudaDto.getMontoPagado());

        return deudaRepository.save(miDeuda);
    }
    public void deleteMeta(long idMeta) {
        // Verificar si la meta existe
        if (!deudaRepository.existsById(idMeta)) {
            throw new RuntimeException("Meta no encontrada");
        }

        // Eliminar la meta
        deudaRepository.deleteById(idMeta);
    }
}

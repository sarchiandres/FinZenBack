package com.FinZenBack.ws.FinZenBack.Services;


import com.FinZenBack.ws.FinZenBack.models.DTO.DeudaDTO;
import com.FinZenBack.ws.FinZenBack.models.Entities.Deuda;

import com.FinZenBack.ws.FinZenBack.repository.DeudaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeudaServiceImpl implements DeudaService {

    private final DeudaRepository deudaRepository;

    @Autowired
    public DeudaServiceImpl(DeudaRepository deudaRepository) {
        this.deudaRepository = deudaRepository;
    }

    @Override
    public Deuda crearDeuda(DeudaDTO deudaDTO) {
        Deuda deuda = new Deuda();
        deuda.setIdCuenta(deudaDTO.getIdCuenta());
        deuda.setMonto(deudaDTO.getMonto());
        deuda.setFechaVencimiento(deudaDTO.getFechaVencimiento());
        deuda.setIdUsuario(deudaDTO.getIdUsuario());
        return deudaRepository.save(deuda);
    }

    @Override
    public List<Deuda> listarDeudas() {
        return deudaRepository.findAll();
    }
}

package com.FinZenBack.ws.FinZenBack.Services;

import com.FinZenBack.ws.FinZenBack.models.DTO.DeudaDTO;
import com.FinZenBack.ws.FinZenBack.models.Entities.Deuda;

import java.util.List;


public interface DeudaService {


    Deuda crearDeuda(DeudaDTO deudaDTO);

    List<Deuda> listarDeudas();
}

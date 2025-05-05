package com.FinZenBack.ws.FinZenBack.Services;


import com.FinZenBack.ws.FinZenBack.models.DTO.GastoDto;
import com.FinZenBack.ws.FinZenBack.models.Entities.CategoriaGasto;
import com.FinZenBack.ws.FinZenBack.models.Entities.Gasto;
import com.FinZenBack.ws.FinZenBack.models.Entities.Presupuesto;
import com.FinZenBack.ws.FinZenBack.repository.CategoriaGastoRepository;
import com.FinZenBack.ws.FinZenBack.repository.GastoRepository;
import com.FinZenBack.ws.FinZenBack.repository.PresupuestoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GastoServices {
    private final GastoRepository gastoRepository;
    private final PresupuestoRepository presupuestoRepository;
    private final CategoriaGastoRepository categoriaGastoRepository;

    public GastoServices(GastoRepository gastoRepository, PresupuestoRepository presupuestoRepository, CategoriaGastoRepository categoriaGastoRepository) {
        this.gastoRepository = gastoRepository;
        this.presupuestoRepository = presupuestoRepository;
        this.categoriaGastoRepository = categoriaGastoRepository;
    }

    public Gasto createGasto (GastoDto gastoDto){
        Presupuesto presuspuesto = presupuestoRepository.findById(gastoDto.getIdPresupuesto())
                .orElseThrow(()-> new RuntimeException(" el presupuesto no se encontro  "));
        CategoriaGasto categoriaGasto =categoriaGastoRepository.findById(gastoDto.getIdCategoria())
                .orElseThrow(()-> new RuntimeException("la categoria no se encontro "));


        Gasto miGasto = new Gasto();

        miGasto.setCategoria(categoriaGasto);
        miGasto.setPresupuesto(presuspuesto);
        miGasto.setDescripcion(gastoDto.getDescripcion());
        miGasto.setMonto(gastoDto.getMonto());
        miGasto.setFecha(gastoDto.getFecha());

        return gastoRepository.save(miGasto);

    }

    public List<Gasto> getGasto (Long idPresupeusto){
        return gastoRepository.findByPresupuestoIdPresupuesto(idPresupeusto);
    }

    public Gasto updateGasto (long id , GastoDto gastoDto){
        Presupuesto presuspuesto = presupuestoRepository.findById(gastoDto.getIdPresupuesto())
                .orElseThrow(()-> new RuntimeException(" el presupuesto no se encontro  "));
        CategoriaGasto categoriaGasto =categoriaGastoRepository.findById(gastoDto.getIdCategoria())
                .orElseThrow(()-> new RuntimeException("la categoria no se encontro "));


        Gasto miGasto = new Gasto();

        miGasto.setCategoria(categoriaGasto);
        miGasto.setPresupuesto(presuspuesto);
        miGasto.setDescripcion(gastoDto.getDescripcion());
        miGasto.setMonto(gastoDto.getMonto());
        miGasto.setFecha(gastoDto.getFecha());

        return gastoRepository.save(miGasto);
    }
    public void deleteGasto(long idGasto){
        if (!gastoRepository.existsById(idGasto)) {
            throw new RuntimeException("Meta no encontrada");
        }

        // Eliminar
        gastoRepository.deleteById(idGasto);
    }
}

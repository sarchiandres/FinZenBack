package services;

import models.Entities.TipoDocumento;
import repository.TipoDocumentoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TipoDocumentoServices {
    private final TipoDocumentoRepository TDrepository;

    public TipoDocumentoServices(TipoDocumentoRepository tDrepository) {
        TDrepository = tDrepository;
    }

    public List<TipoDocumento> getTipos (){
        return TDrepository.findAll();
    }
    public TipoDocumento createTipo(TipoDocumento tipoDocumento){
        return TDrepository.save(tipoDocumento);
    }
}

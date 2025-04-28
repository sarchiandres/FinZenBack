package controllers;

import services.TipoDocumentoServices;
import models.Entities.TipoDocumento;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/FinZen/TipoD")
public class TipoDocumentoController {
    private final TipoDocumentoServices TDservices;

    public TipoDocumentoController(TipoDocumentoServices tDservices) {
        TDservices = tDservices;
    }

    @GetMapping
    public ResponseEntity<List<TipoDocumento>> getTipos(){
        List<TipoDocumento> tipoDocumentos = TDservices.getTipos();
        return  ResponseEntity.ok(tipoDocumentos) ;
    }
}

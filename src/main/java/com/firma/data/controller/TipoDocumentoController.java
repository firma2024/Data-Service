package com.firma.data.controller;

import com.firma.data.model.TipoDocumento;
import com.firma.data.service.intf.ITipoDocumentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/data/documento")
public class TipoDocumentoController {

    @Autowired
    private ITipoDocumentoService tipoDocumentoService;

    @GetMapping("/all")
    public ResponseEntity<?> getAllTipoDocumentos() {
        return new ResponseEntity<>(tipoDocumentoService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/get")
    public ResponseEntity<?> getTipoDocumento(@RequestParam String documentName){
        TipoDocumento tipoDocumento = tipoDocumentoService.findByName(documentName);
        if (tipoDocumento == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(tipoDocumento, HttpStatus.OK);
    }
}

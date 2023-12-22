package com.firma.data.controller;

import com.firma.data.model.TipoProceso;
import com.firma.data.service.intf.ITipoProcesoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/api/data/tipoProceso")
public class TipoProcesoController {

    @Autowired
    private ITipoProcesoService tipoProcesoService;

    @GetMapping("/all")
    public ResponseEntity<?> getAllTipoProcesos() {
        return new ResponseEntity<>(tipoProcesoService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/get")
    public ResponseEntity<?> getTipoProceso(@RequestParam String tipoProcesoName) {
        TipoProceso tipoProceso = tipoProcesoService.findByName(tipoProcesoName);
        if (tipoProceso == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(tipoProceso, HttpStatus.OK);
    }
}

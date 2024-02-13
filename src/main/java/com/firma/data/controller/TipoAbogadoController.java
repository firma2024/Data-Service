package com.firma.data.controller;

import com.firma.data.model.TipoAbogado;
import com.firma.data.service.intf.ITipoAbogadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/data/tipoAbogado")
public class TipoAbogadoController {
    @Autowired
    private ITipoAbogadoService tipoAbogadoService;

    @GetMapping("/all")
    public ResponseEntity<?> getAllTipoAbogados() {
        return new ResponseEntity<>(tipoAbogadoService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/get")
    public ResponseEntity<?> getTipoAbogado(@RequestParam String tipoAbogadoName) {
        TipoAbogado tipoAbogado = tipoAbogadoService.findByName(tipoAbogadoName);
        if (tipoAbogado == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(tipoAbogado, HttpStatus.OK);
    }
}

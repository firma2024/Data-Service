package com.firma.data.controller;

import com.firma.data.model.EstadoProceso;
import com.firma.data.service.intf.IEstadoProcesoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/data/estadoProceso")
public class EstadoProcesoController {

    @Autowired
    private IEstadoProcesoService estadoProcesoService;

    @GetMapping("/all")
    public ResponseEntity<?> getAllEstadoProcesos() {
        return new ResponseEntity<>(estadoProcesoService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/get")
    public ResponseEntity<?> getEstadoProceso(@RequestParam String estadoProcesoName) {
        EstadoProceso estadoProceso = estadoProcesoService.findByName(estadoProcesoName);
        if (estadoProceso == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(estadoProceso, HttpStatus.OK);
    }
}

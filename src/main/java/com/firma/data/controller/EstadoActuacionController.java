package com.firma.data.controller;

import com.firma.data.model.EstadoActuacion;
import com.firma.data.service.intf.IEstadoActuacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/api/data/estadoActuacion")
public class EstadoActuacionController {

    @Autowired
    private IEstadoActuacionService estadoActuacionService;

    @GetMapping("/all")
    public ResponseEntity<?> getAllEstadoActuaciones() {
        return new ResponseEntity<>(estadoActuacionService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/get")
    public ResponseEntity<?> getEstadoActuacion(@RequestParam String estadoActuacionName) {
        EstadoActuacion estadoActuacion = estadoActuacionService.findByName(estadoActuacionName);
        if (estadoActuacion == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(estadoActuacion, HttpStatus.OK);
    }
}

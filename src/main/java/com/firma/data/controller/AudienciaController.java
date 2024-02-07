package com.firma.data.controller;

import com.firma.data.model.Audiencia;
import com.firma.data.model.Proceso;
import com.firma.data.payload.request.AudienciaRequest;
import com.firma.data.service.intf.IAudienciaService;
import com.firma.data.service.intf.IProcesoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/data/audiencia")
public class AudienciaController {

    @Autowired
    private IAudienciaService audienciaService;
    @Autowired
    private IProcesoService procesoService;

    @PutMapping("/update")
    public ResponseEntity <?> updateAudiencia(@RequestParam Integer id, @RequestParam String enlace){
        Audiencia audiencia = audienciaService.findByid(id);
        audiencia.setEnlace(enlace);
        audienciaService.updateAudiencia(audiencia);
        return new ResponseEntity<>("Audiencia Actualizada", HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<?> addAudiencia(@RequestBody AudienciaRequest audiencia) {
        Proceso proceso = procesoService.findById(audiencia.getProcesoid());
        Audiencia newAudiencia = Audiencia.builder()
                .enlace(audiencia.getEnlace())
                .nombre(audiencia.getNombre())
                .proceso(proceso)
                .build();

        audienciaService.saveAudiencia(newAudiencia);
        return new ResponseEntity<>("Audiencia Creada", HttpStatus.CREATED);
    }
}

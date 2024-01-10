package com.firma.data.controller;

import com.firma.data.model.Audiencia;
import com.firma.data.service.intf.IAudienciaService;
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

    @PutMapping("/update")
    public ResponseEntity <?> updateAudiencia(@RequestParam Integer id, @RequestBody String enlace){
        Audiencia audiencia = audienciaService.findByid(id);
        audiencia.setEnlace(enlace);
        audienciaService.updateAudiencia(audiencia);
        return new ResponseEntity<>(audiencia, HttpStatus.OK);
    }
}

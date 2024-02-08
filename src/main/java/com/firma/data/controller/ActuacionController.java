package com.firma.data.controller;

import com.firma.data.model.EstadoActuacion;
import com.firma.data.payload.request.ActuacionRequest;
import com.firma.data.service.intf.IActuacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@EnableTransactionManagement
@RequestMapping("/api/data/actuacion")
public class ActuacionController {

    @Autowired
    private IActuacionService actuacionService;

    @PostMapping("/save")
    public ResponseEntity<?> saveActuacion(@RequestBody Set<ActuacionRequest> actuacionRequest){
        return actuacionService.saveActuacion(actuacionRequest);
    }

    @GetMapping("/get/all/send")
    public ResponseEntity <?> getAllActuacionesNotSend(){
        return actuacionService.findAllActuacionesNotSend();
    }

    @PutMapping("/update/send")
    public ResponseEntity <?> updateActuacionSend(@RequestBody Set <Integer> actuacionesIds){
        return actuacionService.updateActuacionSend(actuacionesIds);
    }

    @GetMapping("/get/all/abogado/filter")
    public ResponseEntity <?> getAllActuacionesByProcesoAbogado(@RequestParam Integer procesoId,
                                                                @RequestParam(required = false) String fechaInicioStr,
                                                                @RequestParam(required = false) String fechaFinStr,
                                                                @RequestParam(required = false) Boolean existeDoc,
                                                                @RequestParam(defaultValue = "0") Integer page,
                                                                @RequestParam(defaultValue = "2") Integer size){
        return actuacionService.findActuacionesByProcessAbogadoFilter(procesoId, fechaInicioStr, fechaFinStr, existeDoc, page, size);
    }

    @GetMapping("/jefe/get/all/filter")
    public ResponseEntity<?> getActuacionesFilter(@RequestParam Integer procesoId,
                                                  @RequestParam(required = false) String fechaInicioStr,
                                                  @RequestParam(required = false) String fechaFinStr,
                                                  @RequestParam(required = false) String estadoActuacion,
                                                  @RequestParam(defaultValue = "0") Integer page,
                                                  @RequestParam(defaultValue = "2") Integer size){
        return actuacionService.findActuacionesByProcessJefeFilter(procesoId, fechaInicioStr, fechaFinStr, estadoActuacion, page, size);
    }

    @GetMapping("/get/")
    public ResponseEntity <?> getActuacion(@RequestParam Integer id){
        return actuacionService.findActuacion(id);
    }

    @GetMapping("/estadoActuacion/get/all")
    public ResponseEntity<?> getAllEstadoActuacion(){
        return actuacionService.findAllEstadoActuacion();
    }

    @GetMapping("/estadoActuacion/get")
    public ResponseEntity<?> getEstadoActuacion(@RequestParam String estadoActuacionName){
        EstadoActuacion estadoActuacion = actuacionService.findEstadoActuacionByName(estadoActuacionName);
        if (estadoActuacion == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(estadoActuacion);
    }
}

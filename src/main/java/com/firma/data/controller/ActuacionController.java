package com.firma.data.controller;

import com.firma.data.model.Actuacion;
import com.firma.data.model.RegistroCorreo;
import com.firma.data.intfService.IActuacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<?> saveActuacion(@RequestBody Actuacion actuacionRequest){
        return actuacionService.saveActuacion(actuacionRequest);
    }

    @PostMapping("/registroCorreo/save")
    public ResponseEntity<?> saveRegistroCorreo(@RequestBody RegistroCorreo registroCorreo){
        return actuacionService.saveRegistroCorreo(registroCorreo);
    }


    @PostMapping("/save/all")
    public ResponseEntity<?> saveActuacion(@RequestBody Set<Actuacion> actuacionRequest){
        return actuacionService.saveActuaciones(actuacionRequest);
    }

    @GetMapping("/get/all/send")
    public ResponseEntity <?> getAllActuacionesNotSend(){
        return actuacionService.findAllActuacionesNotSend();
    }

    @PutMapping("/update")
    public ResponseEntity <?> updateActuacionSend(@RequestBody Actuacion actuacion){
        return actuacionService.updateActuacionSend(actuacion);
    }

    @GetMapping("/get/all/abogado/filter")
    public ResponseEntity <?> getAllActuacionesByProcesoAbogado(@RequestParam Integer procesoId,
                                                                @RequestParam(required = false) String fechaInicioStr,
                                                                @RequestParam(required = false) String fechaFinStr,
                                                                @RequestParam(required = false) Boolean existeDoc,
                                                                @RequestParam(defaultValue = "0") Integer page,
                                                                @RequestParam(defaultValue = "5") Integer size){
        return actuacionService.findActuacionesByProcessAbogadoFilter(procesoId, fechaInicioStr, fechaFinStr, existeDoc, page, size);
    }

    @GetMapping("/jefe/get/all/filter")
    public ResponseEntity<?> getActuacionesFilter(@RequestParam Integer procesoId,
                                                  @RequestParam(required = false) String fechaInicioStr,
                                                  @RequestParam(required = false) String fechaFinStr,
                                                  @RequestParam(required = false) String estadoActuacion,
                                                  @RequestParam(defaultValue = "0") Integer page,
                                                  @RequestParam(defaultValue = "5") Integer size){
        return actuacionService.findActuacionesByProcessJefeFilter(procesoId, fechaInicioStr, fechaFinStr, estadoActuacion, page, size);
    }

    @GetMapping("/get/")
    public ResponseEntity <?> getActuacion(@RequestParam Integer id){
        return actuacionService.findActuacion(id);
    }

    @GetMapping("/get/last")
    public ResponseEntity <?> getLastActuacionByProcess(@RequestParam Integer processid){
        return actuacionService.findLastActuacion(processid);
    }

    @GetMapping("/get/byNoVisto")
    public ResponseEntity<?> getActuacionesByNoVisto(@RequestParam Integer firmaId){
        return new ResponseEntity<>(actuacionService.findByNoVisto(firmaId), HttpStatus.OK);
    }

    @GetMapping("/estadoActuacion/get/all")
    public ResponseEntity<?> getAllEstadoActuacion(){
        return actuacionService.findAllEstadoActuacion();
    }

    @GetMapping("/estadoActuacion/get")
    public ResponseEntity<?> getEstadoActuacion(@RequestParam String state){
        return actuacionService.findEstadoActuacionByName(state);
    }
}

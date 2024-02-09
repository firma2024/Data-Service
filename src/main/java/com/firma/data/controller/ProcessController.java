package com.firma.data.controller;

import com.firma.data.payload.request.AudienciaRequest;
import com.firma.data.payload.request.EnlaceRequest;
import com.firma.data.payload.request.ProcesoRequest;
import com.firma.data.service.intf.IProcessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@EnableTransactionManagement
@RequestMapping("/api/data/process")
public class ProcessController {

    @Autowired
    private IProcessService processService;

    @PostMapping("/save")
    public ResponseEntity<?> saveProcess(@RequestBody ProcesoRequest procesoRequest) {
        return processService.saveProcess(procesoRequest);
    }

    @GetMapping("/get/jefe")
    public ResponseEntity<?> getProcessJefe(@RequestParam Integer procesoId){
        return processService.findProcessJefe(procesoId);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateProcess(@RequestParam Integer processId, @RequestBody ProcesoRequest procesoRequest){
        return processService.updateProcess(processId, procesoRequest);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteProcess(@RequestParam Integer processId){
        return processService.deleteProcess(processId);
    }

    @GetMapping("/get/all/firma/filter")
    public ResponseEntity<?> getProcessByFirmaFilter(@RequestParam(required = false) String fechaInicioStr,
                                                      @RequestParam Integer firmaId,
                                                      @RequestParam(required = false) String fechaFinStr,
                                                      @RequestParam(required = false) List<String> estadosProceso,
                                                      @RequestParam(required = false) String tipoProceso,
                                                      @RequestParam(defaultValue = "0") Integer page,
                                                      @RequestParam(defaultValue = "10") Integer size){
        return processService.findProcessByFirmaFilter(firmaId, fechaInicioStr, fechaFinStr, estadosProceso, tipoProceso, page, size);
    }

    @GetMapping("/get/all/abogado/filter")
    public ResponseEntity<?> getProcessAbogado(@RequestParam Integer abogadoId,
                                                @RequestParam(required = false) String fechaInicioStr,
                                                @RequestParam(required = false) String fechaFinStr,
                                                @RequestParam(required = false) List<String> estadosProceso,
                                                @RequestParam(required = false) String tipoProceso,
                                                @RequestParam(defaultValue = "0") Integer page,
                                                @RequestParam(defaultValue = "10") Integer size){
        return processService.findProcessByAbogadoFilter(abogadoId, fechaInicioStr, fechaFinStr, estadosProceso, tipoProceso, page, size);
    }

    @GetMapping("/get/all")
    public ResponseEntity<?> getAllProcesses(){
        return processService.findAllProcesses();
    }

    @GetMapping("/get/all/estado")
    public ResponseEntity<?> getAllByEstado(@RequestParam String name, @RequestParam Integer firmaId){
        return processService.findAllByEstadoFirma(firmaId, name);
    }

    @GetMapping("/get/all/estado/abogado")
    public ResponseEntity<?> getAllByEstadoAbogado(@RequestParam String name, @RequestParam String userName){
        return processService.findAllByEstadoAbogado(userName, name);
    }

    @GetMapping("/get/abogado")
    public ResponseEntity<?> getProcessAbogado(@RequestParam Integer procesoId){
        return processService.findProcessAbogado(procesoId);
    }

    @GetMapping("/estadoProceso/get/all")
    public ResponseEntity<?> getAllEstadoProceso(){
        return processService.findAllEstadoProceso();
    }

    @GetMapping("/estadoProceso/get")
    public ResponseEntity<?> getEstadoProcesoByName(@RequestParam String name){
        return processService.findEstadoProcesoByName(name);
    }

    @PutMapping("/audiencia/update")
    public ResponseEntity<?> updateAudiencia(@RequestParam Integer id, @RequestParam String enlace){
        return processService.updateAudiencia(id, enlace);
    }

    @PostMapping("/audiencia/save")
    public ResponseEntity<?> saveAudiencia(@RequestBody AudienciaRequest audiencia){
        return processService.saveAudiencia(audiencia);
    }

    @GetMapping("/despacho/get/all/notlink")
    public ResponseEntity<?> getAllDespachosWithOutLink(@RequestParam String year){
        return processService.findAllDespachoWithOutLink(year);
    }

    @GetMapping("/despacho/get")
    public ResponseEntity<?> getDespachoByProceso(@RequestParam Integer procesoId){
        return processService.findDespachoByProcess(procesoId);
    }

    @GetMapping("/tipoProceso/get/all")
    public ResponseEntity<?> getAllTipoProceso(){
        return processService.findAllTipoProceso();
    }

    @GetMapping("/tipoProceso/get")
    public ResponseEntity<?> getTipoProcesoByName(@RequestParam String name){
        return processService.findTipoProcesoByName(name);
    }

    @PostMapping("/enlace/save")
    public ResponseEntity<?> saveEnlace(@RequestBody EnlaceRequest enlaceRequest){
        return processService.saveEnlace(enlaceRequest);
    }
}

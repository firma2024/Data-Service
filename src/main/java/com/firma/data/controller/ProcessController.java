package com.firma.data.controller;

import com.firma.data.model.Audiencia;
import com.firma.data.model.Enlace;
import com.firma.data.model.Proceso;
import com.firma.data.payload.request.ProcessRequest;
import com.firma.data.intfService.IProcessService;
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
    public ResponseEntity<?> saveProcess(@RequestBody ProcessRequest procesoRequest) {
        return processService.saveProcess(procesoRequest);
    }

    @GetMapping("/get")
    public ResponseEntity<?> getProcess(@RequestParam Integer procesoId){
        return processService.findProcessById(procesoId);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateProcess(@RequestBody Proceso process){
        return processService.updateProcess(process);
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

    @GetMapping("/get/radicado")
    public ResponseEntity<?> getProcessByRadicado(@RequestParam String radicado){
        return processService.findProcessByRadicado(radicado);
    }

    @GetMapping("/get/all/estado")
    public ResponseEntity<?> getAllByEstado(@RequestParam String name, @RequestParam Integer firmaId){
        return processService.findAllByEstadoFirma(firmaId, name);
    }

    @GetMapping("/get/all/estado/abogado")
    public ResponseEntity<?> getAllByEstadoAbogado(@RequestParam String name, @RequestParam String userName){
        return processService.findAllByEstadoAbogado(userName, name);
    }

    @GetMapping("/estadoProceso/get/all")
    public ResponseEntity<?> getAllEstadoProceso(){
        return processService.findAllEstadoProceso();
    }

    @GetMapping("/estadoProceso/get")
    public ResponseEntity<?> getEstadoProcesoByName(@RequestParam String name){
        return processService.findEstadoProcesoByName(name);
    }
    @GetMapping("/audiencia/get/all")
    public ResponseEntity<?> getAllAudienciasByProceso(@RequestParam Integer procesoId){
        return processService.findAllAudienciasByProceso(procesoId);
    }

    @PutMapping("/audiencia/update")
    public ResponseEntity<?> updateAudiencia(@RequestParam Integer id, @RequestParam String enlace){
        return processService.updateAudiencia(id, enlace);
    }

    @PostMapping("/audiencia/save")
    public ResponseEntity<?> saveAudiencia(@RequestBody Audiencia audiencia){
        return processService.saveAudiencia(audiencia);
    }

    @GetMapping("/despacho/get/all/notlink")
    public ResponseEntity<?> getAllDespachosWithOutLink(@RequestParam String year){
        return processService.findAllDespachoWithOutLink(year);
    }

    @GetMapping("/despacho/get")
    public ResponseEntity<?> getDespachoByProceso(@RequestParam String name){
        return processService.findDespachoByName(name);
    }

    @GetMapping("/despacho/get/id")
    public ResponseEntity<?> getDespachoById(@RequestParam Integer despachoid){
        return processService.findDespachoById(despachoid);
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
    public ResponseEntity<?> saveEnlace(@RequestBody Enlace enlaceRequest){
        return processService.saveEnlace(enlaceRequest);
    }

    @GetMapping("/enlace/get")
    public ResponseEntity<?> getEnlaceByDespachoAndYear(@RequestParam Integer id, @RequestParam String year){
        return processService.findEnlaceByDespachoAndYear(id, year);
    }

    @GetMapping("/despacho/get/all/date")
    public ResponseEntity<?> getAllDespachosWithDateActuacion(){
        return processService.findAllDespachosWithDateActuacion();
    }
}

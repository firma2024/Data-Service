package com.firma.data.controller;

import com.firma.data.model.*;
import com.firma.data.payload.request.ActuacionRequest;
import com.firma.data.payload.response.ActuacionJefeResponse;
import com.firma.data.payload.response.ActuacionResponse;
import com.firma.data.payload.response.FileResponse;
import com.firma.data.service.intf.*;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/data/actuacion")
@EnableTransactionManagement
public class ActuacionController {

    @Autowired
    private IActuacionService actuacionService;
    @Autowired
    private IProcesoService procesoService;
    @Autowired
    private IEstadoActuacionService estadoActuacionService;
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    @Autowired
    private IRegistroCorreoService registroCorreoService;
    @Autowired
    private IStorageService storageService;
    @Autowired
    private IEnlaceService enlaceService;

    @GetMapping("/jefe/actuaciones/filter")
    public ResponseEntity<?> getActuacionesFilter(@RequestParam Integer procesoId,
                                                  @RequestParam(required = false) String fechaInicioStr,
                                                  @RequestParam(required = false) String fechaFinStr,
                                                  @RequestParam(required = false) String estadoActuacion,
                                                  @RequestParam(required = false) boolean existDocument){

        LocalDate fechaInicio = null;
        LocalDate fechaFin = null;

        if (fechaInicioStr != null && !fechaInicioStr.isEmpty()) {
            fechaInicio = LocalDate.parse(fechaInicioStr);
        }
        if (fechaFinStr != null && !fechaFinStr.isEmpty()) {
            fechaFin = LocalDate.parse(fechaFinStr);
        }
        Set<Actuacion> actuacionesFilter = actuacionService.findByFiltros(procesoId, fechaInicio, fechaFin, estadoActuacion, existDocument);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        List<ActuacionJefeResponse> actuacionesResponse = new ArrayList<>();
        
        for (Actuacion actuacion : actuacionesFilter) {
            ActuacionJefeResponse act = ActuacionJefeResponse.builder()
                    .nombreActuacion(actuacion.getActuacion())
                    .fechaActuacion(actuacion.getFechaactuacion().format(formatter))
                    .fechaRegistro(actuacion.getFecharegistro().format(formatter))
                    .anotacion(actuacion.getAnotacion())
                    .existDocument(actuacion.getExistedoc())
                    .estado(actuacion.getEstadoactuacion().getNombre())
                    .build();
            actuacionesResponse.add(act);
        }

        return ResponseEntity.ok(actuacionesResponse);
    }

    @GetMapping("/jefe/actuaciones")
    public ResponseEntity <?> getActuacionesByProceso(@RequestParam Integer procesoId){
        Proceso proceso = procesoService.findById(procesoId);
        if (proceso == null) {
            return new ResponseEntity<>("Proceso no encontrado", HttpStatus.NOT_FOUND);
        }
        Set<Actuacion> actuaciones = actuacionService.findAllByProceso(procesoId);
        List<ActuacionJefeResponse> actuacionesResponse = new ArrayList<>();

        for (Actuacion actuacion : actuaciones) {
            ActuacionJefeResponse act = ActuacionJefeResponse.builder()
                    .nombreActuacion(actuacion.getActuacion())
                    .fechaActuacion(actuacion.getFechaactuacion().format(formatter))
                    .fechaRegistro(actuacion.getFecharegistro().format(formatter))
                    .anotacion(actuacion.getAnotacion())
                    .existDocument(actuacion.getExistedoc())
                    .estado(actuacion.getEstadoactuacion().getNombre())
                    .build();
            actuacionesResponse.add(act);
        }

        return new ResponseEntity<>(actuacionesResponse, HttpStatus.OK);
    }

    @Transactional
    @PostMapping("/save")
    public ResponseEntity <?> saveActuacion(@RequestBody Set <ActuacionRequest> actuacionRequest){
        EstadoActuacion estadoActuacion = estadoActuacionService.findByName("No Visto");
        for (ActuacionRequest ac : actuacionRequest){
            Proceso proceso = procesoService.findByRadicado(ac.getProceso());
            if (proceso == null) {
                return new ResponseEntity<>("Process not found", HttpStatus.NOT_FOUND);
            }
            DateTimeFormatter formatterTime = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
            Actuacion actuacion = Actuacion.builder()
                    .actuacion(ac.getNombreActuacion())
                    .anotacion(ac.getAnotacion())
                    .enviado('N')
                    .fechaactuacion(LocalDateTime.parse(ac.getFechaActuacion(), formatterTime).toLocalDate())
                    .fecharegistro(LocalDateTime.parse(ac.getFechaRegistro(), formatterTime).toLocalDate())
                    .proceso(proceso)
                    .existedoc(ac.isExistDocument())
                    .estadoactuacion(estadoActuacion)
                    .build();

            if (ac.getFechaInicia() != null && ac.getFechaFinaliza() != null){
                actuacion.setFechainicia(LocalDateTime.parse(ac.getFechaInicia(), formatterTime).toLocalDate());
                actuacion.setFechafinaliza(LocalDateTime.parse(ac.getFechaFinaliza(), formatterTime).toLocalDate());
            }
            actuacionService.saveActuacion(actuacion);
        }
        return new ResponseEntity<>("Actuaciones have been saved", HttpStatus.OK);
    }

    @GetMapping("/get/all/send")
    public ResponseEntity <?> getAllActuacionesNotSend(){
        Set<Actuacion> actuaciones = actuacionService.findAllByNoSend();
        List<ActuacionResponse> actuacionesResponse = new ArrayList<>();
        for (Actuacion actuacion : actuaciones){
            ActuacionResponse res = ActuacionResponse.builder()
                    .id(actuacion.getId())
                    .demandante(actuacion.getProceso().getDemandante())
                    .demandado(actuacion.getProceso().getDemandado())
                    .actuacion(actuacion.getActuacion())
                    .radicado(actuacion.getProceso().getRadicado())
                    .anotacion(actuacion.getAnotacion())
                    .fechaActuacion(actuacion.getFechaactuacion().format(formatter))
                    .emailAbogado(actuacion.getProceso().getEmpleado().getUsuario().getCorreo())
                    .nameAbogado(actuacion.getProceso().getEmpleado().getUsuario().getNombres())
                    .build();
            actuacionesResponse.add(res);
        }
        return new ResponseEntity<>(actuacionesResponse, HttpStatus.OK);
    }

    @Transactional
    @PutMapping("/update/send")
    public ResponseEntity <?> updateActuacionSend(@RequestBody Set <Integer> actuacionesIds){
        for (Integer actuacionId : actuacionesIds){
            Actuacion actuacion = actuacionService.findById(actuacionId);
            if (actuacion == null) {
                return new ResponseEntity<>("Actuacion not found", HttpStatus.NOT_FOUND);
            }
            actuacion.setEnviado('Y');
            actuacionService.saveActuacion(actuacion);

            RegistroCorreo reg = RegistroCorreo.builder()
                    .correo(actuacion.getProceso().getEmpleado().getUsuario().getCorreo())
                    .fecha(LocalDateTime.now())
                    .build();

            registroCorreoService.save(reg);
        }
        return new ResponseEntity<>("Sent status updated", HttpStatus.OK);
    }

    @GetMapping("/abogado/all")
    public ResponseEntity <?> getAllActuacionesByProcesoAbogado(@RequestParam Integer procesoId){
        Set<Actuacion> actuaciones = actuacionService.findAllByProceso(procesoId);
        List<ActuacionResponse> actuacionesResponse = new ArrayList<>();
        for (Actuacion actuacion : actuaciones){
            ActuacionResponse res = ActuacionResponse.builder()
                    .id(actuacion.getId())
                    .actuacion(actuacion.getActuacion())
                    .anotacion(actuacion.getAnotacion())
                    .existeDocumento(actuacion.getExistedoc())
                    .fechaActuacion(actuacion.getFechaactuacion().format(formatter))
                    .fechaRegistro(actuacion.getFecharegistro().format(formatter))
                    .build();
            actuacionesResponse.add(res);
        }
        return new ResponseEntity<>(actuacionesResponse, HttpStatus.OK);
    }

    @GetMapping("/get")
    public ResponseEntity <?> getAudiencia(@RequestParam Integer id){
        Actuacion actuacion = actuacionService.findById(id);
        if (actuacion == null) {
            return new ResponseEntity<>("Actuacion not found", HttpStatus.NOT_FOUND);
        }

        String link = null;

        if (actuacion.getExistedoc() && actuacion.getDocumento() == null){
            DateTimeFormatter yearFormatter = DateTimeFormatter.ofPattern("yyyy");
            String year = yearFormatter.format(actuacion.getFechaactuacion());
            link = enlaceService.findByDespachoAndYear(actuacion.getProceso().getDespacho().getId(), year).getUrl();
        }

        ActuacionResponse res = ActuacionResponse.builder()
                .id(actuacion.getId())
                .demandado(actuacion.getProceso().getDemandado())
                .demandante(actuacion.getProceso().getDemandante())
                .despacho(actuacion.getProceso().getDespacho().getNombre())
                .tipoProceso(actuacion.getProceso().getTipoproceso().getNombre())
                .actuacion(actuacion.getActuacion())
                .anotacion(actuacion.getAnotacion())
                .existeDocumento(actuacion.getExistedoc())
                .fechaInicia(actuacion.getFechainicia().format(formatter))
                .fechaFinaliza(actuacion.getFechafinaliza().format(formatter))
                .fechaActuacion(actuacion.getFechaactuacion().format(formatter))
                .fechaRegistro(actuacion.getFecharegistro().format(formatter))
                .existeDocumento(actuacion.getExistedoc())
                .link(link)
                .build();

        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @Transactional
    @PostMapping("/upload/document")
    public ResponseEntity <?> uploadDocument(@RequestParam("doc") MultipartFile file, @RequestParam Integer actuacionId){
        try {
            storageService.uploadDocument(file, actuacionId);
        } catch (IOException e) {
            return new ResponseEntity<>("Error to upload file", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/download/document")
    public ResponseEntity <?> downloadDocument(@RequestParam Integer actuacionId){
        byte[] document = storageService.downloadDocument(actuacionId);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .body(document);
    }

    @GetMapping("/dowload/all/documents")
    public ResponseEntity <?> downloadAllDocuments(@RequestParam Integer procesoId){
        try {
            FileResponse fileResponse = storageService.downloadAllDocuments(procesoId);
            return ResponseEntity.ok()
                    .contentLength(fileResponse.getFile().length)
                    .header("Content-Disposition",  String.format("attachment; filename=\"%s\"", fileResponse.getFileName()))
                    .body(fileResponse.getFile());
        } catch (IOException e) {
            return new ResponseEntity<>("Error to download documents", HttpStatus.BAD_REQUEST);
        }
    }
}

package com.firma.data.controller;

import com.firma.data.model.Actuacion;
import com.firma.data.model.Proceso;
import com.firma.data.payload.response.ActuacionJefeResponse;
import com.firma.data.service.intf.IActuacionService;
import com.firma.data.service.intf.IProcesoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/api/data/actuacion")
public class ActuacionController {

    @Autowired
    private IActuacionService actuacionService;
    @Autowired
    private IProcesoService procesoService;
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @GetMapping("/jefe/actuaciones/filter")
    public ResponseEntity<?> getActuacionesFilter(@RequestParam Integer procesoId,
                                                  @RequestParam(required = false) String fechaInicioStr,
                                                  @RequestParam(required = false) String fechaFinStr,
                                                  @RequestParam(required = false) String estadoActuacion){

        LocalDate fechaInicio = null;
        LocalDate fechaFin = null;

        if (fechaInicioStr != null && !fechaInicioStr.isEmpty()) {
            fechaInicio = LocalDate.parse(fechaInicioStr);
        }
        if (fechaFinStr != null && !fechaFinStr.isEmpty()) {
            fechaFin = LocalDate.parse(fechaFinStr);
        }
        Set<Actuacion> actuacionesFilter = actuacionService.findByFiltros(procesoId, fechaInicio, fechaFin, estadoActuacion);
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
}

package com.firma.data.controller;

import com.firma.data.model.*;
import com.firma.data.payload.request.ActuacionRequest;
import com.firma.data.payload.request.ProcesoRequest;
import com.firma.data.payload.response.ActuacionJefeResponse;
import com.firma.data.payload.response.ProcesoJefeResponse;
import com.firma.data.service.intf.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Controller
@EnableTransactionManagement
@RequestMapping("/api/data/proceso")
public class ProcesoController {

    @Autowired
    private IProcesoService procesoService;
    @Autowired
    private IActuacionService actuacionService;
    @Autowired
    private IFirmaService firmaService;
    @Autowired
    private IEmpleadoService empleadoService;
    @Autowired
    private ITipoProcesoService tipoProcesoService;
    @Autowired
    private IDespachoService despachoService;
    @Autowired
    private IEstadoProcesoService estadoProcesoService;
    @Autowired
    private IEstadoActuacionService estadoActuacionService;


    @Transactional
    @PostMapping("/save")
    public ResponseEntity<?> saveProceso(@RequestBody ProcesoRequest procesoRequest) {
        try {
            Firma firma = firmaService.findById(procesoRequest.getIdFirma());
            if (firma == null) {
                return new ResponseEntity<>("Firma no encontrada", HttpStatus.NOT_FOUND);
            }
            Empleado empleado = empleadoService.findEmpleadoByUsuario(procesoRequest.getIdAbogado());
            if (empleado == null) {
                return new ResponseEntity<>("Empleado no encontrado", HttpStatus.NOT_FOUND);
            }
            TipoProceso tipoProceso = tipoProcesoService.findByName(procesoRequest.getTipoProceso());

            if (tipoProceso == null) {
                tipoProceso = tipoProcesoService.saveTipoProceso(TipoProceso.builder().nombre(procesoRequest.getTipoProceso()).build());
            }
            Despacho despacho = despachoService.findDespachoByNombre(procesoRequest.getDespacho());

            if (despacho == null) {
                despacho = despachoService.saveDespacho(Despacho.builder().nombre(procesoRequest.getDespacho()).build());
            }

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

            LocalDateTime dateRadicado = LocalDateTime.parse(procesoRequest.getFechaRadicacion(), formatter);

            EstadoProceso estadoProceso = estadoProcesoService.findByName("Activo");

            Proceso newProceso = Proceso.builder()
                    .radicado(procesoRequest.getNumeroRadicado())
                    .numeroproceso(procesoRequest.getIdProceso())
                    .demandado(procesoRequest.getDemandado())
                    .demandante(procesoRequest.getDemandante())
                    .fecharadicado(dateRadicado.toLocalDate())
                    .ubicacionexpediente(procesoRequest.getUbicacionExpediente())
                    .eliminado('N')
                    .despacho(despacho)
                    .tipoproceso(tipoProceso)
                    .estadoproceso(estadoProceso)
                    .empleado(empleado)
                    .firma(firma)
                    .build();

            newProceso = procesoService.saveProceso(newProceso);

            EstadoActuacion estadoActuacion = estadoActuacionService.findByName("Visto");
            List<Actuacion> actuaciones = new ArrayList<>();

            for (ActuacionRequest actuacionReq : procesoRequest.getActuaciones()) {
                LocalDateTime dateActuacion = LocalDateTime.parse(actuacionReq.getFechaActuacion(), formatter);
                LocalDateTime dateRegistro = LocalDateTime.parse(actuacionReq.getFechaRegistro(), formatter);
                Actuacion newActuacion = Actuacion.builder()
                        .proceso(newProceso)
                        .anotacion(actuacionReq.getAnotacion())
                        .actuacion(actuacionReq.getNombreActuacion())
                        .estadoactuacion(estadoActuacion)
                        .fechaactuacion(dateActuacion.toLocalDate())
                        .fecharegistro(dateRegistro.toLocalDate())
                        .documento(null)
                        .existedoc(actuacionReq.isExistDocument())
                        .build();
                actuaciones.add(newActuacion);
            }

            actuacionService.saveAllActuaciones(actuaciones);
            return new ResponseEntity<>("Proceso almacenado", HttpStatus.NO_CONTENT);

        } catch (Exception e) {
            return new ResponseEntity<>("Error al almacenar el proceso", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/get/firma")
    public ResponseEntity<?> getProcesos(@RequestParam Integer firmaId) {
        Set<Proceso> procesos = procesoService.findAllByFirma(firmaId);
        List<ProcesoJefeResponse> responses = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        for (Proceso proceso : procesos) {
            boolean estado = false;
            List<Actuacion> actuaciones = actuacionService.findByNoVisto(proceso.getFirma().getId());
            if (actuaciones.size() > 0) {
                estado = true;
            }
            ProcesoJefeResponse response = ProcesoJefeResponse.builder()
                    .id(proceso.getId())
                    .numeroRadicado(proceso.getRadicado())
                    .tipoProceso(proceso.getTipoproceso().getNombre())
                    .despacho(proceso.getDespacho().getNombre())
                    .abogado(proceso.getEmpleado().getUsuario().getNombres())
                    .fechaRadicacion(proceso.getFecharadicado().format(formatter))
                    .estadoVisto(estado)
                    .build();
            responses.add(response);
        }
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }

    @GetMapping("/get")
    public ResponseEntity<?> getProceso(@RequestParam Integer procesoId) {
        Proceso proceso = procesoService.findById(procesoId);
        if (proceso == null) {
            return new ResponseEntity<>("Proceso no encontrado", HttpStatus.NOT_FOUND);
        }
        Set<Actuacion> actuaciones = actuacionService.findAllByProceso(procesoId);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
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

        ProcesoJefeResponse response = ProcesoJefeResponse.builder()
                .id(proceso.getId())
                .numeroRadicado(proceso.getRadicado())
                .tipoProceso(proceso.getTipoproceso().getNombre())
                .demandado(proceso.getDemandado())
                .demandante(proceso.getDemandante())
                .abogado(proceso.getEmpleado().getUsuario().getNombres())
                .actuaciones(actuacionesResponse)
                .estado(proceso.getEstadoproceso().getNombre())
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Transactional
    @PutMapping("/update")
    public ResponseEntity<?> updateProceso(@RequestParam Integer procesoId, @RequestBody ProcesoRequest procesoRequest) {
        Proceso proceso = procesoService.findById(procesoId);
        if (proceso == null) {
            return new ResponseEntity<>("Proceso no encontrado", HttpStatus.NOT_FOUND);
        }
        if (procesoRequest.getIdAbogado() != null) {
            Empleado empleado = empleadoService.findEmpleadoByUsuario(procesoRequest.getIdAbogado());
            proceso.setEmpleado(empleado);
        }
        if (procesoRequest.getEstadoProceso() != null) {
            EstadoProceso estadoProceso = estadoProcesoService.findByName(procesoRequest.getEstadoProceso());
            proceso.setEstadoproceso(estadoProceso);
        }
        procesoService.updateProceso(proceso);
        return new ResponseEntity<>("Proceso actualizado", HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteProceso(@RequestParam Integer procesoId) {
        Proceso proceso = procesoService.findById(procesoId);
        if (proceso == null) {
            return new ResponseEntity<>("Proceso no encontrado", HttpStatus.NOT_FOUND);
        }
        EstadoProceso estadoProceso = estadoProcesoService.findByName("Retirado");
        proceso.setEliminado('S');
        proceso.setEstadoproceso(estadoProceso);
        procesoService.updateProceso(proceso);
        return new ResponseEntity<>("Proceso eliminado", HttpStatus.NO_CONTENT);
    }

    @GetMapping("/jefe/procesos/filter")
    public ResponseEntity<?> getProcesosFilter(@RequestParam(required = false) String fechaInicioStr,
                                               @RequestParam(required = false) String fechaFinStr,
                                               @RequestParam(required = false) List<String> estadosProceso,
                                               @RequestParam(required = false) String tipoProceso) {
        LocalDate fechaInicio = null;
        LocalDate fechaFin = null;

        if (fechaInicioStr != null && !fechaInicioStr.isEmpty()) {
            fechaInicio = LocalDate.parse(fechaInicioStr);
        }
        if (fechaFinStr != null && !fechaFinStr.isEmpty()) {
            fechaFin = LocalDate.parse(fechaFinStr);
        }
        Set<Proceso> procesosFiltrados = procesoService.findByFiltros(fechaInicio, fechaFin, estadosProceso, tipoProceso);

        List<ProcesoJefeResponse> responses = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        for (Proceso proceso : procesosFiltrados) {
            boolean estado = false;
            List<Actuacion> actuaciones = actuacionService.findByNoVisto(proceso.getFirma().getId());
            if (actuaciones.size() > 0) {
                estado = true;
            }
            ProcesoJefeResponse response = ProcesoJefeResponse.builder()
                    .id(proceso.getId())
                    .numeroRadicado(proceso.getRadicado())
                    .tipoProceso(proceso.getTipoproceso().getNombre())
                    .despacho(proceso.getDespacho().getNombre())
                    .abogado(proceso.getEmpleado().getUsuario().getNombres())
                    .fechaRadicacion(proceso.getFecharadicado().format(formatter))
                    .estadoVisto(estado)
                    .build();
            responses.add(response);
        }

        return new ResponseEntity<>(responses, HttpStatus.OK);
    }

}

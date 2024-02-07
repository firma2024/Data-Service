package com.firma.data.controller;

import com.firma.data.model.*;
import com.firma.data.payload.request.ActuacionRequest;
import com.firma.data.payload.request.ProcesoRequest;
import com.firma.data.payload.response.PageableResponse;
import com.firma.data.payload.response.ProcesoAbogadoResponse;
import com.firma.data.payload.response.ProcesoJefeResponse;
import com.firma.data.payload.response.ProcesoResponse;
import com.firma.data.service.intf.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.data.domain.Page;
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

@RestController
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
    @Autowired
    private IAudienciaService audienciaService;
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");


    @Transactional
    @PostMapping("/save")
    public ResponseEntity<?> saveProceso(@RequestBody ProcesoRequest procesoRequest) {
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

        DateTimeFormatter formatterTime = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

        LocalDateTime dateRadicado = LocalDateTime.parse(procesoRequest.getFechaRadicacion(), formatterTime);

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
            LocalDateTime dateActuacion = LocalDateTime.parse(actuacionReq.getFechaActuacion(), formatterTime);
            LocalDateTime dateRegistro = LocalDateTime.parse(actuacionReq.getFechaRegistro(), formatterTime);

            Actuacion newActuacion = Actuacion.builder()
                    .proceso(newProceso)
                    .anotacion(actuacionReq.getAnotacion())
                    .actuacion(actuacionReq.getNombreActuacion())
                    .estadoactuacion(estadoActuacion)
                    .fechaactuacion(dateActuacion.toLocalDate())
                    .fecharegistro(dateRegistro.toLocalDate())
                    .documento(null)
                    .enviado('Y')
                    .existedoc(actuacionReq.isExistDocument())
                    .build();

            if (actuacionReq.getFechaInicia() != null && actuacionReq.getFechaFinaliza() != null) {
                LocalDateTime dateInicia = LocalDateTime.parse(actuacionReq.getFechaInicia(), formatterTime);
                LocalDateTime dateFinaliza = LocalDateTime.parse(actuacionReq.getFechaFinaliza(), formatterTime);
                newActuacion.setFechainicia(dateInicia.toLocalDate());
                newActuacion.setFechafinaliza(dateFinaliza.toLocalDate());
            }

            actuaciones.add(newActuacion);
        }

        actuacionService.saveAllActuaciones(actuaciones);
        return new ResponseEntity<>("Proceso almacenado", HttpStatus.NO_CONTENT);
    }

    @GetMapping("/get/jefe")
    public ResponseEntity<?> getProceso(@RequestParam Integer procesoId) {
        Proceso proceso = procesoService.findById(procesoId);
        if (proceso == null) {
            return new ResponseEntity<>("Proceso no encontrado", HttpStatus.NOT_FOUND);
        }

        ProcesoJefeResponse response = ProcesoJefeResponse.builder()
                .id(proceso.getId())
                .numeroRadicado(proceso.getRadicado())
                .tipoProceso(proceso.getTipoproceso().getNombre())
                .demandado(proceso.getDemandado())
                .demandante(proceso.getDemandante())
                .abogado(proceso.getEmpleado().getUsuario().getNombres())
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
        return new ResponseEntity<>("Proceso actualizado", HttpStatus.OK);
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
        return new ResponseEntity<>("Proceso eliminado", HttpStatus.OK);
    }

    @GetMapping("/get/all/firma/filter")
    public ResponseEntity<?> getProcesosByFirmaFilter(@RequestParam(required = false) String fechaInicioStr,
                                                      @RequestParam Integer firmaId,
                                                      @RequestParam(required = false) String fechaFinStr,
                                                      @RequestParam(required = false) List<String> estadosProceso,
                                                      @RequestParam(required = false) String tipoProceso,
                                                      @RequestParam(defaultValue = "0") Integer page,
                                                      @RequestParam(defaultValue = "2") Integer size) {
        LocalDate fechaInicio = null;
        LocalDate fechaFin = null;

        if (fechaInicioStr != null && !fechaInicioStr.isEmpty()) {
            fechaInicio = LocalDate.parse(fechaInicioStr);
        }
        if (fechaFinStr != null && !fechaFinStr.isEmpty()) {
            fechaFin = LocalDate.parse(fechaFinStr);
        }
        Page<Proceso> pageProcesosFiltrados = procesoService.findByFiltros(fechaInicio, fechaFin, estadosProceso, tipoProceso, page, size, firmaId);

        List<ProcesoJefeResponse> responses = new ArrayList<>();

        for (Proceso proceso : pageProcesosFiltrados.getContent()) {
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

        PageableResponse<ProcesoJefeResponse> response = PageableResponse.<ProcesoJefeResponse>builder()
                .data(responses)
                .totalPages(pageProcesosFiltrados.getTotalPages())
                .totalItems(pageProcesosFiltrados.getTotalElements())
                .currentPage(pageProcesosFiltrados.getNumber() + 1)
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/get/all/abogado/filter")
    public ResponseEntity<?> getProcesosAbogado(@RequestParam Integer abogadoId,
                                                @RequestParam(required = false) String fechaInicioStr,
                                                @RequestParam(required = false) String fechaFinStr,
                                                @RequestParam(required = false) List<String> estadosProceso,
                                                @RequestParam(required = false) String tipoProceso,
                                                @RequestParam(defaultValue = "0") Integer page,
                                                @RequestParam(defaultValue = "2") Integer size) {
        Page<Proceso> pageProcesosAbogado = procesoService.findAllByAbogado(abogadoId, fechaInicioStr, fechaFinStr, estadosProceso, tipoProceso, page, size);
        List<ProcesoResponse> responses = new ArrayList<>();

        for (Proceso proceso : pageProcesosAbogado.getContent()) {
            ProcesoResponse response = ProcesoResponse.builder()
                    .id(proceso.getId())
                    .numeroRadicado(proceso.getRadicado())
                    .despacho(proceso.getDespacho().getNombre())
                    .tipoProceso(proceso.getTipoproceso().getNombre())
                    .fechaRadicacion(proceso.getFecharadicado().format(formatter))
                    .build();
            responses.add(response);
        }

        PageableResponse<ProcesoResponse> response = PageableResponse.<ProcesoResponse>builder()
                .data(responses)
                .totalPages(pageProcesosAbogado.getTotalPages())
                .totalItems(pageProcesosAbogado.getTotalElements())
                .currentPage(pageProcesosAbogado.getNumber() + 1)
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //pendiente
    @GetMapping("/get/all")
    public ResponseEntity<?> getAllProcesos() {
        Set<Proceso> procesos = procesoService.findAll();
        List<ProcesoResponse> procesosResponses = new ArrayList<>();
        for (Proceso proceso : procesos) {

            Actuacion actuacion = actuacionService.findLastActuacion(proceso.getId());

            ProcesoResponse p = ProcesoResponse.builder()
                    .numeroProceso(proceso.getNumeroproceso())
                    .numeroRadicado(proceso.getRadicado())
                    .id(proceso.getId())
                    .fechaRadicacion(proceso.getFecharadicado().format(formatter))
                    .fechaUltimaActuacion(actuacion.getFechaactuacion().format(formatter))
                    .build();

            procesosResponses.add(p);
        }
        return new ResponseEntity<>(procesosResponses, HttpStatus.OK);
    }

    @GetMapping("/get/all/estado")
    public ResponseEntity<?> getAllByEstado(@RequestParam String name, @RequestParam Integer firmaId) {
        Set<Proceso> procesos = procesoService.findAllByFirmaAndEstado(firmaId, name);
        List<ProcesoResponse> procesosResponses = new ArrayList<>();
        for (Proceso proceso : procesos) {
            ProcesoResponse p = ProcesoResponse.builder()
                    .numeroProceso(proceso.getNumeroproceso())
                    .numeroRadicado(proceso.getRadicado())
                    .id(proceso.getId())
                    .fechaRadicacion(proceso.getFecharadicado().format(formatter))
                    .build();

            procesosResponses.add(p);
        }
        return new ResponseEntity<>(procesosResponses, HttpStatus.OK);
    }

    @GetMapping("/get/all/estado/abogado")
    public ResponseEntity<?> getAllByEstadoAbogado(@RequestParam String name, @RequestParam String userName) {
        Set<Proceso> procesos = procesoService.findAllByAbogadoAndEstado(userName, name);
        List<ProcesoResponse> procesosResponses = new ArrayList<>();
        for (Proceso proceso : procesos) {
            ProcesoResponse p = ProcesoResponse.builder()
                    .numeroProceso(proceso.getNumeroproceso())
                    .numeroRadicado(proceso.getRadicado())
                    .id(proceso.getId())
                    .fechaRadicacion(proceso.getFecharadicado().format(formatter))
                    .build();

            procesosResponses.add(p);
        }
        return new ResponseEntity<>(procesosResponses, HttpStatus.OK);
    }

    @GetMapping("/get/abogado")
    public ResponseEntity<?> getProcesoAbogado(@RequestParam Integer procesoId) {
        Proceso proceso = procesoService.findById(procesoId);
        if (proceso == null) {
            return new ResponseEntity<>("Proceso no encontrado", HttpStatus.NOT_FOUND);
        }

        Set<Audiencia> audiencias = audienciaService.findAllByProceso(procesoId);

        ProcesoAbogadoResponse response = ProcesoAbogadoResponse.builder()
                .id(proceso.getId())
                .numeroRadicado(proceso.getRadicado())
                .tipoProceso(proceso.getTipoproceso().getNombre())
                .demandado(proceso.getDemandado())
                .despacho(proceso.getDespacho().getNombre())
                .demandante(proceso.getDemandante())
                .fechaRadicacion(proceso.getFecharadicado().format(formatter))
                .estado(proceso.getEstadoproceso().getNombre())
                .audiencias(audiencias)
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);

    }

}

package com.firma.data.service.impl;

import com.firma.data.model.*;
import com.firma.data.payload.request.ActuacionRequest;
import com.firma.data.payload.request.AudienciaRequest;
import com.firma.data.payload.request.EnlaceRequest;
import com.firma.data.payload.request.ProcesoRequest;
import com.firma.data.payload.response.PageableResponse;
import com.firma.data.payload.response.ProcesoAbogadoResponse;
import com.firma.data.payload.response.ProcesoJefeResponse;
import com.firma.data.payload.response.ProcesoResponse;
import com.firma.data.repository.*;
import com.firma.data.service.intf.IActuacionService;
import com.firma.data.service.intf.IFirmaService;
import com.firma.data.service.intf.IProcessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class ProcessService implements IProcessService {

    @Autowired
    private EnlaceRepository enlaceRepository;
    @Autowired
    private ProcesoRepository processRepository;
    @Autowired
    private EstadoProcesoRepository estadoProcesoRepository;
    @Autowired
    private AudienciaRepository audienciaRepository;
    @Autowired
    private TipoProcesoRepository tipoProcesoRepository;
    @Autowired
    private DespachoRepository despachoRepository;
    @Autowired
    private IFirmaService firmaService;
    @Autowired
    private IActuacionService actuacionService;
    DateTimeFormatter formatterTime = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");


    @Transactional
    @Override
    public ResponseEntity<?> saveProcess(ProcesoRequest procesoRequest) {
        Firma firma = firmaService.findFirmaById(procesoRequest.getIdFirma());
        if (firma == null) {
            return new ResponseEntity<>("Firma no encontrada", HttpStatus.NOT_FOUND);
        }
        Empleado empleado = firmaService.findEmpleadoByUsuario(procesoRequest.getIdAbogado());
        if (empleado == null) {
            return new ResponseEntity<>("Empleado no encontrado", HttpStatus.NOT_FOUND);
        }
        TipoProceso tipoProceso = tipoProcesoRepository.findByNombre(procesoRequest.getTipoProceso());

        if (tipoProceso == null) {
            tipoProceso = tipoProcesoRepository.save(TipoProceso.builder().nombre(procesoRequest.getTipoProceso()).build());
        }
        Despacho despacho = despachoRepository.findDespachoByNombre(procesoRequest.getDespacho());

        if (despacho == null) {
            despacho = despachoRepository.save(Despacho.builder().nombre(procesoRequest.getDespacho()).build());
        }

        LocalDateTime dateRadicado = LocalDateTime.parse(procesoRequest.getFechaRadicacion(), formatterTime);

        EstadoProceso estadoProceso = estadoProcesoRepository.findByNombre("Activo");

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

        newProceso = processRepository.save(newProceso);

        EstadoActuacion estadoActuacion = actuacionService.findEstadoActuacionByName("Visto");
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

    @Override
    public ResponseEntity<?> findProcessJefe(Integer processId) {
        Proceso proceso = processRepository.findById(processId).orElse(null);
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

    @Override
    public ResponseEntity<?> findProcessAbogado(Integer processId) {
        Proceso proceso = processRepository.findById(processId).orElse(null);
        if (proceso == null) {
            return new ResponseEntity<>("Proceso no encontrado", HttpStatus.NOT_FOUND);
        }

        Set<Audiencia> audiencias = audienciaRepository.findAllByProceso(processId);

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

    @Transactional
    @Override
    public ResponseEntity<?> updateProcess(Integer id, ProcesoRequest procesoRequest) {
        Proceso proceso = processRepository.findById(id).orElse(null);
        if (proceso == null) {
            return new ResponseEntity<>("Proceso no encontrado", HttpStatus.NOT_FOUND);
        }
        if (procesoRequest.getIdAbogado() != null) {
            Empleado empleado = firmaService.findEmpleadoByUsuario(procesoRequest.getIdAbogado());
            proceso.setEmpleado(empleado);
        }
        if (procesoRequest.getEstadoProceso() != null) {
            EstadoProceso estadoProceso = estadoProcesoRepository.findByNombre(procesoRequest.getEstadoProceso());
            proceso.setEstadoproceso(estadoProceso);
        }
        processRepository.save(proceso);
        return new ResponseEntity<>("Proceso actualizado", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> deleteProcess(Integer id) {
        Proceso proceso = processRepository.findById(id).orElse(null);
        if (proceso == null) {
            return new ResponseEntity<>("Proceso no encontrado", HttpStatus.NOT_FOUND);
        }
        EstadoProceso estadoProceso = estadoProcesoRepository.findByNombre("Retirado");
        proceso.setEliminado('S');
        proceso.setEstadoproceso(estadoProceso);
        processRepository.save(proceso);
        return new ResponseEntity<>("Proceso eliminado", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> findProcessByFirmaFilter(Integer firmaId, String fechaInicioStr, String fechaFinStr, List<String> estadosProceso, String tipoProceso, Integer page, Integer size) {
        LocalDate fechaInicio = null;
        LocalDate fechaFin = null;

        if (fechaInicioStr != null && !fechaInicioStr.isEmpty()) {
            fechaInicio = LocalDate.parse(fechaInicioStr);
        }
        if (fechaFinStr != null && !fechaFinStr.isEmpty()) {
            fechaFin = LocalDate.parse(fechaFinStr);
        }
        Pageable paging = PageRequest.of(page, size);
        Page<Proceso> pageProcesosFiltrados = processRepository.findByFiltros(fechaInicio, fechaFin, estadosProceso, tipoProceso, paging, firmaId);

        List<ProcesoJefeResponse> responses = new ArrayList<>();

        for (Proceso proceso : pageProcesosFiltrados.getContent()) {
            boolean estado = false;
            List<Actuacion> actuaciones = actuacionService.findByNoVisto(proceso.getFirma().getId());
            if (!actuaciones.isEmpty()) {
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

    @Override
    public ResponseEntity<?> findProcessByAbogadoFilter(Integer abogadoId, String fechaInicioStr, String fechaFinStr, List<String> estadosProceso, String tipoProceso, Integer page, Integer size) {

        Pageable paging = PageRequest.of(page, size);
        Page<Proceso> pageProcesosAbogado = processRepository.findAllByAbogado(abogadoId, fechaInicioStr, fechaFinStr, estadosProceso, tipoProceso, paging);
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

    @Override
    public ResponseEntity<?> findAllProcesses() {
        Set<Proceso> procesos = processRepository.findAllProcesos();
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

    @Override
    public ResponseEntity<?> findAllByEstadoFirma(Integer firmaId, String name) {
        Set<Proceso> procesos = processRepository.findAllByFirmaAndEstado(firmaId, name);
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

    @Override
    public ResponseEntity<?> findAllByEstadoAbogado(String userName, String name) {
        Set<Proceso> procesos = processRepository.findAllByAbogadoAndEstado(userName, name);
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

    @Override
    public ResponseEntity<?> saveAudiencia(AudienciaRequest audiencia) {
        Proceso proceso = processRepository.findById(audiencia.getProcesoid()).orElse(null);
        Audiencia newAudiencia = Audiencia.builder()
                .enlace(audiencia.getEnlace())
                .nombre(audiencia.getNombre())
                .proceso(proceso)
                .build();

        audienciaRepository.save(newAudiencia);
        return new ResponseEntity<>("Audiencia Creada", HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<?> updateAudiencia(Integer id, String enlace) {
        Audiencia audiencia = audienciaRepository.findById(id).orElse(null);
        audiencia.setEnlace(enlace);
        audienciaRepository.save(audiencia);
        return new ResponseEntity<>("Audiencia Actualizada", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> findAllEstadoProceso() {
        return new ResponseEntity<>(estadoProcesoRepository.findAll(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> findEstadoProcesoByName(String name) {
        EstadoProceso estadoProceso = estadoProcesoRepository.findByNombre(name);
        if (estadoProceso == null) {
            return new ResponseEntity<>("Estado proceso no encontrado", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(estadoProceso, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> findAllTipoProceso() {
        return new ResponseEntity<>(tipoProcesoRepository.findAll(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> findTipoProcesoByName(String name) {
        TipoProceso tipoProceso = tipoProcesoRepository.findByNombre(name);
        if (tipoProceso == null) {
            return new ResponseEntity<>("Tipo de proceso no encontrado", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(tipoProceso, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> findAllDespachoWithOutLink(String year) {
        return new ResponseEntity<>(despachoRepository.findAllDespachosWithOutLinkByYear(year), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> findDespachoByProcess(Integer processId) {
        Despacho despacho = despachoRepository.findDespachoByProceso(processId);
        if (despacho == null) {
            return new ResponseEntity<>("Despacho no encontrado", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(despacho, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> saveEnlace(EnlaceRequest enlaceRequest) {
        Despacho despacho = despachoRepository.findById(enlaceRequest.getDespachoid()).orElse(null);
        if (despacho == null) {
            return ResponseEntity.badRequest().body("Despacho no encontrado");
        }
        Enlace en = Enlace.builder()
                .url(enlaceRequest.getUrl())
                .fechaconsulta(enlaceRequest.getFechaconsulta())
                .despacho(despacho)
                .build();

        enlaceRepository.save(en);
        return new ResponseEntity<>("Enlace was saved", HttpStatus.OK);
    }


}

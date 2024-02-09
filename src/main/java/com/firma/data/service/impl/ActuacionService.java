package com.firma.data.service.impl;

import com.firma.data.model.*;
import com.firma.data.payload.request.ActuacionRequest;
import com.firma.data.payload.response.ActuacionJefeResponse;
import com.firma.data.payload.response.ActuacionResponse;
import com.firma.data.payload.response.PageableResponse;
import com.firma.data.repository.*;
import com.firma.data.service.intf.IActuacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
public class ActuacionService implements IActuacionService {

    @Autowired
    private ActuacionRepository actuacionRepository;
    @Autowired
    private RegistroCorreoRepository registroCorreoRepository;
    @Autowired
    private EstadoActuacionRepository estadoActuacionRepository;
    @Autowired
    private EnlaceRepository enlaceRepository;
    @Autowired
    private ProcesoRepository procesoRepository;
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Transactional
    @Override
    public ResponseEntity<?> saveActuacion(Set<ActuacionRequest> actuacionRequest) {
        EstadoActuacion estadoActuacion = estadoActuacionRepository.findByNombre("No Visto");
        for (ActuacionRequest ac : actuacionRequest){
            Proceso proceso = procesoRepository.findByRadicado(ac.getProceso());
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
            actuacionRepository.save(actuacion);
        }
        return new ResponseEntity<>("Actuaciones have been saved", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> findAllActuacionesNotSend() {
        Set<Actuacion> actuaciones = actuacionRepository.findAllByNoSend();
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
    @Override
    public ResponseEntity<?> updateActuacionSend(Set<Integer> actuacionesIds) {
        for (Integer actuacionId : actuacionesIds){
            Actuacion actuacion = actuacionRepository.findById(actuacionId).orElse(null);
            if (actuacion == null) {
                return new ResponseEntity<>("Actuacion not found", HttpStatus.NOT_FOUND);
            }
            actuacion.setEnviado('Y');
            actuacionRepository.save(actuacion);

            RegistroCorreo reg = RegistroCorreo.builder()
                    .correo(actuacion.getProceso().getEmpleado().getUsuario().getCorreo())
                    .fecha(LocalDateTime.now())
                    .build();

            registroCorreoRepository.save(reg);
        }
        return new ResponseEntity<>("Sent status updated", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> findActuacionesByProcessJefeFilter(Integer processId, String fechaInicioStr, String fechaFinStr, String estadoActuacion, Integer page, Integer size) {
        LocalDate fechaInicio = null;
        LocalDate fechaFin = null;

        if (fechaInicioStr != null && !fechaInicioStr.isEmpty()) {
            fechaInicio = LocalDate.parse(fechaInicioStr);
        }
        if (fechaFinStr != null && !fechaFinStr.isEmpty()) {
            fechaFin = LocalDate.parse(fechaFinStr);
        }
        Pageable paging = PageRequest.of(page, size, Sort.by("fechaactuacion").descending());
        Page<Actuacion> pageActuacionesFilter = actuacionRepository.findByProcessJefeFiltros(processId, fechaInicio, fechaFin, estadoActuacion, paging);
        List<ActuacionJefeResponse> actuacionesResponse = new ArrayList<>();

        for (Actuacion actuacion : pageActuacionesFilter.getContent()) {
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

        PageableResponse<ActuacionJefeResponse> response = PageableResponse.<ActuacionJefeResponse>builder()
                .data(actuacionesResponse)
                .totalPages(pageActuacionesFilter.getTotalPages())
                .totalItems(pageActuacionesFilter.getTotalElements())
                .currentPage(pageActuacionesFilter.getNumber() + 1)
                .build();

        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<?> findActuacionesByProcessAbogadoFilter(Integer processId, String fechaInicioStr, String fechaFinStr, Boolean existDoc, Integer page, Integer size) {
        LocalDate fechaInicio = null;
        LocalDate fechaFin = null;

        if (fechaInicioStr != null && !fechaInicioStr.isEmpty()) {
            fechaInicio = LocalDate.parse(fechaInicioStr);
        }
        if (fechaFinStr != null && !fechaFinStr.isEmpty()) {
            fechaFin = LocalDate.parse(fechaFinStr);
        }
        Pageable paging = PageRequest.of(page, size, Sort.by("fechaactuacion").descending());
        Page<Actuacion> pageActuaciones = actuacionRepository.findByProcessAbogadoFiltros(processId, fechaInicio, fechaFin, existDoc, paging);
        List<ActuacionResponse> actuacionesResponse = new ArrayList<>();
        for (Actuacion actuacion : pageActuaciones.getContent()){
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

        PageableResponse<ActuacionResponse> response = PageableResponse.<ActuacionResponse>builder()
                .data(actuacionesResponse)
                .totalPages(pageActuaciones.getTotalPages())
                .totalItems(pageActuaciones.getTotalElements())
                .currentPage(pageActuaciones.getNumber() + 1)
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> findActuacion(Integer actuacionId) {
        Actuacion actuacion = actuacionRepository.findById(actuacionId).orElse(null);
        if (actuacion == null) {
            return new ResponseEntity<>("Actuacion not found", HttpStatus.NOT_FOUND);
        }

        String link = null;

        if (actuacion.getExistedoc() && actuacion.getDocumento() == null){
            DateTimeFormatter yearFormatter = DateTimeFormatter.ofPattern("yyyy");
            String year = yearFormatter.format(actuacion.getFechaactuacion());
            Enlace e = enlaceRepository.findByDespachoAndYear(actuacion.getProceso().getDespacho().getId(), year);
            link = e.getUrl();
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
                .fechaActuacion(actuacion.getFechaactuacion().format(formatter))
                .fechaRegistro(actuacion.getFecharegistro().format(formatter))
                .link(link)
                .build();

        if (actuacion.getFechainicia() != null && actuacion.getFechafinaliza() != null){
            res.setFechaInicia(actuacion.getFechainicia().format(formatter));
            res.setFechaFinaliza(actuacion.getFechafinaliza().format(formatter));
        }

        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @Override
    public Actuacion findById(Integer actuacionId) {
        return actuacionRepository.findById(actuacionId).orElse(null);
    }

    @Override
    public void update(Actuacion actuacion) {
        actuacionRepository.save(actuacion);
    }

    @Override
    public Set<Actuacion> findAllByProcesoAndDocument(Integer procesoId) {
        return actuacionRepository.findAllByProcesoAndDocument(procesoId);
    }

    @Override
    public ResponseEntity<?> findAllEstadoActuacion() {
        return new ResponseEntity<>(estadoActuacionRepository.findAll(), HttpStatus.OK);
    }

    @Override
    public EstadoActuacion findEstadoActuacionByName(String name) {
        return estadoActuacionRepository.findByNombre(name);
    }

    @Override
    public void saveAllActuaciones(List<Actuacion> actuaciones) {
        actuacionRepository.saveAll(actuaciones);
    }

    @Override
    public List<Actuacion> findByNoVisto(Integer id) {
        return actuacionRepository.findByNoVisto(id);
    }

    @Override
    public Actuacion findLastActuacion(Integer id) {
        return actuacionRepository.findLastActuacion(id);
    }

}

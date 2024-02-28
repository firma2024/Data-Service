package com.firma.data.implService;

import com.firma.data.intfService.IActuacionService;
import com.firma.data.model.Actuacion;
import com.firma.data.model.EstadoActuacion;
import com.firma.data.model.RegistroCorreo;
import com.firma.data.payload.response.PageableResponse;
import com.firma.data.repository.ActuacionRepository;
import com.firma.data.repository.EstadoActuacionRepository;
import com.firma.data.repository.RegistroCorreoRepository;
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

    @Transactional
    @Override
    public ResponseEntity<?> saveActuaciones(Set<Actuacion> actuacionRequest) {
        actuacionRepository.saveAll(actuacionRequest);
        return new ResponseEntity<>("Actuaciones have been saved", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> findAllActuacionesNotSend() {
        return new ResponseEntity<>(actuacionRepository.findAllByNoSend(), HttpStatus.OK);
    }

    @Transactional
    @Override
    public ResponseEntity<?> updateActuacionSend(Actuacion actuacion) {
        actuacionRepository.save(actuacion);
        return new ResponseEntity<>("Actuacion actualizada", HttpStatus.OK);
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

        PageableResponse<Actuacion> response = PageableResponse.<Actuacion>builder()
                .data(pageActuacionesFilter.getContent())
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

        PageableResponse<Actuacion> response = PageableResponse.<Actuacion>builder()
                .data(pageActuaciones.getContent())
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
            return new ResponseEntity<>("Actuacion no encontrada", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(actuacion, HttpStatus.OK);
    }

    @Override
    public Actuacion findById(Integer actuacionId) {
        return actuacionRepository.findById(actuacionId).orElse(null);
    }

    @Override
    public String update(Actuacion actuacion) {
        actuacionRepository.save(actuacion);
        return "Actuacion actualizada";
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
    public ResponseEntity<?> findEstadoActuacionByName(String name) {
        EstadoActuacion estadoActuacion = estadoActuacionRepository.findByNombre(name);
        if (estadoActuacion == null) {
            return ResponseEntity.notFound().build();
        }
        return new ResponseEntity<>(estadoActuacion, HttpStatus.OK);
    }

    @Override
    public void saveAllActuaciones(List<Actuacion> actuaciones) {
        actuacionRepository.saveAll(actuaciones);
    }

    @Override
    public List<Actuacion> findByNoVisto(Integer processId) {
        return actuacionRepository.findByNoVisto(processId);
    }

    @Override
    public ResponseEntity<?> findLastActuacion(Integer processId) {
        return new ResponseEntity<>(actuacionRepository.findLastActuacion(processId), HttpStatus.OK);
    }
    @Transactional
    @Override
    public ResponseEntity<?> saveActuacion(Actuacion actuacionRequest) {
        return new ResponseEntity<>(actuacionRepository.save(actuacionRequest), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> saveRegistroCorreo(RegistroCorreo registroCorreo) {
        registroCorreoRepository.save(registroCorreo);
        return new ResponseEntity<>("Registro correo almacenado", HttpStatus.OK);
    }

}
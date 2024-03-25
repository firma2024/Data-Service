package com.firma.data.implService;

import com.firma.data.intfService.IActuacionService;
import com.firma.data.model.*;
import com.firma.data.payload.request.ProcessRequest;
import com.firma.data.payload.response.PageableResponse;
import com.firma.data.repository.*;
import com.firma.data.intfService.IProcessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
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
    private IActuacionService actuacionService;

    @Value("${api.estadoproceso.retirado")
    private String estadoRetirado;


    @Transactional
    @Override
    public ResponseEntity<?> saveProcess(ProcessRequest processRequest) {

        if (processRequest.getProcess().getTipoproceso().getId() == null) {
            TipoProceso tp = tipoProcesoRepository.save(processRequest.getProcess().getTipoproceso());
            processRequest.getProcess().setTipoproceso(tp);
        }

        if (processRequest.getProcess().getDespacho().getId() == null) {
            Despacho dp = despachoRepository.save(processRequest.getProcess().getDespacho());
            processRequest.getProcess().setDespacho(dp);
        }

        Proceso newProces = processRepository.save(processRequest.getProcess());

        for(Actuacion actuacion : processRequest.getActions()){
            actuacion.setProceso(newProces);
        }

        actuacionService.saveAllActuaciones(processRequest.getActions());

        return new ResponseEntity<>("Proceso almacenado", HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<?> findProcessById(Integer processId) {
        Proceso proceso = processRepository.findById(processId).orElse(null);
        if (proceso == null) {
            return new ResponseEntity<>("Proceso no encontrado", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(proceso, HttpStatus.OK);
    }

    @Transactional
    @Override
    public ResponseEntity<?> updateProcess(Proceso process) {
        processRepository.save(process);
        return new ResponseEntity<>("Proceso actualizado", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> deleteProcess(Integer id) {
        Proceso proceso = processRepository.findById(id).orElse(null);
        if (proceso == null) {
            return new ResponseEntity<>("Proceso no encontrado", HttpStatus.NOT_FOUND);
        }
        EstadoProceso estadoProceso = estadoProcesoRepository.findByNombre(estadoRetirado);
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

        PageableResponse<Proceso> response = PageableResponse.<Proceso>builder()
                .data(pageProcesosFiltrados.getContent())
                .totalPages(pageProcesosFiltrados.getTotalPages())
                .totalItems(pageProcesosFiltrados.getTotalElements())
                .currentPage(pageProcesosFiltrados.getNumber() + 1)
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> findProcessByAbogadoFilter(Integer abogadoId, String fechaInicioStr, String fechaFinStr, List<String> estadosProceso, String tipoProceso, Integer page, Integer size) {

        LocalDate fechaInicio = null;
        LocalDate fechaFin = null;

        if (fechaInicioStr != null && !fechaInicioStr.isEmpty()) {
            fechaInicio = LocalDate.parse(fechaInicioStr);
        }
        if (fechaFinStr != null && !fechaFinStr.isEmpty()) {
            fechaFin = LocalDate.parse(fechaFinStr);
        }

        Pageable paging = PageRequest.of(page, size);
        Page<Proceso> pageProcesosAbogado = processRepository.findAllByAbogado(abogadoId, fechaInicio, fechaFin, estadosProceso, tipoProceso, paging);

        PageableResponse<Proceso> response = PageableResponse.<Proceso>builder()
                .data(pageProcesosAbogado.getContent())
                .totalPages(pageProcesosAbogado.getTotalPages())
                .totalItems(pageProcesosAbogado.getTotalElements())
                .currentPage(pageProcesosAbogado.getNumber() + 1)
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> findAllProcesses() {
        return new ResponseEntity<>(processRepository.findAll(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> findAllByEstadoFirma(Integer firmaId, String name) {
        Set<Proceso> procesos = processRepository.findAllByFirmaAndEstado(firmaId, name);
        return new ResponseEntity<>(procesos, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> findAllByEstadoAbogado(String userName, String name) {
        Set<Proceso> procesos = processRepository.findAllByAbogadoAndEstado(userName, name);
        return new ResponseEntity<>(procesos, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> saveAudiencia(Audiencia audiencia) {
        audienciaRepository.save(audiencia);
        return new ResponseEntity<>("Audiencia Creada", HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<?> updateAudiencia(Integer id, String enlace) {
        Audiencia audiencia = audienciaRepository.findById(id).orElse(null);
        if (audiencia == null) {
            return new ResponseEntity<>("Audiencia no encontrada", HttpStatus.NOT_FOUND);
        }
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
        return new ResponseEntity<>(tipoProceso, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> findAllDespachoWithOutLink(String year) {
        return new ResponseEntity<>(despachoRepository.findAllDespachosWithOutLinkByYear(year), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> findDespachoByProcess(Integer processId) {
        return new ResponseEntity<>(despachoRepository.findDespachoByProceso(processId), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> saveEnlace(Enlace enlace) {
        enlaceRepository.save(enlace);
        return new ResponseEntity<>("Enlace was saved", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> findAllAudienciasByProceso(Integer procesoId) {
        return new ResponseEntity<>(audienciaRepository.findAllByProceso(procesoId), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> findDespachoById(Integer id) {
        Despacho despacho = despachoRepository.findById(id).orElse(null);
        if (despacho == null) {
            return new ResponseEntity<>("Despacho no encontrado", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(despacho, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> findProcessByRadicado(String radicado) {
        return new ResponseEntity<>(processRepository.findByRadicado(radicado), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> findEnlaceByDespachoAndYear(Integer id, String year) {
        return new ResponseEntity<>(enlaceRepository.findByDespachoAndYear(id, year), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> findDespachoByName(String name) {
        return new ResponseEntity<>(despachoRepository.findDespachoByNombre(name), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?>  findAllDespachosWithDateActuacion(){
        return new ResponseEntity<>(despachoRepository.findAllDespachosWithDateActuacion(), HttpStatus.OK);
    }

}
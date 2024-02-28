package com.firma.data.service;

import com.firma.data.implService.ProcessService;
import com.firma.data.model.*;
import com.firma.data.payload.request.ProcessRequest;
import com.firma.data.repository.*;
import com.firma.data.intfService.IActuacionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProcessServiceTest {
    @InjectMocks
    ProcessService processService;

    @Mock
    ProcesoRepository procesoRepository;
    @Mock
    TipoProcesoRepository tipoProcesoRepository;
    @Mock
    DespachoRepository despachoRepository;
    @Mock
    IActuacionService actuacionService;
    @Mock
    EstadoProcesoRepository estadoProcesoRepository;
    @Mock
    AudienciaRepository audienciaRepository;
    @Mock
    EnlaceRepository enlaceRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void shouldSaveProcessSuccessfully() {
        // Given
        ProcessRequest processRequest = new ProcessRequest();
        Actuacion actuacion = new Actuacion();
        processRequest.setActions(List.of(actuacion));

        Proceso process = new Proceso();
        TipoProceso tipoProceso = new TipoProceso();
        tipoProceso.setNombre("tipoProceso");
        Despacho despacho = new Despacho();
        despacho.setNombre("despacho");
        process.setTipoproceso(tipoProceso);
        process.setDespacho(despacho);
        processRequest.setProcess(process);

        when(tipoProcesoRepository.save(tipoProceso)).thenReturn(tipoProceso);
        when(despachoRepository.save(despacho)).thenReturn(despacho);
        when(procesoRepository.save(process)).thenReturn(process);

        ResponseEntity<?> response = processService.saveProcess(processRequest);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());

    }

    @Test
    void shouldFindProcessById() {
        Integer processId = 1;
        Proceso proceso = new Proceso();
        when(procesoRepository.findById(processId)).thenReturn(Optional.of(proceso));

        ResponseEntity<?> response = processService.findProcessById(processId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void shouldFindProcessByIdNotFound() {
        Integer processId = 1;
        when(procesoRepository.findById(processId)).thenReturn(Optional.empty());

        ResponseEntity<?> response = processService.findProcessById(processId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void shouldUpdateProcess() {
        Proceso process = new Proceso();
        ResponseEntity<?> response = processService.updateProcess(process);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void shouldDeleteProcess() {
        Integer id = 1;
        Proceso proceso = new Proceso();
        when(procesoRepository.findById(id)).thenReturn(Optional.of(proceso));

        EstadoProceso estadoProceso = new EstadoProceso();
        when(estadoProcesoRepository.findByNombre("Retirado")).thenReturn(estadoProceso);

        ResponseEntity<?> response = processService.deleteProcess(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void shouldDeleteProcessNotFound() {
        Integer id = 1;
        when(procesoRepository.findById(id)).thenReturn(Optional.empty());

        ResponseEntity<?> response = processService.deleteProcess(id);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void shouldFindAllProcesses() {
        List<Proceso> procesos = List.of(new Proceso());
        when(procesoRepository.findAll()).thenReturn(procesos);

        ResponseEntity<?> response = processService.findAllProcesses();

        assertEquals(HttpStatus.OK  , response.getStatusCode());
    }

    @Test
    void shouldFindAllByEstadoFrima(){
        Integer firmaId = 1;
        String state = "state";
        Set<Proceso> processes = Set.of(new Proceso());
        when(procesoRepository.findAllByFirmaAndEstado(firmaId, state)).thenReturn(processes);

        ResponseEntity<?> response = processService.findAllByEstadoFirma(firmaId, state);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void shouldFindAllByEstadoAbogado(){
        String userName = "user";
        String state = "state";
        Set<Proceso> processes = Set.of(new Proceso());
        when(procesoRepository.findAllByAbogadoAndEstado(userName, state)).thenReturn(processes);

        ResponseEntity<?> response = processService.findAllByEstadoAbogado(userName, state);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void shouldSaveAudiencia(){
        Audiencia audiencia = new Audiencia();
        ResponseEntity<?> response = processService.saveAudiencia(audiencia);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    void shouldUpdateAudiencia(){
        Integer id = 1;
        String enlace = "enlace";
        Audiencia audiencia = new Audiencia();
        when(audienciaRepository.findById(id)).thenReturn(Optional.of(audiencia));

        ResponseEntity<?> response = processService.updateAudiencia(id, enlace);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void shouldUpdateAudienciaNotFound(){
        Integer id = 1;
        String enlace = "enlace";
        when(audienciaRepository.findById(id)).thenReturn(Optional.empty());

        ResponseEntity<?> response = processService.updateAudiencia(id, enlace);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void shouldFindAllEstadoProceso(){
        ResponseEntity<?> response = processService.findAllEstadoProceso();

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void shouldFindEstadoProcesoByName(){
        String name = "name";
        EstadoProceso estadoProceso = new EstadoProceso();
        when(estadoProcesoRepository.findByNombre(name)).thenReturn(estadoProceso);

        ResponseEntity<?> response = processService.findEstadoProcesoByName(name);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void shouldFindEstadoProcesoByNameNotFound(){
        String name = "name";
        when(estadoProcesoRepository.findByNombre(name)).thenReturn(null);

        ResponseEntity<?> response = processService.findEstadoProcesoByName(name);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Estado proceso no encontrado", response.getBody());
    }

    @Test
    void shouldFindAllTipoProceso(){
        ResponseEntity<?> response = processService.findAllTipoProceso();
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void shouldFindTipoProcesoByName(){
        String name = "name";
        TipoProceso tipoProceso = new TipoProceso();
        when(tipoProcesoRepository.findByNombre(name)).thenReturn(tipoProceso);

        ResponseEntity<?> response = processService.findTipoProcesoByName(name);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void shouldFindAllDespachoWithOutLink(){
        String year = "year";
        List<Despacho> despachos = List.of(new Despacho());
        when(despachoRepository.findAllDespachosWithOutLinkByYear(year)).thenReturn(despachos);

        ResponseEntity<?> response = processService.findAllDespachoWithOutLink(year);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void shouldFindDespachoByProcess(){
        Integer processId = 1;
        Despacho despacho = new Despacho();
        when(despachoRepository.findDespachoByProceso(processId)).thenReturn(despacho);

        ResponseEntity<?> response = processService.findDespachoByProcess(processId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void shouldSaveEnlace(){
        Enlace enlace = new Enlace();
        ResponseEntity<?> response = processService.saveEnlace(enlace);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void shouldFindAllAudienciasByProceso(){
        Integer procesoId = 1;
        Set<Audiencia> audiencias = Set.of(new Audiencia());
        when(audienciaRepository.findAllByProceso(procesoId)).thenReturn(audiencias);

        ResponseEntity<?> response = processService.findAllAudienciasByProceso(procesoId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void shouldFindDespachoById(){
        Integer id = 1;
        Despacho despacho = new Despacho();
        when(despachoRepository.findById(id)).thenReturn(Optional.of(despacho));

        ResponseEntity<?> response = processService.findDespachoById(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void shouldFindDespachoByIdNotFound(){
        Integer id = 1;
        when(despachoRepository.findById(id)).thenReturn(Optional.empty());

        ResponseEntity<?> response = processService.findDespachoById(id);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Despacho no encontrado", response.getBody());
    }

    @Test
    void shouldFindProcessByRadicado(){
        String radicado = "radicado";
        Proceso proceso = new Proceso();
        when(procesoRepository.findByRadicado(radicado)).thenReturn(proceso);

        ResponseEntity<?> response = processService.findProcessByRadicado(radicado);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }



    @Test
    void shouldFindEnlaceByDespachoAndYear(){
        Integer id = 1;
        String year = "year";
        Enlace enlace = new Enlace();
        when(enlaceRepository.findByDespachoAndYear(id, year)).thenReturn(enlace);

        ResponseEntity<?> response = processService.findEnlaceByDespachoAndYear(id, year);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void shouldFindDespachoByName(){
        String name = "name";
        Despacho despacho = new Despacho();
        when(despachoRepository.findDespachoByNombre(name)).thenReturn(despacho);

        ResponseEntity<?> response = processService.findDespachoByName(name);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }


}
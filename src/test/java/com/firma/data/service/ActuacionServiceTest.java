package com.firma.data.service;

import com.firma.data.implService.ActuacionService;
import com.firma.data.model.Actuacion;
import com.firma.data.model.EstadoActuacion;
import com.firma.data.model.RegistroCorreo;
import com.firma.data.repository.ActuacionRepository;
import com.firma.data.repository.EstadoActuacionRepository;
import com.firma.data.repository.RegistroCorreoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ActuacionServiceTest {

    @InjectMocks
    ActuacionService actuacionService;

    @Mock
    ActuacionRepository actuacionRepository;
    @Mock
    EstadoActuacionRepository estadoActuacionRepository;
    @Mock
    RegistroCorreoRepository registroCorreoRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void shouldSaveActuacionesSuccessfully() {
        // Mocking data
        Set<Actuacion> actuacionRequest = new HashSet<>();
        Actuacion actuacion = new Actuacion();
        actuacion.setActuacion("Actuacion 1");
        actuacionRequest.add(actuacion);

        when(actuacionRepository.saveAll(actuacionRequest)).thenReturn(List.of(actuacion));

        ResponseEntity<?> response = actuacionService.saveActuaciones(actuacionRequest);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Actuaciones have been saved", response.getBody());
    }

    @Test
    void shouldFindAllActuacionesNotSendSuccessfully() {
        // Mocking data
        Actuacion actuacion = new Actuacion();
        when(actuacionRepository.findAllByNoSend()).thenReturn(Set.of(actuacion));

        ResponseEntity<?> response = actuacionService.findAllActuacionesNotSend();

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void shouldUpdateActuacionSendSuccessfully() {
        // Mocking data
        Actuacion actuacion = new Actuacion();
        when(actuacionRepository.save(actuacion)).thenReturn(actuacion);

        ResponseEntity<?> response = actuacionService.updateActuacion(actuacion);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Actuacion actualizada", response.getBody());
    }

    @Test
    void shouldFindActuacionSuccessfully() {
        // Mocking data
        Actuacion actuacion = new Actuacion();
        when(actuacionRepository.findById(1)).thenReturn(Optional.of(actuacion));

        ResponseEntity<?> response = actuacionService.findActuacion(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void shouldFindActuacionNoSuccessfully() {
        // Mocking data
        when(actuacionRepository.findById(1)).thenReturn(Optional.empty());

        ResponseEntity<?> response = actuacionService.findActuacion(1);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void shouldFindByIdSuccessfully() {
        // Mocking data
        Actuacion actuacion = new Actuacion();
        when(actuacionRepository.findById(1)).thenReturn(Optional.of(actuacion));

        Actuacion response = actuacionService.findById(1);

        assertEquals(actuacion, response);
    }

    @Test
    void shouldUpdateSuccessfully() {
        // Mocking data
        Actuacion actuacion = new Actuacion();
        when(actuacionRepository.save(actuacion)).thenReturn(actuacion);

        String response = actuacionService.update(actuacion);

        assertEquals("Actuacion actualizada", response);
    }

    @Test
    void shouldFindAllByProcesoAndDocumentSuccessfully() {
        // Mocking data
        Actuacion actuacion = new Actuacion();
        when(actuacionRepository.findAllByProcesoAndDocument(1)).thenReturn(Set.of(actuacion));

        Set<Actuacion> response = actuacionService.findAllByProcesoAndDocument(1);

        assertEquals(Set.of(actuacion), response);
    }

    @Test
    void shouldFindAllEstadoActuacionSuccessfully() {
        // Mocking data
        EstadoActuacion estadoActuacion = new EstadoActuacion();
        when(estadoActuacionRepository.findAll()).thenReturn(List.of(estadoActuacion));

        ResponseEntity<?> response = actuacionService.findAllEstadoActuacion();

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void shouldFindEstadoActuacionByNameSuccessfully() {
        // Mocking data
        EstadoActuacion estadoActuacion = new EstadoActuacion();
        when(estadoActuacionRepository.findByNombre("name")).thenReturn(estadoActuacion);

        ResponseEntity<?> response = actuacionService.findEstadoActuacionByName("name");

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void shouldFindEstadoActuacionNoSuccessfully() {
        // Mocking data
        when(estadoActuacionRepository.findByNombre("name")).thenReturn(null);

        ResponseEntity<?> response = actuacionService.findEstadoActuacionByName("name");

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void shouldSaveAllActuacionesSuccessfully() {
        // Mocking data
        Actuacion actuacion = new Actuacion();
        when(actuacionRepository.saveAll(List.of(actuacion))).thenReturn(List.of(actuacion));

        actuacionService.saveAllActuaciones(List.of(actuacion));

        assertEquals(List.of(actuacion), actuacionRepository.saveAll(List.of(actuacion)));

    }

    @Test
    void shouldFindByNoVistoSuccessfully() {
        // Mocking data
        Actuacion actuacion = new Actuacion();
        when(actuacionRepository.findByNoVisto(1)).thenReturn(List.of(actuacion));

        List<Actuacion> actuacions = actuacionService.findByNoVisto(1);

        assertEquals(List.of(actuacion), actuacions);
    }

    @Test
    void shouldFindLastActuacionSuccessfully() {
        // Mocking data
        Actuacion actuacion = new Actuacion();
        when(actuacionRepository.findLastActuacion(1)).thenReturn(actuacion);

        ResponseEntity<?> response = actuacionService.findLastActuacion(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void shouldSaveActuacionSuccessfully() {
        // Mocking data
        Actuacion actuacion = new Actuacion();
        when(actuacionRepository.save(actuacion)).thenReturn(actuacion);

        ResponseEntity<?> response = actuacionService.saveActuacion(actuacion);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void shouldSaveRegistroCorreoSuccessfully() {
        RegistroCorreo registroCorreo = new RegistroCorreo();
        when(registroCorreoRepository.save(registroCorreo)).thenReturn(registroCorreo);

        ResponseEntity<?> response = actuacionService.saveRegistroCorreo(registroCorreo);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(registroCorreoRepository).save(registroCorreo);
    }





}
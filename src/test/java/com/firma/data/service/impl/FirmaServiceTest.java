package com.firma.data.service.impl;

import com.firma.data.model.Empleado;
import com.firma.data.model.Firma;
import com.firma.data.payload.request.FirmaRequest;
import com.firma.data.repository.EmpleadoRepository;
import com.firma.data.repository.FirmaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class FirmaServiceTest {

    @InjectMocks
    FirmaService firmaService;

    @Mock
    FirmaRepository firmaRepository;
    @Mock
    EmpleadoRepository empleadoRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void shouldSaveFirmaSuccessfully() {
        // Mocking data
        Firma firma = new Firma();
        firma.setNombre("Firma 1");
        firma.setDireccion("Calle 1");

        FirmaRequest firmaRequest = new FirmaRequest();
        firmaRequest.setNombre("Firma 1");
        firmaRequest.setDireccion("Calle 1");

        when(firmaRepository.save(firma)).thenReturn(firma);

        ResponseEntity<?> response = firmaService.saveFirma(firmaRequest);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Firma Creada", response.getBody());
    }

    @Test
    void shouldFindFirmaByUserSuccessfully() {
        // Mocking data
        String userName = "user1";
        Firma firma = new Firma();

        when(firmaRepository.findByUser(userName)).thenReturn(firma);

        ResponseEntity<?> response = firmaService.findFirmaByUser(userName);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(firma, response.getBody());
    }

    @Test
    void shouldReturnFirmaNotFoundWhenFirmaByUserNotFound() {
        // Mocking data
        String userName = "user1";

        when(firmaRepository.findByUser(userName)).thenReturn(null);

        ResponseEntity<?> response = firmaService.findFirmaByUser(userName);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Firma no encontrada", response.getBody());
    }

    @Test
    void shouldFindFirmaByIdSuccessfully() {
        // Mocking data
        Integer id = 1;
        Firma firma = new Firma();

        when(firmaRepository.findById(id)).thenReturn(Optional.of(firma));

        ResponseEntity<?> response = firmaService.findFirmaById(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(firma, response.getBody());
    }

    @Test
    void shouldReturnFirmaNotFoundWhenFirmaByIdNotFound() {
        // Mocking data
        Integer id = 1;

        when(firmaRepository.findById(id)).thenReturn(Optional.empty());

        ResponseEntity<?> response = firmaService.findFirmaById(id);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Firma no encontrada", response.getBody());
    }

    @Test
    void shouldSaveEmpleadoSuccessfully() {
        // Mocking data
        Empleado empleado = new Empleado();

        when(empleadoRepository.save(empleado)).thenReturn(empleado);

        Empleado response = firmaService.saveEmpleado(empleado);

        assertEquals(empleado, response);
    }

    @Test
    void shouldFindEmpleadoByUsuarioSuccessfully() {
        // Mocking data
        Integer idAbogado = 1;
        Empleado empleado = new Empleado();

        when(empleadoRepository.findEmpleadoByUsuario(idAbogado)).thenReturn(empleado);

        ResponseEntity<?> response = firmaService.findEmpleadoByUsuario(idAbogado);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(empleado, response.getBody());
    }

    @Test
    void shouldReturnEmpleadoNotFoundWhenEmpleadoByUsuarioNotFound() {
        // Mocking data
        Integer idAbogado = 1;

        when(empleadoRepository.findEmpleadoByUsuario(idAbogado)).thenReturn(null);

        ResponseEntity<?> response = firmaService.findEmpleadoByUsuario(idAbogado);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Empleado no encontrado", response.getBody());
    }

}
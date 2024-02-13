package com.firma.data.service.impl;

import com.firma.data.model.Actuacion;
import com.firma.data.model.Proceso;
import com.firma.data.model.Usuario;
import com.firma.data.utils.ImageUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Set;
import java.util.zip.Deflater;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class StorageServiceTest {

    @InjectMocks
    StorageService storageService;

    @Mock
    UserService userService;
    @Mock
    ActuacionService actuacionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void shouldUploadPhotoUserSuccessfully() throws IOException {
        // Mocking data
        Integer userId = 1;
        MultipartFile file = new MockMultipartFile("file", "test.jpg", "image/jpeg", "test".getBytes());

        Usuario user = new Usuario();
        when(userService.findById(userId)).thenReturn(user);
        when(userService.updateUser(user)).thenReturn(new ResponseEntity("Usuario Actualizado", HttpStatus.OK));

        // Calling the method
        ResponseEntity<?> response = storageService.uploadPhotoUser(file, userId);

        // Verifying the result
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Foto alamacenada", response.getBody());
        verify(userService, times(1)).findById(userId);
        verify(userService, times(1)).updateUser(user);
    }

    @Test
    void shouldReturnUserNotFoundWhenUserIdNotFound() throws IOException {
        // Mocking data
        Integer userId = 1;
        MultipartFile file = mock(MultipartFile.class);
        when(userService.findById(userId)).thenReturn(null);

        // Calling the method
        ResponseEntity<?> response = storageService.uploadPhotoUser(file, userId);

        // Verifying the result
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Usuario no encontrado", response.getBody());
        verify(userService, times(1)).findById(userId);
        verify(userService, never()).updateUser(any());
    }

    @Test
    void shouldDownloadPhotoUserSuccessfully() {
        // Mocking data
        Integer userId = 1;
        Usuario user = new Usuario();
        user.setImg(ImageUtils.compressImage("test".getBytes()));
        when(userService.findById(userId)).thenReturn(user);

        // Calling the method
        ResponseEntity<?> response = storageService.downloadPhotoUser(userId);

        // Verifying the result
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("test", new String((byte[]) response.getBody()));
        verify(userService, times(1)).findById(userId);
    }

    @Test
    void shouldReturnUserNotFoundWhenUserIdNotFoundForDownload() {
        // Mocking data
        Integer userId = 1;
        when(userService.findById(userId)).thenReturn(null);

        // Calling the method
        ResponseEntity<?> response = storageService.downloadPhotoUser(userId);

        // Verifying the result
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Usuario no encontrado", response.getBody());
        verify(userService, times(1)).findById(userId);
    }

    @Test
    void shouldReturnPhotoNotFoundWhenUserPhotoNotFound() {
        // Mocking data
        Integer userId = 1;
        Usuario user = new Usuario();
        when(userService.findById(userId)).thenReturn(user);

        // Calling the method
        ResponseEntity<?> response = storageService.downloadPhotoUser(userId);

        // Verifying the result
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Foto no encontrada", response.getBody());
        verify(userService, times(1)).findById(userId);
    }

    @Test
    void shouldUploadDocumentSuccessfully() throws IOException {
        // Mocking data
        Integer actuacionId = 1;
        MultipartFile file = new MockMultipartFile("file", "test.pdf", "application/pdf", "test".getBytes());

        Actuacion actuacion = new Actuacion();
        when(actuacionService.findById(actuacionId)).thenReturn(actuacion);
        when(actuacionService.update(actuacion)).thenReturn("Actuacion actualizada");

        ResponseEntity<?> response = storageService.uploadDocument(file, actuacionId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Documento almacenado", response.getBody());
    }

    @Test
    void shouldUploadDocumentNoSuccessfully() throws IOException {
        // Mocking data
        Integer actuacionId = 1;
        MultipartFile file = new MockMultipartFile("file", "test.pdf", "application/pdf", "test".getBytes());

        Actuacion actuacion = new Actuacion();
        when(actuacionService.findById(actuacionId)).thenReturn(null);

        ResponseEntity<?> response = storageService.uploadDocument(file, actuacionId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Actuacion no encontrada", response.getBody());

    }

    @Test
    void shouldDownloadDocumentSuccessfully() {
        // Mocking data
        Integer actuacionId = 1;
        Actuacion actuacion = new Actuacion();
        actuacion.setDocumento(ImageUtils.compressImage("test".getBytes()));
        when(actuacionService.findById(actuacionId)).thenReturn(actuacion);

        ResponseEntity<?> response = storageService.downloadDocument(actuacionId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("test", new String((byte[]) response.getBody()));
    }

    @Test
    void shouldDownloadAllDocumentsSuccessfully() {
        // Mocking data
        Integer processId = 1;
        Actuacion actuacion = new Actuacion();
        actuacion.setDocumento(ImageUtils.compressImage("test".getBytes()));
        actuacion.setFechaactuacion(LocalDate.now());
        Proceso proceso = new Proceso();
        proceso.setRadicado("11001400305420210000800");
        actuacion.setProceso(proceso);
        when(actuacionService.findAllByProcesoAndDocument(processId)).thenReturn(Set.of(actuacion));

        ResponseEntity<?> response = storageService.downloadAllDocuments(processId);

        assertEquals(HttpStatus.OK, response.getStatusCode());

    }


}
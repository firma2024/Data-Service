package com.firma.data.service;

import com.firma.data.implService.FirmaService;
import com.firma.data.implService.UserService;
import com.firma.data.model.*;
import com.firma.data.payload.request.UsuarioRequest;
import com.firma.data.repository.RolRepository;
import com.firma.data.repository.TipoAbogadoRepository;
import com.firma.data.repository.TipoDocumentoRepository;
import com.firma.data.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @InjectMocks
    UserService userService;

    @Mock
    UsuarioRepository usuarioRepository;
    @Mock
    RolRepository rolRepository;
    @Mock
    TipoAbogadoRepository tipoAbogadoRepository;
    @Mock
    TipoDocumentoRepository tipoDocumentoRepository;
    @Mock
    FirmaService firmaService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void shouldSaveUserSuccessfully() {
        UsuarioRequest userRequest = new UsuarioRequest();
        Usuario user = new Usuario();
        Rol rol = new Rol(1, "test");
        userRequest.setUser(user);
        user.setRol(rol);

        when(usuarioRepository.save(user)).thenReturn(user);

        ResponseEntity<?> response = userService.saveUser(userRequest);

        assertEquals(200, response.getStatusCodeValue());
        verify(usuarioRepository, times(1)).save(user);
    }

    @Test
    void shouldSaveUserWithEmployeeSuccessfully() {
        UsuarioRequest userRequest = new UsuarioRequest();
        Usuario user = new Usuario();
        Empleado employee = new Empleado(1, user, new Firma());
        Rol rol = new Rol(1, "test");
        userRequest.setUser(user);
        user.setRol(rol);
        userRequest.setEmployee(employee);

        when(usuarioRepository.save(user)).thenReturn(user);

        ResponseEntity<?> response = userService.saveUser(userRequest);

        assertEquals(200, response.getStatusCodeValue());
        verify(usuarioRepository, times(1)).save(user);
    }

    @Test
    void shouldUpdateUserSuccessfully() {
        Usuario user = new Usuario();

        when(usuarioRepository.save(user)).thenReturn(user);

        ResponseEntity<?> response = userService.updateUser(user);

        assertEquals(200, response.getStatusCodeValue());
        verify(usuarioRepository, times(1)).save(user);
    }

    @Test
    void shouldDeleteUserSuccessfully() {
        Usuario user = new Usuario();
        user.setId(1);

        when(usuarioRepository.findById(1)).thenReturn(Optional.of(user));

        ResponseEntity<?> response = userService.deleteUser(1);

        assertEquals(200, response.getStatusCodeValue());
        verify(usuarioRepository, times(1)).findById(1);
    }

    @Test
    void shouldNotDeleteUserWhenNotFound() {
        when(usuarioRepository.findById(1)).thenReturn(Optional.empty());

        ResponseEntity<?> response = userService.deleteUser(1);

        assertEquals(404, response.getStatusCodeValue());
        verify(usuarioRepository, times(1)).findById(1);
    }

    @Test
    void shouldFindUserByNameSuccessfully() {
        Usuario user = new Usuario();
        user.setUsername("test");

        when(usuarioRepository.findByUsername("test")).thenReturn(user);

        ResponseEntity<?> response = userService.findByUserName("test");

        assertEquals(200, response.getStatusCodeValue());
        verify(usuarioRepository, times(1)).findByUsername("test");
    }

    @Test
    void shouldNotFindUserByNameWhenNotFound() {
        when(usuarioRepository.findByUsername("test")).thenReturn(null);

        ResponseEntity<?> response = userService.findByUserName("test");

        assertEquals(404, response.getStatusCodeValue());
        verify(usuarioRepository, times(1)).findByUsername("test");
    }

    @Test
    void shouldFindAllAbogadosNamesNoSuccessfully() {
        Usuario user = new Usuario();
        user.setNombres("test");

        when(usuarioRepository.findAllNamesAbogadosByFirma(1)).thenReturn(null);

        ResponseEntity<?> response = userService.findAllAbogadosNames(1);

        assertEquals(404, response.getStatusCodeValue());
        verify(usuarioRepository, times(1)).findAllNamesAbogadosByFirma(1);
    }

    @Test
    void shouldFindAllAbogadosNamesSuccessfully() {
        Usuario user = new Usuario();
        user.setNombres("test");

        when(usuarioRepository.findAllNamesAbogadosByFirma(1)).thenReturn(List.of(user));

        ResponseEntity<?> response = userService.findAllAbogadosNames(1);

        assertEquals(200, response.getStatusCodeValue());
        verify(usuarioRepository, times(1)).findAllNamesAbogadosByFirma(1);
    }

    @Test
    void shouldFindByUserNameSuccessfully() {
        Usuario user = new Usuario();
        user.setUsername("test");

        when(usuarioRepository.findByUsername("test")).thenReturn(user);

        ResponseEntity<?> response = userService.findByUserName("test");

        assertEquals(200, response.getStatusCodeValue());
        verify(usuarioRepository, times(1)).findByUsername("test");
    }

    @Test
    void shouldNotFindByUserNameWhenNotFound() {
        when(usuarioRepository.findByUsername("test")).thenReturn(null);

        ResponseEntity<?> response = userService.findByUserName("test");

        assertEquals(404, response.getStatusCodeValue());
        verify(usuarioRepository, times(1)).findByUsername("test");
    }

    @Test
    void shouldFindRoleByUserSuccessfully() {
        Usuario user = new Usuario();
        user.setUsername("test");
        Rol rol = new Rol(1, "test");
        user.setRol(rol);

        when(rolRepository.findByUsuario("test")).thenReturn(rol);

        ResponseEntity<?> response = userService.findRoleByUser("test");

        assertEquals(200, response.getStatusCodeValue());
        verify(rolRepository, times(1)).findByUsuario("test");
    }

    @Test
    void shouldNotFindRoleByUserWhenNotFound() {
        when(rolRepository.findByUsuario("test")).thenReturn(null);

        ResponseEntity<?> response = userService.findRoleByUser("test");

        assertEquals(404, response.getStatusCodeValue());
        verify(rolRepository, times(1)).findByUsuario("test");
    }

    @Test
    void shouldSaveRolSuccessfully() {
        Rol rol = new Rol(null, "test");

        when(rolRepository.save(rol)).thenReturn(rol);

        ResponseEntity<?> response = userService.saveRol("test");

        assertEquals(201, response.getStatusCodeValue());
    }

    @Test
    void shouldFindRolByNameSuccessfully() {
        Rol rol = new Rol(1, "test");
        when(rolRepository.findByNombre("test")).thenReturn(rol);

        ResponseEntity<?> response = userService.findRolByName("test");

        assertEquals(200, response.getStatusCodeValue());
        verify(rolRepository, times(1)).findByNombre("test");
    }

    @Test
    void shouldFindRolByNameNoSuccessfully() {
        when(rolRepository.findByNombre("test")).thenReturn(null);

        ResponseEntity<?> response = userService.findRolByName("test");

        assertEquals(404, response.getStatusCodeValue());
        verify(rolRepository, times(1)).findByNombre("test");
    }

    @Test
    void shouldFindAllTipoAbogadoSuccessfully() {
        when(tipoAbogadoRepository.findAll()).thenReturn(List.of());

        ResponseEntity<?> response = userService.findAllTipoAbogado();

        assertEquals(200, response.getStatusCodeValue());
        verify(tipoAbogadoRepository, times(1)).findAll();
    }

    @Test
    void shouldFindTipoAbogadoByNameSuccessfully() {
        TipoAbogado tipoAbogado = new TipoAbogado(1, "test");
        when(tipoAbogadoRepository.findByNombre("test")).thenReturn(tipoAbogado);

        ResponseEntity<?> response = userService.findTipoAbogadoByName("test");

        assertEquals(200, response.getStatusCodeValue());
        verify(tipoAbogadoRepository, times(1)).findByNombre("test");
    }

    @Test
    void shouldFindTipoAbogadoByNameNoSuccessfully() {
        when(tipoAbogadoRepository.findByNombre("test")).thenReturn(null);

        ResponseEntity<?> response = userService.findTipoAbogadoByName("test");

        assertEquals(404, response.getStatusCodeValue());
        verify(tipoAbogadoRepository, times(1)).findByNombre("test");
    }

    @Test
    void shouldSaveTipoAbogadoSuccessfully() {
        TipoAbogado tipoAbogado = new TipoAbogado(null, "test");

        when(tipoAbogadoRepository.save(tipoAbogado)).thenReturn(tipoAbogado);

        ResponseEntity<?> response = userService.saveTipoAbogado("test");

        assertEquals(201, response.getStatusCodeValue());
    }

    @Test
    void shouldFindAllTipoDocumentoSuccessfully() {
        when(tipoDocumentoRepository.findAll()).thenReturn(List.of());

        ResponseEntity<?> response = userService.findAllTipoDocumento();

        assertEquals(200, response.getStatusCodeValue());
        verify(tipoDocumentoRepository, times(1)).findAll();
    }

    @Test
    void shouldFindTipoDocumentoByNameSuccessfully() {
        TipoDocumento tipoDocumento = new TipoDocumento(1, "test");
        when(tipoDocumentoRepository.findByNombre("test")).thenReturn(tipoDocumento);

        ResponseEntity<?> response = userService.findTipoDocumentoByName("test");

        assertEquals(200, response.getStatusCodeValue());
        verify(tipoDocumentoRepository, times(1)).findByNombre("test");
    }

    @Test
    void shouldFindTipoDocumentoByNameNoSuccessfully() {
        when(tipoDocumentoRepository.findByNombre("test")).thenReturn(null);

        ResponseEntity<?> response = userService.findTipoDocumentoByName("test");

        assertEquals(404, response.getStatusCodeValue());
        verify(tipoDocumentoRepository, times(1)).findByNombre("test");
    }

    @Test
    void shouldSaveTipoDocumentoSuccessfully() {
        TipoDocumento tipoDocumento = new TipoDocumento(null, "test");

        when(tipoDocumentoRepository.save(tipoDocumento)).thenReturn(tipoDocumento);

        ResponseEntity<?> response = userService.saveTipoDocumento("test");

        assertEquals(201, response.getStatusCodeValue());
    }

    @Test
    void shouldFindUserByIdSuccessfully() {
        Usuario user = new Usuario();
        user.setId(1);

        when(usuarioRepository.findById(1)).thenReturn(Optional.of(user));

        ResponseEntity<?> response = userService.getUserById(1);

        assertEquals(200, response.getStatusCodeValue());
        verify(usuarioRepository, times(1)).findById(1);
    }

    @Test
    void shouldNotFindUserByIdWhenNotFound() {
        when(usuarioRepository.findById(1)).thenReturn(Optional.empty());

        ResponseEntity<?> response = userService.getUserById(1);

        assertEquals(404, response.getStatusCodeValue());
        verify(usuarioRepository, times(1)).findById(1);
    }

    @Test
    void shouldFindAssignedProcessesSuccessfully() {
        Integer userId = 1;
        Integer assignedProcesses = 5;
        when(usuarioRepository.getNumberAssignedProcesses(userId)).thenReturn(assignedProcesses);

        ResponseEntity<?> response = userService.getAssingedProcesses(1);

        assertEquals(200, response.getStatusCodeValue());
        verify(usuarioRepository, times(1)).getNumberAssignedProcesses(userId);
    }

    @Test
    void shouldFindByIdSuccessfully() {
        Usuario user = new Usuario();
        user.setId(1);

        when(usuarioRepository.findById(1)).thenReturn(Optional.of(user));

        Usuario us  = userService.findById(1);

        assertEquals(user, us);
        verify(usuarioRepository, times(1)).findById(1);
    }

}
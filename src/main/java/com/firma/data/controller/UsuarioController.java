package com.firma.data.controller;

import com.firma.data.model.*;
import com.firma.data.payload.request.UsuarioRequest;
import com.firma.data.payload.response.FirmaUsuariosResponse;
import com.firma.data.payload.response.UsuarioResponse;
import com.firma.data.service.intf.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@EnableTransactionManagement
@RequestMapping("/api/data/usuario")
public class UsuarioController {

    @Autowired
    private IUsuarioService usuarioService;
    @Autowired
    private IRoleService roleService;
    @Autowired
    private IFirmaService firmaService;
    @Autowired
    private ITipoDocumentoService tipoDocumentoService;
    @Autowired
    private ITipoAbogadoService tipoAbogadoService;
    @Autowired
    private IEmpleadoService empleadoService;
    @Autowired
    private IStorageService storageService;

    @GetMapping("/get/abogados")
    public ResponseEntity<?> getFirmaAbogados(@RequestParam Integer firmaId) {
        Rol role = roleService.findByName("ABOGADO");
        Set<Usuario> users = usuarioService.findAllAbogadosByFirma(firmaId, role.getId());
        if (users == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        List<UsuarioResponse> userResponse = new ArrayList<>();

        for (Usuario user : users) {
            Integer number = usuarioService.numberAssignedProcesses(user.getId());
            List<String> especialidades = new ArrayList<>();

            for(TipoAbogado tipoAbogado : user.getEspecialidadesAbogado()){
                especialidades.add(tipoAbogado.getNombre());
            }
            userResponse.add(UsuarioResponse.builder()
                    .id(user.getId())
                    .nombres(user.getNombres())
                    .correo(user.getCorreo())
                    .telefono(user.getTelefono())
                    .especialidades(especialidades)
                    .numeroProcesosAsignados(number)
                    .build());

        }

        Firma firma = firmaService.findById(firmaId);
        FirmaUsuariosResponse response = FirmaUsuariosResponse.builder()
                .id(firma.getId())
                .nombre(firma.getNombre())
                .direccion(firma.getDireccion())
                .usuarios(userResponse)
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/get/abogado")
    public ResponseEntity<?> getAbogado(@RequestParam Integer usuarioId) {
        Usuario user = usuarioService.findById(usuarioId);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        List<String> especialidades = new ArrayList<>();

        for(TipoAbogado tipoAbogado : user.getEspecialidadesAbogado()){
            especialidades.add(tipoAbogado.getNombre());
        }
        UsuarioResponse response = UsuarioResponse.builder()
                .id(user.getId())
                .nombres(user.getNombres())
                .correo(user.getCorreo())
                .telefono(user.getTelefono())
                .identificacion(user.getIdentificacion())
                .especialidades(especialidades)
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Transactional
    @PostMapping("/upload/photo")
    public ResponseEntity<?> addAbogado(@RequestParam("image") MultipartFile file, @RequestParam Integer usuarioId) {
        try {
            storageService.uploadImage(file, usuarioId);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @GetMapping("/download/photo")
    public ResponseEntity<?> downloadImage(@RequestParam Integer usuarioId){
        byte[] image = storageService.downloadImage(usuarioId);
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(image);
    }

    @Transactional
    @PostMapping("/add/abogado")
    public ResponseEntity<?> addAbogado(@RequestBody UsuarioRequest userRequest) {
        TipoDocumento tipoDocumento = tipoDocumentoService.findByName(userRequest.getTipoDocumento());
        Rol role = roleService.findByName("ABOGADO");

        Set<TipoAbogado> tipoAbogados = new HashSet<>();

        for (String especialidad : userRequest.getEspecialidades()) {
            TipoAbogado tipoAbogado = tipoAbogadoService.findByName(especialidad);
            tipoAbogados.add(tipoAbogado);
        }

        Usuario newUser = Usuario.builder()
                .nombres(userRequest.getNombres())
                .correo(userRequest.getCorreo())
                .username(userRequest.getUsername())
                .telefono(userRequest.getTelefono())
                .password(userRequest.getPassword())
                .identificacion(userRequest.getIdentificacion())
                .rol(role)
                .tipodocumento(tipoDocumento)
                .especialidadesAbogado(tipoAbogados)
                .build();

        usuarioService.save(newUser);

        Firma firma = firmaService.findById(userRequest.getFirmaId());

        Empleado empleado = Empleado.builder()
                .usuario(newUser)
                .firma(firma)
                .build();

        empleadoService.saveEmpleado(empleado);

        return new ResponseEntity<>(HttpStatus.OK);

    }

    @GetMapping("/get/personal/info/jefe")
    public ResponseEntity <?> getPersonalInfo (@RequestParam Integer id){
        Usuario user = usuarioService.findById(id);
        if (user == null) {
            return new ResponseEntity<>("Usuario no encontrado", HttpStatus.NOT_FOUND);
        }
        UsuarioResponse response = UsuarioResponse.builder()
                .id(user.getId())
                .nombres(user.getNombres())
                .correo(user.getCorreo())
                .telefono(user.getTelefono())
                .identificacion(user.getIdentificacion())
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/update/personal/info/jefe")
    public ResponseEntity <?> updatePersonalInfo (@RequestBody UsuarioRequest userRequest, @RequestParam Integer id){
        Usuario user = usuarioService.findById(id);
        if (user == null) {
            return new ResponseEntity<>("Usuario no encontrado", HttpStatus.NOT_FOUND);
        }
        user.setNombres(userRequest.getNombres());
        user.setCorreo(userRequest.getCorreo());
        user.setTelefono(userRequest.getTelefono());
        user.setIdentificacion(userRequest.getIdentificacion());
        usuarioService.save(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/get/personal/info/abogado")
    public ResponseEntity <?> getPersonalInfoAbogado (@RequestParam Integer id){
        Usuario user = usuarioService.findById(id);
        if (user == null) {
            return new ResponseEntity<>("Usuario no encontrado", HttpStatus.NOT_FOUND);
        }
        List<String> especialidades = new ArrayList<>();

        for(TipoAbogado tipoAbogado : user.getEspecialidadesAbogado()){
            especialidades.add(tipoAbogado.getNombre());
        }
        UsuarioResponse response = UsuarioResponse.builder()
                .id(user.getId())
                .nombres(user.getNombres())
                .correo(user.getCorreo())
                .telefono(user.getTelefono())
                .identificacion(user.getIdentificacion())
                .especialidades(especialidades)
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/update/personal/info/abogado")
    public ResponseEntity <?> updatePersonalInfoAbogado (@RequestBody UsuarioRequest userRequest, @RequestParam Integer id){
        Usuario user = usuarioService.findById(id);
        if (user == null) {
            return new ResponseEntity<>("Usuario no encontrado", HttpStatus.NOT_FOUND);
        }
        user.setNombres(userRequest.getNombres());
        user.setCorreo(userRequest.getCorreo());
        user.setTelefono(userRequest.getTelefono());
        user.setIdentificacion(userRequest.getIdentificacion());
        usuarioService.save(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

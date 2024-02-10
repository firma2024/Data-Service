package com.firma.data.service.impl;

import com.firma.data.model.*;
import com.firma.data.payload.request.UsuarioRequest;
import com.firma.data.payload.response.PageableResponse;
import com.firma.data.payload.response.UsuarioResponse;
import com.firma.data.repository.RolRepository;
import com.firma.data.repository.TipoAbogadoRepository;
import com.firma.data.repository.TipoDocumentoRepository;
import com.firma.data.repository.UsuarioRepository;
import com.firma.data.service.intf.IFirmaService;
import com.firma.data.service.intf.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserService implements IUserService {

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private RolRepository rolRepository;
    @Autowired
    private TipoAbogadoRepository tipoAbogadoRepository;
    @Autowired
    private TipoDocumentoRepository tipoDocumentoRepository;
    @Autowired
    private IFirmaService firmaService;

    @Transactional
    @Override
    public ResponseEntity<?> saveAbogado(UsuarioRequest userRequest) {
        TipoDocumento typeDocument = tipoDocumentoRepository.findByNombre(userRequest.getTipoDocumento());
        Rol role = rolRepository.findByNombre("ABOGADO");

        Set<TipoAbogado> specialties = new HashSet<>();

        for (String specialty : userRequest.getEspecialidades()) {
            TipoAbogado typeLawyer = tipoAbogadoRepository.findByNombre(specialty);
            specialties.add(typeLawyer);
        }

        Usuario newUser = Usuario.builder()
                .nombres(userRequest.getNombres())
                .correo(userRequest.getCorreo())
                .username(userRequest.getUsername())
                .telefono(userRequest.getTelefono())
                .identificacion(userRequest.getIdentificacion())
                .rol(role)
                .tipodocumento(typeDocument)
                .especialidadesAbogado(specialties)
                .eliminado('N')
                .build();

        usuarioRepository.save(newUser);

        Firma firma = firmaService.findFirmaById(userRequest.getFirmaId());
        Empleado newEmployee = Empleado.builder()
                .usuario(newUser)
                .firma(firma)
                .build();

        firmaService.saveEmpleado(newEmployee);

        return new ResponseEntity<>("Abogado fue creado", HttpStatus.OK);
    }

    @Transactional
    @Override
    public ResponseEntity<?> saveJefe(UsuarioRequest userRequest) {
        TipoDocumento tipoDocumento = tipoDocumentoRepository.findByNombre(userRequest.getTipoDocumento());
        Rol role = rolRepository.findByNombre("JEFE");

        Usuario newUser = Usuario.builder()
                .nombres(userRequest.getNombres())
                .correo(userRequest.getCorreo())
                .username(userRequest.getUsername())
                .telefono(userRequest.getTelefono())
                .identificacion(userRequest.getIdentificacion())
                .rol(role)
                .tipodocumento(tipoDocumento)
                .eliminado('N')
                .build();

        usuarioRepository.save(newUser);

        Firma firma = firmaService.findFirmaById(userRequest.getFirmaId());
        Empleado empleado = Empleado.builder()
                .usuario(newUser)
                .firma(firma)
                .build();

        firmaService.saveEmpleado(empleado);

        return new ResponseEntity<>("Jefe fue creado", HttpStatus.OK);
    }

    @Transactional
    @Override
    public ResponseEntity<?> saveAdmin(UsuarioRequest userRequest) {
        TipoDocumento tipoDocumento = tipoDocumentoRepository.findByNombre(userRequest.getTipoDocumento());
        Rol role = rolRepository.findByNombre("ADMIN");

        Usuario newUser = Usuario.builder()
                .nombres(userRequest.getNombres())
                .correo(userRequest.getCorreo())
                .username(userRequest.getUsername())
                .telefono(userRequest.getTelefono())
                .identificacion(userRequest.getIdentificacion())
                .rol(role)
                .tipodocumento(tipoDocumento)
                .eliminado('N')
                .build();

        usuarioRepository.save(newUser);
        return new ResponseEntity<>("Admin fue creado", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> updateAbogado(UsuarioRequest userRequest) {
        Usuario user = usuarioRepository.findById(userRequest.getId()).orElse(null);
        if (user == null) {
            return new ResponseEntity<>("Usuario no encontrado", HttpStatus.NOT_FOUND);
        }
        user.setNombres(userRequest.getNombres());
        user.setCorreo(userRequest.getCorreo());
        user.setTelefono(userRequest.getTelefono());
        user.setIdentificacion(userRequest.getIdentificacion());
        usuarioRepository.save(user);
        return new ResponseEntity<>("Usuario Actualizado", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> updateJefe(UsuarioRequest userRequest) {
        Usuario user = usuarioRepository.findById(userRequest.getId()).orElse(null);
        if (user == null) {
            return new ResponseEntity<>("Usuario no encontrado", HttpStatus.NOT_FOUND);
        }

        Set<TipoAbogado> specialties = new HashSet<>();

        for (String specialty : userRequest.getEspecialidades()) {
            TipoAbogado typeLawyer = tipoAbogadoRepository.findByNombre(specialty);
            specialties.add(typeLawyer);
        }

        user.setNombres(userRequest.getNombres());
        user.setCorreo(userRequest.getCorreo());
        user.setTelefono(userRequest.getTelefono());
        user.setIdentificacion(userRequest.getIdentificacion());
        user.setEspecialidadesAbogado(specialties);
        usuarioRepository.save(user);
        return new ResponseEntity<>("Usuario Actualizado", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> deleteUser(Integer id) {
        Usuario user = usuarioRepository.findById(id).orElse(null);
        if (user == null) {
            return new ResponseEntity<>("Usuario no encontrado", HttpStatus.NOT_FOUND);
        }

        if (user.getRol().getNombre().equals("ABOGADO")){
            Integer number = usuarioRepository.getNumberAssignedProcesses(user.getId());
            if (number == null) {
                number = 0;
            }
            if (number != 0) {
                return new ResponseEntity<>("El abogado tiene procesos asignados", HttpStatus.BAD_REQUEST);
            }
        }

        user.setEliminado('S');
        usuarioRepository.save(user);
        return new ResponseEntity<>("Usuario Eliminado", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getInfoAbogado(Integer id) {
        Usuario user = usuarioRepository.findById(id).orElse(null);
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

    @Override
    public ResponseEntity<?> getInfoJefe(Integer id) {
        Usuario user = usuarioRepository.findById(id).orElse(null);
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

    @Override
    public ResponseEntity<?> findAllAbogadosNames(Integer firmaId) {
        List<Usuario> users = usuarioRepository.findAllNamesAbogadosByFirma(firmaId);
        if (users == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        List<UsuarioResponse> userResponse = new ArrayList<>();

        for (Usuario user : users) {
            userResponse.add(UsuarioResponse.builder()
                    .id(user.getId())
                    .nombres(user.getNombres())
                    .build());
        }

        return new ResponseEntity<>(userResponse, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> findAllAbogadosByFirmaFilter(Integer numProcesosInicial, Integer numProcesosFinal, List<String> especialidades, Integer firmaId, Integer page, Integer size) {
        Rol role = rolRepository.findByNombre("ABOGADO");
        Pageable paging = PageRequest.of(page, size, Sort.by("nombres").ascending());
        Page<Usuario> pageUsers = usuarioRepository.findAbogadosFirmaByFilter(especialidades, paging, firmaId, role.getId());
        List<UsuarioResponse> userResponse = new ArrayList<>();

        for (Usuario user : pageUsers.getContent()) {
            Integer number = usuarioRepository.getNumberAssignedProcesses(user.getId());
            if (number == null) {
                number = 0;
            }
            List<String> especialidadesAbogado = new ArrayList<>();

            for(TipoAbogado tipoAbogado : user.getEspecialidadesAbogado()){
                especialidadesAbogado.add(tipoAbogado.getNombre());
            }
            if (number >= numProcesosInicial && number <= numProcesosFinal) {
                userResponse.add(UsuarioResponse.builder()
                        .id(user.getId())
                        .nombres(user.getNombres())
                        .correo(user.getCorreo())
                        .telefono(user.getTelefono())
                        .especialidades(especialidadesAbogado)
                        .numeroProcesosAsignados(number)
                        .build());
            }
        }

        PageableResponse<UsuarioResponse> response = PageableResponse.<UsuarioResponse>builder()
                .data(userResponse)
                .currentPage(pageUsers.getNumber() + 1)
                .totalItems(pageUsers.getTotalElements())
                .totalPages(pageUsers.getTotalPages())
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> findByUserName(String name) {
        Usuario user = usuarioRepository.findByUsername(name);
        if (user == null) {
            return new ResponseEntity<>("Usuario no encontrado", HttpStatus.NOT_FOUND);
        }
        UsuarioResponse res = UsuarioResponse.builder()
                .id(user.getId())
                .nombres(user.getNombres())
                .build();
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> findRoleByUser(String userName) {
        Rol rol = rolRepository.findByUsuario(userName);
        if (rol == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(rol, HttpStatus.OK);
    }
    
    @Override
    public ResponseEntity<?> saveRol(String name) {
        Rol rol = Rol.builder()
                .nombre(name)
                .build();
        rolRepository.save(rol);
        return new ResponseEntity<>("Rol Creado", HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<?> findAllTipoAbogado() {
        return new ResponseEntity<>(tipoAbogadoRepository.findAll(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> tipoAbogadoFindByName(String name) {
        TipoAbogado tipoAbogado = tipoAbogadoRepository.findByNombre(name);
        if (tipoAbogado == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(tipoAbogado, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> saveTipoAbogado(String name) {
        TipoAbogado tipoAbogado = TipoAbogado.builder()
                .nombre(name)
                .build();
        tipoAbogadoRepository.save(tipoAbogado);
        return new ResponseEntity<>("Tipo Abogado Creado", HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<?> findAllTipoDocumento() {
        return new ResponseEntity<>(tipoDocumentoRepository.findAll(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> findByNameTipoDocumento(String name) {
        TipoDocumento tipoDocumento = tipoDocumentoRepository.findByNombre(name);
        if (tipoDocumento == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(tipoDocumento, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> saveTipoDocumento(String name) {
        TipoDocumento tipoDocumento = TipoDocumento.builder()
                .nombre(name)
                .build();
        tipoDocumentoRepository.save(tipoDocumento);
        return new ResponseEntity<>("Tipo Documento Creado", HttpStatus.CREATED);
    }

    @Override
    public Usuario findById(Integer userId) {
        return usuarioRepository.findById(userId).orElse(null);
    }

    @Override
    public void update(Usuario user) {
        usuarioRepository.save(user);
    }
}

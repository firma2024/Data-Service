package com.firma.data.implService;

import com.firma.data.intfService.IUserService;
import com.firma.data.model.*;
import com.firma.data.payload.request.UserRequest;
import com.firma.data.payload.request.UsuarioRequest;
import com.firma.data.payload.response.PageableResponse;
import com.firma.data.repository.RolRepository;
import com.firma.data.repository.TipoAbogadoRepository;
import com.firma.data.repository.TipoDocumentoRepository;
import com.firma.data.repository.UsuarioRepository;
import com.firma.data.intfService.IFirmaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



import java.util.List;

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
    public ResponseEntity<?> saveUser(UsuarioRequest userRequest) {

        Usuario user = usuarioRepository.save(userRequest.getUser());

        if (userRequest.getEmployee() != null){
            userRequest.getEmployee().setUsuario(user);
            firmaService.saveEmpleado(userRequest.getEmployee());
        }

        return new ResponseEntity<>(user.getId(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> updateUser(Usuario user) {
        usuarioRepository.save(user);
        return new ResponseEntity<>("Usuario Actualizado", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> deleteUser(Integer id) {
        Usuario user = usuarioRepository.findById(id).orElse(null);
        if (user == null) {
            return new ResponseEntity<>("Usuario no encontrado", HttpStatus.NOT_FOUND);
        }
        user.setEliminado('S');
        usuarioRepository.save(user);
        return new ResponseEntity<>("Usuario Eliminado", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> findAllAbogadosNames(Integer firmaId) {
        List<Usuario> users = usuarioRepository.findAllNamesAbogadosByFirma(firmaId);
        if (users == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> findAllAbogadosByFirmaFilter(List<String> especialidades, Integer firmaId, Integer rolId, Integer page, Integer size) {
        Pageable paging = PageRequest.of(page, size, Sort.by("nombres").ascending());
        Page<Usuario> pageUsers = usuarioRepository.findAbogadosFirmaByFilter(especialidades, paging, firmaId, rolId);

        PageableResponse<Usuario> response = PageableResponse.<Usuario>builder()
                .data(pageUsers.getContent())
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
        return new ResponseEntity<>(user, HttpStatus.OK);
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
    public ResponseEntity<?> getUserById(Integer id) {
        Usuario user = usuarioRepository.findById(id).orElse(null);
        if (user == null) {
            return new ResponseEntity<>("Usuario no encontrado", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getAssingedProcesses(Integer id) {
        return new ResponseEntity<>(usuarioRepository.getNumberAssignedProcesses(id), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> findRolByName(String name) {
        Rol rol = rolRepository.findByNombre(name);
        if (rol == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(rol, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> findTipoAbogadoByName(String name) {
        TipoAbogado tipoAbogado = tipoAbogadoRepository.findByNombre(name);
        if (tipoAbogado == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(tipoAbogado, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> findTipoDocumentoByName(String name) {
        TipoDocumento tipoDocumento = tipoDocumentoRepository.findByNombre(name);
        if (tipoDocumento == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(tipoDocumento, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> checkInsertUser(UserRequest userRequest) {
        Usuario user;
        user = usuarioRepository.findByUsername(userRequest.getUsername());
        if (user != null) {
            return new ResponseEntity<>("Usuario ya existe", HttpStatus.CONFLICT);
        }
        user = usuarioRepository.findByCorreo(userRequest.getCorreo());
        if (user != null) {
            return new ResponseEntity<>("Correo ya existe", HttpStatus.CONFLICT);
        }
        user = usuarioRepository.findByIdentificacion(userRequest.getIdentificacion());
        if (user != null) {
            return new ResponseEntity<>("Identificacion ya existe", HttpStatus.CONFLICT);
        }
        user = usuarioRepository.findByTelefono(userRequest.getTelefono());
        if (user != null) {
            return new ResponseEntity<>("Telefono ya existe", HttpStatus.CONFLICT);
        }

        return new ResponseEntity<>("Usuario no existe", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> findAllAbogadosByFirma(Integer firmaId) {
        return new ResponseEntity<>(usuarioRepository.findAllAbogadosByFirma(firmaId), HttpStatus.OK);
    }
}
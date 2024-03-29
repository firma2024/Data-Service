package com.firma.data.intfService;

import com.firma.data.model.Usuario;
import com.firma.data.payload.request.UserRequest;
import com.firma.data.payload.request.UsuarioRequest;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IUserService {
    ResponseEntity<?> saveUser(UsuarioRequest userRequest);
    ResponseEntity<?> updateUser(Usuario user);
    ResponseEntity<?> deleteUser(Integer id);
    ResponseEntity<?> findAllAbogadosNames(Integer firmaId);
    ResponseEntity<?> findAllAbogadosByFirmaFilter(List<String> especialidades, Integer firmaId, Integer rolId, Integer page, Integer size);
    ResponseEntity<?> findByUserName(String name);
    ResponseEntity<?> findRoleByUser(String userName);
    ResponseEntity<?> saveRol(String name);
    ResponseEntity<?> findAllTipoAbogado();
    ResponseEntity<?> saveTipoAbogado(String name);
    ResponseEntity<?> findAllTipoDocumento();
    ResponseEntity<?> saveTipoDocumento(String name);
    Usuario findById(Integer userId);
    ResponseEntity<?> getUserById(Integer id);
    ResponseEntity<?> getAssingedProcesses(Integer id);
    ResponseEntity<?> findRolByName(String name);
    ResponseEntity<?> findTipoAbogadoByName(String name);
    ResponseEntity<?> findTipoDocumentoByName(String name);
    ResponseEntity<?> checkInsertUser(UserRequest userRequest);
    ResponseEntity<?> findAllAbogadosByFirma(Integer firmaId);
}
package com.firma.data.service.intf;

import com.firma.data.model.Usuario;
import com.firma.data.payload.request.UsuarioRequest;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IUserService {
    ResponseEntity<?> saveUser(UsuarioRequest userRequest);
    ResponseEntity<?> updateUser(Usuario user);
    ResponseEntity<?> deleteUser(Integer id);
    ResponseEntity<?> findAllAbogadosNames(Integer firmaId);
    ResponseEntity<?> findAllAbogadosByFirmaFilter(Integer numProcesosInicial, Integer numProcesosFinal, List<String> especialidades, Integer firmaId, Integer rolId, Integer page, Integer size);
    ResponseEntity<?> findByUserName(String name);
    ResponseEntity<?> findRoleByUser(String userName);
    ResponseEntity<?> saveRol(String name);
    ResponseEntity<?> findAllTipoAbogado();
    ResponseEntity<?> tipoAbogadoFindByName(String name);
    ResponseEntity<?> saveTipoAbogado(String name);
    ResponseEntity<?> findAllTipoDocumento();
    ResponseEntity<?> findByNameTipoDocumento(String name);
    ResponseEntity<?> saveTipoDocumento(String name);
    Usuario findById(Integer userId);
    ResponseEntity<?> getUserById(Integer id);
    ResponseEntity<?> getAssingedProcesses(Integer id);
    ResponseEntity<?> findRolByName(String name);
    ResponseEntity<?> findTipoAbogadoByName(String name);
    ResponseEntity<?> findTipoDocumentoByName(String name);
}

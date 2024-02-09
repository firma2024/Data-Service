package com.firma.data.service.intf;

import com.firma.data.model.Usuario;
import com.firma.data.payload.request.UsuarioRequest;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IUserService {
    ResponseEntity<?> saveAbogado(UsuarioRequest userRequest);
    ResponseEntity<?> saveJefe(UsuarioRequest userRequest);
    ResponseEntity<?> saveAdmin(UsuarioRequest userRequest);
    ResponseEntity<?> updateAbogado(UsuarioRequest userRequest);
    ResponseEntity<?> updateJefe(UsuarioRequest userRequest);
    ResponseEntity<?> deleteUser(Integer id);
    ResponseEntity<?> getInfoAbogado(Integer id);
    ResponseEntity<?> getInfoJefe(Integer id);
    ResponseEntity<?> findAllAbogadosNames(Integer firmaId);
    ResponseEntity<?> findAllAbogadosByFirmaFilter(Integer numProcesosInicial, Integer numProcesosFinal, List<String> especialidades, Integer firmaId, Integer page, Integer size);
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
    void update(Usuario user);
}

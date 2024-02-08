package com.firma.data.controller;

import com.firma.data.payload.request.UsuarioRequest;
import com.firma.data.service.intf.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@EnableTransactionManagement
@RequestMapping("/api/data/user")
public class UserController {

    @Autowired
    private IUserService userService;

    @PostMapping("/add/abogado")
    public ResponseEntity<?> addAbogado(@RequestBody UsuarioRequest userRequest) {
        return userService.saveAbogado(userRequest);
    }

    @PostMapping("/add/jefe")
    public ResponseEntity<?> addJefe(@RequestBody UsuarioRequest userRequest) {
        return userService.saveJefe(userRequest);
    }

    @PostMapping("/add/admin")
    public ResponseEntity<?> addAdmin(@RequestBody UsuarioRequest userRequest) {
        return userService.saveAdmin(userRequest);
    }

    @GetMapping("/get/info/jefe")
    public ResponseEntity <?> getPersonalInfo (@RequestParam Integer id){
        return userService.getInfoJefe(id);
    }

    @GetMapping("/get/info/abogado")
    public ResponseEntity <?> getPersonalInfoAbogado (@RequestParam Integer id){
        return userService.getInfoAbogado(id);
    }

    @PutMapping("/update/info/abogado")
    public ResponseEntity <?> updatePersonalInfoAbogado (@RequestBody UsuarioRequest userRequest){
        return userService.updateAbogado(userRequest);
    }

    @PutMapping("/update/info/jefe")
    public ResponseEntity <?> updatePersonalInfoJefe (@RequestBody UsuarioRequest userRequest){
        return userService.updateJefe(userRequest);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteAbogado(@RequestParam Integer id) {
        return userService.deleteUser(id);
    }

    @GetMapping("/get/name")
    public ResponseEntity<?> getUserName(@RequestParam String userName) {
        return userService.findByUserName(userName);
    }

    @GetMapping("/get/all/names/abogados")
    public ResponseEntity<?> getAllAbogadosNames(@RequestParam Integer firmaId) {
        return userService.findAllAbogadosNames(firmaId);
    }

    @GetMapping("/jefe/abogados/filter")
    public ResponseEntity<?> getAbogadosFilter(@RequestParam(required = false) List<String> especialidades,
                                               @RequestParam Integer firmaId,
                                               @RequestParam(defaultValue = "0") Integer numProcesosInicial,
                                               @RequestParam(defaultValue = "5") Integer numProcesosFinal,
                                               @RequestParam(defaultValue = "0") Integer page,
                                               @RequestParam(defaultValue = "2") Integer size){
        return userService.findAllAbogadosByFirmaFilter(numProcesosInicial, numProcesosFinal, especialidades, firmaId, page, size);
    }

    @GetMapping("/get/abogado")
    public ResponseEntity<?> getAbogado(@RequestParam Integer usuarioId){
        return userService.getInfoAbogado(usuarioId);
    }

    @GetMapping("/rol/get/user")
    public ResponseEntity<?> getRoleByUser(@RequestParam String username){
        return userService.findRoleByUser(username);
    }

    @GetMapping("/rol/get")
    public ResponseEntity<?> getRole(@RequestParam String roleName){
        return userService.findRoleByUser(roleName);
    }

    @GetMapping("/tipoAbogado/get/all")
    public ResponseEntity<?> getAllTipoAbogado(){
        return userService.findAllTipoAbogado();
    }

    @GetMapping("/tipoAbogado/get")
    public ResponseEntity<?> getTipoAbogado(@RequestParam String name){
        return userService.tipoAbogadoFindByName(name);
    }

    @GetMapping("/tipoDocumento/get/all")
    public ResponseEntity<?> getAllTipoDocumento(){
        return userService.findAllTipoDocumento();
    }

    @GetMapping("/tipoDocumento/get")
    public ResponseEntity<?> getTipoDocumento(@RequestParam String name){
        return userService.findByNameTipoDocumento(name);
    }
}

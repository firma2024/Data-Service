package com.firma.data.controller;

import com.firma.data.model.Usuario;
import com.firma.data.payload.request.UserRequest;
import com.firma.data.payload.request.UsuarioRequest;
import com.firma.data.intfService.IUserService;
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

    @PostMapping("/check/insert")
    public ResponseEntity<?> checkInsertUser(@RequestBody UserRequest userRequest) {
        return userService.checkInsertUser(userRequest);
    }

    @PostMapping("/save")
    public ResponseEntity<?> saveUser(@RequestBody UsuarioRequest userRequest) {
        return userService.saveUser(userRequest);
    }

    @GetMapping("/get")
    public ResponseEntity<?> getUser(@RequestParam Integer id) {
        return userService.getUserById(id);
    }

    @PutMapping("/update")
    public ResponseEntity <?> updateUser (@RequestBody Usuario user){
        return userService.updateUser(user);
    }

    @GetMapping("/get/assigned/processes")
    public ResponseEntity<?> getAssignedProcesses(@RequestParam Integer id) {
        return userService.getAssingedProcesses(id);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteUser(@RequestParam Integer id) {
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
    public ResponseEntity<?> getAbogadosByFirmaFilter(@RequestParam(required = false) List<String> especialidades,
                                                      @RequestParam Integer firmaId, @RequestParam Integer roleId,
                                                      @RequestParam(defaultValue = "0") Integer page,
                                                      @RequestParam(defaultValue = "10") Integer size){
        return userService.findAllAbogadosByFirmaFilter(especialidades, firmaId, roleId, page, size);
    }

    @GetMapping("/jefe/abogados")
    public ResponseEntity<?> getAbogadosByFirma(@RequestParam Integer firmaId){
        return userService.findAllAbogadosByFirma(firmaId);
    }

    @GetMapping("/rol/get/user")
    public ResponseEntity<?> getRoleByUser(@RequestParam String username){
        return userService.findRoleByUser(username);
    }

    @GetMapping("/rol/get")
    public ResponseEntity<?> getRole(@RequestParam String roleName){
        return userService.findRolByName(roleName);
    }

    @GetMapping("/tipoAbogado/get/all")
    public ResponseEntity<?> getAllTipoAbogado(){
        return userService.findAllTipoAbogado();
    }

    @GetMapping("/tipoAbogado/get")
    public ResponseEntity<?> getTipoAbogado(@RequestParam String name){
        return userService.findTipoAbogadoByName(name);
    }

    @GetMapping("/tipoDocumento/get/all")
    public ResponseEntity<?> getAllTipoDocumento(){
        return userService.findAllTipoDocumento();
    }

    @GetMapping("/tipoDocumento/get")
    public ResponseEntity<?> getTipoDocumento(@RequestParam String name){
        return userService.findTipoDocumentoByName(name);
    }
}

package com.firma.data.controller;

import com.firma.data.model.Rol;
import com.firma.data.service.intf.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/data/rol")
public class RolController {

    @Autowired
    private IRoleService roleService;

    @GetMapping("/all")
    public ResponseEntity<?> getAllRoles() {
        return new ResponseEntity<>(roleService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/get")
    public ResponseEntity<?> getRole(@RequestParam String roleName){
        Rol rol = roleService.findByName(roleName);
        if (rol == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(rol, HttpStatus.OK);
    }

    @GetMapping("/get/user")
    public ResponseEntity<?> getRoleByUser(@RequestParam String username){
        Rol rol = roleService.findByUser(username);
        if (rol == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(rol, HttpStatus.OK);
    }

}

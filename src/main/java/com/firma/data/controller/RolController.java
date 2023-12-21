package com.firma.data.controller;

import com.firma.data.service.intf.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/data/rol")
public class RolController {
    @Qualifier("rolService")
    @Autowired
    private IRoleService roleService;

    @GetMapping("/all")
    public ResponseEntity<?> getAllRoles() {
        return new ResponseEntity<>(roleService.findAll(), HttpStatus.OK);
    }


}

package com.firma.data.controller;

import com.firma.data.model.EstadoProceso;
import com.firma.data.model.Firma;
import com.firma.data.model.Rol;
import com.firma.data.model.Usuario;
import com.firma.data.payload.request.FirmaRequest;
import com.firma.data.payload.response.FirmaUsuariosResponse;
import com.firma.data.payload.response.UsuarioResponse;
import com.firma.data.service.intf.IEstadoProcesoService;
import com.firma.data.service.intf.IFirmaService;
import com.firma.data.service.intf.IRoleService;
import com.firma.data.service.intf.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/data/firma")
public class FirmaController {

    @Autowired
    private IFirmaService firmaService;
    @Autowired
    private IEstadoProcesoService estadoProcesoService;

    @GetMapping("/all")
    public ResponseEntity<?> getAllFirmas() {
        return new ResponseEntity<>(firmaService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/get/user")
    public ResponseEntity<?> getFirmaByUser(@RequestParam String userName) {
        Firma firma = firmaService.findByUser(userName);
        if (firma == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(firma, HttpStatus.OK);
    }

    @GetMapping("/get/id")
    public ResponseEntity<?> getFirmaId(@RequestParam Integer firmaId) {
        Firma firma = firmaService.findById(firmaId);
        if (firma == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(firma, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<?> addFirma(@RequestBody FirmaRequest firma) {
        Firma fm = Firma.builder()
                .nombre(firma.getNombre())
                .direccion(firma.getDireccion())
                .build();
        Firma firmaNew = firmaService.saveFirma(fm);
        if (firmaNew == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(firmaNew, HttpStatus.OK);
    }

}

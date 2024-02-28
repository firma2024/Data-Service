package com.firma.data.controller;

import com.firma.data.payload.request.FirmaRequest;
import com.firma.data.intfService.IFirmaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.*;

@RestController
@EnableTransactionManagement
@RequestMapping("/api/data/firma")
public class FirmaController {

    @Autowired
    private IFirmaService firmaService;

    @GetMapping("/get/user")
    public ResponseEntity<?> getFirmaByUser(@RequestParam String userName) {
        return firmaService.findFirmaByUser(userName);
    }

    @PostMapping("/save")
    public ResponseEntity<?> saveFirma(@RequestBody FirmaRequest firma) {
        return firmaService.saveFirma(firma);
    }

    @GetMapping("/get")
    public ResponseEntity<?> getFirma(@RequestParam Integer id) {
        return firmaService.findFirmaById(id);
    }

    @GetMapping("/empleado/get")
    public ResponseEntity<?> getEmpleado(@RequestParam Integer idAbogado) {
        return firmaService.findEmpleadoByUsuario(idAbogado);
    }
}

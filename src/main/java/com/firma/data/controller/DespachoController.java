package com.firma.data.controller;

import com.firma.data.model.Despacho;
import com.firma.data.service.intf.IDespachoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/api/data/despacho")
public class DespachoController {

    @Autowired
    private IDespachoService despachoService;

    @GetMapping("/get/all/notlink")
    public ResponseEntity<?> getAllDespachosWithOutLink(@RequestParam String year) {
        return new ResponseEntity<>(despachoService.findAllDespachosWithOutLinkByYear(year), HttpStatus.OK);
    }

    @GetMapping("/get")
    public ResponseEntity<?> getDespachoByProceso(@RequestParam Integer procesoId) {
        Despacho despacho = despachoService.findDespachoByProceso(procesoId);
        if (despacho == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(despacho, HttpStatus.OK);
    }
}

package com.firma.data.controller;

import com.firma.data.model.Despacho;
import com.firma.data.service.intf.IDespachoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/api/data/despacho")
public class DespachoController {

    @Autowired
    private IDespachoService despachoService;

    @PutMapping("/update")
    public ResponseEntity<?> updateDespacho(@RequestParam Integer despachoId, @RequestBody Despacho despachoUpdate) {
        Despacho despacho = despachoService.findDespachoById(despachoId);
        if (despacho == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        despacho.setFechaconsulta(despachoUpdate.getFechaconsulta());
        despacho.setUrl(despachoUpdate.getUrl());
        despachoService.updateDespacho(despacho);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

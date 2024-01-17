package com.firma.data.controller;

import com.firma.data.model.Despacho;
import com.firma.data.model.Enlace;
import com.firma.data.payload.request.EnlaceRequest;
import com.firma.data.service.intf.IDespachoService;
import com.firma.data.service.intf.IEnlaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/data/enlace")
public class EnlaceController {

    @Autowired
    private IEnlaceService enlaceService;
    @Autowired
    private IDespachoService despachoService;

    @PostMapping("/save")
    public ResponseEntity<?> saveEnlace(@RequestBody EnlaceRequest enlaceRequest) {
        Despacho despacho = despachoService.findDespachoById(enlaceRequest.getDespachoid());
        if (despacho == null) {
            return ResponseEntity.badRequest().body("Despacho not found");
        }
        Enlace en = Enlace.builder()
                .url(enlaceRequest.getUrl())
                .fechaconsulta(enlaceRequest.getFechaconsulta())
                .despacho(despacho)
                .build();

        enlaceService.saveEnlace(en);
        return new ResponseEntity<>("Enlace was saved", HttpStatus.OK);
    }
}

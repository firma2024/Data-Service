package com.firma.data.controller;

import com.firma.data.model.EstadoProceso;
import com.firma.data.model.Firma;
import com.firma.data.model.Rol;
import com.firma.data.model.Usuario;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
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

    @GetMapping("/get/name")
    public ResponseEntity<?> getFirmaDirection(@RequestParam String firmaName) {
        List<Firma> firma = firmaService.findByName(firmaName);
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

}

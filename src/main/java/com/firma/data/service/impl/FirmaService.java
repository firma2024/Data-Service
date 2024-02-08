package com.firma.data.service.impl;

import com.firma.data.model.Empleado;
import com.firma.data.model.Firma;
import com.firma.data.payload.request.FirmaRequest;
import com.firma.data.repository.EmpleadoRepository;
import com.firma.data.repository.FirmaRepository;
import com.firma.data.service.intf.IFirmaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class FirmaService implements IFirmaService {

    @Autowired
    private FirmaRepository firmaRepository;
    @Autowired
    private EmpleadoRepository empleadoRepository;

    @Override
    public ResponseEntity<?> saveFirma(FirmaRequest firma) {
        Firma fm = Firma.builder()
                .nombre(firma.getNombre())
                .direccion(firma.getDireccion())
                .build();
        firmaRepository.save(fm);
        return new ResponseEntity<>("Firma Creada", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> findFirmaByUser(String userName) {
        Firma firma = firmaRepository.findByUser(userName);
        if (firma == null) {
            return new ResponseEntity<>("Firma no encontrada", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(firma, HttpStatus.OK);
    }

    @Override
    public Firma findFirmaById(Integer id) {
        return firmaRepository.findById(id).orElse(null);
    }

    @Override
    public Empleado saveEmpleado(Empleado empleado) {
        return empleadoRepository.save(empleado);
    }

    @Override
    public Empleado findEmpleadoByUsuario(Integer idAbogado) {
        return empleadoRepository.findEmpleadoByUsuario(idAbogado);
    }

}

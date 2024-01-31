package com.firma.data.service.impl;

import com.firma.data.model.Actuacion;
import com.firma.data.model.Usuario;
import com.firma.data.payload.response.ActuacionDocumentResponse;
import com.firma.data.service.intf.IActuacionService;
import com.firma.data.service.intf.IStorageService;
import com.firma.data.service.intf.IUsuarioService;
import com.firma.data.utils.ImageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class StorageService implements IStorageService {

    @Autowired
    private IUsuarioService usuarioService;
    @Autowired
    private IActuacionService actuacionService;
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Override
    public String uploadImage(MultipartFile file, Integer userId) throws IOException {
        Usuario user = usuarioService.findById(userId);
        if (user == null) {
            return "Usuario no encontrado";
        }
        user.setImg(ImageUtils.compressImage(file.getBytes()));
        usuarioService.update(user);
        return null;
    }

    @Override
    public byte[] downloadImage(Integer userId) {
        Usuario user = usuarioService.findById(userId);
        return ImageUtils.decompressFile(user.getImg());
    }

    @Override
    public String uploadDocument(MultipartFile file, Integer actuacionId) throws IOException {
        Actuacion actuacion = actuacionService.findById(actuacionId);
        if (actuacion == null) {
            return "Actuacion no encontrada";
        }
        byte[] document = ImageUtils.compressImage(file.getBytes());
        actuacion.setDocumento(document);
        actuacionService.update(actuacion);
        return null;
    }

    @Override
    public byte[] downloadDocument(Integer actuacionId) {
        Actuacion actuacion = actuacionService.findById(actuacionId);
        return ImageUtils.decompressFile(actuacion.getDocumento());
    }

    @Override
    public List<ActuacionDocumentResponse> downloadAllDocuments(Integer procesoId){
        Set<Actuacion> actuaciones = actuacionService.findAllByProcesoAndDocument(procesoId);
        List<ActuacionDocumentResponse> documents = new ArrayList<>();

        for (Actuacion actuacion : actuaciones) {
            ActuacionDocumentResponse acDoc = ActuacionDocumentResponse.builder()
                    .document(ImageUtils.decompressFile(actuacion.getDocumento()))
                    .fechaActuacion(actuacion.getFechaactuacion().format(formatter))
                    .radicado(actuacion.getProceso().getRadicado())
                    .build();
            documents.add(acDoc);
        }

        return documents;
    }
}

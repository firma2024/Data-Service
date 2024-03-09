package com.firma.data.implService;

import com.firma.data.intfService.IActuacionService;
import com.firma.data.intfService.IStorageService;
import com.firma.data.intfService.IUserService;
import com.firma.data.model.Actuacion;
import com.firma.data.model.Usuario;
import com.firma.data.payload.response.ActuacionDocumentResponse;
import com.firma.data.utils.ImageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class StorageService implements IStorageService {

    @Autowired
    private IUserService userService;
    @Autowired
    private IActuacionService actuacionService;
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Override
    public ResponseEntity<?> uploadPhotoUser(MultipartFile file, Integer userId) throws IOException {
        Usuario user = userService.findById(userId);
        if (user == null) {
            return new ResponseEntity<>("Usuario no encontrado", HttpStatus.NOT_FOUND);
        }
        user.setImg(ImageUtils.compressImage(file.getBytes()));
        userService.updateUser(user);
        return new ResponseEntity<>("Foto alamacenada", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> downloadPhotoUser(Integer userId) {
        Usuario user = userService.findById(userId);
        if (user == null){
            return new ResponseEntity<>("Usuario no encontrado", HttpStatus.NOT_FOUND);
        }
        if (user.getImg() == null){
            return new ResponseEntity<>("Foto no encontrada", HttpStatus.NOT_FOUND);
        }
        byte[] image = ImageUtils.decompressFile(user.getImg());
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(image);
    }

    @Override
    public ResponseEntity<?> uploadDocument(MultipartFile file, Integer actuacionId) throws IOException {
        Actuacion actuacion = actuacionService.findById(actuacionId);
        if (actuacion == null) {
            return new ResponseEntity<>("Actuacion no encontrada", HttpStatus.NOT_FOUND);
        }
        byte[] document = ImageUtils.compressImage(file.getBytes());
        actuacion.setDocumento(document);
        actuacionService.update(actuacion);
        return new ResponseEntity<>("Documento almacenado", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> downloadDocument(Integer actuacionId) {
        Actuacion actuacion = actuacionService.findById(actuacionId);
        byte[] document = ImageUtils.decompressFile(actuacion.getDocumento());
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .body(document);
    }

    @Override
    public ResponseEntity<?> downloadAllDocuments(Integer procesoId) {
        Set<Actuacion> actuaciones = actuacionService.findAllByProcesoAndDocument(procesoId);
        List<ActuacionDocumentResponse> documents = new ArrayList<>();

        for (Actuacion actuacion : actuaciones) {
            if (actuacion.getDocumento() != null){
                ActuacionDocumentResponse acDoc = ActuacionDocumentResponse.builder()
                        .document(ImageUtils.decompressFile(actuacion.getDocumento()))
                        .fechaActuacion(actuacion.getFechaactuacion().format(formatter))
                        .radicado(actuacion.getProceso().getRadicado())
                        .build();
                documents.add(acDoc);
            }
        }
        return new ResponseEntity<>(documents, HttpStatus.OK);
    }
}

package com.firma.data.service.impl;

import com.firma.data.model.Actuacion;
import com.firma.data.model.Usuario;
import com.firma.data.payload.response.FileResponse;
import com.firma.data.service.intf.IActuacionService;
import com.firma.data.service.intf.IStorageService;
import com.firma.data.service.intf.IUsuarioService;
import com.firma.data.utils.ImageUtils;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.*;
import java.time.format.DateTimeFormatter;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

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
    public FileResponse downloadAllDocuments(Integer procesoId) throws IOException {
        Set<Actuacion> actuaciones = actuacionService.findAllByProcesoAndDocument(procesoId);
        String radicado = null;

        File tempFolder = new File("temp");
        if (!tempFolder.exists()) {
            tempFolder.mkdirs();
        }
        File zipFolder = new File("zip");
        if (!zipFolder.exists()) {
            zipFolder.mkdirs();
        }

        // Crear archivos PDF y guardarlos en la carpeta temporal
        for (Actuacion actuacion : actuaciones) {
            byte[] documentData = ImageUtils.decompressFile(actuacion.getDocumento());
            File pdfFile = new File(tempFolder, "providencia_" + actuacion.getFechaactuacion().format(formatter) + ".pdf");
            FileOutputStream fos = new FileOutputStream(pdfFile);
            radicado = actuacion.getProceso().getRadicado();
            fos.write(documentData);
            fos.close();
        }

        // Comprimir los archivos PDF en un archivo ZIP
        File zipFile = new File("zip/providencias_" + radicado + ".zip");
        ZipOutputStream zipOut = new ZipOutputStream(new FileOutputStream(zipFile));

        for (File pdfFile : tempFolder.listFiles()) {
            ZipEntry zipEntry = new ZipEntry(pdfFile.getName());
            zipOut.putNextEntry(zipEntry);

            byte[] bytes = FileUtils.readFileToByteArray(pdfFile);
            zipOut.write(bytes);

            pdfFile.delete();
        }

        zipOut.close();
        FileUtils.deleteDirectory(tempFolder);

        return FileResponse.builder()
                .file(FileUtils.readFileToByteArray(zipFile))
                .fileName("providencias_" + radicado + ".zip")
                .build();
    }
}

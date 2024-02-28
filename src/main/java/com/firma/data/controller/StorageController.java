package com.firma.data.controller;

import com.firma.data.intfService.IStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@EnableTransactionManagement
@RequestMapping("/api/data/storage")
public class StorageController {
    @Autowired
    private IStorageService storageService;

    @PostMapping("/upload/photo")
    public ResponseEntity<?> uploadPhoto(@RequestParam("image") MultipartFile file, Integer userId){
        try {
            return storageService.uploadPhotoUser(file, userId);
        } catch (IOException e) {
            return ResponseEntity.badRequest().body("Error al subir la foto");
        }
    }

    @GetMapping("/download/photo")
    public ResponseEntity<?> downloadPhoto(@RequestParam Integer usuarioId){
        return storageService.downloadPhotoUser(usuarioId);
    }

    @PostMapping("/upload/document")
    public ResponseEntity<?> uploadDocument(@RequestParam("doc") MultipartFile file, @RequestParam Integer actuacionId){
        try {
            return storageService.uploadDocument(file, actuacionId);
        } catch (IOException e) {
            return ResponseEntity.badRequest().body("Error al subir el documento");
        }
    }

    @GetMapping("/download/document")
    public ResponseEntity<?> downloadDocument(@RequestParam Integer actuacionId){
        return storageService.downloadDocument(actuacionId);
    }

    @GetMapping("/download/all/documents")
    public ResponseEntity<?> downloadAllDocuments(@RequestParam Integer procesoId){
        return storageService.downloadAllDocuments(procesoId);
    }

}

package com.firma.data.intfService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface IStorageService {
    ResponseEntity<?> uploadPhotoUser(MultipartFile file, Integer userId) throws IOException;
    ResponseEntity<?> downloadPhotoUser(Integer userId);
    ResponseEntity<?> uploadDocument(MultipartFile file, Integer actuacionId) throws IOException;
    ResponseEntity<?> downloadDocument(Integer actuacionId);
    ResponseEntity<?> downloadAllDocuments(Integer procesoId);

}

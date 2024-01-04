package com.firma.data.service.intf;

import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface IStorageService {
    String uploadImage(MultipartFile file, Integer userId) throws IOException;
    public byte[] downloadImage(Integer userId);
    String uploadDocument(MultipartFile file, Integer actuacionId) throws IOException;
    public byte[] downloadDocument(Integer actuacionId);
    public byte[] downloadAllDocuments(Integer procesoId) throws IOException;
}

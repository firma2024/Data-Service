package com.firma.data.service.intf;

import com.firma.data.payload.response.ActuacionDocumentResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface IStorageService {
    String uploadImage(MultipartFile file, Integer userId) throws IOException;
    public byte[] downloadImage(Integer userId);
    String uploadDocument(MultipartFile file, Integer actuacionId) throws IOException;
    public byte[] downloadDocument(Integer actuacionId);
    List<ActuacionDocumentResponse> downloadAllDocuments(Integer procesoId);
}

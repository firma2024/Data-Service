package com.firma.data.service.impl;

import com.firma.data.model.Usuario;
import com.firma.data.service.intf.IStorageService;
import com.firma.data.service.intf.IUsuarioService;
import com.firma.data.utils.ImageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class StorageService implements IStorageService {

    @Autowired
    private IUsuarioService usuarioService;

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
        return ImageUtils.decompressImage(user.getImg());
    }
}

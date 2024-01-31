package com.firma.data.service.intf;

import com.firma.data.model.Firma;
import com.firma.data.model.Usuario;

import java.util.List;

public interface IFirmaService {
    Firma saveFirma(Firma firma);
    List<Firma> findAll();
    Firma findById(Integer id);
    Firma findByUser(String name);
}

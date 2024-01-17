package com.firma.data.service.intf;

import com.firma.data.model.Enlace;

public interface IEnlaceService {
    Enlace saveEnlace(Enlace enlace);
    Enlace findByDespachoAndYear(Integer despachoId, String year);
}

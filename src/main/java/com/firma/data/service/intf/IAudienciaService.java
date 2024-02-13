package com.firma.data.service.intf;

import com.firma.data.model.Audiencia;

import java.util.Set;

public interface IAudienciaService {
    Audiencia saveAudiencia(Audiencia audiencia);
    Audiencia updateAudiencia(Audiencia audiencia);
    Set <Audiencia> findAllByProceso(Integer procesoId);

    Audiencia findByid(Integer id);
}

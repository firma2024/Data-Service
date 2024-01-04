package com.firma.data.service.impl;

import com.firma.data.model.Audiencia;
import com.firma.data.repository.AudienciaRepository;
import com.firma.data.service.intf.IAudienciaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class AudienciaService implements IAudienciaService{

    @Autowired
    private AudienciaRepository audienciaRepository;

    @Override
    public Audiencia saveAudiencia(Audiencia audiencia) {
        return audienciaRepository.save(audiencia);
    }

    @Override
    public Audiencia updateAudiencia(Audiencia audiencia) {
        return audienciaRepository.save(audiencia);
    }

    @Override
    public Set<Audiencia> findAllByProceso(Integer procesoId) {
        return audienciaRepository.findAllByProceso(procesoId);
    }

    @Override
    public Audiencia findByid(Integer id) {
        return audienciaRepository.findById(id).orElse(null);
    }
}

package com.gft.management.services;


import com.gft.management.models.Tecnologia;
import com.gft.management.repositories.TecnologiaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TecnologiaService {
    @Autowired
    private TecnologiaRepository tecnologiaRepository;

    public void saveTecnologia(Tecnologia tecnologia){
        tecnologiaRepository.save(tecnologia);
    }

    public List<Tecnologia> listAllTecnologias(){
        return tecnologiaRepository.findAll();
    }

    public Tecnologia findTecnologia(Long id) throws Exception {
        Optional<Tecnologia> tecnologia = tecnologiaRepository.findById(id);

        if(tecnologia.isEmpty()){
            throw new Exception("Tecnologia não encontrado");
        }
        return tecnologia.get();
    }

    public void deleteTecnologia(Long id) {
        tecnologiaRepository.deleteById(id);
    }

}

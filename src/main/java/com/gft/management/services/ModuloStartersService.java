package com.gft.management.services;

import com.gft.management.models.Modulo;
import com.gft.management.repositories.ModuloRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ModuloStartersService {

    @Autowired
    private ModuloRepository moduloRepository;

    public Modulo saveModulo(Modulo modulo){
        Modulo moduloSalvo = moduloRepository.save(modulo);
        return modulo;
    }

    public Modulo getModulo(Long id){
        Modulo modulo = moduloRepository.findById(id).get();
        return modulo;
    }

    public List<Modulo> listAllModulos(){
        return moduloRepository.findAll();
    }

    public Modulo findModulo(Long id) throws Exception {
        Optional<Modulo> modulo = moduloRepository.findById(id);

        if(modulo.isEmpty()){
            throw new Exception("Módulo não encontrado");
        }
        return modulo.get();
    }

    public void deleteModulo(Long id) {
        moduloRepository.deleteById(id);
    }

}

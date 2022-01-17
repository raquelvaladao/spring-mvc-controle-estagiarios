package com.gft.management.services;

import com.gft.management.models.Projeto;
import com.gft.management.models.Starter;
import com.gft.management.repositories.StarterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StarterService {

    @Autowired
    private StarterRepository starterRepository;

    public List<Starter> getAllStarters(){
        List<Starter> starter = starterRepository.findAll();
        return starter;
    }

    public Starter getStarter(Long id) throws Exception {
        Optional<Starter> starter = starterRepository.findById(id);

        if(starter.isEmpty()){
            throw new Exception("Starter não encontrado");
        }
        return starter.get();
    }

    public void saveStarter(Starter starter){
        starterRepository.save(starter);
    }

    public void deleteStarter(Long id){
        starterRepository.deleteById(id);
    }

    public Page<Starter> pegaPaginado(int pageNum, int pageSize){
        Pageable pageable = PageRequest.of(pageNum - 1, pageSize);
        return this.starterRepository.findAll(pageable);
    }

    public void updateStarter(Starter starter){
        Starter starterUpdated = starterRepository.getById(starter.getId());

       starterUpdated.setNome(starter.getNome());
       starterUpdated.setLetras(starter.getLetras());
       starterUpdated.setGrupoStarters(starter.getGrupoStarters());
       starterRepository.save(starterUpdated);
    }

    public void updateFotoStarter(Starter starter, String fileName){
        Starter starterUpdated = starterRepository.getById(starter.getId());

        starterUpdated.setFoto(fileName);
        starterRepository.save(starterUpdated);
    }


}

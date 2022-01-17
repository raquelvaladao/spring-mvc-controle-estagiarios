package com.gft.management.services;

import com.gft.management.models.ProgramaStarter;
import com.gft.management.repositories.ProgramaStarterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProgramaStarterService {

    @Autowired
    private ProgramaStarterRepository programaStarterRepository;

    public void saveProgramaStarter(ProgramaStarter programaStarter){
        programaStarterRepository.save(programaStarter);
    }

    public List<ProgramaStarter> listAllProgramaStarters(){
        return programaStarterRepository.findAll();
    }

    public ProgramaStarter findProgramaStarter(Long id) throws Exception {
        Optional<ProgramaStarter> programaStarter = programaStarterRepository.findById(id);

        if(programaStarter.isEmpty()){
            throw new Exception("Programa não encontrado");
        }
        return programaStarter.get();
    }

    public void deleteProgramaStarter(Long id) {
        programaStarterRepository.deleteById(id);
    }

}

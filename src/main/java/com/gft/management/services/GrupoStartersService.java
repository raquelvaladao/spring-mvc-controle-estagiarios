package com.gft.management.services;


import com.gft.management.models.Grupo;
import com.gft.management.models.Starter;
import com.gft.management.repositories.GrupoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GrupoStartersService {

    @Autowired
    private GrupoRepository grupoRepository;

    public List<Grupo> getAllGrupos(){
        List<Grupo> grupo = grupoRepository.findAll();
        return grupo;
    }

    public Grupo getGrupo(Long id) throws Exception {
        Optional<Grupo> grupo = grupoRepository.findById(id);

        if(grupo.isEmpty()){
            throw new Exception("Grupo não encontrado");
        }
        return grupo.get();
    }

    public Grupo saveGrupo(Grupo grupo){
        grupoRepository.save(grupo);
        return grupo;
    }

    public void deleteGrupo(Long id){
        grupoRepository.deleteById(id);
    }

    public Page<Grupo> pegaPaginado(int pageNum, int pageSize){
        Pageable pageable = PageRequest.of(pageNum - 1, pageSize);
        return this.grupoRepository.findAll(pageable);
    }

    public List<Grupo> pegarGruposComEspacoDisponivel(){
        return grupoRepository.pegarGruposComEspacoDisponivel();
    }


}

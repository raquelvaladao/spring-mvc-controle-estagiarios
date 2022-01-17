package com.gft.management.services;

import com.gft.management.models.Projeto;
import com.gft.management.models.Starter;
import com.gft.management.repositories.ProjetoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProjetoService {

    @Autowired
    private ProjetoRepository projetoRepository;

    public List<Projeto> listAllProjetos(){
        List<Projeto> projeto = projetoRepository.findAll();
        return projeto;
    }

    public Projeto getProjeto(Long id) throws Exception {
        Optional<Projeto> projeto = projetoRepository.findById(id);

        if(projeto.isEmpty()){
            throw new Exception("Projeto não encontrado");
        }
        return projeto.get();
    }

    public Projeto saveProjeto(Projeto projeto){
        projetoRepository.save(projeto);
        return projeto;
    }

    public void deleteProjeto(Long id){
        projetoRepository.deleteById(id);
    }

    public Page<Projeto> pegaNotasPaginado(int pageNum, int pageSize){
        Pageable pageable = PageRequest.of(pageNum - 1, pageSize,
                Sort.by("nota").descending()
                        .and(Sort.by("modulo")).descending()
                        .and(Sort.by("etapa")).descending());

        return this.projetoRepository.findAll(pageable);
    }

    public List<Projeto> getNotasOrdenadas(){
        List<Projeto> projetos = projetoRepository.findAll(Sort.by("nota").descending()
                .and(Sort.by("modulo")).descending()
                .and(Sort.by("etapa")).descending());
        return  projetos;
    }

    public Page<Projeto> pegaPaginado(int pageNum, int pageSize){
        Pageable pageable = PageRequest.of(pageNum - 1, pageSize);
        return this.projetoRepository.findAll(pageable);
    }

}

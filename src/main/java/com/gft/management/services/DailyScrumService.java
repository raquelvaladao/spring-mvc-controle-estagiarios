package com.gft.management.services;


import com.gft.management.models.Daily;
import com.gft.management.models.Grupo;
import com.gft.management.models.Starter;
import com.gft.management.models.Usuario;
import com.gft.management.repositories.DailyRepository;
import com.gft.management.repositories.GrupoRepository;
import com.gft.management.repositories.StarterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DailyScrumService {

    @Autowired
    private GrupoRepository grupoRepository;

    @Autowired
    private StarterRepository starterRepository;

    public List<Starter> listarStartersPorRoleParaAcessoAsDailys(Usuario usuarioLogado){
        Grupo grupoDoScrum = new Grupo();
        List<Starter> startersASeremMostradosAoScrum;

        if(usuarioLogado.getRole().getNome().equals("ROLE_ADMIN")){
            return starterRepository.findAll();
        }

        List<Grupo> grupos = grupoRepository.findAll();
        for(Grupo grupo : grupos){
            if(grupo.getScrumMaster().equals(usuarioLogado)){
                grupoDoScrum = grupo;
            }
        }
        List<Starter> startersDoGrupoDoScrum = grupoDoScrum.getStarters();
        if(startersDoGrupoDoScrum == null){
            startersASeremMostradosAoScrum = new ArrayList<>();
        } else {
            startersASeremMostradosAoScrum = new ArrayList<>(grupoDoScrum.getStarters());
        }

        return startersASeremMostradosAoScrum;
    }


}

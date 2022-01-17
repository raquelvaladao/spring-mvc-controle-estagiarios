package com.gft.management.repositories;

import com.gft.management.models.Grupo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GrupoRepository extends JpaRepository<Grupo, Long> {

    @Query("select g from Grupo g where size(g.starters) < 7")
    public List<Grupo> pegarGruposComEspacoDisponivel();
}

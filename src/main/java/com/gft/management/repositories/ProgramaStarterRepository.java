package com.gft.management.repositories;

import com.gft.management.models.ProgramaStarter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProgramaStarterRepository extends JpaRepository<ProgramaStarter, Long> {
}

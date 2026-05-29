package com.trebol.repository;

import com.trebol.entity.EscaneoPlanta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EscaneoPlantaRepository extends JpaRepository<EscaneoPlanta, Long> {
}

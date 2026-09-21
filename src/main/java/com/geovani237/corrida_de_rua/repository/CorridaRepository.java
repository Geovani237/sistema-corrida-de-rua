package com.geovani237.corrida_de_rua.repository;

import com.geovani237.corrida_de_rua.entity.CorredorEntity;
import com.geovani237.corrida_de_rua.entity.CorridaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CorridaRepository extends JpaRepository<CorridaEntity, Long> {
}

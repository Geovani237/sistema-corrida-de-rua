package com.geovani237.corrida_de_rua.repository;

import com.geovani237.corrida_de_rua.model.Corredor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CorredorRepository extends JpaRepository<Corredor, Integer> {
}

package com.moedaestudantil.repository;

import com.moedaestudantil.model.Institution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InstitutionRepository extends JpaRepository<Institution, Long> {
    
    Optional<Institution> findByCnpj(String cnpj);
    
    Optional<Institution> findByName(String name);
}

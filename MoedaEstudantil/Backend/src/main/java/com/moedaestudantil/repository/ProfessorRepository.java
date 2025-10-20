package com.moedaestudantil.repository;

import com.moedaestudantil.model.Professor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProfessorRepository extends JpaRepository<Professor, Long> {
    
    Optional<Professor> findByCpf(String cpf);
    
    List<Professor> findByInstitutionId(Long institutionId);
    
    @Query("SELECT p FROM Professor p WHERE p.institution.id = :institutionId ORDER BY p.name ASC")
    List<Professor> findProfessorsByInstitution(@Param("institutionId") Long institutionId);
}

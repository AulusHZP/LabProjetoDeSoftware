package com.moedaestudantil.repository;

import com.moedaestudantil.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    
    Optional<Student> findByEmail(String email);
    
    Optional<Student> findByCpf(String cpf);
    
    List<Student> findByInstitutionId(Long institutionId);
    
    @Query("SELECT s FROM Student s WHERE s.institution.id = :institutionId ORDER BY s.name ASC")
    List<Student> findStudentsByInstitution(@Param("institutionId") Long institutionId);
    
    @Query("SELECT s FROM Student s WHERE s.coinBalance > 0 ORDER BY s.coinBalance DESC")
    List<Student> findStudentsWithCoins();
}

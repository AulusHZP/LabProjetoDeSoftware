package com.moedaestudantil.repository;

import com.moedaestudantil.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {
    Optional<Company> findByEmail(String email);
    Optional<Company> findByCnpj(String cnpj);
    boolean existsByEmail(String email);
    boolean existsByCnpj(String cnpj);
}

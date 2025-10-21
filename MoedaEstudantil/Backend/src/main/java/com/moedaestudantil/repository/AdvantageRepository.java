package com.moedaestudantil.repository;

import com.moedaestudantil.model.Advantage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdvantageRepository extends JpaRepository<Advantage, Long> {
    List<Advantage> findByCompanyId(Long companyId);
    List<Advantage> findByIsActiveTrue();
    
    @Query("SELECT a FROM Advantage a WHERE a.isActive = true AND a.currentRedemptions < a.maxRedemptions")
    List<Advantage> findAvailableAdvantages();
    
    @Query("SELECT a FROM Advantage a WHERE a.isActive = true AND a.coinCost <= :maxCost AND a.currentRedemptions < a.maxRedemptions")
    List<Advantage> findAffordableAdvantages(@Param("maxCost") Integer maxCost);
}

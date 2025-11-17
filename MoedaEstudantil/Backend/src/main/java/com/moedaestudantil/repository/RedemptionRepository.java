package com.moedaestudantil.repository;

import com.moedaestudantil.model.Redemption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RedemptionRepository extends JpaRepository<Redemption, Long> {
    Optional<Redemption> findByCouponCode(String couponCode);
    
    @Query("SELECT r FROM Redemption r WHERE r.advantage.company.id = :companyId ORDER BY r.createdAt DESC")
    List<Redemption> findRedemptionsByCompany(@Param("companyId") Long companyId);
    
    List<Redemption> findByStudent_IdOrderByCreatedAtDesc(Long studentId);
}
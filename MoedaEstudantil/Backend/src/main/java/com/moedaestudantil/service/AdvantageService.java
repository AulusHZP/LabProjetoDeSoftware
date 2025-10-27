package com.moedaestudantil.service;

import java.security.SecureRandom;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.moedaestudantil.dto.AdvantageRequest;
import com.moedaestudantil.dto.RedemptionRequest;
import com.moedaestudantil.model.Advantage;
import com.moedaestudantil.model.Company;
import com.moedaestudantil.model.Redemption;
import com.moedaestudantil.repository.AdvantageRepository;
import com.moedaestudantil.repository.CompanyRepository;
import com.moedaestudantil.repository.RedemptionRepository;

@Service
@Transactional
public class AdvantageService {
    
    @Autowired
    private AdvantageRepository advantageRepository;
    
    @Autowired
    private CompanyRepository companyRepository;
    
    @Autowired
    private RedemptionRepository redemptionRepository;
    
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final SecureRandom random = new SecureRandom();
    
    public Advantage createAdvantage(Long companyId, AdvantageRequest request) {
        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new RuntimeException("Empresa não encontrada"));
        
        Advantage advantage = new Advantage();
        advantage.setCompany(company);
        advantage.setTitle(request.getTitle());
        advantage.setDescription(request.getDescription());
        advantage.setPhotoUrl(request.getPhotoUrl());
        advantage.setCoinCost(request.getCoinCost());
        advantage.setIsActive(request.getIsActive());
        advantage.setMaxRedemptions(request.getMaxRedemptions());
        
        return advantageRepository.save(advantage);
    }
    
    public List<Advantage> getAdvantagesByCompany(Long companyId) {
        return advantageRepository.findByCompanyId(companyId);
    }
    
    public List<Advantage> getAvailableAdvantages() {
        return advantageRepository.findAvailableAdvantages();
    }
    
    public List<Advantage> getAffordableAdvantages(Integer maxCost) {
        return advantageRepository.findAffordableAdvantages(maxCost);
    }
    
    public Optional<Advantage> getAdvantageById(Long id) {
        return advantageRepository.findById(id);
    }
    
    public Advantage updateAdvantage(Long id, AdvantageRequest request) {
        Advantage advantage = advantageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vantagem não encontrada"));
        
        if (request.getTitle() != null) {
            advantage.setTitle(request.getTitle());
        }
        if (request.getDescription() != null) {
            advantage.setDescription(request.getDescription());
        }
        if (request.getPhotoUrl() != null) {
            advantage.setPhotoUrl(request.getPhotoUrl());
        }
        if (request.getCoinCost() != null) {
            advantage.setCoinCost(request.getCoinCost());
        }
        if (request.getIsActive() != null) {
            advantage.setIsActive(request.getIsActive());
        }
        if (request.getMaxRedemptions() != null) {
            advantage.setMaxRedemptions(request.getMaxRedemptions());
        }
        
        return advantageRepository.save(advantage);
    }
    
    public void deleteAdvantage(Long id) {
        advantageRepository.deleteById(id);
    }
    
    public Redemption redeemAdvantage(RedemptionRequest request) {
        Advantage advantage = advantageRepository.findById(request.getAdvantageId())
                .orElseThrow(() -> new RuntimeException("Vantagem não encontrada"));
        
        if (!advantage.getIsActive()) {
            throw new RuntimeException("Vantagem não está ativa");
        }
        
        if (advantage.getCurrentRedemptions() >= advantage.getMaxRedemptions()) {
            throw new RuntimeException("Vantagem esgotada");
        }
        
        // Generate unique coupon code
        String couponCode = generateCouponCode();
        
        // Create redemption
        Redemption redemption = new Redemption();
        redemption.setAdvantage(advantage);
        redemption.setCouponCode(couponCode);
        redemption.setStudentEmail(request.getStudentEmail());
        redemption.setStudentName(request.getStudentName());
        
        redemption = redemptionRepository.save(redemption);
        
        // Update advantage redemption count
        advantage.setCurrentRedemptions(advantage.getCurrentRedemptions() + 1);
        advantageRepository.save(advantage);
        
        return redemption;
    }
    
    public List<Redemption> getRedemptionsByCompany(Long companyId) {
        return redemptionRepository.findRedemptionsByCompany(companyId);
    }
    
    public Optional<Redemption> getRedemptionByCouponCode(String couponCode) {
        return redemptionRepository.findByCouponCode(couponCode);
    }
    
    private String generateCouponCode() {
        StringBuilder code = new StringBuilder(8);
        for (int i = 0; i < 8; i++) {
            code.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
        }
        return code.toString();
    }
}

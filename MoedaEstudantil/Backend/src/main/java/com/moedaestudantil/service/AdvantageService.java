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
import com.moedaestudantil.model.Student;
import com.moedaestudantil.repository.AdvantageRepository;
import com.moedaestudantil.repository.CompanyRepository;
import com.moedaestudantil.repository.RedemptionRepository;
import com.moedaestudantil.repository.StudentRepository;

@Service
@Transactional
public class AdvantageService {
    
    @Autowired
    private AdvantageRepository advantageRepository;
    
    @Autowired
    private CompanyRepository companyRepository;
    
    @Autowired
    private RedemptionRepository redemptionRepository;
    
    @Autowired
    private StudentRepository studentRepository;
    
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
    
    @Transactional
    public Redemption redeemAdvantage(RedemptionRequest request) {
        System.out.println("Iniciando resgate para vantagem: " + request.getAdvantageId());
        try {
            // Find advantage and validate it's available
            Advantage advantage = advantageRepository.findById(request.getAdvantageId())
                    .orElseThrow(() -> new RuntimeException("Vantagem não encontrada"));
            
            System.out.println("Vantagem encontrada: " + advantage.getTitle());
            
            if (!advantage.getIsActive()) {
                throw new RuntimeException("Vantagem não está ativa");
            }
            
            if (advantage.getCurrentRedemptions() >= advantage.getMaxRedemptions()) {
                throw new RuntimeException("Vantagem esgotada");
            }

            // Find student and validate balance
            Student student = studentRepository.findByEmail(request.getStudentEmail())
                    .orElseThrow(() -> new RuntimeException("Estudante não encontrado com o email: " + request.getStudentEmail()));
            
            System.out.println("Estudante encontrado: " + student.getName() + ", saldo: " + student.getCoinBalance());
            
            if (student.getCoinBalance() < advantage.getCoinCost()) {
                throw new RuntimeException("Saldo insuficiente de moedas para resgatar esta vantagem");
            }
            
            // Generate coupon and create redemption
            String couponCode = generateCouponCode();
            System.out.println("Cupom gerado: " + couponCode);
            
            Redemption redemption = new Redemption();
            redemption.setAdvantage(advantage);
            redemption.setStudent(student);
            redemption.setCouponCode(couponCode);
            redemption.setStudentEmail(request.getStudentEmail());
            redemption.setStudentName(request.getStudentName());
            
            // Deduct coins from student balance
            student.setCoinBalance(student.getCoinBalance() - advantage.getCoinCost());
            studentRepository.save(student);
            System.out.println("Saldo atualizado: " + student.getCoinBalance());
            
            // Save redemption and update advantage count
            Redemption savedRedemption = redemptionRepository.save(redemption);
            System.out.println("Resgate salvo com ID: " + savedRedemption.getId());
            
            advantage.setCurrentRedemptions(advantage.getCurrentRedemptions() + 1);
            advantageRepository.save(advantage);
            System.out.println("Contagem de resgates atualizada: " + advantage.getCurrentRedemptions());
            
            return savedRedemption;
        } catch (Exception e) {
            System.err.println("Erro ao resgatar vantagem:");
            e.printStackTrace();
            throw new RuntimeException("Erro ao processar resgate: " + e.getMessage(), e);
        }
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

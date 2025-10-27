package com.moedaestudantil.controller;

import com.moedaestudantil.dto.AdvantageRequest;
import com.moedaestudantil.dto.AdvantageResponse;
import com.moedaestudantil.dto.RedemptionRequest;
import com.moedaestudantil.model.Advantage;
import com.moedaestudantil.model.Redemption;
import com.moedaestudantil.service.AdvantageService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/advantages")
@CrossOrigin(origins = "*")
public class AdvantageController {
    
    @Autowired
    private AdvantageService advantageService;
    
    @PostMapping("/company/{companyId}")
    public ResponseEntity<?> createAdvantage(@PathVariable Long companyId,
                                           @RequestBody AdvantageRequest request) {
        try {
            System.out.println("Creating advantage for company: " + companyId);
            System.out.println("Request: " + request);
            
            Advantage advantage = advantageService.createAdvantage(companyId, request);
            System.out.println("Advantage created: " + advantage.getId());
            
            // Retornar DTO sem referências circulares
            AdvantageResponse response = new AdvantageResponse(
                advantage.getId(),
                advantage.getTitle(),
                advantage.getDescription(),
                advantage.getPhotoUrl(),
                advantage.getCoinCost(),
                advantage.getIsActive(),
                advantage.getMaxRedemptions(),
                advantage.getCurrentRedemptions(),
                advantage.getCreatedAt()
            );
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            System.out.println("Error creating advantage: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @GetMapping("/company/{companyId}")
    public ResponseEntity<List<AdvantageResponse>> getAdvantagesByCompany(@PathVariable Long companyId) {
        List<Advantage> advantages = advantageService.getAdvantagesByCompany(companyId);
        List<AdvantageResponse> response = advantages.stream()
            .map(adv -> new AdvantageResponse(
                adv.getId(),
                adv.getTitle(),
                adv.getDescription(),
                adv.getPhotoUrl(),
                adv.getCoinCost(),
                adv.getIsActive(),
                adv.getMaxRedemptions(),
                adv.getCurrentRedemptions(),
                adv.getCreatedAt()
            ))
            .toList();
        return ResponseEntity.ok(response);
    }
    
    @GetMapping
    public ResponseEntity<List<AdvantageResponse>> getAvailableAdvantages() {
        List<Advantage> advantages = advantageService.getAvailableAdvantages();
        List<AdvantageResponse> response = advantages.stream()
            .map(adv -> new AdvantageResponse(
                adv.getId(),
                adv.getTitle(),
                adv.getDescription(),
                adv.getPhotoUrl(),
                adv.getCoinCost(),
                adv.getIsActive(),
                adv.getMaxRedemptions(),
                adv.getCurrentRedemptions(),
                adv.getCreatedAt()
            ))
            .toList();
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/affordable/{maxCost}")
    public ResponseEntity<List<AdvantageResponse>> getAffordableAdvantages(@PathVariable Integer maxCost) {
        List<Advantage> advantages = advantageService.getAffordableAdvantages(maxCost);
        List<AdvantageResponse> response = advantages.stream()
            .map(adv -> new AdvantageResponse(
                adv.getId(),
                adv.getTitle(),
                adv.getDescription(),
                adv.getPhotoUrl(),
                adv.getCoinCost(),
                adv.getIsActive(),
                adv.getMaxRedemptions(),
                adv.getCurrentRedemptions(),
                adv.getCreatedAt()
            ))
            .toList();
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<AdvantageResponse> getAdvantageById(@PathVariable Long id) {
        return advantageService.getAdvantageById(id)
                .map(adv -> new AdvantageResponse(
                    adv.getId(),
                    adv.getTitle(),
                    adv.getDescription(),
                    adv.getPhotoUrl(),
                    adv.getCoinCost(),
                    adv.getIsActive(),
                    adv.getMaxRedemptions(),
                    adv.getCurrentRedemptions(),
                    adv.getCreatedAt()
                ))
                .map(resp -> ResponseEntity.ok(resp))
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<?> updateAdvantage(@PathVariable Long id,
                                           @Valid @RequestBody AdvantageRequest request) {
        try {
            Advantage advantage = advantageService.updateAdvantage(id, request);
            return ResponseEntity.ok(advantage);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAdvantage(@PathVariable Long id) {
        try {
            advantageService.deleteAdvantage(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @PostMapping("/redeem")
    public ResponseEntity<?> redeemAdvantage(@Valid @RequestBody RedemptionRequest request) {
        try {
            Redemption redemption = advantageService.redeemAdvantage(request);
            return ResponseEntity.ok(redemption);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @GetMapping("/redemptions/company/{companyId}")
    public ResponseEntity<List<Redemption>> getRedemptionsByCompany(@PathVariable Long companyId) {
        List<Redemption> redemptions = advantageService.getRedemptionsByCompany(companyId);
        return ResponseEntity.ok(redemptions);
    }
    
    @GetMapping("/redemptions/coupon/{couponCode}")
    public ResponseEntity<Redemption> getRedemptionByCouponCode(@PathVariable String couponCode) {
        return advantageService.getRedemptionByCouponCode(couponCode)
                .map(redemption -> ResponseEntity.ok(redemption))
                .orElse(ResponseEntity.notFound().build());
    }
}

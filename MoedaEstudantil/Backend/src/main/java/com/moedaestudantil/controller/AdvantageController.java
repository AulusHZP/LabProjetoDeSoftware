package com.moedaestudantil.controller;

import com.moedaestudantil.dto.AdvantageRequest;
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
            
            // Teste simples - retornar os dados recebidos
            return ResponseEntity.ok(Map.of(
                "id", 1L,
                "companyId", companyId,
                "title", request.getTitle(),
                "description", request.getDescription(),
                "coinCost", request.getCoinCost(),
                "maxRedemptions", request.getMaxRedemptions()
            ));
        } catch (Exception e) {
            System.out.println("Error creating advantage: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @GetMapping("/company/{companyId}")
    public ResponseEntity<List<Advantage>> getAdvantagesByCompany(@PathVariable Long companyId) {
        List<Advantage> advantages = advantageService.getAdvantagesByCompany(companyId);
        return ResponseEntity.ok(advantages);
    }
    
    @GetMapping
    public ResponseEntity<List<Advantage>> getAvailableAdvantages() {
        List<Advantage> advantages = advantageService.getAvailableAdvantages();
        return ResponseEntity.ok(advantages);
    }
    
    @GetMapping("/affordable/{maxCost}")
    public ResponseEntity<List<Advantage>> getAffordableAdvantages(@PathVariable Integer maxCost) {
        List<Advantage> advantages = advantageService.getAffordableAdvantages(maxCost);
        return ResponseEntity.ok(advantages);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Advantage> getAdvantageById(@PathVariable Long id) {
        return advantageService.getAdvantageById(id)
                .map(advantage -> ResponseEntity.ok(advantage))
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

package com.moedaestudantil.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.hibernate.annotations.CreationTimestamp;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "advantages")
public class Advantage {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;
    
    @NotBlank(message = "Título é obrigatório")
    @Column(nullable = false)
    private String title;
    
    @NotBlank(message = "Descrição é obrigatória")
    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;
    
    @Column(name = "photo_url")
    private String photoUrl;
    
    @NotNull(message = "Custo em moedas é obrigatório")
    @Positive(message = "Custo deve ser positivo")
    @Column(name = "coin_cost", nullable = false)
    private Integer coinCost;
    
    @Column(name = "is_active")
    private Boolean isActive = true;
    
    @Column(name = "max_redemptions")
    private Integer maxRedemptions = 1;
    
    @Column(name = "current_redemptions")
    private Integer currentRedemptions = 0;
    
    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @OneToMany(mappedBy = "advantage", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Redemption> redemptions;
    
    // Constructors
    public Advantage() {}
    
    public Advantage(Company company, String title, String description, Integer coinCost) {
        this.company = company;
        this.title = title;
        this.description = description;
        this.coinCost = coinCost;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Company getCompany() {
        return company;
    }
    
    public void setCompany(Company company) {
        this.company = company;
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getPhotoUrl() {
        return photoUrl;
    }
    
    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }
    
    public Integer getCoinCost() {
        return coinCost;
    }
    
    public void setCoinCost(Integer coinCost) {
        this.coinCost = coinCost;
    }
    
    public Boolean getIsActive() {
        return isActive;
    }
    
    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }
    
    public Integer getMaxRedemptions() {
        return maxRedemptions;
    }
    
    public void setMaxRedemptions(Integer maxRedemptions) {
        this.maxRedemptions = maxRedemptions;
    }
    
    public Integer getCurrentRedemptions() {
        return currentRedemptions;
    }
    
    public void setCurrentRedemptions(Integer currentRedemptions) {
        this.currentRedemptions = currentRedemptions;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public List<Redemption> getRedemptions() {
        return redemptions;
    }
    
    public void setRedemptions(List<Redemption> redemptions) {
        this.redemptions = redemptions;
    }
}

package com.moedaestudantil.dto;

import java.time.LocalDateTime;

public class AdvantageResponse {
    private Long id;
    private String title;
    private String description;
    private String photoUrl;
    private Integer coinCost;
    private Boolean isActive;
    private Integer maxRedemptions;
    private Integer currentRedemptions;
    private LocalDateTime createdAt;
    
    public AdvantageResponse() {}
    
    public AdvantageResponse(Long id, String title, String description, String photoUrl, 
                           Integer coinCost, Boolean isActive, Integer maxRedemptions, 
                           Integer currentRedemptions, LocalDateTime createdAt) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.photoUrl = photoUrl;
        this.coinCost = coinCost;
        this.isActive = isActive;
        this.maxRedemptions = maxRedemptions;
        this.currentRedemptions = currentRedemptions;
        this.createdAt = createdAt;
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    public String getPhotoUrl() { return photoUrl; }
    public void setPhotoUrl(String photoUrl) { this.photoUrl = photoUrl; }
    
    public Integer getCoinCost() { return coinCost; }
    public void setCoinCost(Integer coinCost) { this.coinCost = coinCost; }
    
    public Boolean getIsActive() { return isActive; }
    public void setIsActive(Boolean isActive) { this.isActive = isActive; }
    
    public Integer getMaxRedemptions() { return maxRedemptions; }
    public void setMaxRedemptions(Integer maxRedemptions) { this.maxRedemptions = maxRedemptions; }
    
    public Integer getCurrentRedemptions() { return currentRedemptions; }
    public void setCurrentRedemptions(Integer currentRedemptions) { this.currentRedemptions = currentRedemptions; }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}

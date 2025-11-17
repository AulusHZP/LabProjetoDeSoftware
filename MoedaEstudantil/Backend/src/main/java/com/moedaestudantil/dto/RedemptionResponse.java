package com.moedaestudantil.dto;

import java.time.LocalDateTime;

public class RedemptionResponse {
    private Long id;
    private Long advantageId;
    private String advantageTitle;
    private String advantageDescription;
    private Integer advantageCoinCost;
    private String couponCode;
    private Long studentId;
    private String studentEmail;
    private String studentName;
    private LocalDateTime createdAt;

    // Constructor
    public RedemptionResponse(Long id, Long advantageId, String advantageTitle, 
            String advantageDescription, Integer advantageCoinCost, String couponCode, 
            Long studentId, String studentEmail, String studentName, LocalDateTime createdAt) {
        this.id = id;
        this.advantageId = advantageId;
        this.advantageTitle = advantageTitle;
        this.advantageDescription = advantageDescription;
        this.advantageCoinCost = advantageCoinCost;
        this.couponCode = couponCode;
        this.studentId = studentId;
        this.studentEmail = studentEmail;
        this.studentName = studentName;
        this.createdAt = createdAt;
    }

    // Getters
    public Long getId() {
        return id;
    }

    public Long getAdvantageId() {
        return advantageId;
    }

    public String getAdvantageTitle() {
        return advantageTitle;
    }

    public String getAdvantageDescription() {
        return advantageDescription;
    }

    public Integer getAdvantageCoinCost() {
        return advantageCoinCost;
    }

    public String getCouponCode() {
        return couponCode;
    }

    public Long getStudentId() {
        return studentId;
    }

    public String getStudentEmail() {
        return studentEmail;
    }

    public String getStudentName() {
        return studentName;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
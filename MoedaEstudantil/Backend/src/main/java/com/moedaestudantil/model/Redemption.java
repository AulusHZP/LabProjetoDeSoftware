package com.moedaestudantil.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "redemptions")
public class Redemption {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "advantage_id", nullable = false)
    private Advantage advantage;
    
    @NotBlank(message = "Código do cupom é obrigatório")
    @Column(name = "coupon_code", nullable = false, unique = true)
    private String couponCode;
    
    @Column(name = "student_email")
    private String studentEmail;
    
    @Column(name = "student_name")
    private String studentName;
    
    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    // Constructors
    public Redemption() {}
    
    public Redemption(Advantage advantage, String couponCode, String studentEmail, String studentName) {
        this.advantage = advantage;
        this.couponCode = couponCode;
        this.studentEmail = studentEmail;
        this.studentName = studentName;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Advantage getAdvantage() {
        return advantage;
    }
    
    public void setAdvantage(Advantage advantage) {
        this.advantage = advantage;
    }
    
    public String getCouponCode() {
        return couponCode;
    }
    
    public void setCouponCode(String couponCode) {
        this.couponCode = couponCode;
    }
    
    public String getStudentEmail() {
        return studentEmail;
    }
    
    public void setStudentEmail(String studentEmail) {
        this.studentEmail = studentEmail;
    }
    
    public String getStudentName() {
        return studentName;
    }
    
    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}

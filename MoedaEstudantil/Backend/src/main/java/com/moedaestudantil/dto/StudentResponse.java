package com.moedaestudantil.dto;

import java.time.LocalDateTime;

public class StudentResponse {
    
    private Long id;
    private String name;
    private String email;
    private String cpf;
    private String rg;
    private String address;
    private Long institutionId;
    private String institutionName;
    private String course;
    private Integer coinBalance;
    private LocalDateTime createdAt;
    
    // Constructors
    public StudentResponse() {}
    
    public StudentResponse(Long id, String name, String email, String cpf, String rg, 
                          String address, Long institutionId, String institutionName, 
                          String course, Integer coinBalance, LocalDateTime createdAt) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.cpf = cpf;
        this.rg = rg;
        this.address = address;
        this.institutionId = institutionId;
        this.institutionName = institutionName;
        this.course = course;
        this.coinBalance = coinBalance;
        this.createdAt = createdAt;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getCpf() {
        return cpf;
    }
    
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
    
    public String getRg() {
        return rg;
    }
    
    public void setRg(String rg) {
        this.rg = rg;
    }
    
    public String getAddress() {
        return address;
    }
    
    public void setAddress(String address) {
        this.address = address;
    }
    
    public Long getInstitutionId() {
        return institutionId;
    }
    
    public void setInstitutionId(Long institutionId) {
        this.institutionId = institutionId;
    }
    
    public String getInstitutionName() {
        return institutionName;
    }
    
    public void setInstitutionName(String institutionName) {
        this.institutionName = institutionName;
    }
    
    public String getCourse() {
        return course;
    }
    
    public void setCourse(String course) {
        this.course = course;
    }
    
    public Integer getCoinBalance() {
        return coinBalance;
    }
    
    public void setCoinBalance(Integer coinBalance) {
        this.coinBalance = coinBalance;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}

package com.moedaestudantil.dto;

import java.time.LocalDateTime;

import com.moedaestudantil.model.Professor;

public class ProfessorResponse {
    private Long id;
    private String name;
    private String email;
    private String cpf;
    private String department;
    private Long institutionId;
    private String institutionName;
    private Integer coinBalance;
    private LocalDateTime createdAt;
    private String message;

    public ProfessorResponse() {}

    public ProfessorResponse(Professor professor) {
        this.id = professor.getId();
        this.name = professor.getName();
        this.email = professor.getEmail();
        this.cpf = professor.getCpf();
        this.department = professor.getDepartment();
        if (professor.getInstitution() != null) {
            this.institutionId = professor.getInstitution().getId();
            this.institutionName = professor.getInstitution().getName();
        }
        this.coinBalance = professor.getCoinBalance();
        this.createdAt = professor.getCreatedAt();
    }

    public ProfessorResponse(String message) {
        this.message = message;
    }

    // Getters and setters
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

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

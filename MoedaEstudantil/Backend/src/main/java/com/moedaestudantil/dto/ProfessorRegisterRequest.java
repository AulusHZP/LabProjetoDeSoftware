package com.moedaestudantil.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ProfessorRegisterRequest {
    
    @NotBlank(message = "Nome é obrigatório")
    private String name;
    
    @Email(message = "Email deve ser válido")
    @NotBlank(message = "Email é obrigatório")
    private String email;
    
    @NotBlank(message = "CPF é obrigatório")
    private String cpf;
    
    @NotNull(message = "ID da instituição é obrigatório")
    private Long institutionId;
    
    @NotBlank(message = "Departamento é obrigatório")
    private String department;
    
    @NotBlank(message = "Senha é obrigatória")
    private String password;
    
    // Constructors
    public ProfessorRegisterRequest() {}
    
    public ProfessorRegisterRequest(String name, String email, String cpf, Long institutionId, String department, String password) {
        this.name = name;
        this.email = email;
        this.cpf = cpf;
        this.institutionId = institutionId;
        this.department = department;
        this.password = password;
    }

    // Getters and Setters
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

    public Long getInstitutionId() {
        return institutionId;
    }

    public void setInstitutionId(Long institutionId) {
        this.institutionId = institutionId;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

package com.moedaestudantil.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class StudentRegisterRequest {
    
    @NotBlank(message = "Nome é obrigatório")
    private String name;
    
    @Email(message = "Email deve ser válido")
    @NotBlank(message = "Email é obrigatório")
    private String email;
    
    @NotBlank(message = "CPF é obrigatório")
    private String cpf;
    
    @NotBlank(message = "RG é obrigatório")
    private String rg;
    
    @NotBlank(message = "Endereço é obrigatório")
    private String address;
    
    @NotNull(message = "ID da instituição é obrigatório")
    private Long institutionId;
    
    @NotBlank(message = "Curso é obrigatório")
    private String course;
    
    @NotBlank(message = "Senha é obrigatória")
    private String password;
    
    // Constructors
    public StudentRegisterRequest() {}
    
    public StudentRegisterRequest(String name, String email, String cpf, String rg, 
                                   String address, Long institutionId, String course, String password) {
        this.name = name;
        this.email = email;
        this.cpf = cpf;
        this.rg = rg;
        this.address = address;
        this.institutionId = institutionId;
        this.course = course;
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
    
    public String getCourse() {
        return course;
    }
    
    public void setCourse(String course) {
        this.course = course;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
}

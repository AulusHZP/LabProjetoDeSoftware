package com.moedaestudantil.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class CompanyRegisterRequest {
    
    @NotBlank(message = "Nome da empresa é obrigatório")
    private String companyName;
    
    @NotBlank(message = "CNPJ é obrigatório")
    private String cnpj;
    
    @Email(message = "Email deve ser válido")
    @NotBlank(message = "Email é obrigatório")
    private String email;
    
    @NotBlank(message = "Senha é obrigatória")
    private String password;
    
    // Constructors
    public CompanyRegisterRequest() {}
    
    public CompanyRegisterRequest(String companyName, String cnpj, String email, String password) {
        this.companyName = companyName;
        this.cnpj = cnpj;
        this.email = email;
        this.password = password;
    }
    
    // Getters and Setters
    public String getCompanyName() {
        return companyName;
    }
    
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
    
    public String getCnpj() {
        return cnpj;
    }
    
    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
}

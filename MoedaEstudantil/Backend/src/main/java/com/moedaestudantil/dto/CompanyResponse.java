package com.moedaestudantil.dto;

public class CompanyResponse {
    
    private Long id;
    private String companyName;
    private String cnpj;
    private String email;
    private String message;
    
    // Constructors
    public CompanyResponse() {}
    
    public CompanyResponse(Long id, String companyName, String cnpj, String email) {
        this.id = id;
        this.companyName = companyName;
        this.cnpj = cnpj;
        this.email = email;
    }
    
    public CompanyResponse(String message) {
        this.message = message;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
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
    
    public String getMessage() {
        return message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
}

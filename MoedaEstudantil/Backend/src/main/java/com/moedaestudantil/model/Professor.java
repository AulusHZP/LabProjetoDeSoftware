package com.moedaestudantil.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.CreationTimestamp;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "professors")
public class Professor {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Nome é obrigatório")
    @Column(name = "name", nullable = false)
    private String name;
    
    @NotBlank(message = "CPF é obrigatório")
    @Column(name = "cpf", nullable = false, unique = true)
    private String cpf;
    
    @NotBlank(message = "Departamento é obrigatório")
    @Column(name = "department", nullable = false)
    private String department;
    
    @NotNull(message = "Instituição é obrigatória")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "institution_id", nullable = false)
    private Institution institution;
    
    @Column(name = "coin_balance", nullable = false)
    private Integer coinBalance = 1000; // Professores começam com 1000 moedas por semestre
    
    @Column(name = "password", nullable = false)
    private String password;
    
    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @OneToMany(mappedBy = "professor", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Transaction> transactions;
    
    // Constructors
    public Professor() {}
    
    public Professor(String name, String cpf, String department, Institution institution, String password) {
        this.name = name;
        this.cpf = cpf;
        this.department = department;
        this.institution = institution;
        this.password = password;
        this.coinBalance = 1000;
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
    
    public Institution getInstitution() {
        return institution;
    }
    
    public void setInstitution(Institution institution) {
        this.institution = institution;
    }
    
    public Integer getCoinBalance() {
        return coinBalance;
    }
    
    public void setCoinBalance(Integer coinBalance) {
        this.coinBalance = coinBalance;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public List<Transaction> getTransactions() {
        return transactions;
    }
    
    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }
}

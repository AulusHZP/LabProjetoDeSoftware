package org.example.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 * Entidade Agente representando um representante de empresa ou banco
 * Baseado nas histórias de usuário do Agente
 */
@Entity
@Table(name = "agentes")
public class Agente {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Nome é obrigatório")
    @Size(min = 2, max = 100, message = "Nome deve ter entre 2 e 100 caracteres")
    @Column(nullable = false, length = 100)
    private String nome;
    
    @NotBlank(message = "Email é obrigatório")
    @Email(message = "Email deve ter formato válido")
    @Column(nullable = false, unique = true, length = 150)
    private String email;
    
    @NotBlank(message = "Senha é obrigatória")
    @Size(min = 6, message = "Senha deve ter pelo menos 6 caracteres")
    @Column(nullable = false)
    private String senha;
    
    @NotBlank(message = "CPF é obrigatório")
    @Pattern(regexp = "\\d{11}", message = "CPF deve conter exatamente 11 dígitos")
    @Column(nullable = false, unique = true, length = 11)
    private String cpf;
    
    @NotBlank(message = "Telefone é obrigatório")
    @Pattern(regexp = "\\d{10,11}", message = "Telefone deve conter 10 ou 11 dígitos")
    @Column(nullable = false, length = 11)
    private String telefone;
    
    @NotBlank(message = "Nome da empresa é obrigatório")
    @Size(max = 100, message = "Nome da empresa deve ter no máximo 100 caracteres")
    @Column(name = "nome_empresa", nullable = false, length = 100)
    private String nomeEmpresa;
    
    @NotBlank(message = "CNPJ é obrigatório")
    @Pattern(regexp = "\\d{14}", message = "CNPJ deve conter exatamente 14 dígitos")
    @Column(nullable = false, unique = true, length = 14)
    private String cnpj;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_agente", nullable = false, length = 20)
    private TipoAgente tipoAgente;
    
    @Column(name = "limite_aprovacao", precision = 15, scale = 2)
    private java.math.BigDecimal limiteAprovacao;
    
    @Column(name = "data_cadastro", nullable = false)
    private LocalDateTime dataCadastro;
    
    @Column(name = "data_ultima_atualizacao")
    private LocalDateTime dataUltimaAtualizacao;
    
    @Column(name = "ativo", nullable = false)
    private Boolean ativo = true;
    
    // Construtores
    public Agente() {
        this.dataCadastro = LocalDateTime.now();
        this.ativo = true;
    }
    
    public Agente(String nome, String email, String senha, String cpf, String telefone,
                  String nomeEmpresa, String cnpj, TipoAgente tipoAgente) {
        this();
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.cpf = cpf;
        this.telefone = telefone;
        this.nomeEmpresa = nomeEmpresa;
        this.cnpj = cnpj;
        this.tipoAgente = tipoAgente;
    }
    
    // Getters e Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getNome() {
        return nome;
    }
    
    public void setNome(String nome) {
        this.nome = nome;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getSenha() {
        return senha;
    }
    
    public void setSenha(String senha) {
        this.senha = senha;
    }
    
    public String getCpf() {
        return cpf;
    }
    
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
    
    public String getTelefone() {
        return telefone;
    }
    
    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
    
    public String getNomeEmpresa() {
        return nomeEmpresa;
    }
    
    public void setNomeEmpresa(String nomeEmpresa) {
        this.nomeEmpresa = nomeEmpresa;
    }
    
    public String getCnpj() {
        return cnpj;
    }
    
    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }
    
    public TipoAgente getTipoAgente() {
        return tipoAgente;
    }
    
    public void setTipoAgente(TipoAgente tipoAgente) {
        this.tipoAgente = tipoAgente;
    }
    
    public java.math.BigDecimal getLimiteAprovacao() {
        return limiteAprovacao;
    }
    
    public void setLimiteAprovacao(java.math.BigDecimal limiteAprovacao) {
        this.limiteAprovacao = limiteAprovacao;
    }
    
    public LocalDateTime getDataCadastro() {
        return dataCadastro;
    }
    
    public void setDataCadastro(LocalDateTime dataCadastro) {
        this.dataCadastro = dataCadastro;
    }
    
    public LocalDateTime getDataUltimaAtualizacao() {
        return dataUltimaAtualizacao;
    }
    
    public void setDataUltimaAtualizacao(LocalDateTime dataUltimaAtualizacao) {
        this.dataUltimaAtualizacao = dataUltimaAtualizacao;
    }
    
    public Boolean getAtivo() {
        return ativo;
    }
    
    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }
    
    // Métodos de negócio
    @PreUpdate
    public void preUpdate() {
        this.dataUltimaAtualizacao = LocalDateTime.now();
    }
    
    public void ativar() {
        this.ativo = true;
        this.dataUltimaAtualizacao = LocalDateTime.now();
    }
    
    public void desativar() {
        this.ativo = false;
        this.dataUltimaAtualizacao = LocalDateTime.now();
    }
    
    public boolean podeAprovarPedido(java.math.BigDecimal valorPedido) {
        if (limiteAprovacao == null) {
            return true; // Sem limite definido, pode aprovar qualquer valor
        }
        return valorPedido.compareTo(limiteAprovacao) <= 0;
    }
    
    public String getDescricaoCompleta() {
        return String.format("%s - %s (%s)", nome, nomeEmpresa, tipoAgente.getDescricao());
    }
    
    // Enum para tipo de agente
    public enum TipoAgente {
        BANCO("Banco"),
        FINANCEIRA("Financeira"),
        EMPRESA("Empresa"),
        COOPERATIVA("Cooperativa");
        
        private final String descricao;
        
        TipoAgente(String descricao) {
            this.descricao = descricao;
        }
        
        public String getDescricao() {
            return descricao;
        }
    }
    
    @Override
    public String toString() {
        return "Agente{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", email='" + email + '\'' +
                ", cpf='" + cpf + '\'' +
                ", telefone='" + telefone + '\'' +
                ", nomeEmpresa='" + nomeEmpresa + '\'' +
                ", cnpj='" + cnpj + '\'' +
                ", tipoAgente=" + tipoAgente +
                ", limiteAprovacao=" + limiteAprovacao +
                ", dataCadastro=" + dataCadastro +
                ", ativo=" + ativo +
                '}';
    }
}

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
 * Entidade Administrador representando um administrador do sistema
 * Baseado nas histórias de usuário do Administrador
 */
@Entity
@Table(name = "administradores")
public class Administrador {
    
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
    @Size(min = 8, message = "Senha deve ter pelo menos 8 caracteres")
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
    
    @Enumerated(EnumType.STRING)
    @Column(name = "nivel_acesso", nullable = false, length = 20)
    private NivelAcesso nivelAcesso = NivelAcesso.ADMINISTRADOR;
    
    @Column(name = "data_cadastro", nullable = false)
    private LocalDateTime dataCadastro;
    
    @Column(name = "data_ultima_atualizacao")
    private LocalDateTime dataUltimaAtualizacao;
    
    @Column(name = "data_ultimo_login")
    private LocalDateTime dataUltimoLogin;
    
    @Column(name = "ativo", nullable = false)
    private Boolean ativo = true;
    
    // Construtores
    public Administrador() {
        this.dataCadastro = LocalDateTime.now();
        this.ativo = true;
        this.nivelAcesso = NivelAcesso.ADMINISTRADOR;
    }
    
    public Administrador(String nome, String email, String senha, String cpf, String telefone) {
        this();
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.cpf = cpf;
        this.telefone = telefone;
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
    
    public NivelAcesso getNivelAcesso() {
        return nivelAcesso;
    }
    
    public void setNivelAcesso(NivelAcesso nivelAcesso) {
        this.nivelAcesso = nivelAcesso;
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
    
    public LocalDateTime getDataUltimoLogin() {
        return dataUltimoLogin;
    }
    
    public void setDataUltimoLogin(LocalDateTime dataUltimoLogin) {
        this.dataUltimoLogin = dataUltimoLogin;
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
    
    public void registrarLogin() {
        this.dataUltimoLogin = LocalDateTime.now();
        this.dataUltimaAtualizacao = LocalDateTime.now();
    }
    
    public boolean temPermissaoGerenciarUsuarios() {
        return this.nivelAcesso == NivelAcesso.SUPER_ADMINISTRADOR || 
               this.nivelAcesso == NivelAcesso.ADMINISTRADOR;
    }
    
    public boolean temPermissaoGerenciarAutomoveis() {
        return this.nivelAcesso == NivelAcesso.SUPER_ADMINISTRADOR || 
               this.nivelAcesso == NivelAcesso.ADMINISTRADOR;
    }
    
    public boolean temPermissaoGerarRelatorios() {
        return this.nivelAcesso == NivelAcesso.SUPER_ADMINISTRADOR || 
               this.nivelAcesso == NivelAcesso.ADMINISTRADOR;
    }
    
    public boolean temPermissaoConfiguracoesSistema() {
        return this.nivelAcesso == NivelAcesso.SUPER_ADMINISTRADOR;
    }
    
    public String getDescricaoCompleta() {
        return String.format("%s (%s)", nome, nivelAcesso.getDescricao());
    }
    
    // Enum para nível de acesso
    public enum NivelAcesso {
        SUPER_ADMINISTRADOR("Super Administrador"),
        ADMINISTRADOR("Administrador"),
        OPERADOR("Operador");
        
        private final String descricao;
        
        NivelAcesso(String descricao) {
            this.descricao = descricao;
        }
        
        public String getDescricao() {
            return descricao;
        }
    }
    
    @Override
    public String toString() {
        return "Administrador{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", email='" + email + '\'' +
                ", cpf='" + cpf + '\'' +
                ", telefone='" + telefone + '\'' +
                ", nivelAcesso=" + nivelAcesso +
                ", dataCadastro=" + dataCadastro +
                ", ativo=" + ativo +
                '}';
    }
}

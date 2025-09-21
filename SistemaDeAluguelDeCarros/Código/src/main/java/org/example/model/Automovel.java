package org.example.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 * Entidade Automóvel representando um veículo disponível para aluguel
 * Baseado nas histórias de usuário do Administrador
 */
@Entity
@Table(name = "automoveis")
public class Automovel {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Marca é obrigatória")
    @Size(min = 2, max = 50, message = "Marca deve ter entre 2 e 50 caracteres")
    @Column(nullable = false, length = 50)
    private String marca;
    
    @NotBlank(message = "Modelo é obrigatório")
    @Size(min = 2, max = 50, message = "Modelo deve ter entre 2 e 50 caracteres")
    @Column(nullable = false, length = 50)
    private String modelo;
    
    @NotBlank(message = "Ano é obrigatório")
    @Pattern(regexp = "\\d{4}", message = "Ano deve conter exatamente 4 dígitos")
    @Column(nullable = false, length = 4)
    private String ano;
    
    @NotBlank(message = "Placa é obrigatória")
    @Pattern(regexp = "[A-Z]{3}\\d{4}|[A-Z]{3}\\d[A-Z]\\d{2}", message = "Placa deve ter formato válido")
    @Column(nullable = false, unique = true, length = 8)
    private String placa;
    
    @NotBlank(message = "Cor é obrigatória")
    @Size(max = 30, message = "Cor deve ter no máximo 30 caracteres")
    @Column(nullable = false, length = 30)
    private String cor;
    
    @NotBlank(message = "Categoria é obrigatória")
    @Size(max = 30, message = "Categoria deve ter no máximo 30 caracteres")
    @Column(nullable = false, length = 30)
    private String categoria; // Ex: Econômico, Intermediário, Luxo, SUV
    
    @NotNull(message = "Valor diário é obrigatório")
    @DecimalMin(value = "0.01", message = "Valor diário deve ser maior que zero")
    @Column(name = "valor_diario", nullable = false, precision = 10, scale = 2)
    private BigDecimal valorDiario;
    
    @Column(name = "quilometragem", nullable = false)
    private Long quilometragem = 0L;
    
    @Column(name = "capacidade_passageiros", nullable = false)
    private Integer capacidadePassageiros;
    
    @Column(name = "tipo_combustivel", nullable = false, length = 20)
    private String tipoCombustivel; // Ex: Gasolina, Etanol, Flex, Elétrico
    
    @Column(name = "transmissao", nullable = false, length = 20)
    private String transmissao; // Ex: Manual, Automático
    
    @Column(name = "ar_condicionado", nullable = false)
    private Boolean arCondicionado = false;
    
    @Column(name = "direcao_hidraulica", nullable = false)
    private Boolean direcaoHidraulica = false;
    
    @Column(name = "airbag", nullable = false)
    private Boolean airbag = false;
    
    @Column(name = "abs", nullable = false)
    private Boolean abs = false;
    
    @Column(name = "data_cadastro", nullable = false)
    private LocalDateTime dataCadastro;
    
    @Column(name = "data_ultima_atualizacao")
    private LocalDateTime dataUltimaAtualizacao;
    
    @Column(name = "disponivel", nullable = false)
    private Boolean disponivel = true;
    
    @Column(name = "ativo", nullable = false)
    private Boolean ativo = true;
    
    // Construtores
    public Automovel() {
        this.dataCadastro = LocalDateTime.now();
        this.disponivel = true;
        this.ativo = true;
    }
    
    public Automovel(String marca, String modelo, String ano, String placa, String cor, 
                     String categoria, BigDecimal valorDiario, Integer capacidadePassageiros,
                     String tipoCombustivel, String transmissao) {
        this();
        this.marca = marca;
        this.modelo = modelo;
        this.ano = ano;
        this.placa = placa;
        this.cor = cor;
        this.categoria = categoria;
        this.valorDiario = valorDiario;
        this.capacidadePassageiros = capacidadePassageiros;
        this.tipoCombustivel = tipoCombustivel;
        this.transmissao = transmissao;
    }
    
    // Getters e Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getMarca() {
        return marca;
    }
    
    public void setMarca(String marca) {
        this.marca = marca;
    }
    
    public String getModelo() {
        return modelo;
    }
    
    public void setModelo(String modelo) {
        this.modelo = modelo;
    }
    
    public String getAno() {
        return ano;
    }
    
    public void setAno(String ano) {
        this.ano = ano;
    }
    
    public String getPlaca() {
        return placa;
    }
    
    public void setPlaca(String placa) {
        this.placa = placa;
    }
    
    public String getCor() {
        return cor;
    }
    
    public void setCor(String cor) {
        this.cor = cor;
    }
    
    public String getCategoria() {
        return categoria;
    }
    
    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
    
    public BigDecimal getValorDiario() {
        return valorDiario;
    }
    
    public void setValorDiario(BigDecimal valorDiario) {
        this.valorDiario = valorDiario;
    }
    
    public Long getQuilometragem() {
        return quilometragem;
    }
    
    public void setQuilometragem(Long quilometragem) {
        this.quilometragem = quilometragem;
    }
    
    public Integer getCapacidadePassageiros() {
        return capacidadePassageiros;
    }
    
    public void setCapacidadePassageiros(Integer capacidadePassageiros) {
        this.capacidadePassageiros = capacidadePassageiros;
    }
    
    public String getTipoCombustivel() {
        return tipoCombustivel;
    }
    
    public void setTipoCombustivel(String tipoCombustivel) {
        this.tipoCombustivel = tipoCombustivel;
    }
    
    public String getTransmissao() {
        return transmissao;
    }
    
    public void setTransmissao(String transmissao) {
        this.transmissao = transmissao;
    }
    
    public Boolean getArCondicionado() {
        return arCondicionado;
    }
    
    public void setArCondicionado(Boolean arCondicionado) {
        this.arCondicionado = arCondicionado;
    }
    
    public Boolean getDirecaoHidraulica() {
        return direcaoHidraulica;
    }
    
    public void setDirecaoHidraulica(Boolean direcaoHidraulica) {
        this.direcaoHidraulica = direcaoHidraulica;
    }
    
    public Boolean getAirbag() {
        return airbag;
    }
    
    public void setAirbag(Boolean airbag) {
        this.airbag = airbag;
    }
    
    public Boolean getAbs() {
        return abs;
    }
    
    public void setAbs(Boolean abs) {
        this.abs = abs;
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
    
    public Boolean getDisponivel() {
        return disponivel;
    }
    
    public void setDisponivel(Boolean disponivel) {
        this.disponivel = disponivel;
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
    
    public void disponibilizar() {
        this.disponivel = true;
        this.dataUltimaAtualizacao = LocalDateTime.now();
    }
    
    public void indisponibilizar() {
        this.disponivel = false;
        this.dataUltimaAtualizacao = LocalDateTime.now();
    }
    
    public String getDescricaoCompleta() {
        return String.format("%s %s %s - %s (%s)", marca, modelo, ano, cor, placa);
    }
    
    @Override
    public String toString() {
        return "Automovel{" +
                "id=" + id +
                ", marca='" + marca + '\'' +
                ", modelo='" + modelo + '\'' +
                ", ano='" + ano + '\'' +
                ", placa='" + placa + '\'' +
                ", cor='" + cor + '\'' +
                ", categoria='" + categoria + '\'' +
                ", valorDiario=" + valorDiario +
                ", quilometragem=" + quilometragem +
                ", capacidadePassageiros=" + capacidadePassageiros +
                ", tipoCombustivel='" + tipoCombustivel + '\'' +
                ", transmissao='" + transmissao + '\'' +
                ", disponivel=" + disponivel +
                ", ativo=" + ativo +
                '}';
    }
}

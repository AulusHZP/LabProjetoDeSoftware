package org.example.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

/**
 * Entidade ContratoCredito representando um contrato de financiamento
 * Baseado nas histórias de usuário do Agente
 */
@Entity
@Table(name = "contratos_credito")
public class ContratoCredito {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "pedido_aluguel_id", nullable = false)
    @NotNull(message = "Pedido de aluguel é obrigatório")
    private PedidoAluguel pedidoAluguel;
    
    @ManyToOne
    @JoinColumn(name = "agente_id", nullable = false)
    @NotNull(message = "Agente é obrigatório")
    private Agente agente;
    
    @NotNull(message = "Valor financiado é obrigatório")
    @DecimalMin(value = "0.01", message = "Valor financiado deve ser maior que zero")
    @Column(name = "valor_financiado", nullable = false, precision = 15, scale = 2)
    private BigDecimal valorFinanciado;
    
    @NotNull(message = "Taxa de juros é obrigatória")
    @DecimalMin(value = "0.0", message = "Taxa de juros não pode ser negativa")
    @Column(name = "taxa_juros", nullable = false, precision = 5, scale = 4)
    private BigDecimal taxaJuros;
    
    @NotNull(message = "Número de parcelas é obrigatório")
    @Positive(message = "Número de parcelas deve ser positivo")
    @Column(name = "numero_parcelas", nullable = false)
    private Integer numeroParcelas;
    
    @NotNull(message = "Valor da parcela é obrigatório")
    @DecimalMin(value = "0.01", message = "Valor da parcela deve ser maior que zero")
    @Column(name = "valor_parcela", nullable = false, precision = 10, scale = 2)
    private BigDecimal valorParcela;
    
    @NotNull(message = "Data de início é obrigatória")
    @Column(name = "data_inicio", nullable = false)
    private LocalDate dataInicio;
    
    @Column(name = "data_vencimento")
    private LocalDate dataVencimento;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private StatusContrato status = StatusContrato.ATIVO;
    
    @Column(name = "observacoes", length = 500)
    private String observacoes;
    
    @Column(name = "data_cadastro", nullable = false)
    private LocalDateTime dataCadastro;
    
    @Column(name = "data_ultima_atualizacao")
    private LocalDateTime dataUltimaAtualizacao;
    
    @Column(name = "data_quitação")
    private LocalDateTime dataQuitacao;
    
    // Construtores
    public ContratoCredito() {
        this.dataCadastro = LocalDateTime.now();
        this.status = StatusContrato.ATIVO;
    }
    
    public ContratoCredito(PedidoAluguel pedidoAluguel, Agente agente, BigDecimal valorFinanciado,
                           BigDecimal taxaJuros, Integer numeroParcelas, BigDecimal valorParcela,
                           LocalDate dataInicio) {
        this();
        this.pedidoAluguel = pedidoAluguel;
        this.agente = agente;
        this.valorFinanciado = valorFinanciado;
        this.taxaJuros = taxaJuros;
        this.numeroParcelas = numeroParcelas;
        this.valorParcela = valorParcela;
        this.dataInicio = dataInicio;
        this.dataVencimento = dataInicio.plusMonths(numeroParcelas);
    }
    
    // Getters e Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public PedidoAluguel getPedidoAluguel() {
        return pedidoAluguel;
    }
    
    public void setPedidoAluguel(PedidoAluguel pedidoAluguel) {
        this.pedidoAluguel = pedidoAluguel;
    }
    
    public Agente getAgente() {
        return agente;
    }
    
    public void setAgente(Agente agente) {
        this.agente = agente;
    }
    
    public BigDecimal getValorFinanciado() {
        return valorFinanciado;
    }
    
    public void setValorFinanciado(BigDecimal valorFinanciado) {
        this.valorFinanciado = valorFinanciado;
    }
    
    public BigDecimal getTaxaJuros() {
        return taxaJuros;
    }
    
    public void setTaxaJuros(BigDecimal taxaJuros) {
        this.taxaJuros = taxaJuros;
    }
    
    public Integer getNumeroParcelas() {
        return numeroParcelas;
    }
    
    public void setNumeroParcelas(Integer numeroParcelas) {
        this.numeroParcelas = numeroParcelas;
    }
    
    public BigDecimal getValorParcela() {
        return valorParcela;
    }
    
    public void setValorParcela(BigDecimal valorParcela) {
        this.valorParcela = valorParcela;
    }
    
    public LocalDate getDataInicio() {
        return dataInicio;
    }
    
    public void setDataInicio(LocalDate dataInicio) {
        this.dataInicio = dataInicio;
    }
    
    public LocalDate getDataVencimento() {
        return dataVencimento;
    }
    
    public void setDataVencimento(LocalDate dataVencimento) {
        this.dataVencimento = dataVencimento;
    }
    
    public StatusContrato getStatus() {
        return status;
    }
    
    public void setStatus(StatusContrato status) {
        this.status = status;
    }
    
    public String getObservacoes() {
        return observacoes;
    }
    
    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
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
    
    public LocalDateTime getDataQuitacao() {
        return dataQuitacao;
    }
    
    public void setDataQuitacao(LocalDateTime dataQuitacao) {
        this.dataQuitacao = dataQuitacao;
    }
    
    // Métodos de negócio
    @PreUpdate
    public void preUpdate() {
        this.dataUltimaAtualizacao = LocalDateTime.now();
    }
    
    public void quitar() {
        this.status = StatusContrato.QUITADO;
        this.dataQuitacao = LocalDateTime.now();
        this.dataUltimaAtualizacao = LocalDateTime.now();
    }
    
    public void suspender() {
        this.status = StatusContrato.SUSPENSO;
        this.dataUltimaAtualizacao = LocalDateTime.now();
    }
    
    public void cancelar() {
        this.status = StatusContrato.CANCELADO;
        this.dataUltimaAtualizacao = LocalDateTime.now();
    }
    
    public BigDecimal calcularValorTotal() {
        return valorParcela.multiply(BigDecimal.valueOf(numeroParcelas));
    }
    
    public BigDecimal calcularJurosTotal() {
        return calcularValorTotal().subtract(valorFinanciado);
    }
    
    public boolean estaVencido() {
        return LocalDate.now().isAfter(dataVencimento) && status == StatusContrato.ATIVO;
    }
    
    public String getDescricaoResumida() {
        return String.format("Contrato #%d - %s (%d parcelas de R$ %.2f)", 
                id, agente.getNomeEmpresa(), numeroParcelas, valorParcela);
    }
    
    // Enum para status do contrato
    public enum StatusContrato {
        ATIVO("Ativo"),
        QUITADO("Quitado"),
        SUSPENSO("Suspenso"),
        CANCELADO("Cancelado");
        
        private final String descricao;
        
        StatusContrato(String descricao) {
            this.descricao = descricao;
        }
        
        public String getDescricao() {
            return descricao;
        }
    }
    
    @Override
    public String toString() {
        return "ContratoCredito{" +
                "id=" + id +
                ", pedidoAluguel=" + (pedidoAluguel != null ? pedidoAluguel.getId() : "null") +
                ", agente=" + (agente != null ? agente.getNome() : "null") +
                ", valorFinanciado=" + valorFinanciado +
                ", taxaJuros=" + taxaJuros +
                ", numeroParcelas=" + numeroParcelas +
                ", valorParcela=" + valorParcela +
                ", dataInicio=" + dataInicio +
                ", dataVencimento=" + dataVencimento +
                ", status=" + status +
                ", dataCadastro=" + dataCadastro +
                '}';
    }
}

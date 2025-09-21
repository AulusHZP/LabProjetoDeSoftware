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
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

/**
 * Entidade PedidoAluguel representando uma solicitação de aluguel de carro
 * Baseado nas histórias de usuário do Cliente
 */
@Entity
@Table(name = "pedidos_aluguel")
public class PedidoAluguel {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    @NotNull(message = "Cliente é obrigatório")
    private Cliente cliente;
    
    @ManyToOne
    @JoinColumn(name = "automovel_id", nullable = false)
    @NotNull(message = "Automóvel é obrigatório")
    private Automovel automovel;
    
    @NotNull(message = "Data de início é obrigatória")
    @Future(message = "Data de início deve ser no futuro")
    @Column(name = "data_inicio", nullable = false)
    private LocalDate dataInicio;
    
    @NotNull(message = "Data de fim é obrigatória")
    @Future(message = "Data de fim deve ser no futuro")
    @Column(name = "data_fim", nullable = false)
    private LocalDate dataFim;
    
    @NotNull(message = "Valor total é obrigatório")
    @DecimalMin(value = "0.01", message = "Valor total deve ser maior que zero")
    @Column(name = "valor_total", nullable = false, precision = 10, scale = 2)
    private BigDecimal valorTotal;
    
    @NotNull(message = "Número de dias é obrigatório")
    @Positive(message = "Número de dias deve ser positivo")
    @Column(name = "numero_dias", nullable = false)
    private Integer numeroDias;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private StatusPedido status = StatusPedido.PENDENTE;
    
    @Column(name = "observacoes", length = 500)
    private String observacoes;
    
    @Column(name = "data_cadastro", nullable = false)
    private LocalDateTime dataCadastro;
    
    @Column(name = "data_ultima_atualizacao")
    private LocalDateTime dataUltimaAtualizacao;
    
    @Column(name = "data_aprovacao")
    private LocalDateTime dataAprovacao;
    
    @Column(name = "data_rejeicao")
    private LocalDateTime dataRejeicao;
    
    @Column(name = "motivo_rejeicao", length = 500)
    private String motivoRejeicao;
    
    // Construtores
    public PedidoAluguel() {
        this.dataCadastro = LocalDateTime.now();
        this.status = StatusPedido.PENDENTE;
    }
    
    public PedidoAluguel(Cliente cliente, Automovel automovel, LocalDate dataInicio, 
                         LocalDate dataFim, BigDecimal valorTotal, Integer numeroDias) {
        this();
        this.cliente = cliente;
        this.automovel = automovel;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.valorTotal = valorTotal;
        this.numeroDias = numeroDias;
    }
    
    // Getters e Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Cliente getCliente() {
        return cliente;
    }
    
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
    
    public Automovel getAutomovel() {
        return automovel;
    }
    
    public void setAutomovel(Automovel automovel) {
        this.automovel = automovel;
    }
    
    public LocalDate getDataInicio() {
        return dataInicio;
    }
    
    public void setDataInicio(LocalDate dataInicio) {
        this.dataInicio = dataInicio;
    }
    
    public LocalDate getDataFim() {
        return dataFim;
    }
    
    public void setDataFim(LocalDate dataFim) {
        this.dataFim = dataFim;
    }
    
    public BigDecimal getValorTotal() {
        return valorTotal;
    }
    
    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }
    
    public Integer getNumeroDias() {
        return numeroDias;
    }
    
    public void setNumeroDias(Integer numeroDias) {
        this.numeroDias = numeroDias;
    }
    
    public StatusPedido getStatus() {
        return status;
    }
    
    public void setStatus(StatusPedido status) {
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
    
    public LocalDateTime getDataAprovacao() {
        return dataAprovacao;
    }
    
    public void setDataAprovacao(LocalDateTime dataAprovacao) {
        this.dataAprovacao = dataAprovacao;
    }
    
    public LocalDateTime getDataRejeicao() {
        return dataRejeicao;
    }
    
    public void setDataRejeicao(LocalDateTime dataRejeicao) {
        this.dataRejeicao = dataRejeicao;
    }
    
    public String getMotivoRejeicao() {
        return motivoRejeicao;
    }
    
    public void setMotivoRejeicao(String motivoRejeicao) {
        this.motivoRejeicao = motivoRejeicao;
    }
    
    // Métodos de negócio
    @PreUpdate
    public void preUpdate() {
        this.dataUltimaAtualizacao = LocalDateTime.now();
    }
    
    public void aprovar() {
        this.status = StatusPedido.APROVADO;
        this.dataAprovacao = LocalDateTime.now();
        this.dataUltimaAtualizacao = LocalDateTime.now();
    }
    
    public void rejeitar(String motivo) {
        this.status = StatusPedido.REJEITADO;
        this.dataRejeicao = LocalDateTime.now();
        this.motivoRejeicao = motivo;
        this.dataUltimaAtualizacao = LocalDateTime.now();
    }
    
    public void cancelar() {
        this.status = StatusPedido.CANCELADO;
        this.dataUltimaAtualizacao = LocalDateTime.now();
    }
    
    public void finalizar() {
        this.status = StatusPedido.FINALIZADO;
        this.dataUltimaAtualizacao = LocalDateTime.now();
    }
    
    public boolean podeSerModificado() {
        return this.status == StatusPedido.PENDENTE;
    }
    
    public boolean podeSerCancelado() {
        return this.status == StatusPedido.PENDENTE || this.status == StatusPedido.APROVADO;
    }
    
    public String getDescricaoResumida() {
        return String.format("Pedido #%d - %s (%s a %s)", 
                id, automovel.getDescricaoCompleta(), dataInicio, dataFim);
    }
    
    // Enum para status do pedido
    public enum StatusPedido {
        PENDENTE("Pendente"),
        APROVADO("Aprovado"),
        REJEITADO("Rejeitado"),
        CANCELADO("Cancelado"),
        FINALIZADO("Finalizado");
        
        private final String descricao;
        
        StatusPedido(String descricao) {
            this.descricao = descricao;
        }
        
        public String getDescricao() {
            return descricao;
        }
    }
    
    @Override
    public String toString() {
        return "PedidoAluguel{" +
                "id=" + id +
                ", cliente=" + (cliente != null ? cliente.getNome() : "null") +
                ", automovel=" + (automovel != null ? automovel.getDescricaoCompleta() : "null") +
                ", dataInicio=" + dataInicio +
                ", dataFim=" + dataFim +
                ", valorTotal=" + valorTotal +
                ", numeroDias=" + numeroDias +
                ", status=" + status +
                ", dataCadastro=" + dataCadastro +
                '}';
    }
}

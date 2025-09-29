package com.sistemaaluguel.sistemaaluguelcarros.entities;

import com.sistemaaluguel.sistemaaluguelcarros.enums.StatusContrato;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDate;

@Entity
@Table(name = "contratos")
public class Contrato {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "data_assinatura")
    private LocalDate dataAssinatura;

    @NotNull(message = "Valor é obrigatório")
    @Positive(message = "Valor deve ser positivo")
    @Column(name = "valor", nullable = false)
    private Double valor;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private StatusContrato status;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pedido_id", nullable = false)
    private Pedido pedido;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "agente_id", nullable = false)
    private Agente agente;

    @OneToOne(mappedBy = "contrato", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private ContratoCredito contratoCredito;

    // Construtores
    public Contrato() {
        this.status = StatusContrato.PENDENTE;
    }

    public Contrato(Double valor, Pedido pedido, Agente agente) {
        this();
        this.valor = valor;
        this.pedido = pedido;
        this.agente = agente;
    }

    // Métodos específicos da classe Contrato
    public void gerar() {
        this.status = StatusContrato.PENDENTE;
    }

    public void assinar() {
        if (this.status == StatusContrato.PENDENTE) {
            this.status = StatusContrato.ASSINADO;
            this.dataAssinatura = LocalDate.now();
        }
    }

    public void cancelar() {
        if (this.status != StatusContrato.CANCELADO && this.status != StatusContrato.FINALIZADO) {
            this.status = StatusContrato.CANCELADO;
        }
    }

    public void finalizar() {
        if (this.status == StatusContrato.ASSINADO) {
            this.status = StatusContrato.FINALIZADO;
        }
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDataAssinatura() {
        return dataAssinatura;
    }

    public void setDataAssinatura(LocalDate dataAssinatura) {
        this.dataAssinatura = dataAssinatura;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public StatusContrato getStatus() {
        return status;
    }

    public void setStatus(StatusContrato status) {
        this.status = status;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public Agente getAgente() {
        return agente;
    }

    public void setAgente(Agente agente) {
        this.agente = agente;
    }

    public ContratoCredito getContratoCredito() {
        return contratoCredito;
    }

    public void setContratoCredito(ContratoCredito contratoCredito) {
        this.contratoCredito = contratoCredito;
    }

    @Override
    public String toString() {
        return "Contrato{" +
                "id=" + id +
                ", dataAssinatura=" + dataAssinatura +
                ", valor=" + valor +
                ", status=" + status +
                '}';
    }
}

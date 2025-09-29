package com.sistemaaluguel.sistemaaluguelcarros.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.sistemaaluguel.sistemaaluguelcarros.enums.StatusPedido;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Future;

import java.time.LocalDate;

@Entity
@Table(name = "pedidos")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Data de início é obrigatória")
    @Column(name = "data_inicio", nullable = false)
    private LocalDate dataInicio;

    @NotNull(message = "Data de fim é obrigatória")
    @Future(message = "Data de fim deve ser no futuro")
    @Column(name = "data_fim", nullable = false)
    private LocalDate dataFim;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private StatusPedido status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id", nullable = false)
    @JsonBackReference
    private Cliente cliente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "agente_id")
    private Agente agente;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "automovel_id", nullable = false)
    @JsonManagedReference
    private Automovel automovel;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "contrato_id")
    private Contrato contrato;

    // Construtores
    public Pedido() {
        this.status = StatusPedido.PENDENTE;
    }

    public Pedido(LocalDate dataInicio, LocalDate dataFim, Cliente cliente, Automovel automovel) {
        this();
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.cliente = cliente;
        this.automovel = automovel;
    }

    // Métodos específicos da classe Pedido
    public void criar() {
        this.status = StatusPedido.PENDENTE;
    }

    public void modificar() {
        if (this.status == StatusPedido.PENDENTE || this.status == StatusPedido.EM_ANALISE) {
            // Permitir modificação
        }
    }

    public void cancelar() {
        if (this.status != StatusPedido.CANCELADO) {
            this.status = StatusPedido.CANCELADO;
        }
    }

    public void avaliar() {
        if (this.status == StatusPedido.PENDENTE) {
            this.status = StatusPedido.EM_ANALISE;
        }
    }

    public void aprovar() {
        if (this.status == StatusPedido.EM_ANALISE) {
            this.status = StatusPedido.APROVADO;
        }
    }

    public void rejeitar() {
        if (this.status == StatusPedido.EM_ANALISE) {
            this.status = StatusPedido.REJEITADO;
        }
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public StatusPedido getStatus() {
        return status;
    }

    public void setStatus(StatusPedido status) {
        this.status = status;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Agente getAgente() {
        return agente;
    }

    public void setAgente(Agente agente) {
        this.agente = agente;
    }

    public Automovel getAutomovel() {
        return automovel;
    }

    public void setAutomovel(Automovel automovel) {
        this.automovel = automovel;
    }

    public Contrato getContrato() {
        return contrato;
    }

    public void setContrato(Contrato contrato) {
        this.contrato = contrato;
    }

    @Override
    public String toString() {
        return "Pedido{" +
                "id=" + id +
                ", dataInicio=" + dataInicio +
                ", dataFim=" + dataFim +
                ", status=" + status +
                '}';
    }
}

package com.sistemaaluguel.sistemaaluguelcarros.dto;

import com.sistemaaluguel.sistemaaluguelcarros.enums.StatusPedido;

import java.time.LocalDate;

public class PedidoResponseDTO {
    private Long id;
    private LocalDate dataInicio;
    private LocalDate dataFim;
    private StatusPedido status;
    private Long clienteId;
    private String clienteNome;
    private Long automovelId;
    private String automovelModelo;
    private String automovelMarca;
    private String automovelPlaca;

    // Constructors
    public PedidoResponseDTO() {}

    // Getters and Setters
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

    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }

    public String getClienteNome() {
        return clienteNome;
    }

    public void setClienteNome(String clienteNome) {
        this.clienteNome = clienteNome;
    }

    public Long getAutomovelId() {
        return automovelId;
    }

    public void setAutomovelId(Long automovelId) {
        this.automovelId = automovelId;
    }

    public String getAutomovelModelo() {
        return automovelModelo;
    }

    public void setAutomovelModelo(String automovelModelo) {
        this.automovelModelo = automovelModelo;
    }

    public String getAutomovelMarca() {
        return automovelMarca;
    }

    public void setAutomovelMarca(String automovelMarca) {
        this.automovelMarca = automovelMarca;
    }

    public String getAutomovelPlaca() {
        return automovelPlaca;
    }

    public void setAutomovelPlaca(String automovelPlaca) {
        this.automovelPlaca = automovelPlaca;
    }
}

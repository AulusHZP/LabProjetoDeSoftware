package com.sistemaaluguel.sistemaaluguelcarros.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Min;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "contratos_credito")
public class ContratoCredito {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Valor financiado é obrigatório")
    @Positive(message = "Valor financiado deve ser positivo")
    @Column(name = "valor_financiado", nullable = false)
    private Double valorFinanciado;

    @NotNull(message = "Taxa de juros é obrigatória")
    @Positive(message = "Taxa de juros deve ser positiva")
    @Column(name = "taxa_juros", nullable = false)
    private Double taxaJuros;

    @NotNull(message = "Prazo é obrigatório")
    @Min(value = 1, message = "Prazo deve ser pelo menos 1")
    @Column(name = "prazo", nullable = false)
    private Integer prazo;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contrato_id", nullable = false)
    private Contrato contrato;

    // Construtores
    public ContratoCredito() {}

    public ContratoCredito(Double valorFinanciado, Double taxaJuros, Integer prazo, Contrato contrato) {
        this.valorFinanciado = valorFinanciado;
        this.taxaJuros = taxaJuros;
        this.prazo = prazo;
        this.contrato = contrato;
    }

    // Métodos específicos da classe ContratoCredito
    public void associarContrato() {
        if (this.contrato != null) {
            this.contrato.setContratoCredito(this);
        }
    }

    public List<Double> calcularParcelas() {
        List<Double> parcelas = new ArrayList<>();
        
        if (this.valorFinanciado != null && this.taxaJuros != null && this.prazo != null && this.prazo > 0) {
            // Cálculo de parcelas usando fórmula de financiamento
            double taxaMensal = this.taxaJuros / 100 / 12; // Convertendo taxa anual para mensal
            double valorParcela = this.valorFinanciado * (taxaMensal * Math.pow(1 + taxaMensal, this.prazo)) 
                                / (Math.pow(1 + taxaMensal, this.prazo) - 1);
            
            for (int i = 0; i < this.prazo; i++) {
                parcelas.add(valorParcela);
            }
        }
        
        return parcelas;
    }

    public Double calcularValorTotal() {
        List<Double> parcelas = calcularParcelas();
        return parcelas.stream().mapToDouble(Double::doubleValue).sum();
    }

    public Double calcularValorJuros() {
        Double valorTotal = calcularValorTotal();
        return valorTotal - this.valorFinanciado;
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getValorFinanciado() {
        return valorFinanciado;
    }

    public void setValorFinanciado(Double valorFinanciado) {
        this.valorFinanciado = valorFinanciado;
    }

    public Double getTaxaJuros() {
        return taxaJuros;
    }

    public void setTaxaJuros(Double taxaJuros) {
        this.taxaJuros = taxaJuros;
    }

    public Integer getPrazo() {
        return prazo;
    }

    public void setPrazo(Integer prazo) {
        this.prazo = prazo;
    }

    public Contrato getContrato() {
        return contrato;
    }

    public void setContrato(Contrato contrato) {
        this.contrato = contrato;
    }

    @Override
    public String toString() {
        return "ContratoCredito{" +
                "id=" + id +
                ", valorFinanciado=" + valorFinanciado +
                ", taxaJuros=" + taxaJuros +
                ", prazo=" + prazo +
                '}';
    }
}

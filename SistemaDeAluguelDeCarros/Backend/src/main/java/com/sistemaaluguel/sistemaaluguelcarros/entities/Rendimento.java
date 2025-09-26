package com.sistemaaluguel.sistemaaluguelcarros.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "rendimentos")
public class Rendimento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Entidade empregadora é obrigatória")
    @Size(max = 255, message = "Entidade empregadora deve ter no máximo 255 caracteres")
    @Column(name = "entidade_empregadora", nullable = false)
    private String entidadeEmpregadora;

    @NotNull(message = "Valor é obrigatório")
    @Positive(message = "Valor deve ser positivo")
    @Column(name = "valor", nullable = false)
    private Double valor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    // Construtores
    public Rendimento() {}

    public Rendimento(String entidadeEmpregadora, Double valor, Cliente cliente) {
        this.entidadeEmpregadora = entidadeEmpregadora;
        this.valor = valor;
        this.cliente = cliente;
    }

    // Métodos específicos da classe Rendimento
    public void adicionar() {
        if (this.cliente != null) {
            this.cliente.getRendimentos().add(this);
        }
    }

    public void remover() {
        if (this.cliente != null) {
            this.cliente.getRendimentos().remove(this);
        }
    }

    public void atualizar() {
        // Implementar lógica de atualização
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEntidadeEmpregadora() {
        return entidadeEmpregadora;
    }

    public void setEntidadeEmpregadora(String entidadeEmpregadora) {
        this.entidadeEmpregadora = entidadeEmpregadora;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    @Override
    public String toString() {
        return "Rendimento{" +
                "id=" + id +
                ", entidadeEmpregadora='" + entidadeEmpregadora + '\'' +
                ", valor=" + valor +
                '}';
    }
}

package com.sistemaaluguel.sistemaaluguelcarros.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max;

@Entity
@Table(name = "automoveis")
public class Automovel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Matrícula é obrigatória")
    @Size(max = 50, message = "Matrícula deve ter no máximo 50 caracteres")
    @Column(name = "matricula", nullable = false, unique = true)
    private String matricula;

    @NotNull(message = "Ano é obrigatório")
    @Min(value = 1900, message = "Ano deve ser maior que 1900")
    @Max(value = 2024, message = "Ano deve ser menor ou igual a 2024")
    @Column(name = "ano", nullable = false)
    private Integer ano;

    @NotBlank(message = "Marca é obrigatória")
    @Size(max = 100, message = "Marca deve ter no máximo 100 caracteres")
    @Column(name = "marca", nullable = false)
    private String marca;

    @NotBlank(message = "Modelo é obrigatório")
    @Size(max = 100, message = "Modelo deve ter no máximo 100 caracteres")
    @Column(name = "modelo", nullable = false)
    private String modelo;

    @NotBlank(message = "Placa é obrigatória")
    @Pattern(regexp = "[A-Z]{3}[0-9]{4}|[A-Z]{3}[0-9][A-Z][0-9]{2}", 
             message = "Placa deve seguir o formato ABC1234 ou ABC1D23")
    @Column(name = "placa", nullable = false, unique = true)
    private String placa;

    @NotBlank(message = "Proprietário é obrigatório")
    @Size(max = 255, message = "Proprietário deve ter no máximo 255 caracteres")
    @Column(name = "proprietario", nullable = false)
    private String proprietario;

    @OneToOne(mappedBy = "automovel", fetch = FetchType.LAZY)
    @JsonBackReference
    private Pedido pedido;

    // Construtores
    public Automovel() {}

    public Automovel(String matricula, Integer ano, String marca, String modelo, String placa, String proprietario) {
        this.matricula = matricula;
        this.ano = ano;
        this.marca = marca;
        this.modelo = modelo;
        this.placa = placa;
        this.proprietario = proprietario;
    }

    // Métodos específicos da classe Automovel
    public void cadastrar() {
        // Implementar lógica de cadastro
    }

    public void atualizar() {
        // Implementar lógica de atualização
    }

    public Automovel consultar() {
        return this;
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public Integer getAno() {
        return ano;
    }

    public void setAno(Integer ano) {
        this.ano = ano;
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

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getProprietario() {
        return proprietario;
    }

    public void setProprietario(String proprietario) {
        this.proprietario = proprietario;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    @Override
    public String toString() {
        return "Automovel{" +
                "id=" + id +
                ", matricula='" + matricula + '\'' +
                ", ano=" + ano +
                ", marca='" + marca + '\'' +
                ", modelo='" + modelo + '\'' +
                ", placa='" + placa + '\'' +
                ", proprietario='" + proprietario + '\'' +
                '}';
    }
}

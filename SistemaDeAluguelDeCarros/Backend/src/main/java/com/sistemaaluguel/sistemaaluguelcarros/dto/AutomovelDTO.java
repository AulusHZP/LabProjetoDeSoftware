package com.sistemaaluguel.sistemaaluguelcarros.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max;

public class AutomovelDTO {
    
    private Long id;
    
    @NotBlank(message = "Matrícula é obrigatória")
    @Size(max = 50, message = "Matrícula deve ter no máximo 50 caracteres")
    private String matricula;
    
    @NotNull(message = "Ano é obrigatório")
    @Min(value = 1900, message = "Ano deve ser maior que 1900")
    @Max(value = 2024, message = "Ano deve ser menor ou igual a 2024")
    private Integer ano;
    
    @NotBlank(message = "Marca é obrigatória")
    @Size(max = 100, message = "Marca deve ter no máximo 100 caracteres")
    private String marca;
    
    @NotBlank(message = "Modelo é obrigatório")
    @Size(max = 100, message = "Modelo deve ter no máximo 100 caracteres")
    private String modelo;
    
    @NotBlank(message = "Placa é obrigatória")
    @Pattern(regexp = "[A-Z]{3}[0-9]{4}|[A-Z]{3}[0-9][A-Z][0-9]{2}", 
             message = "Placa deve seguir o formato ABC1234 ou ABC1D23")
    private String placa;
    
    @NotBlank(message = "Proprietário é obrigatório")
    @Size(max = 255, message = "Proprietário deve ter no máximo 255 caracteres")
    private String proprietario;

    // Construtores
    public AutomovelDTO() {}

    public AutomovelDTO(String matricula, Integer ano, String marca, String modelo, String placa, String proprietario) {
        this.matricula = matricula;
        this.ano = ano;
        this.marca = marca;
        this.modelo = modelo;
        this.placa = placa;
        this.proprietario = proprietario;
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
}

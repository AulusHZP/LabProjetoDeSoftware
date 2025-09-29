package com.sistemaaluguel.sistemaaluguelcarros.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RegisterDTO {
    public String nome;
    public String email;
    public String senha;
    public String tipoUsuario; // "cliente" | "agente" | "administrador"
    // Cliente
    public String rg;
    public String cpf;
    public String endereco;
    public String profissao;
    // Agente (opcional para futuro)
    public String cnpj;
    public String tipoAgente; // EMPRESA | BANCO
}



package com.sistemaaluguel.sistemaaluguelcarros.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.sistemaaluguel.sistemaaluguelcarros.enums.TipoUsuario;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "clientes")
@DiscriminatorValue("CLIENTE")
public class Cliente extends Usuario {

    @NotBlank(message = "RG é obrigatório")
    @Pattern(regexp = "\\d{7,9}", message = "RG deve ter entre 7 e 9 dígitos")
    @Column(name = "rg", nullable = false, unique = true)
    private String rg;

    @NotBlank(message = "CPF é obrigatório")
    @Pattern(regexp = "\\d{11}", message = "CPF deve ter 11 dígitos")
    @Column(name = "cpf", nullable = false, unique = true)
    private String cpf;

    @NotBlank(message = "Endereço é obrigatório")
    @Size(max = 255, message = "Endereço deve ter no máximo 255 caracteres")
    @Column(name = "endereco", nullable = false)
    private String endereco;

    @NotBlank(message = "Profissão é obrigatória")
    @Size(max = 100, message = "Profissão deve ter no máximo 100 caracteres")
    @Column(name = "profissao", nullable = false)
    private String profissao;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Rendimento> rendimentos = new ArrayList<>();

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Pedido> pedidos = new ArrayList<>();

    // Construtores
    public Cliente() {
        super();
        this.setTipo(TipoUsuario.CLIENTE);
    }

    public Cliente(String nome, String email, String senha, String rg, String cpf, String endereco, String profissao) {
        super(nome, email, senha, TipoUsuario.CLIENTE);
        this.rg = rg;
        this.cpf = cpf;
        this.endereco = endereco;
        this.profissao = profissao;
    }

    // Implementação dos métodos abstratos
    @Override
    public boolean autenticar() {
        return this.getEmail() != null && this.getSenha() != null;
    }

    @Override
    public void atualizarPerfil() {
        // Implementar lógica de atualização de perfil específica para cliente
    }

    // Métodos específicos da classe Cliente
    public Pedido criarPedido() {
        Pedido pedido = new Pedido();
        pedido.setCliente(this);
        this.pedidos.add(pedido);
        return pedido;
    }

    public boolean modificarPedido(Pedido pedido) {
        if (this.pedidos.contains(pedido)) {
            return true;
        }
        return false;
    }

    public Pedido consultarPedido(Long id) {
        return this.pedidos.stream()
                .filter(pedido -> pedido.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public boolean cancelarPedido(Pedido pedido) {
        if (this.pedidos.contains(pedido)) {
            pedido.cancelar();
            return true;
        }
        return false;
    }

    // Getters e Setters
    public String getRg() {
        return rg;
    }

    public void setRg(String rg) {
        this.rg = rg;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getProfissao() {
        return profissao;
    }

    public void setProfissao(String profissao) {
        this.profissao = profissao;
    }

    public List<Rendimento> getRendimentos() {
        return rendimentos;
    }

    public void setRendimentos(List<Rendimento> rendimentos) {
        this.rendimentos = rendimentos;
    }

    public List<Pedido> getPedidos() {
        return pedidos;
    }

    public void setPedidos(List<Pedido> pedidos) {
        this.pedidos = pedidos;
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "id=" + getId() +
                ", nome='" + getNome() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", rg='" + rg + '\'' +
                ", cpf='" + cpf + '\'' +
                ", endereco='" + endereco + '\'' +
                ", profissao='" + profissao + '\'' +
                '}';
    }
}

package com.sistemaaluguel.sistemaaluguelcarros.entities;

import com.sistemaaluguel.sistemaaluguelcarros.enums.TipoAgente;
import com.sistemaaluguel.sistemaaluguelcarros.enums.TipoUsuario;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "agentes")
@DiscriminatorValue("AGENTE")
public class Agente extends Usuario {

    @NotBlank(message = "CNPJ é obrigatório")
    @Pattern(regexp = "\\d{14}", message = "CNPJ deve ter 14 dígitos")
    @Column(name = "cnpj", nullable = false, unique = true)
    private String cnpj;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_agente", nullable = false)
    private TipoAgente tipoAgente;

    @OneToMany(mappedBy = "agente", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Pedido> pedidosAvaliados = new ArrayList<>();

    @OneToMany(mappedBy = "agente", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Contrato> contratosFornecidos = new ArrayList<>();

    // Construtores
    public Agente() {
        super();
        this.setTipo(TipoUsuario.AGENTE);
    }

    public Agente(String nome, String email, String senha, String cnpj, TipoAgente tipoAgente) {
        super(nome, email, senha, TipoUsuario.AGENTE);
        this.cnpj = cnpj;
        this.tipoAgente = tipoAgente;
    }

    // Implementação dos métodos abstratos
    @Override
    public boolean autenticar() {
        return this.getEmail() != null && this.getSenha() != null;
    }

    @Override
    public void atualizarPerfil() {
        // Implementar lógica de atualização de perfil específica para agente
    }

    // Métodos específicos da classe Agente
    public boolean avaliarPedido(Pedido pedido) {
        if (pedido != null) {
            pedido.setAgente(this);
            this.pedidosAvaliados.add(pedido);
            pedido.avaliar();
            return true;
        }
        return false;
    }

    public boolean modificarPedido(Pedido pedido) {
        if (this.pedidosAvaliados.contains(pedido)) {
            return true;
        }
        return false;
    }

    // Getters e Setters
    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public TipoAgente getTipoAgente() {
        return tipoAgente;
    }

    public void setTipoAgente(TipoAgente tipoAgente) {
        this.tipoAgente = tipoAgente;
    }

    public List<Pedido> getPedidosAvaliados() {
        return pedidosAvaliados;
    }

    public void setPedidosAvaliados(List<Pedido> pedidosAvaliados) {
        this.pedidosAvaliados = pedidosAvaliados;
    }

    public List<Contrato> getContratosFornecidos() {
        return contratosFornecidos;
    }

    public void setContratosFornecidos(List<Contrato> contratosFornecidos) {
        this.contratosFornecidos = contratosFornecidos;
    }

    @Override
    public String toString() {
        return "Agente{" +
                "id=" + getId() +
                ", nome='" + getNome() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", cnpj='" + cnpj + '\'' +
                ", tipoAgente=" + tipoAgente +
                '}';
    }
}

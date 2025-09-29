package com.sistemaaluguel.sistemaaluguelcarros.entities;

import com.sistemaaluguel.sistemaaluguelcarros.enums.TipoUsuario;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("ADMINISTRADOR")
public class Administrador extends Usuario {

    public Administrador() {
        super();
        setTipo(TipoUsuario.ADMINISTRADOR);
    }

    public Administrador(String nome, String email, String senha) {
        super(nome, email, senha, TipoUsuario.ADMINISTRADOR);
    }

    @Override
    public boolean autenticar() {
        return true;
    }

    @Override
    public void atualizarPerfil() {
        // Implementação específica se necessário
    }
}



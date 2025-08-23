package org.example;

import java.util.List;

public abstract class Usuario {
    String nome;
    String login;
    String senha;

    public boolean autenticar(String senha) {
        return this.senha != null && this.senha.equals(senha);
    }
}

package org.example;

import java.util.ArrayList;
import java.util.List;

public class SistemaMatriculas {
    List<Usuario> usuarios = new ArrayList<>();
    List<Curso> cursos = new ArrayList<>();

    public Usuario login(String login, String senha) {
        for (Usuario u : usuarios) {
            if (u.login.equals(login) && u.autenticar(senha)) {
                return u;
            }
        }
        return null;
    }
}

package org.example;

import java.util.ArrayList;
import java.util.List;

public class Professor extends Usuario {
    private List<Disciplina> disciplinasMinistradas;

    public Professor(String nome, String login, String senha) {
        super(nome, login, senha);
        this.disciplinasMinistradas = new ArrayList<>();
    }

    public boolean adicionarDisciplina(Disciplina disciplina) {
        if (!disciplinasMinistradas.contains(disciplina)) {
            disciplinasMinistradas.add(disciplina);
        }
        return false;
    }

    public boolean removerDisciplina(Disciplina disciplina) {
        return disciplinasMinistradas.remove(disciplina);
    }

    public List<Disciplina> getDisciplinasMinistradas() {
        return disciplinasMinistradas;
    }
}


package org.example;

import java.util.ArrayList;
import java.util.List;

public class Disciplina {
    String codigo;
    String nome;
    int creditos;
    Professor professor;
    List<Aluno> alunos = new ArrayList<>();
    int maxAlunos = 60;
    boolean ativa;
    boolean obrigatoria;

    public boolean adicionarAluno(Aluno a) {
        if (alunos.size() < maxAlunos && !alunos.contains(a)) {
            return alunos.add(a);
        }
        return false;
    }

    public boolean removerAluno(Aluno a) {
        return alunos.remove(a);
    }

    public void verificarAtivacao() {
        this.ativa = alunos.size() >= 3;
        if (!ativa) {
            alunos.clear();
        }
    }
}


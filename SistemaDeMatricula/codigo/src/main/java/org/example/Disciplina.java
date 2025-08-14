package org.example;

import java.util.List;

public class Disciplina {
    private String codigo;
    private String nome;
    private int creditos;
    private Professor professor;
    private List<Aluno> alunos;
    private int maxAlunos = 60;
    private boolean ativa;

    public boolean adicionarAluno(Aluno a) {
        // TODO: implementar regra para adicionar aluno
        return false;
    }

    public boolean removerAluno(Aluno a) {
        // TODO: implementar regra para remover aluno
        return false;
    }

    public void verificarAtivacao() {
        // TODO: implementar verificação de ativação da disciplina
    }
}


package org.example;

import java.util.ArrayList;
import java.util.List;

public class Secretaria extends Usuario {
    public void criarCurriculo() {
        System.out.println("Currículo do semestre criado.");
    }

    public void efetuarMatricula(Aluno a, Disciplina d) {
        a.matricular(d);
    }

    public void cancelarMatricula(Aluno a, Disciplina d) {
        a.cancelarMatricula(d);
    }

    public void encerrarPeriodo() {
        System.out.println("Encerrando período de matrícula...");
    }

    public boolean adicionarAluno(Aluno a, Disciplina d) {
        return d.adicionarAluno(a);
    }

    public boolean removerAluno(Aluno a, Disciplina d) {
        return d.removerAluno(a);
    }
}

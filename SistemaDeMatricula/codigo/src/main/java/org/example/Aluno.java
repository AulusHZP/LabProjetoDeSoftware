package org.example;

import java.util.ArrayList;
import java.util.List;

public class Aluno extends Usuario {
    String matricula;
    List<Disciplina> disciplinasObrigatorias = new ArrayList<>();
    List<Disciplina> disciplinasOptativas = new ArrayList<>();

    public void matricular(Disciplina d) {
        if (d != null && !disciplinasObrigatorias.contains(d) && !disciplinasOptativas.contains(d)) {
            if (d.obrigatoria) {
                if (disciplinasObrigatorias.size() < 4) {
                    disciplinasObrigatorias.add(d);
                    d.adicionarAluno(this);
                } else {
                    System.out.println("Limite de 4 obrigatórias já atingido.");
                }
            } else {
                if (disciplinasOptativas.size() < 2) {
                    disciplinasOptativas.add(d);
                    d.adicionarAluno(this);
                } else {
                    System.out.println("Limite de 2 optativas já atingido.");
                }
            }
        }
    }

    public void cancelarMatricula(Disciplina d) {
        if (disciplinasObrigatorias.remove(d) || disciplinasOptativas.remove(d)) {
            d.removerAluno(this);
        }
    }
}

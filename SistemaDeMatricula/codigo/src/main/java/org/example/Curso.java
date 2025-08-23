package org.example;

import java.util.ArrayList;
import java.util.List;

public class Curso {
    String nome;
    int creditos;
    List<Disciplina> disciplinas = new ArrayList<>();

    public void adicionarDisciplina(Disciplina d) {
        if (d != null && !disciplinas.contains(d)) {
            disciplinas.add(d);
        }
    }
}

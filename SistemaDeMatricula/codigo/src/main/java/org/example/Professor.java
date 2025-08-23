package org.example;

import java.util.ArrayList;
import java.util.List;

public class Professor extends Usuario {
    String matricula;
    List<Disciplina> disciplinasMinistradas = new ArrayList<>();

    public void adicionarDisciplina(Disciplina d) {
        if (d != null && !disciplinasMinistradas.contains(d)) {
            disciplinasMinistradas.add(d);
            d.professor = this;
        }
    }
}

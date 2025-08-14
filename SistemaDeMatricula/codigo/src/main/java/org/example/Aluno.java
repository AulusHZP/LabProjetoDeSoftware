package org.example;

import java.util.ArrayList;
import java.util.List;

public class Aluno extends Usuario {
    private String matricula;
    private List<Disciplina> disciplinasObrigatorias;
    private List<Disciplina> disciplinasOptativas;

    public static final int MAX_OBRIGATORIAS = 4;
    public static final int MAX_OPTATIVAS = 2;

    public Aluno(String nome, String login, String senha, String matricula) {
        super(nome, login, senha);
        this.matricula = matricula;
        this.disciplinasObrigatorias = new ArrayList<>();
        this.disciplinasOptativas = new ArrayList<>();
    }

    public boolean matricular(Disciplina disciplina, boolean obrigatorias) {
        if (obrigatorias) {
            if (disciplinasObrigatorias.size() >= MAX_OBRIGATORIAS) {
                System.out.println("Limite de disciplinas obrigatorias atingido!");
                return false;
            }
            return disciplinasObrigatorias.add(disciplina);
        }
        else {
            if (disciplinasOptativas.size() >= MAX_OPTATIVAS) {
                System.out.println("Limite de disciplinas optativas atingido!");
                return false;
            }
            return disciplinasOptativas.add(disciplina);
        }
    }

    public boolean cancelarMatricula(Disciplina disciplina) {
        if(disciplinasObrigatorias.remove(disciplina)) {
            return true;
        }
        return disciplinasOptativas.remove(disciplina);
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public List<Disciplina> getDisciplinasObrigatorias() {
        return disciplinasObrigatorias;
    }

    public List<Disciplina> getDisciplinasOptativas() {
        return disciplinasOptativas;
    }

}

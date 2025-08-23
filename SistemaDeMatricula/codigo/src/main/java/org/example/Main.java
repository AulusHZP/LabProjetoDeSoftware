package org.example;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        System.out.println("Sistema de Matrículas iniciado...");

        // Exemplo simples
        Aluno aluno = new Aluno();
        aluno.matricula = "2025001";

        Disciplina d1 = new Disciplina();
        d1.codigo = "SO101";
        d1.nome = "Sistemas Operacionais";
        d1.obrigatoria = true;

        aluno.matricular(d1);

        System.out.println("Aluno " + aluno.matricula + " matriculado em " + d1.nome);
    }
}

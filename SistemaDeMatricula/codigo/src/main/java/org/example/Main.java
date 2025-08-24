package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);

    // Seus “dados em memória”
    private static final SistemaMatriculas sistemaMatriculas = new SistemaMatriculas();
    private static final List<Aluno> alunos = new ArrayList<>();
    private static final List<Disciplina> disciplinas = new ArrayList<>();

    public static void main(String[] args) {
        int opcao;
        do {
            mostrarMenu();
            while (!scanner.hasNextInt()) {
                System.out.print("Digite um número válido: ");
                scanner.next();
            }
            opcao = scanner.nextInt();
            scanner.nextLine(); // limpa \n

            switch (opcao) {
                case 1 -> cadastrarAluno();
                case 2 -> cadastrarDisciplina();
                case 3 -> matricularAlunoEmDisciplina();
                case 4 -> cancelarMatricula();
                case 5 -> listarAlunosEMatriculas();
                case 0 -> System.out.println("Encerrando o sistema...");
                default -> System.out.println("Opção inválida. Tente novamente.");
            }
        } while (opcao != 0);
    }

    private static void mostrarMenu() {
        System.out.println("\n=== Sistema de Matrículas (CLI) ===");
        System.out.println("1 - Cadastrar Aluno");
        System.out.println("2 - Cadastrar Disciplina");
        System.out.println("3 - Matricular Aluno em Disciplina");
        System.out.println("4 - Cancelar Matrícula");
        System.out.println("5 - Listar Alunos e Matrículas");
        System.out.println("0 - Sair");
        System.out.print("Escolha uma opção: ");
    }

    private static void cadastrarAluno() {
        System.out.print("Nome do aluno: ");
        String nome = scanner.nextLine();

        System.out.print("Matrícula do aluno: ");
        String matricula = scanner.nextLine();

        Aluno a = new Aluno();
        a.nome = nome;          // herdado de Usuario
        a.matricula = matricula;

        alunos.add(a);
        System.out.println("Aluno cadastrado!");
    }

    private static void cadastrarDisciplina() {
        System.out.print("Nome da disciplina: ");
        String nome = scanner.nextLine();

        System.out.print("Código da disciplina: ");
        String codigo = scanner.nextLine();

        System.out.print("Horas (inteiro): ");
        int creditos = lerInt();

        System.out.print("É obrigatória? (s/n): ");
        boolean obrigatoria = lerSimNao();

        Disciplina d = new Disciplina();
        d.nome = nome;
        d.codigo = codigo;
        d.creditos = creditos;
        d.obrigatoria = obrigatoria;

        disciplinas.add(d);
        System.out.println("Disciplina cadastrada!");
    }

    private static void matricularAlunoEmDisciplina() {
        if (alunos.isEmpty() || disciplinas.isEmpty()) {
            System.out.println("Cadastre ao menos 1 aluno e 1 disciplina antes.");
            return;
        }

        Aluno aluno = escolherAluno();
        Disciplina disc = escolherDisciplina();
        if (aluno == null || disc == null) return;

        // >>> CORREÇÃO: matrícula é no Aluno (Aluno.matricular), não no SistemaMatriculas
        aluno.matricular(disc);
        System.out.println("Operação de matrícula executada.");
    }

    private static void cancelarMatricula() {
        if (alunos.isEmpty()) {
            System.out.println("Nenhum aluno cadastrado.");
            return;
        }
        Aluno aluno = escolherAluno();
        if (aluno == null) return;

        // Mostrar disciplinas do aluno (obrigatórias e optativas)
        List<Disciplina> todas = new ArrayList<>();
        todas.addAll(aluno.disciplinasObrigatorias);
        todas.addAll(aluno.disciplinasOptativas);

        if (todas.isEmpty()) {
            System.out.println("Este aluno não possui disciplinas matriculadas.");
            return;
        }

        System.out.println("Escolha a disciplina para cancelar:");
        for (int i = 0; i < todas.size(); i++) {
            Disciplina d = todas.get(i);
            System.out.printf("%d - %s (%s) [%s]%n",
                    i + 1, d.nome, d.codigo, d.obrigatoria ? "Obrigatória" : "Optativa");
        }
        int idx = lerIntIndex(todas.size());
        if (idx == -1) return;

        Disciplina d = todas.get(idx);
        aluno.cancelarMatricula(d);
        System.out.println("Matrícula cancelada.");
    }

    private static void listarAlunosEMatriculas() {
        if (alunos.isEmpty()) {
            System.out.println("Nenhum aluno cadastrado.");
            return;
        }
        for (Aluno a : alunos) {
            System.out.printf("%nAluno: %s (matrícula %s)%n", a.nome, a.matricula);

            // >>> CORREÇÃO: não existe a.lista "disciplinas" — usar as duas listas do Aluno
            if (a.disciplinasObrigatorias.isEmpty() && a.disciplinasOptativas.isEmpty()) {
                System.out.println("   Sem matrículas.");
            } else {
                if (!a.disciplinasObrigatorias.isEmpty()) {
                    System.out.println("   Obrigatórias:");
                    for (Disciplina d : a.disciplinasObrigatorias) {
                        System.out.printf("     - %s (%s)%n", d.nome, d.codigo);
                    }
                }
                if (!a.disciplinasOptativas.isEmpty()) {
                    System.out.println("   Optativas:");
                    for (Disciplina d : a.disciplinasOptativas) {
                        System.out.printf("     - %s (%s)%n", d.nome, d.codigo);
                    }
                }
            }
        }
    }

    // ==== Utilitários de entrada ====

    private static int lerInt() {
        while (!scanner.hasNextInt()) {
            System.out.print("Digite um número inteiro válido: ");
            scanner.next();
        }
        int v = scanner.nextInt();
        scanner.nextLine();
        return v;
    }

    private static boolean lerSimNao() {
        String s = scanner.nextLine().trim().toLowerCase();
        while (!s.equals("s") && !s.equals("n")) {
            System.out.print("Digite 's' ou 'n': ");
            s = scanner.nextLine().trim().toLowerCase();
        }
        return s.equals("s");
    }

    private static int lerIntIndex(int maxSize) {
        if (maxSize <= 0) return -1;
        while (!scanner.hasNextInt()) {
            System.out.print("Digite um número válido: ");
            scanner.next();
        }
        int pos = scanner.nextInt();
        scanner.nextLine();
        if (pos < 1 || pos > maxSize) {
            System.out.println("Índice inválido.");
            return -1;
        }
        return pos - 1;
    }

    private static Aluno escolherAluno() {
        System.out.println("Selecione o aluno:");
        for (int i = 0; i < alunos.size(); i++) {
            System.out.printf("%d - %s (%s)%n", i + 1, alunos.get(i).nome, alunos.get(i).matricula);
        }
        int idx = lerIntIndex(alunos.size());
        return idx == -1 ? null : alunos.get(idx);
    }

    private static Disciplina escolherDisciplina() {
        System.out.println("Selecione a disciplina:");
        for (int i = 0; i < disciplinas.size(); i++) {
            Disciplina d = disciplinas.get(i);
            System.out.printf("%d - %s (%s) [%s]%n",
                    i + 1, d.nome, d.codigo, d.obrigatoria ? "Obrigatória" : "Optativa");
        }
        int idx = lerIntIndex(disciplinas.size());
        return idx == -1 ? null : disciplinas.get(idx);
    }
}

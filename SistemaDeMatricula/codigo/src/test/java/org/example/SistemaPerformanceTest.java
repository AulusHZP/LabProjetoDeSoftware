package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.List;

@DisplayName("Testes de Performance do Sistema de Matrículas")
class SistemaPerformanceTest {

    private List<Aluno> alunos;
    private List<Disciplina> disciplinas;
    private Professor professor;
    private Secretaria secretaria;

    @BeforeEach
    void setUp() {
        alunos = new ArrayList<>();
        disciplinas = new ArrayList<>();
        professor = new Professor();
        professor.nome = "Dr. Performance";
        professor.login = "perf.test";
        professor.senha = "senha123";

        secretaria = new Secretaria();

        for (int i = 0; i < 100; i++) {
            Aluno a = new Aluno();
            a.nome = "Aluno " + i;
            a.login = "aluno" + i;
            a.senha = "senha" + i;
            a.matricula = "2023" + String.format("%03d", i);
            alunos.add(a);

            Disciplina d = new Disciplina();
            d.obrigatoria = (i % 2 == 0);
            disciplinas.add(d);
        }
    }

    @Test
    @DisplayName("Múltiplas matrículas executam rápido")
    void multiplasMatriculas() {
        long start = System.currentTimeMillis();
        for (int i = 0; i < 100; i++) {
            Aluno a = alunos.get(i);
            Disciplina d = disciplinas.get(i % 10);
            a.matricular(d);
            secretaria.efetuarMatricula(a, d);
        }
        long dur = System.currentTimeMillis() - start;
        assertTrue(dur < 2000, "Operação demorou: " + dur + "ms");
    }

    @Test
    @DisplayName("Múltiplos cancelamentos executam rápido")
    void multiplosCancelamentos() {
        for (int i = 0; i < 50; i++) {
            Aluno a = alunos.get(i);
            Disciplina d = disciplinas.get(i % 5);
            d.obrigatoria = true;
            a.matricular(d);
            secretaria.efetuarMatricula(a, d);
        }
        long start = System.currentTimeMillis();
        for (int i = 0; i < 50; i++) {
            Aluno a = alunos.get(i);
            Disciplina d = disciplinas.get(i % 5);
            a.cancelarMatricula(d);
            secretaria.cancelarMatricula(a, d);
        }
        long dur = System.currentTimeMillis() - start;
        assertTrue(dur < 2000, "Operação demorou: " + dur + "ms");
    }

    @Test
    @DisplayName("Professor ministra múltiplas disciplinas")
    void professorMinistraMultiplas() {
        long start = System.currentTimeMillis();
        for (int i = 0; i < 50; i++) {
            professor.adicionarDisciplina(disciplinas.get(i));
        }
        long dur = System.currentTimeMillis() - start;
        assertTrue(dur < 2000, "Operação demorou: " + dur + "ms");
        assertEquals(50, professor.disciplinasMinistradas.size());
    }

    @Test
    @DisplayName("Autenticação em lote")
    void autenticacaoEmLote() {
        long start = System.currentTimeMillis();
        for (int i = 0; i < 100; i++) {
            Aluno a = alunos.get(i);
            assertTrue(a.autenticar("senha" + i));
            assertFalse(a.autenticar("senhaErrada"));
        }
        long dur = System.currentTimeMillis() - start;
        assertTrue(dur < 2000, "Operação demorou: " + dur + "ms");
    }
}


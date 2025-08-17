package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Testes de performance para o sistema de matrículas
 */
@DisplayName("Testes de Performance do Sistema de Matrículas")
class SistemaPerformanceTest {

    private SistemaMatriculas sistema;
    private List<Aluno> alunos;
    private List<Disciplina> disciplinas;
    private Professor professor;

    @BeforeEach
    void setUp() {
        sistema = new SistemaMatriculas();
        alunos = new ArrayList<>();
        disciplinas = new ArrayList<>();
        professor = new Professor("Dr. Performance", "perf.test", "senha123");
        
        // Criando dados de teste
        for (int i = 0; i < 100; i++) {
            alunos.add(new Aluno("Aluno " + i, "aluno" + i, "senha" + i, "2023" + String.format("%03d", i)));
            disciplinas.add(new Disciplina());
        }
    }

    @Test
    @DisplayName("Deve lidar com múltiplas matrículas de alunos")
    void deveLidarComMultiplasMatriculasDeAlunos() {
        long startTime = System.currentTimeMillis();
        
        // Simula 100 alunos se matriculando em disciplinas
        for (int i = 0; i < 100; i++) {
            Aluno aluno = alunos.get(i);
            Disciplina disciplina = disciplinas.get(i % 10); // 10 disciplinas diferentes
            
            assertTrue(aluno.matricular(disciplina, i % 2 == 0)); // Alterna entre obrigatória e optativa
            sistema.efetuarMatricula(aluno, disciplina);
        }
        
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        
        // Verifica se a operação foi executada em tempo razoável (menos de 1 segundo)
        assertTrue(duration < 1000, "Operação demorou muito: " + duration + "ms");
        
        // Verifica se as matrículas foram processadas
        for (int i = 0; i < 100; i++) {
            Aluno aluno = alunos.get(i);
            Disciplina disciplina = disciplinas.get(i % 10);
            
            if (i % 2 == 0) {
                assertTrue(aluno.getDisciplinasObrigatorias().contains(disciplina));
            } else {
                assertTrue(aluno.getDisciplinasOptativas().contains(disciplina));
            }
        }
    }

    @Test
    @DisplayName("Deve lidar com múltiplos cancelamentos de matrícula")
    void deveLidarComMultiplosCancelamentosDeMatricula() {
        // Primeiro, matricula todos os alunos
        for (int i = 0; i < 50; i++) {
            Aluno aluno = alunos.get(i);
            Disciplina disciplina = disciplinas.get(i % 5);
            aluno.matricular(disciplina, true);
            sistema.efetuarMatricula(aluno, disciplina);
        }
        
        long startTime = System.currentTimeMillis();
        
        // Cancela todas as matrículas
        for (int i = 0; i < 50; i++) {
            Aluno aluno = alunos.get(i);
            Disciplina disciplina = disciplinas.get(i % 5);
            
            assertTrue(aluno.cancelarMatricula(disciplina));
            sistema.cancelarMatricula(aluno, disciplina);
        }
        
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        
        // Verifica se a operação foi executada em tempo razoável
        assertTrue(duration < 1000, "Operação demorou muito: " + duration + "ms");
        
        // Verifica se todas as matrículas foram canceladas
        for (int i = 0; i < 50; i++) {
            Aluno aluno = alunos.get(i);
            Disciplina disciplina = disciplinas.get(i % 5);
            assertFalse(aluno.getDisciplinasObrigatorias().contains(disciplina));
        }
    }

    @Test
    @DisplayName("Deve lidar com professor ministrando múltiplas disciplinas")
    void deveLidarComProfessorMinistrandoMultiplasDisciplinas() {
        long startTime = System.currentTimeMillis();
        
        // Professor ministra 50 disciplinas
        for (int i = 0; i < 50; i++) {
            assertTrue(professor.adicionarDisciplina(disciplinas.get(i)));
        }
        
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        
        // Verifica se a operação foi executada em tempo razoável
        assertTrue(duration < 1000, "Operação demorou muito: " + duration + "ms");
        
        // Verifica se todas as disciplinas foram adicionadas
        assertEquals(50, professor.getDisciplinasMinistradas().size());
        
        // Remove todas as disciplinas
        startTime = System.currentTimeMillis();
        for (int i = 0; i < 50; i++) {
            assertTrue(professor.removerDisciplina(disciplinas.get(i)));
        }
        endTime = System.currentTimeMillis();
        duration = endTime - startTime;
        
        assertTrue(duration < 1000, "Operação de remoção demorou muito: " + duration + "ms");
        assertEquals(0, professor.getDisciplinasMinistradas().size());
    }

    @Test
    @DisplayName("Deve lidar com múltiplas autenticações")
    void deveLidarComMultiplasAutenticacoes() {
        long startTime = System.currentTimeMillis();
        
        // Testa autenticação de 100 alunos
        for (int i = 0; i < 100; i++) {
            Aluno aluno = alunos.get(i);
            assertTrue(aluno.autenticar("senha" + i));
            assertFalse(aluno.autenticar("senhaErrada"));
        }
        
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        
        // Verifica se a operação foi executada em tempo razoável
        assertTrue(duration < 1000, "Operação demorou muito: " + duration + "ms");
    }

    @Test
    @DisplayName("Deve lidar com operações concorrentes de matrícula")
    void deveLidarComOperacoesConcorrentesDeMatricula() {
        long startTime = System.currentTimeMillis();
        
        // Simula operações concorrentes (sequenciais neste teste)
        for (int i = 0; i < 100; i++) {
            Aluno aluno = alunos.get(i);
            Disciplina disciplina = disciplinas.get(i % 20);
            
            // Matricula
            if (aluno.matricular(disciplina, i % 2 == 0)) {
                sistema.efetuarMatricula(aluno, disciplina);
            }
            
            // Cancela algumas matrículas
            if (i % 3 == 0 && aluno.getDisciplinasObrigatorias().contains(disciplina)) {
                aluno.cancelarMatricula(disciplina);
                sistema.cancelarMatricula(aluno, disciplina);
            }
        }
        
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        
        // Verifica se a operação foi executada em tempo razoável
        assertTrue(duration < 2000, "Operação demorou muito: " + duration + "ms");
    }

    @Test
    @DisplayName("Deve manter performance com múltiplos encerramentos de período")
    void deveManterPerformanceComMultiplosEncerramentosDePeriodo() {
        long startTime = System.currentTimeMillis();
        
        // Simula múltiplos encerramentos de período
        for (int i = 0; i < 50; i++) {
            sistema.encerrarPeriodo();
        }
        
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        
        // Verifica se a operação foi executada em tempo razoável
        assertTrue(duration < 1000, "Operação demorou muito: " + duration + "ms");
    }
}

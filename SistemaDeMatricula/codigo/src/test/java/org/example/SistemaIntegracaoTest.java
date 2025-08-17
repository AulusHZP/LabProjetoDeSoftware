package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes de integração para o sistema de matrículas
 */
@DisplayName("Testes de Integração do Sistema de Matrículas")
class SistemaIntegracaoTest {

    private SistemaMatriculas sistema;
    private Aluno aluno1;
    private Aluno aluno2;
    private Professor professor;
    private Disciplina disciplina1;
    private Disciplina disciplina2;
    private Curso curso;

    @BeforeEach
    void setUp() {
        sistema = new SistemaMatriculas();
        aluno1 = new Aluno("João Silva", "joao.silva", "senha123", "2023001");
        aluno2 = new Aluno("Maria Santos", "maria.santos", "senha456", "2023002");
        professor = new Professor("Dr. Carlos Oliveira", "carlos.oliveira", "senha789");
        disciplina1 = new Disciplina();
        disciplina2 = new Disciplina();
        curso = new Curso();
    }

    @Test
    @DisplayName("Deve integrar todas as classes do sistema")
    void deveIntegrarTodasAsClassesDoSistema() {
        // Testa a criação e interação entre todas as classes
        assertNotNull(sistema);
        assertNotNull(aluno1);
        assertNotNull(aluno2);
        assertNotNull(professor);
        assertNotNull(disciplina1);
        assertNotNull(disciplina2);
        assertNotNull(curso);
    }

    @Test
    @DisplayName("Deve permitir fluxo completo de matrícula")
    void devePermitirFluxoCompletoDeMatricula() {
        // Simula um fluxo completo de matrícula
        assertDoesNotThrow(() -> {
            // 1. Aluno se matricula em disciplinas
            assertTrue(aluno1.matricular(disciplina1, true));
            assertTrue(aluno1.matricular(disciplina2, false));
            
            // 2. Professor adiciona disciplinas
            assertTrue(professor.adicionarDisciplina(disciplina1));
            assertTrue(professor.adicionarDisciplina(disciplina2));
            
            // 3. Sistema processa matrículas
            sistema.efetuarMatricula(aluno1, disciplina1);
            sistema.efetuarMatricula(aluno1, disciplina2);
            
            // 4. Curso adiciona disciplinas
            curso.adicionarDisciplina(disciplina1);
            curso.adicionarDisciplina(disciplina2);
        });
    }

    @Test
    @DisplayName("Deve permitir cancelamento de matrícula")
    void devePermitirCancelamentoDeMatricula() {
        assertDoesNotThrow(() -> {
            // Matricula aluno
            assertTrue(aluno1.matricular(disciplina1, true));
            sistema.efetuarMatricula(aluno1, disciplina1);
            
            // Cancela matrícula
            assertTrue(aluno1.cancelarMatricula(disciplina1));
            sistema.cancelarMatricula(aluno1, disciplina1);
        });
    }

    @Test
    @DisplayName("Deve respeitar limites de matrícula por aluno")
    void deveRespeitarLimitesDeMatriculaPorAluno() {
        // Testa limites de disciplinas obrigatórias
        assertTrue(aluno1.matricular(disciplina1, true));
        assertTrue(aluno1.matricular(disciplina2, true));
        
        // Criando mais disciplinas para testar o limite
        Disciplina disciplina3 = new Disciplina();
        Disciplina disciplina4 = new Disciplina();
        Disciplina disciplina5 = new Disciplina();
        
        assertTrue(aluno1.matricular(disciplina3, true));
        assertTrue(aluno1.matricular(disciplina4, true));
        
        // Tentando matricular a 5ª disciplina obrigatória (deve falhar)
        assertFalse(aluno1.matricular(disciplina5, true));
        
        assertEquals(4, aluno1.getDisciplinasObrigatorias().size());
    }

    @Test
    @DisplayName("Deve permitir múltiplos alunos no sistema")
    void devePermitirMultiplosAlunosNoSistema() {
        assertDoesNotThrow(() -> {
            // Aluno 1 se matricula
            assertTrue(aluno1.matricular(disciplina1, true));
            sistema.efetuarMatricula(aluno1, disciplina1);
            
            // Aluno 2 se matricula
            assertTrue(aluno2.matricular(disciplina1, true));
            sistema.efetuarMatricula(aluno2, disciplina1);
            
            // Verifica que ambos têm a disciplina
            assertTrue(aluno1.getDisciplinasObrigatorias().contains(disciplina1));
            assertTrue(aluno2.getDisciplinasObrigatorias().contains(disciplina1));
        });
    }

    @Test
    @DisplayName("Deve permitir professor ministrar múltiplas disciplinas")
    void devePermitirProfessorMinistrarMultiplasDisciplinas() {
        assertTrue(professor.adicionarDisciplina(disciplina1));
        assertTrue(professor.adicionarDisciplina(disciplina2));
        
        assertEquals(2, professor.getDisciplinasMinistradas().size());
        assertTrue(professor.getDisciplinasMinistradas().contains(disciplina1));
        assertTrue(professor.getDisciplinasMinistradas().contains(disciplina2));
    }

    @Test
    @DisplayName("Deve permitir autenticação de diferentes tipos de usuário")
    void devePermitirAutenticacaoDeDiferentesTiposDeUsuario() {
        // Testa autenticação de aluno
        assertTrue(aluno1.autenticar("senha123"));
        assertFalse(aluno1.autenticar("senhaErrada"));
        
        // Testa autenticação de professor
        assertTrue(professor.autenticar("senha789"));
        assertFalse(professor.autenticar("senhaErrada"));
    }

    @Test
    @DisplayName("Deve permitir encerramento do período")
    void devePermitirEncerramentoDoPeriodo() {
        assertDoesNotThrow(() -> {
            // Simula um período de matrículas
            assertTrue(aluno1.matricular(disciplina1, true));
            assertTrue(aluno2.matricular(disciplina1, true));
            sistema.efetuarMatricula(aluno1, disciplina1);
            sistema.efetuarMatricula(aluno2, disciplina1);
            
            // Encerra o período
            sistema.encerrarPeriodo();
        });
    }

    @Test
    @DisplayName("Deve manter consistência entre diferentes operações")
    void deveManterConsistenciaEntreDiferentesOperacoes() {
        assertDoesNotThrow(() -> {
            // Operações complexas que devem manter consistência
            assertTrue(aluno1.matricular(disciplina1, true));
            assertTrue(professor.adicionarDisciplina(disciplina1));
            curso.adicionarDisciplina(disciplina1);
            sistema.efetuarMatricula(aluno1, disciplina1);
            
            // Verifica consistência
            assertTrue(aluno1.getDisciplinasObrigatorias().contains(disciplina1));
            assertTrue(professor.getDisciplinasMinistradas().contains(disciplina1));
            
            // Cancela matrícula
            assertTrue(aluno1.cancelarMatricula(disciplina1));
            sistema.cancelarMatricula(aluno1, disciplina1);
            
            // Verifica que foi removida
            assertFalse(aluno1.getDisciplinasObrigatorias().contains(disciplina1));
        });
    }
}

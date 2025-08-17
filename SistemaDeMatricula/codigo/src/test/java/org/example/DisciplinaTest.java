package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes para a classe Disciplina
 */
@DisplayName("Testes da classe Disciplina")
class DisciplinaTest {

    private Disciplina disciplina;
    private Aluno aluno1;
    private Aluno aluno2;
    private Professor professor;

    @BeforeEach
    void setUp() {
        disciplina = new Disciplina();
        aluno1 = new Aluno("João Silva", "joao.silva", "senha123", "2023001");
        aluno2 = new Aluno("Maria Santos", "maria.santos", "senha456", "2023002");
        professor = new Professor("Dr. Carlos Oliveira", "carlos.oliveira", "senha789");
    }

    @Test
    @DisplayName("Deve criar disciplina com valores padrão")
    void deveCriarDisciplinaComValoresPadrao() {
        // Como não há construtor implementado, testamos o comportamento padrão
        assertNotNull(disciplina);
    }

    @Test
    @DisplayName("Deve retornar false ao adicionar aluno (implementação atual)")
    void deveRetornarFalseAoAdicionarAluno() {
        // Com a implementação atual (TODO), sempre retorna false
        assertFalse(disciplina.adicionarAluno(aluno1));
        assertFalse(disciplina.adicionarAluno(aluno2));
    }

    @Test
    @DisplayName("Deve retornar false ao remover aluno (implementação atual)")
    void deveRetornarFalseAoRemoverAluno() {
        // Com a implementação atual (TODO), sempre retorna false
        assertFalse(disciplina.removerAluno(aluno1));
        assertFalse(disciplina.removerAluno(null));
    }

    @Test
    @DisplayName("Deve executar verificarAtivacao sem erro")
    void deveExecutarVerificarAtivacaoSemErro() {
        // Testa se o método executa sem exceção
        assertDoesNotThrow(() -> disciplina.verificarAtivacao());
    }

    @Test
    @DisplayName("Deve lidar com aluno nulo em adicionarAluno")
    void deveLidarComAlunoNuloEmAdicionarAluno() {
        assertFalse(disciplina.adicionarAluno(null));
    }

    @Test
    @DisplayName("Deve lidar com aluno nulo em removerAluno")
    void deveLidarComAlunoNuloEmRemoverAluno() {
        assertFalse(disciplina.removerAluno(null));
    }

    @Test
    @DisplayName("Deve manter consistência entre adicionar e remover")
    void deveManterConsistenciaEntreAdicionarERemover() {
        // Com a implementação atual, ambos retornam false
        assertFalse(disciplina.adicionarAluno(aluno1));
        assertFalse(disciplina.removerAluno(aluno1));
    }

    @Test
    @DisplayName("Deve permitir múltiplas chamadas de verificarAtivacao")
    void devePermitirMultiplasChamadasDeVerificarAtivacao() {
        assertDoesNotThrow(() -> {
            disciplina.verificarAtivacao();
            disciplina.verificarAtivacao();
            disciplina.verificarAtivacao();
        });
    }

    @Test
    @DisplayName("Deve lidar com operações em sequência")
    void deveLidarComOperacoesEmSequencia() {
        // Testa uma sequência de operações
        assertFalse(disciplina.adicionarAluno(aluno1));
        assertFalse(disciplina.adicionarAluno(aluno2));
        assertFalse(disciplina.removerAluno(aluno1));
        assertFalse(disciplina.removerAluno(aluno2));
        assertDoesNotThrow(() -> disciplina.verificarAtivacao());
    }
}

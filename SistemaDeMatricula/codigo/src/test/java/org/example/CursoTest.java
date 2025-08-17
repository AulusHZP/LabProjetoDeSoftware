package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes para a classe Curso
 */
@DisplayName("Testes da classe Curso")
class CursoTest {

    private Curso curso;
    private Disciplina disciplina1;
    private Disciplina disciplina2;

    @BeforeEach
    void setUp() {
        curso = new Curso();
        disciplina1 = new Disciplina();
        disciplina2 = new Disciplina();
    }

    @Test
    @DisplayName("Deve criar curso")
    void deveCriarCurso() {
        assertNotNull(curso);
    }

    @Test
    @DisplayName("Deve executar adicionarDisciplina sem erro")
    void deveExecutarAdicionarDisciplinaSemErro() {
        // Testa se o método executa sem exceção
        assertDoesNotThrow(() -> curso.adicionarDisciplina(disciplina1));
    }

    @Test
    @DisplayName("Deve permitir adicionar múltiplas disciplinas")
    void devePermitirAdicionarMultiplasDisciplinas() {
        assertDoesNotThrow(() -> {
            curso.adicionarDisciplina(disciplina1);
            curso.adicionarDisciplina(disciplina2);
        });
    }

    @Test
    @DisplayName("Deve lidar com disciplina nula")
    void deveLidarComDisciplinaNula() {
        assertDoesNotThrow(() -> curso.adicionarDisciplina(null));
    }

    @Test
    @DisplayName("Deve permitir múltiplas chamadas de adicionarDisciplina")
    void devePermitirMultiplasChamadasDeAdicionarDisciplina() {
        assertDoesNotThrow(() -> {
            curso.adicionarDisciplina(disciplina1);
            curso.adicionarDisciplina(disciplina1); // Mesma disciplina
            curso.adicionarDisciplina(disciplina2);
        });
    }

    @Test
    @DisplayName("Deve executar operações em sequência")
    void deveExecutarOperacoesEmSequencia() {
        assertDoesNotThrow(() -> {
            curso.adicionarDisciplina(disciplina1);
            curso.adicionarDisciplina(disciplina2);
            curso.adicionarDisciplina(disciplina1); // Adicionar novamente
        });
    }
}

package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Testes da classe Curso")
class CursoTest {

    private Curso curso;
    private Disciplina d1, d2;

    @BeforeEach
    void setUp() {
        curso = new Curso();
        d1 = new Disciplina();
        d2 = new Disciplina();
    }

    @Test
    @DisplayName("Cria curso")
    void criaCurso() {
        assertNotNull(curso);
    }

    @Test
    @DisplayName("adicionarDisciplina executa sem erro")
    void adicionarDisciplinaSemErro() {
        assertDoesNotThrow(() -> curso.adicionarDisciplina(d1));
    }

    @Test
    @DisplayName("Permite adicionar múltiplas disciplinas")
    void multipasDisciplinas() {
        assertDoesNotThrow(() -> {
            curso.adicionarDisciplina(d1);
            curso.adicionarDisciplina(d2);
        });
    }

    @Test
    @DisplayName("Aceita nulo sem lançar exceção")
    void aceitaNulo() {
        assertDoesNotThrow(() -> curso.adicionarDisciplina(null));
    }
}

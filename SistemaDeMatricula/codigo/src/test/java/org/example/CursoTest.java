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
    @DisplayName("Deve adicionar disciplina corretamente")
    void deveAdicionarDisciplina() {
        curso.adicionarDisciplina(d1);
        assertEquals(1, curso.disciplinas.size());
        assertTrue(curso.disciplinas.contains(d1));
    }

    @Test
    @DisplayName("Deve adicionar múltiplas disciplinas")
    void deveAdicionarMultiplasDisciplinas() {
        curso.adicionarDisciplina(d1);
        curso.adicionarDisciplina(d2);
        assertEquals(2, curso.disciplinas.size());
        assertTrue(curso.disciplinas.contains(d1));
        assertTrue(curso.disciplinas.contains(d2));
    }

    @Test
    @DisplayName("Não deve adicionar disciplina duplicada")
    void naoDeveAdicionarDisciplinaDuplicada() {
        curso.adicionarDisciplina(d1);
        curso.adicionarDisciplina(d1);
        assertEquals(1, curso.disciplinas.size());
    }

    @Test
    @DisplayName("Deve lidar com disciplina nula")
    void deveLidarComDisciplinaNula() {
        curso.adicionarDisciplina(null);
        assertEquals(0, curso.disciplinas.size());
    }

    @Test
    @DisplayName("Deve permitir configuração de nome e créditos")
    void devePermitirConfiguracaoDeNomeECreditos() {
        curso.nome = "Ciência da Computação";
        curso.creditos = 240;
        
        assertEquals("Ciência da Computação", curso.nome);
        assertEquals(240, curso.creditos);
    }
}

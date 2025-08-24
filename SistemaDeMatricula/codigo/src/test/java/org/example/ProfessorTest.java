package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

@DisplayName("Testes da classe Professor")
class ProfessorTest {

    private Professor professor;
    private Disciplina d1, d2, d3;

    @BeforeEach
    void setUp() {
        professor = new Professor();
        professor.nome = "Dr. Carlos Oliveira";
        professor.login = "carlos.oliveira";
        professor.senha = "senha456";

        d1 = new Disciplina();
        d2 = new Disciplina();
        d3 = new Disciplina();
    }

    @Test
    @DisplayName("Cria professor com dados corretos")
    void criaProfessor() {
        assertEquals("Dr. Carlos Oliveira", professor.nome);
        assertEquals("carlos.oliveira", professor.login);
        assertTrue(professor.autenticar("senha456"));
    }

    @Test
    @DisplayName("Adicionar disciplina ministrada (efeito na lista)")
    void adicionarDisciplinaMinistrada() {
        professor.adicionarDisciplina(d1);
        assertEquals(1, professor.disciplinasMinistradas.size());
        assertTrue(professor.disciplinasMinistradas.contains(d1));
    }

    @Test
    @DisplayName("Adicionar múltiplas disciplinas")
    void adicionarMultiplas() {
        professor.adicionarDisciplina(d1);
        professor.adicionarDisciplina(d2);
        professor.adicionarDisciplina(d3);
        assertEquals(3, professor.disciplinasMinistradas.size());
    }

    @Test
    @DisplayName("Não permite adicionar disciplina duplicada")
    void naoPermiteDisciplinaDuplicada() {
        professor.adicionarDisciplina(d1);
        professor.adicionarDisciplina(d1); // tentativa de adicionar a mesma disciplina
        assertEquals(1, professor.disciplinasMinistradas.size());
    }

    @Test
    @DisplayName("Lida com disciplina nula")
    void lidaComDisciplinaNula() {
        professor.adicionarDisciplina(null); // não deve lançar exceção
        assertEquals(0, professor.disciplinasMinistradas.size());
    }

    @Test
    @DisplayName("Estabelece relação bidirecional com disciplina")
    void estabeleceRelacaoBidirecional() {
        professor.adicionarDisciplina(d1);
        assertEquals(professor, d1.professor);
    }
}


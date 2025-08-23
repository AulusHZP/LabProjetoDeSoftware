package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Testes de Integração do Sistema de Matrículas")
class SistemaIntegracaoTest {

    private Secretaria secretaria;
    private Aluno aluno1, aluno2;
    private Professor professor;
    private Disciplina d1, d2;
    private Curso curso;

    @BeforeEach
    void setUp() {
        secretaria = new Secretaria();
        aluno1 = new Aluno(); aluno1.matricula = "2023001"; aluno1.senha = "senha123";
        aluno2 = new Aluno(); aluno2.matricula = "2023002"; aluno2.senha = "senha456";
        professor = new Professor(); professor.senha = "senha789";
        d1 = new Disciplina(); d1.obrigatoria = true;
        d2 = new Disciplina(); d2.obrigatoria = false;
        curso = new Curso();
    }

    @Test
    @DisplayName("Integração básica entre classes")
    void integracaoBasica() {
        assertNotNull(secretaria);
        assertNotNull(aluno1);
        assertNotNull(professor);
        assertNotNull(d1);
        assertNotNull(curso);
    }

    @Test
    @DisplayName("Fluxo completo de matrícula via Secretaria")
    void fluxoCompletoMatricula() {
        assertDoesNotThrow(() -> {
            aluno1.matricular(d1);
            aluno1.matricular(d2);
            professor.adicionarDisciplina(d1);
            professor.adicionarDisciplina(d2);
            secretaria.efetuarMatricula(aluno1, d1);
            secretaria.efetuarMatricula(aluno1, d2);
            curso.adicionarDisciplina(d1);
            curso.adicionarDisciplina(d2);
        });
        assertTrue(aluno1.disciplinasObrigatorias.contains(d1));
        assertTrue(aluno1.disciplinasOptativas.contains(d2));
    }

    @Test
    @DisplayName("Cancelamento de matrícula via Secretaria")
    void cancelamentoMatricula() {
        aluno1.matricular(d1);
        secretaria.efetuarMatricula(aluno1, d1);
        secretaria.cancelarMatricula(aluno1, d1);
        assertFalse(aluno1.disciplinasObrigatorias.contains(d1));
    }

    @Test
    @DisplayName("Respeita limite de 4 obrigatórias")
    void limiteObrigatorias() {
        Disciplina d3 = new Disciplina(); d3.obrigatoria = true;
        Disciplina d4 = new Disciplina(); d4.obrigatoria = true;
        Disciplina d5 = new Disciplina(); d5.obrigatoria = true;
        aluno1.matricular(d1);
        aluno1.matricular(d3);
        aluno1.matricular(d4);
        aluno1.matricular(d5);
        Disciplina extra = new Disciplina(); extra.obrigatoria = true;
        aluno1.matricular(extra);
        assertEquals(4, aluno1.disciplinasObrigatorias.size());
    }

    @Test
    @DisplayName("Autenticação de diferentes tipos de usuário")
    void autenticacao() {
        aluno1.senha = "s1";
        professor.senha = "s2";
        assertTrue(aluno1.autenticar("s1"));
        assertTrue(professor.autenticar("s2"));
        assertFalse(aluno1.autenticar("x"));
        assertFalse(professor.autenticar("x"));
    }
}

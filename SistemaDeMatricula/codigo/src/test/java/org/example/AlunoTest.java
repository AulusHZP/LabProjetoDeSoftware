package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

@DisplayName("Testes da classe Aluno")
class AlunoTest {

    private Aluno aluno;
    private Disciplina d1, d2, d3, d4, d5, d6, d7;

    @BeforeEach
    void setUp() {
        aluno = new Aluno();
        aluno.nome = "Maria Santos";
        aluno.login = "maria.santos";
        aluno.senha = "senha123";
        aluno.matricula = "2023001";

        d1 = new Disciplina(); d1.obrigatoria = true;
        d2 = new Disciplina(); d2.obrigatoria = true;
        d3 = new Disciplina(); d3.obrigatoria = true;
        d4 = new Disciplina(); d4.obrigatoria = true;
        d5 = new Disciplina(); d5.obrigatoria = true;
        d6 = new Disciplina(); d6.obrigatoria = false;
        d7 = new Disciplina(); d7.obrigatoria = false;
    }

    @Test
    @DisplayName("Deve criar aluno com dados corretos")
    void deveCriarAlunoComDadosCorretos() {
        assertEquals("Maria Santos", aluno.nome);
        assertEquals("maria.santos", aluno.login);
        assertEquals("senha123", aluno.senha);
        assertEquals("2023001", aluno.matricula);
        assertTrue(aluno.disciplinasObrigatorias.isEmpty());
        assertTrue(aluno.disciplinasOptativas.isEmpty());
    }

    @Test
    @DisplayName("Deve permitir alteração de matrícula")
    void devePermitirAlteracaoDeMatricula() {
        aluno.matricula = "2023002";
        assertEquals("2023002", aluno.matricula);
    }

    @Test
    @DisplayName("Matricular disciplina obrigatória incrementa lista")
    void matricularObrigatoria() {
        aluno.matricular(d1);
        assertEquals(1, aluno.disciplinasObrigatorias.size());
        assertTrue(aluno.disciplinasObrigatorias.contains(d1));
    }

    @Test
    @DisplayName("Matricular disciplina optativa incrementa lista")
    void matricularOptativa() {
        aluno.matricular(d6);
        assertEquals(1, aluno.disciplinasOptativas.size());
        assertTrue(aluno.disciplinasOptativas.contains(d6));
    }

    @Test
    @DisplayName("Respeita limite de 4 obrigatórias")
    void respeitaLimiteObrigatorias() {
        aluno.matricular(d1);
        aluno.matricular(d2);
        aluno.matricular(d3);
        aluno.matricular(d4);
        // 5ª obrigatória não deve entrar (método atual apenas ignora)
        aluno.matricular(d5);
        assertEquals(4, aluno.disciplinasObrigatorias.size());
    }

    @Test
    @DisplayName("Respeita limite de 2 optativas")
    void respeitaLimiteOptativas() {
        aluno.matricular(d6);
        aluno.matricular(d7);
        // terceira optativa (reutilizando d6) não deve alterar
        aluno.matricular(d6);
        assertEquals(2, aluno.disciplinasOptativas.size());
    }

    @Test
    @DisplayName("Cancelar matrícula remove de obrigatória")
    void cancelarObrigatoria() {
        aluno.matricular(d1);
        assertEquals(1, aluno.disciplinasObrigatorias.size());
        aluno.cancelarMatricula(d1);
        assertEquals(0, aluno.disciplinasObrigatorias.size());
    }

    @Test
    @DisplayName("Cancelar matrícula remove de optativa")
    void cancelarOptativa() {
        aluno.matricular(d6);
        assertEquals(1, aluno.disciplinasOptativas.size());
        aluno.cancelarMatricula(d6);
        assertEquals(0, aluno.disciplinasOptativas.size());
    }

    @Test
    @DisplayName("Permite mistura de obrigatórias e optativas")
    void misturaObrigatoriasOptativas() {
        aluno.matricular(d1);
        aluno.matricular(d2);
        aluno.matricular(d6);
        aluno.matricular(d7);
        assertEquals(2, aluno.disciplinasObrigatorias.size());
        assertEquals(2, aluno.disciplinasOptativas.size());
    }

    @Test
    @DisplayName("Cancelar e rematricular funciona")
    void cancelarERematricular() {
        aluno.matricular(d1);
        aluno.cancelarMatricula(d1);
        aluno.matricular(d1);
        assertTrue(aluno.disciplinasObrigatorias.contains(d1));
    }

    @Test
    @DisplayName("A mesma disciplina pode existir em listas diferentes se mudar obrigatoriedade")
    void mesmaDisciplinaEmListasDiferentes() {
        Disciplina dx = new Disciplina();
        dx.obrigatoria = true;
        aluno.matricular(dx); // vai para obrigatórias
        dx.obrigatoria = false;
        aluno.matricular(dx); // vai para optativas
        assertEquals(1, aluno.disciplinasObrigatorias.size());
        assertEquals(1, aluno.disciplinasOptativas.size());
    }
}

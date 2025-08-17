package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes para a classe Aluno
 */
@DisplayName("Testes da classe Aluno")
class AlunoTest {

    private Aluno aluno;
    private Disciplina disciplina1;
    private Disciplina disciplina2;
    private Disciplina disciplina3;
    private Disciplina disciplina4;
    private Disciplina disciplina5;
    private Disciplina disciplina6;
    private Disciplina disciplina7;

    @BeforeEach
    void setUp() {
        aluno = new Aluno("Maria Santos", "maria.santos", "senha123", "2023001");
        
        // Criando disciplinas para teste
        disciplina1 = new Disciplina();
        disciplina2 = new Disciplina();
        disciplina3 = new Disciplina();
        disciplina4 = new Disciplina();
        disciplina5 = new Disciplina();
        disciplina6 = new Disciplina();
        disciplina7 = new Disciplina();
    }

    @Test
    @DisplayName("Deve criar aluno com dados corretos")
    void deveCriarAlunoComDadosCorretos() {
        assertEquals("Maria Santos", aluno.getNome());
        assertEquals("maria.santos", aluno.getLogin());
        assertEquals("senha123", aluno.getSenha());
        assertEquals("2023001", aluno.getMatricula());
        assertTrue(aluno.getDisciplinasObrigatorias().isEmpty());
        assertTrue(aluno.getDisciplinasOptativas().isEmpty());
    }

    @Test
    @DisplayName("Deve permitir alteração de matrícula")
    void devePermitirAlteracaoDeMatricula() {
        aluno.setMatricula("2023002");
        assertEquals("2023002", aluno.getMatricula());
    }

    @Test
    @DisplayName("Deve matricular em disciplina obrigatória")
    void deveMatricularEmDisciplinaObrigatoria() {
        assertTrue(aluno.matricular(disciplina1, true));
        assertEquals(1, aluno.getDisciplinasObrigatorias().size());
        assertTrue(aluno.getDisciplinasObrigatorias().contains(disciplina1));
    }

    @Test
    @DisplayName("Deve matricular em disciplina optativa")
    void deveMatricularEmDisciplinaOptativa() {
        assertTrue(aluno.matricular(disciplina1, false));
        assertEquals(1, aluno.getDisciplinasOptativas().size());
        assertTrue(aluno.getDisciplinasOptativas().contains(disciplina1));
    }

    @Test
    @DisplayName("Deve respeitar limite de disciplinas obrigatórias")
    void deveRespeitarLimiteDeDisciplinasObrigatorias() {
        // Matriculando 4 disciplinas obrigatórias (limite máximo)
        assertTrue(aluno.matricular(disciplina1, true));
        assertTrue(aluno.matricular(disciplina2, true));
        assertTrue(aluno.matricular(disciplina3, true));
        assertTrue(aluno.matricular(disciplina4, true));
        
        assertEquals(4, aluno.getDisciplinasObrigatorias().size());
        
        // Tentando matricular a 5ª disciplina obrigatória
        assertFalse(aluno.matricular(disciplina5, true));
        assertEquals(4, aluno.getDisciplinasObrigatorias().size());
    }

    @Test
    @DisplayName("Deve respeitar limite de disciplinas optativas")
    void deveRespeitarLimiteDeDisciplinasOptativas() {
        // Matriculando 2 disciplinas optativas (limite máximo)
        assertTrue(aluno.matricular(disciplina1, false));
        assertTrue(aluno.matricular(disciplina2, false));
        
        assertEquals(2, aluno.getDisciplinasOptativas().size());
        
        // Tentando matricular a 3ª disciplina optativa
        assertFalse(aluno.matricular(disciplina3, false));
        assertEquals(2, aluno.getDisciplinasOptativas().size());
    }

    @Test
    @DisplayName("Deve cancelar matrícula em disciplina obrigatória")
    void deveCancelarMatriculaEmDisciplinaObrigatoria() {
        aluno.matricular(disciplina1, true);
        assertEquals(1, aluno.getDisciplinasObrigatorias().size());
        
        assertTrue(aluno.cancelarMatricula(disciplina1));
        assertEquals(0, aluno.getDisciplinasObrigatorias().size());
    }

    @Test
    @DisplayName("Deve cancelar matrícula em disciplina optativa")
    void deveCancelarMatriculaEmDisciplinaOptativa() {
        aluno.matricular(disciplina1, false);
        assertEquals(1, aluno.getDisciplinasOptativas().size());
        
        assertTrue(aluno.cancelarMatricula(disciplina1));
        assertEquals(0, aluno.getDisciplinasOptativas().size());
    }

    @Test
    @DisplayName("Deve retornar false ao tentar cancelar disciplina não matriculada")
    void deveRetornarFalseAoTentarCancelarDisciplinaNaoMatriculada() {
        assertFalse(aluno.cancelarMatricula(disciplina1));
    }

    @Test
    @DisplayName("Deve permitir matrícula mista de obrigatórias e optativas")
    void devePermitirMatriculaMistaDeObrigatoriasEOptativas() {
        // Matriculando disciplinas obrigatórias
        assertTrue(aluno.matricular(disciplina1, true));
        assertTrue(aluno.matricular(disciplina2, true));
        
        // Matriculando disciplinas optativas
        assertTrue(aluno.matricular(disciplina3, false));
        assertTrue(aluno.matricular(disciplina4, false));
        
        assertEquals(2, aluno.getDisciplinasObrigatorias().size());
        assertEquals(2, aluno.getDisciplinasOptativas().size());
    }

    @Test
    @DisplayName("Deve permitir cancelar e rematricular disciplina")
    void devePermitirCancelarERematricularDisciplina() {
        // Matriculando disciplina obrigatória
        assertTrue(aluno.matricular(disciplina1, true));
        assertEquals(1, aluno.getDisciplinasObrigatorias().size());
        
        // Cancelando matrícula
        assertTrue(aluno.cancelarMatricula(disciplina1));
        assertEquals(0, aluno.getDisciplinasObrigatorias().size());
        
        // Rematriculando
        assertTrue(aluno.matricular(disciplina1, true));
        assertEquals(1, aluno.getDisciplinasObrigatorias().size());
    }

    @Test
    @DisplayName("Deve manter listas separadas para obrigatórias e optativas")
    void deveManterListasSeparadasParaObrigatoriasEOptativas() {
        // Matriculando a mesma disciplina como obrigatória e optativa
        assertTrue(aluno.matricular(disciplina1, true));
        assertTrue(aluno.matricular(disciplina1, false));
        
        assertEquals(1, aluno.getDisciplinasObrigatorias().size());
        assertEquals(1, aluno.getDisciplinasOptativas().size());
        assertTrue(aluno.getDisciplinasObrigatorias().contains(disciplina1));
        assertTrue(aluno.getDisciplinasOptativas().contains(disciplina1));
    }
}

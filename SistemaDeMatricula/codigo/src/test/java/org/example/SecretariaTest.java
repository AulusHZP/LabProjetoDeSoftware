package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Testes da classe Secretaria")
class SecretariaTest {

    private Secretaria secretaria;
    private Aluno aluno;
    private Disciplina disciplina;

    @BeforeEach
    void setUp() {
        secretaria = new Secretaria();
        secretaria.nome = "Maria Secretária";
        secretaria.login = "secretaria";
        secretaria.senha = "senha123";
        
        aluno = new Aluno();
        aluno.nome = "João Aluno";
        aluno.matricula = "2023001";
        
        disciplina = new Disciplina();
        disciplina.codigo = "MAT001";
        disciplina.nome = "Matemática";
        disciplina.obrigatoria = true;
    }

    @Test
    @DisplayName("Deve criar secretaria com dados corretos")
    void deveCriarSecretariaComDadosCorretos() {
        assertEquals("Maria Secretária", secretaria.nome);
        assertEquals("secretaria", secretaria.login);
        assertEquals("senha123", secretaria.senha);
        assertTrue(secretaria.autenticar("senha123"));
    }

    @Test
    @DisplayName("Deve efetuar matrícula de aluno em disciplina")
    void deveEfetuarMatricula() {
        secretaria.efetuarMatricula(aluno, disciplina);
        assertEquals(1, aluno.disciplinasObrigatorias.size());
        assertTrue(aluno.disciplinasObrigatorias.contains(disciplina));
    }

    @Test
    @DisplayName("Deve cancelar matrícula de aluno em disciplina")
    void deveCancelarMatricula() {
        secretaria.efetuarMatricula(aluno, disciplina);
        assertEquals(1, aluno.disciplinasObrigatorias.size());
        
        secretaria.cancelarMatricula(aluno, disciplina);
        assertEquals(0, aluno.disciplinasObrigatorias.size());
    }

    @Test
    @DisplayName("Deve adicionar aluno diretamente à disciplina")
    void deveAdicionarAlunoADisciplina() {
        assertTrue(secretaria.adicionarAluno(aluno, disciplina));
        assertEquals(1, disciplina.alunos.size());
        assertTrue(disciplina.alunos.contains(aluno));
    }

    @Test
    @DisplayName("Deve remover aluno diretamente da disciplina")
    void deveRemoverAlunoDaDisciplina() {
        secretaria.adicionarAluno(aluno, disciplina);
        assertEquals(1, disciplina.alunos.size());
        
        assertTrue(secretaria.removerAluno(aluno, disciplina));
        assertEquals(0, disciplina.alunos.size());
    }

    @Test
    @DisplayName("Deve lidar com disciplina nula na matrícula")
    void deveLidarComDisciplinaNulaNaMatricula() {
        assertDoesNotThrow(() -> secretaria.efetuarMatricula(aluno, null));
        assertEquals(0, aluno.disciplinasObrigatorias.size());
    }

    @Test
    @DisplayName("Deve lidar com aluno nulo na matrícula")
    void deveLidarComAlunoNuloNaMatricula() {
        assertDoesNotThrow(() -> secretaria.efetuarMatricula(null, disciplina));
    }

    @Test
    @DisplayName("Deve lidar com disciplina nula no cancelamento")
    void deveLidarComDisciplinaNulaNoCancelamento() {
        assertDoesNotThrow(() -> secretaria.cancelarMatricula(aluno, null));
    }

    @Test
    @DisplayName("Deve lidar com aluno nulo no cancelamento")
    void deveLidarComAlunoNuloNoCancelamento() {
        assertDoesNotThrow(() -> secretaria.cancelarMatricula(null, disciplina));
    }

    @Test
    @DisplayName("Deve lidar com disciplina nula na adição direta")
    void deveLidarComDisciplinaNulaNaAdicaoDireta() {
        assertFalse(secretaria.adicionarAluno(aluno, null));
    }

    @Test
    @DisplayName("Deve lidar com aluno nulo na adição direta")
    void deveLidarComAlunoNuloNaAdicaoDireta() {
        assertFalse(secretaria.adicionarAluno(null, disciplina));
    }

    @Test
    @DisplayName("Deve lidar com disciplina nula na remoção direta")
    void deveLidarComDisciplinaNulaNaRemocaoDireta() {
        assertFalse(secretaria.removerAluno(aluno, null));
    }

    @Test
    @DisplayName("Deve lidar com aluno nulo na remoção direta")
    void deveLidarComAlunoNuloNaRemocaoDireta() {
        assertFalse(secretaria.removerAluno(null, disciplina));
    }

    @Test
    @DisplayName("Deve executar criarCurriculo sem erro")
    void deveExecutarCriarCurriculo() {
        assertDoesNotThrow(() -> secretaria.criarCurriculo());
    }

    @Test
    @DisplayName("Deve executar encerrarPeriodo sem erro")
    void deveExecutarEncerrarPeriodo() {
        assertDoesNotThrow(() -> secretaria.encerrarPeriodo());
    }
}

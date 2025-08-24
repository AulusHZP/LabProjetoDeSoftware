package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Testes da classe Disciplina")
class DisciplinaTest {

    private Disciplina disciplina;
    private Aluno a1, a2;
    private Professor p;

    @BeforeEach
    void setUp() {
        disciplina = new Disciplina();
        a1 = new Aluno(); a1.matricula = "2023001";
        a2 = new Aluno(); a2.matricula = "2023002";
        p = new Professor(); p.nome = "Dr. Carlos";
    }

    @Test
    @DisplayName("Cria disciplina com valores padrão")
    void criaDisciplina() {
        assertNotNull(disciplina);
        assertFalse(disciplina.ativa);
        assertEquals(0, disciplina.alunos.size());
        assertEquals(60, disciplina.maxAlunos);
    }

    @Test
    @DisplayName("Adicionar aluno respeita limite e evita duplicatas")
    void adicionarAluno() {
        assertTrue(disciplina.adicionarAluno(a1));
        assertFalse(disciplina.adicionarAluno(a1)); // duplicado
        assertTrue(disciplina.adicionarAluno(a2));
        assertEquals(2, disciplina.alunos.size());
    }

    @Test
    @DisplayName("Remover aluno funciona e lida com nulos")
    void removerAluno() {
        disciplina.adicionarAluno(a1);
        assertTrue(disciplina.removerAluno(a1));
        assertFalse(disciplina.removerAluno(a1)); // já removido
        assertFalse(disciplina.removerAluno(null));
    }

    @Test
    @DisplayName("verificarAtivacao ativa com >=3 alunos; senão limpa alunos")
    void verificarAtivacaoRegra() {
        Aluno a3 = new Aluno();
        disciplina.adicionarAluno(a1);
        disciplina.adicionarAluno(a2);
        disciplina.verificarAtivacao();
        assertFalse(disciplina.ativa);
        assertEquals(0, disciplina.alunos.size());

        // adicionar 3 alunos e ativar
        disciplina.adicionarAluno(a1);
        disciplina.adicionarAluno(a2);
        disciplina.adicionarAluno(a3);
        disciplina.verificarAtivacao();
        assertTrue(disciplina.ativa);
        assertEquals(3, disciplina.alunos.size());
    }

    @Test
    @DisplayName("Deve permitir configuração de propriedades")
    void devePermitirConfiguracaoDePropriedades() {
        disciplina.codigo = "MAT001";
        disciplina.nome = "Matemática";
        disciplina.creditos = 4;
        disciplina.maxAlunos = 30;
        disciplina.obrigatoria = true;
        
        assertEquals("MAT001", disciplina.codigo);
        assertEquals("Matemática", disciplina.nome);
        assertEquals(4, disciplina.creditos);
        assertEquals(30, disciplina.maxAlunos);
        assertTrue(disciplina.obrigatoria);
    }

    @Test
    @DisplayName("Deve respeitar limite máximo de alunos")
    void deveRespeitarLimiteMaximoDeAlunos() {
        disciplina.maxAlunos = 2;
        assertTrue(disciplina.adicionarAluno(a1));
        assertTrue(disciplina.adicionarAluno(a2));
        
        Aluno a3 = new Aluno();
        assertFalse(disciplina.adicionarAluno(a3));
        assertEquals(2, disciplina.alunos.size());
    }

    @Test
    @DisplayName("Deve lidar com aluno nulo na adição")
    void deveLidarComAlunoNuloNaAdicao() {
        assertFalse(disciplina.adicionarAluno(null));
        assertEquals(0, disciplina.alunos.size());
    }

    @Test
    @DisplayName("Deve lidar com aluno nulo na remoção")
    void deveLidarComAlunoNuloNaRemocao() {
        assertFalse(disciplina.removerAluno(null));
    }
}


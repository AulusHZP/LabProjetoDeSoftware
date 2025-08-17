package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes para a classe Professor
 */
@DisplayName("Testes da classe Professor")
class ProfessorTest {

    private Professor professor;
    private Disciplina disciplina1;
    private Disciplina disciplina2;
    private Disciplina disciplina3;

    @BeforeEach
    void setUp() {
        professor = new Professor("Dr. Carlos Oliveira", "carlos.oliveira", "senha456");
        disciplina1 = new Disciplina();
        disciplina2 = new Disciplina();
        disciplina3 = new Disciplina();
    }

    @Test
    @DisplayName("Deve criar professor com dados corretos")
    void deveCriarProfessorComDadosCorretos() {
        assertEquals("Dr. Carlos Oliveira", professor.getNome());
        assertEquals("carlos.oliveira", professor.getLogin());
        assertEquals("senha456", professor.getSenha());
        assertTrue(professor.getDisciplinasMinistradas().isEmpty());
    }

    @Test
    @DisplayName("Deve adicionar disciplina ministrada")
    void deveAdicionarDisciplinaMinistrada() {
        assertTrue(professor.adicionarDisciplina(disciplina1));
        assertEquals(1, professor.getDisciplinasMinistradas().size());
        assertTrue(professor.getDisciplinasMinistradas().contains(disciplina1));
    }

    @Test
    @DisplayName("Deve adicionar múltiplas disciplinas")
    void deveAdicionarMultiplasDisciplinas() {
        assertTrue(professor.adicionarDisciplina(disciplina1));
        assertTrue(professor.adicionarDisciplina(disciplina2));
        assertTrue(professor.adicionarDisciplina(disciplina3));
        
        assertEquals(3, professor.getDisciplinasMinistradas().size());
        assertTrue(professor.getDisciplinasMinistradas().contains(disciplina1));
        assertTrue(professor.getDisciplinasMinistradas().contains(disciplina2));
        assertTrue(professor.getDisciplinasMinistradas().contains(disciplina3));
    }

    @Test
    @DisplayName("Deve remover disciplina ministrada")
    void deveRemoverDisciplinaMinistrada() {
        professor.adicionarDisciplina(disciplina1);
        professor.adicionarDisciplina(disciplina2);
        
        assertEquals(2, professor.getDisciplinasMinistradas().size());
        
        assertTrue(professor.removerDisciplina(disciplina1));
        assertEquals(1, professor.getDisciplinasMinistradas().size());
        assertFalse(professor.getDisciplinasMinistradas().contains(disciplina1));
        assertTrue(professor.getDisciplinasMinistradas().contains(disciplina2));
    }

    @Test
    @DisplayName("Deve retornar false ao tentar remover disciplina não ministrada")
    void deveRetornarFalseAoTentarRemoverDisciplinaNaoMinistrada() {
        assertFalse(professor.removerDisciplina(disciplina1));
    }

    @Test
    @DisplayName("Deve permitir adicionar e remover a mesma disciplina")
    void devePermitirAdicionarERemoverAMesmaDisciplina() {
        assertTrue(professor.adicionarDisciplina(disciplina1));
        assertEquals(1, professor.getDisciplinasMinistradas().size());
        
        assertTrue(professor.removerDisciplina(disciplina1));
        assertEquals(0, professor.getDisciplinasMinistradas().size());
        
        // Pode adicionar novamente
        assertTrue(professor.adicionarDisciplina(disciplina1));
        assertEquals(1, professor.getDisciplinasMinistradas().size());
    }

    @Test
    @DisplayName("Deve manter lista de disciplinas ministradas consistente")
    void deveManterListaDeDisciplinasMinistradasConsistente() {
        professor.adicionarDisciplina(disciplina1);
        professor.adicionarDisciplina(disciplina2);
        
        // Verificando se a lista retornada é a mesma instância
        var disciplinas1 = professor.getDisciplinasMinistradas();
        var disciplinas2 = professor.getDisciplinasMinistradas();
        
        assertEquals(disciplinas1, disciplinas2);
        assertEquals(2, disciplinas1.size());
    }

    @Test
    @DisplayName("Deve herdar funcionalidades de autenticação do Usuario")
    void deveHerdarFuncionalidadesDeAutenticacaoDoUsuario() {
        assertTrue(professor.autenticar("senha456"));
        assertFalse(professor.autenticar("senhaErrada"));
    }

    @Test
    @DisplayName("Deve permitir alteração de dados pessoais")
    void devePermitirAlteracaoDeDadosPessoais() {
        professor.setNome("Dr. Carlos Silva Oliveira");
        professor.setLogin("carlos.silva.oliveira");
        professor.setSenha("novaSenha789");
        
        assertEquals("Dr. Carlos Silva Oliveira", professor.getNome());
        assertEquals("carlos.silva.oliveira", professor.getLogin());
        assertEquals("novaSenha789", professor.getSenha());
        assertTrue(professor.autenticar("novaSenha789"));
    }

    @Test
    @DisplayName("Deve lidar com disciplina nula")
    void deveLidarComDisciplinaNula() {
        // O comportamento atual pode variar dependendo da implementação
        // Este teste documenta o comportamento esperado
        assertFalse(professor.adicionarDisciplina(null));
        assertFalse(professor.removerDisciplina(null));
    }
}

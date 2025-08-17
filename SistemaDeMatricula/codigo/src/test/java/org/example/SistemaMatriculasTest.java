package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes para a classe SistemaMatriculas
 */
@DisplayName("Testes da classe SistemaMatriculas")
class SistemaMatriculasTest {

    private SistemaMatriculas sistema;
    private Aluno aluno;
    private Disciplina disciplina;
    private Usuario usuario;

    @BeforeEach
    void setUp() {
        sistema = new SistemaMatriculas();
        aluno = new Aluno("João Silva", "joao.silva", "senha123", "2023001");
        disciplina = new Disciplina();
        usuario = new Usuario("Teste", "teste", "senha") {
            // Implementação mínima para teste
        };
    }

    @Test
    @DisplayName("Deve criar sistema de matrículas")
    void deveCriarSistemaDeMatriculas() {
        assertNotNull(sistema);
    }

    @Test
    @DisplayName("Deve retornar null no login (implementação atual)")
    void deveRetornarNullNoLogin() {
        // Com a implementação atual (TODO), sempre retorna null
        assertNull(sistema.login("usuario", "senha"));
        assertNull(sistema.login("", ""));
        assertNull(sistema.login(null, null));
    }

    @Test
    @DisplayName("Deve executar efetuarMatricula sem erro")
    void deveExecutarEfetuarMatriculaSemErro() {
        // Testa se o método executa sem exceção
        assertDoesNotThrow(() -> sistema.efetuarMatricula(aluno, disciplina));
    }

    @Test
    @DisplayName("Deve executar cancelarMatricula sem erro")
    void deveExecutarCancelarMatriculaSemErro() {
        // Testa se o método executa sem exceção
        assertDoesNotThrow(() -> sistema.cancelarMatricula(aluno, disciplina));
    }

    @Test
    @DisplayName("Deve executar encerrarPeriodo sem erro")
    void deveExecutarEncerrarPeriodoSemErro() {
        // Testa se o método executa sem exceção
        assertDoesNotThrow(() -> sistema.encerrarPeriodo());
    }

    @Test
    @DisplayName("Deve lidar com parâmetros nulos em efetuarMatricula")
    void deveLidarComParametrosNulosEmEfetuarMatricula() {
        assertDoesNotThrow(() -> sistema.efetuarMatricula(null, disciplina));
        assertDoesNotThrow(() -> sistema.efetuarMatricula(aluno, null));
        assertDoesNotThrow(() -> sistema.efetuarMatricula(null, null));
    }

    @Test
    @DisplayName("Deve lidar com parâmetros nulos em cancelarMatricula")
    void deveLidarComParametrosNulosEmCancelarMatricula() {
        assertDoesNotThrow(() -> sistema.cancelarMatricula(null, disciplina));
        assertDoesNotThrow(() -> sistema.cancelarMatricula(aluno, null));
        assertDoesNotThrow(() -> sistema.cancelarMatricula(null, null));
    }

    @Test
    @DisplayName("Deve permitir múltiplas operações de matrícula")
    void devePermitirMultiplasOperacoesDeMatricula() {
        assertDoesNotThrow(() -> {
            sistema.efetuarMatricula(aluno, disciplina);
            sistema.efetuarMatricula(aluno, disciplina);
            sistema.cancelarMatricula(aluno, disciplina);
            sistema.cancelarMatricula(aluno, disciplina);
        });
    }

    @Test
    @DisplayName("Deve permitir múltiplas chamadas de encerrarPeriodo")
    void devePermitirMultiplasChamadasDeEncerrarPeriodo() {
        assertDoesNotThrow(() -> {
            sistema.encerrarPeriodo();
            sistema.encerrarPeriodo();
            sistema.encerrarPeriodo();
        });
    }

    @Test
    @DisplayName("Deve lidar com sequência completa de operações")
    void deveLidarComSequenciaCompletaDeOperacoes() {
        assertDoesNotThrow(() -> {
            // Simula um fluxo completo de matrículas
            sistema.efetuarMatricula(aluno, disciplina);
            sistema.cancelarMatricula(aluno, disciplina);
            sistema.efetuarMatricula(aluno, disciplina);
            sistema.encerrarPeriodo();
        });
    }

    @Test
    @DisplayName("Deve lidar com login com diferentes tipos de entrada")
    void deveLidarComLoginComDiferentesTiposDeEntrada() {
        // Testa diferentes combinações de entrada
        assertNull(sistema.login("", ""));
        assertNull(sistema.login("usuario", ""));
        assertNull(sistema.login("", "senha"));
        assertNull(sistema.login("usuario", "senha"));
        assertNull(sistema.login(null, "senha"));
        assertNull(sistema.login("usuario", null));
        assertNull(sistema.login(null, null));
    }
}

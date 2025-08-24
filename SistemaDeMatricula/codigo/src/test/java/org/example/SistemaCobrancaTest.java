package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Testes da classe SistemaCobranca")
class SistemaCobrancaTest {

    private SistemaCobranca sistemaCobranca;
    private Aluno aluno;

    @BeforeEach
    void setUp() {
        sistemaCobranca = new SistemaCobranca();
        aluno = new Aluno();
        aluno.nome = "João Silva";
        aluno.matricula = "2023001";
    }

    @Test
    @DisplayName("Deve criar sistema de cobrança")
    void deveCriarSistemaCobranca() {
        assertNotNull(sistemaCobranca);
    }

    @Test
    @DisplayName("Deve gerar cobrança para aluno válido")
    void deveGerarCobrancaParaAlunoValido() {
        assertDoesNotThrow(() -> sistemaCobranca.gerarCobranca(aluno));
    }

    @Test
    @DisplayName("Deve gerar cobrança para aluno com matrícula")
    void deveGerarCobrancaParaAlunoComMatricula() {
        aluno.matricula = "2023001";
        assertDoesNotThrow(() -> sistemaCobranca.gerarCobranca(aluno));
    }

    @Test
    @DisplayName("Deve gerar cobrança para aluno sem matrícula")
    void deveGerarCobrancaParaAlunoSemMatricula() {
        aluno.matricula = null;
        assertDoesNotThrow(() -> sistemaCobranca.gerarCobranca(aluno));
    }

    @Test
    @DisplayName("Deve gerar cobrança para aluno com matrícula vazia")
    void deveGerarCobrancaParaAlunoComMatriculaVazia() {
        aluno.matricula = "";
        assertDoesNotThrow(() -> sistemaCobranca.gerarCobranca(aluno));
    }

    @Test
    @DisplayName("Deve gerar cobrança para múltiplos alunos")
    void deveGerarCobrancaParaMultiplosAlunos() {
        Aluno aluno2 = new Aluno();
        aluno2.nome = "Maria Santos";
        aluno2.matricula = "2023002";
        
        assertDoesNotThrow(() -> {
            sistemaCobranca.gerarCobranca(aluno);
            sistemaCobranca.gerarCobranca(aluno2);
        });
    }

    @Test
    @DisplayName("Deve gerar cobrança para aluno com dados completos")
    void deveGerarCobrancaParaAlunoComDadosCompletos() {
        aluno.nome = "Pedro Costa";
        aluno.login = "pedro.costa";
        aluno.senha = "senha123";
        aluno.matricula = "2023003";
        
        assertDoesNotThrow(() -> sistemaCobranca.gerarCobranca(aluno));
    }
}

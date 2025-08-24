package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;

@DisplayName("Testes da classe SistemaMatriculas")
class SistemaMatriculasTest {

    private SistemaMatriculas sistema;
    private Usuario usuario;

    @BeforeEach
    void setUp() {
        sistema = new SistemaMatriculas();
        sistema.usuarios = new ArrayList<>();
        usuario = new Usuario() { };
        usuario.login = "teste";
        usuario.senha = "senha";
        sistema.usuarios.add(usuario);
    }

    @Test
    @DisplayName("Cria sistema de matrículas")
    void criaSistema() {
        assertNotNull(sistema);
    }

    @Test
    @DisplayName("Login retorna usuário quando credenciais corretas")
    void loginOk() {
        assertEquals(usuario, sistema.login("teste", "senha"));
    }

    @Test
    @DisplayName("Login retorna null quando credenciais incorretas")
    void loginFalhaCredenciaisIncorretas() {
        assertNull(sistema.login("teste", "senhaErrada"));
        assertNull(sistema.login("usuarioInexistente", "senha"));
    }

    @Test
    @DisplayName("Login retorna null quando login está incorreto")
    void loginFalhaLoginIncorreto() {
        assertNull(sistema.login("loginErrado", "senha"));
    }

    @Test
    @DisplayName("Login retorna null quando senha está incorreta")
    void loginFalhaSenhaIncorreta() {
        assertNull(sistema.login("teste", "senhaErrada"));
    }

    @Test
    @DisplayName("Login retorna null quando credenciais são nulas")
    void loginFalhaCredenciaisNulas() {
        assertNull(sistema.login(null, null));
        assertNull(sistema.login("teste", null));
        assertNull(sistema.login(null, "senha"));
    }

    @Test
    @DisplayName("Login retorna null quando credenciais estão vazias")
    void loginFalhaCredenciaisVazias() {
        assertNull(sistema.login("", ""));
        assertNull(sistema.login("teste", ""));
        assertNull(sistema.login("", "senha"));
    }

    @Test
    @DisplayName("Deve funcionar com múltiplos usuários")
    void deveFuncionarComMultiplosUsuarios() {
        Usuario usuario2 = new Usuario() { };
        usuario2.login = "usuario2";
        usuario2.senha = "senha2";
        sistema.usuarios.add(usuario2);

        assertEquals(usuario, sistema.login("teste", "senha"));
        assertEquals(usuario2, sistema.login("usuario2", "senha2"));
        assertNull(sistema.login("usuario2", "senha"));
    }

    @Test
    @DisplayName("Deve funcionar com lista de usuários vazia")
    void deveFuncionarComListaVazia() {
        sistema.usuarios.clear();
        assertNull(sistema.login("teste", "senha"));
    }
}

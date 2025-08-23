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
    @DisplayName("Login retorna null quando credenciais incorretas ou nulas")
    void loginFalha() {
        assertNull(sistema.login("teste", "x"));
        assertNull(sistema.login("", ""));
        assertNull(sistema.login(null, null));
    }
}

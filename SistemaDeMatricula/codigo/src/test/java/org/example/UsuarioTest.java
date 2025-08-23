package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Testes da classe Usuario")
class UsuarioTest {

    private Usuario usuario;

    @BeforeEach
    void setUp() {
        usuario = new Usuario() { };
        usuario.nome = "João Silva";
        usuario.login = "joao.silva";
        usuario.senha = "senha123";
    }

    @Test
    @DisplayName("Deve criar usuário com dados corretos")
    void deveCriarUsuarioComDadosCorretos() {
        assertEquals("João Silva", usuario.nome);
        assertEquals("joao.silva", usuario.login);
        assertEquals("senha123", usuario.senha);
    }

    @Test
    @DisplayName("Deve autenticar com senha correta")
    void deveAutenticarComSenhaCorreta() {
        assertTrue(usuario.autenticar("senha123"));
    }

    @Test
    @DisplayName("Não deve autenticar com senha incorreta")
    void naoDeveAutenticarComSenhaIncorreta() {
        assertFalse(usuario.autenticar("senhaErrada"));
        assertFalse(usuario.autenticar(""));
        assertFalse(usuario.autenticar(null));
    }

    @Test
    @DisplayName("Permite alterar campos diretamente")
    void permiteAlterarCamposDiretamente() {
        usuario.nome = "João Silva Santos";
        usuario.login = "joao.silva.santos";
        usuario.senha = "novaSenha456";
        assertEquals("João Silva Santos", usuario.nome);
        assertEquals("joao.silva.santos", usuario.login);
        assertTrue(usuario.autenticar("novaSenha456"));
    }
}

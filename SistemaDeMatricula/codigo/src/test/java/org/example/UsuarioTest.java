package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes para a classe Usuario
 */
@DisplayName("Testes da classe Usuario")
class UsuarioTest {

    private Usuario usuario;

    @BeforeEach
    void setUp() {
        // Criando uma instância concreta de Usuario para teste
        usuario = new Usuario("João Silva", "joao.silva", "senha123") {
            // Implementação mínima para teste
        };
    }

    @Test
    @DisplayName("Deve criar usuário com dados corretos")
    void deveCriarUsuarioComDadosCorretos() {
        assertEquals("João Silva", usuario.getNome());
        assertEquals("joao.silva", usuario.getLogin());
        assertEquals("senha123", usuario.getSenha());
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
    @DisplayName("Deve permitir alteração de nome")
    void devePermitirAlteracaoDeNome() {
        usuario.setNome("João Silva Santos");
        assertEquals("João Silva Santos", usuario.getNome());
    }

    @Test
    @DisplayName("Deve permitir alteração de login")
    void devePermitirAlteracaoDeLogin() {
        usuario.setLogin("joao.silva.santos");
        assertEquals("joao.silva.santos", usuario.getLogin());
    }

    @Test
    @DisplayName("Deve permitir alteração de senha")
    void devePermitirAlteracaoDeSenha() {
        usuario.setSenha("novaSenha456");
        assertEquals("novaSenha456", usuario.getSenha());
        assertTrue(usuario.autenticar("novaSenha456"));
        assertFalse(usuario.autenticar("senha123"));
    }

    @Test
    @DisplayName("Deve lidar com valores nulos nos setters")
    void deveLidarComValoresNulosNosSetters() {
        usuario.setNome(null);
        usuario.setLogin(null);
        usuario.setSenha(null);
        
        assertNull(usuario.getNome());
        assertNull(usuario.getLogin());
        assertNull(usuario.getSenha());
    }
}

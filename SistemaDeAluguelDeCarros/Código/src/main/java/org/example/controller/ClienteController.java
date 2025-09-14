package org.example.controller;

import org.example.model.Cliente;
import org.example.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Controlador REST para operações CRUD de Cliente
 * Implementa endpoints web baseados nas histórias de usuário
 */
@RestController
@RequestMapping("/api/clientes")
@CrossOrigin(origins = "*") // Para permitir acesso de diferentes origens
public class ClienteController {
    
    @Autowired
    private ClienteService clienteService;
    
    /**
     * Cadastra um novo cliente
     * POST /api/clientes
     * Atende à história: "Quero me cadastrar no sistema fornecendo meus dados pessoais"
     */
    @PostMapping
    public ResponseEntity<?> cadastrarCliente(@Valid @RequestBody Cliente cliente) {
        try {
            Cliente clienteCadastrado = clienteService.cadastrarCliente(cliente);
            return ResponseEntity.status(HttpStatus.CREATED).body(clienteCadastrado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Erro interno do servidor"));
        }
    }
    
    /**
     * Lista todos os clientes
     * GET /api/clientes
     */
    @GetMapping
    public ResponseEntity<List<Cliente>> listarTodos() {
        List<Cliente> clientes = clienteService.listarTodos();
        return ResponseEntity.ok(clientes);
    }
    
    /**
     * Lista apenas clientes ativos
     * GET /api/clientes/ativos
     */
    @GetMapping("/ativos")
    public ResponseEntity<List<Cliente>> listarAtivos() {
        List<Cliente> clientes = clienteService.listarAtivos();
        return ResponseEntity.ok(clientes);
    }
    
    /**
     * Lista apenas clientes inativos
     * GET /api/clientes/inativos
     */
    @GetMapping("/inativos")
    public ResponseEntity<List<Cliente>> listarInativos() {
        List<Cliente> clientes = clienteService.listarInativos();
        return ResponseEntity.ok(clientes);
    }
    
    /**
     * Busca cliente por ID
     * GET /api/clientes/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        Optional<Cliente> cliente = clienteService.buscarPorId(id);
        if (cliente.isPresent()) {
            return ResponseEntity.ok(cliente.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    /**
     * Busca cliente por email
     * GET /api/clientes/email/{email}
     */
    @GetMapping("/email/{email}")
    public ResponseEntity<?> buscarPorEmail(@PathVariable String email) {
        Optional<Cliente> cliente = clienteService.buscarPorEmail(email);
        if (cliente.isPresent()) {
            return ResponseEntity.ok(cliente.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    /**
     * Busca cliente por CPF
     * GET /api/clientes/cpf/{cpf}
     */
    @GetMapping("/cpf/{cpf}")
    public ResponseEntity<?> buscarPorCpf(@PathVariable String cpf) {
        Optional<Cliente> cliente = clienteService.buscarPorCpf(cpf);
        if (cliente.isPresent()) {
            return ResponseEntity.ok(cliente.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    /**
     * Busca clientes por nome (busca parcial)
     * GET /api/clientes/buscar?nome={nome}
     */
    @GetMapping("/buscar")
    public ResponseEntity<List<Cliente>> buscarPorNome(@RequestParam String nome) {
        List<Cliente> clientes = clienteService.buscarPorNome(nome);
        return ResponseEntity.ok(clientes);
    }
    
    /**
     * Busca clientes por cidade
     * GET /api/clientes/cidade/{cidade}
     */
    @GetMapping("/cidade/{cidade}")
    public ResponseEntity<List<Cliente>> buscarPorCidade(@PathVariable String cidade) {
        List<Cliente> clientes = clienteService.buscarPorCidade(cidade);
        return ResponseEntity.ok(clientes);
    }
    
    /**
     * Busca clientes por estado
     * GET /api/clientes/estado/{estado}
     */
    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<Cliente>> buscarPorEstado(@PathVariable String estado) {
        List<Cliente> clientes = clienteService.buscarPorEstado(estado);
        return ResponseEntity.ok(clientes);
    }
    
    /**
     * Atualiza dados de um cliente
     * PUT /api/clientes/{id}
     * Atende à história: "Quero atualizar meus dados pessoais e profissionais"
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> atualizarCliente(@PathVariable Long id, @Valid @RequestBody Cliente cliente) {
        try {
            Cliente clienteAtualizado = clienteService.atualizarCliente(id, cliente);
            return ResponseEntity.ok(clienteAtualizado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Erro interno do servidor"));
        }
    }
    
    /**
     * Autentica cliente por email e senha
     * POST /api/clientes/login
     * Atende à história: "Quero fazer login no sistema com meu email e senha"
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            Optional<Cliente> cliente = clienteService.autenticarCliente(
                    loginRequest.getEmail(), 
                    loginRequest.getSenha()
            );
            
            if (cliente.isPresent()) {
                return ResponseEntity.ok(new LoginResponse(true, "Login realizado com sucesso", cliente.get()));
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(new LoginResponse(false, "Email ou senha inválidos", null));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Erro interno do servidor"));
        }
    }
    
    /**
     * Ativa um cliente
     * PUT /api/clientes/{id}/ativar
     */
    @PutMapping("/{id}/ativar")
    public ResponseEntity<?> ativarCliente(@PathVariable Long id) {
        try {
            Cliente cliente = clienteService.ativarCliente(id);
            return ResponseEntity.ok(cliente);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Erro interno do servidor"));
        }
    }
    
    /**
     * Desativa um cliente (soft delete)
     * PUT /api/clientes/{id}/desativar
     */
    @PutMapping("/{id}/desativar")
    public ResponseEntity<?> desativarCliente(@PathVariable Long id) {
        try {
            Cliente cliente = clienteService.desativarCliente(id);
            return ResponseEntity.ok(cliente);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Erro interno do servidor"));
        }
    }
    
    /**
     * Remove cliente permanentemente
     * DELETE /api/clientes/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> removerCliente(@PathVariable Long id) {
        try {
            clienteService.removerCliente(id);
            return ResponseEntity.ok(new SuccessResponse("Cliente removido com sucesso"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Erro interno do servidor"));
        }
    }
    
    /**
     * Obtém estatísticas dos clientes
     * GET /api/clientes/estatisticas
     */
    @GetMapping("/estatisticas")
    public ResponseEntity<EstatisticasResponse> obterEstatisticas() {
        long ativos = clienteService.contarAtivos();
        long inativos = clienteService.contarInativos();
        
        EstatisticasResponse estatisticas = new EstatisticasResponse(ativos, inativos, ativos + inativos);
        return ResponseEntity.ok(estatisticas);
    }
    
    /**
     * Busca clientes por período de cadastro
     * GET /api/clientes/periodo?inicio={dataInicio}&fim={dataFim}
     */
    @GetMapping("/periodo")
    public ResponseEntity<List<Cliente>> buscarPorPeriodo(
            @RequestParam LocalDateTime inicio, 
            @RequestParam LocalDateTime fim) {
        List<Cliente> clientes = clienteService.buscarPorPeriodoCadastro(inicio, fim);
        return ResponseEntity.ok(clientes);
    }
    
    // Classes auxiliares para responses
    public static class ErrorResponse {
        private final String message;
        private final LocalDateTime timestamp;
        
        public ErrorResponse(String message) {
            this.message = message;
            this.timestamp = LocalDateTime.now();
        }
        
        public String getMessage() { return message; }
        public LocalDateTime getTimestamp() { return timestamp; }
    }
    
    public static class SuccessResponse {
        private final String message;
        private final LocalDateTime timestamp;
        
        public SuccessResponse(String message) {
            this.message = message;
            this.timestamp = LocalDateTime.now();
        }
        
        public String getMessage() { return message; }
        public LocalDateTime getTimestamp() { return timestamp; }
    }
    
    public static class LoginRequest {
        private String email;
        private String senha;
        
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
        public String getSenha() { return senha; }
        public void setSenha(String senha) { this.senha = senha; }
    }
    
    public static class LoginResponse {
        private final boolean success;
        private final String message;
        private final Cliente cliente;
        
        public LoginResponse(boolean success, String message, Cliente cliente) {
            this.success = success;
            this.message = message;
            this.cliente = cliente;
        }
        
        public boolean isSuccess() { return success; }
        public String getMessage() { return message; }
        public Cliente getCliente() { return cliente; }
    }
    
    public static class EstatisticasResponse {
        private final long clientesAtivos;
        private final long clientesInativos;
        private final long totalClientes;
        
        public EstatisticasResponse(long clientesAtivos, long clientesInativos, long totalClientes) {
            this.clientesAtivos = clientesAtivos;
            this.clientesInativos = clientesInativos;
            this.totalClientes = totalClientes;
        }
        
        public long getClientesAtivos() { return clientesAtivos; }
        public long getClientesInativos() { return clientesInativos; }
        public long getTotalClientes() { return totalClientes; }
    }
}

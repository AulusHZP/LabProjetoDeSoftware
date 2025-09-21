package org.example.controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.example.model.Automovel;
import org.example.model.Cliente;
import org.example.model.PedidoAluguel;
import org.example.service.AutomovelService;
import org.example.service.ClienteService;
import org.example.service.PedidoAluguelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

/**
 * Controlador REST para operações CRUD de PedidoAluguel
 * Implementa endpoints web baseados nas histórias de usuário do Cliente
 */
@RestController
@RequestMapping("/api/pedidos-aluguel")
@CrossOrigin(origins = "*") // Para permitir acesso de diferentes origens
public class PedidoAluguelController {
    
    @Autowired
    private PedidoAluguelService pedidoAluguelService;
    
    @Autowired
    private ClienteService clienteService;
    
    @Autowired
    private AutomovelService automovelService;
    
    /**
     * Cria um novo pedido de aluguel
     * POST /api/pedidos-aluguel
     * Atende à história: "Quero criar um pedido de aluguel selecionando o automóvel e período"
     */
    @PostMapping
    public ResponseEntity<?> criarPedidoAluguel(@Valid @RequestBody PedidoAluguel pedidoAluguel) {
        try {
            PedidoAluguel pedidoCriado = pedidoAluguelService.criarPedidoAluguel(pedidoAluguel);
            return ResponseEntity.status(HttpStatus.CREATED).body(pedidoCriado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Erro interno do servidor"));
        }
    }
    
    /**
     * Atualiza um pedido de aluguel
     * PUT /api/pedidos-aluguel/{id}
     * Atende à história: "Quero modificar detalhes do meu pedido de aluguel"
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> atualizarPedidoAluguel(@PathVariable Long id, @Valid @RequestBody PedidoAluguel pedidoAluguel) {
        try {
            PedidoAluguel pedidoAtualizado = pedidoAluguelService.modificarPedidoAluguel(id, pedidoAluguel);
            return ResponseEntity.ok(pedidoAtualizado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Erro interno do servidor"));
        }
    }
    
    /**
     * Cancela um pedido de aluguel
     * PUT /api/pedidos-aluguel/{id}/cancelar
     * Atende à história: "Quero cancelar um pedido de aluguel"
     */
    @PutMapping("/{id}/cancelar")
    public ResponseEntity<?> cancelarPedidoAluguel(@PathVariable Long id) {
        try {
            PedidoAluguel pedidoCancelado = pedidoAluguelService.cancelarPedidoAluguel(id);
            return ResponseEntity.ok(pedidoCancelado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Erro interno do servidor"));
        }
    }
    
    /**
     * Aprova um pedido de aluguel
     * PUT /api/pedidos-aluguel/{id}/aprovar
     */
    @PutMapping("/{id}/aprovar")
    public ResponseEntity<?> aprovarPedidoAluguel(@PathVariable Long id) {
        try {
            PedidoAluguel pedidoAprovado = pedidoAluguelService.aprovarPedidoAluguel(id);
            return ResponseEntity.ok(pedidoAprovado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Erro interno do servidor"));
        }
    }
    
    /**
     * Rejeita um pedido de aluguel
     * PUT /api/pedidos-aluguel/{id}/rejeitar
     */
    @PutMapping("/{id}/rejeitar")
    public ResponseEntity<?> rejeitarPedidoAluguel(@PathVariable Long id, @RequestBody RejeicaoRequest request) {
        try {
            PedidoAluguel pedidoRejeitado = pedidoAluguelService.rejeitarPedidoAluguel(id, request.getMotivo());
            return ResponseEntity.ok(pedidoRejeitado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Erro interno do servidor"));
        }
    }
    
    /**
     * Finaliza um pedido de aluguel
     * PUT /api/pedidos-aluguel/{id}/finalizar
     */
    @PutMapping("/{id}/finalizar")
    public ResponseEntity<?> finalizarPedidoAluguel(@PathVariable Long id) {
        try {
            PedidoAluguel pedidoFinalizado = pedidoAluguelService.finalizarPedidoAluguel(id);
            return ResponseEntity.ok(pedidoFinalizado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Erro interno do servidor"));
        }
    }
    
    /**
     * Lista todos os pedidos
     * GET /api/pedidos-aluguel
     */
    @GetMapping
    public ResponseEntity<List<PedidoAluguel>> listarTodos() {
        List<PedidoAluguel> pedidos = pedidoAluguelService.listarTodos();
        return ResponseEntity.ok(pedidos);
    }
    
    /**
     * Busca pedido por ID
     * GET /api/pedidos-aluguel/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        Optional<PedidoAluguel> pedido = pedidoAluguelService.buscarPorId(id);
        if (pedido.isPresent()) {
            return ResponseEntity.ok(pedido.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    /**
     * Lista pedidos por cliente
     * GET /api/pedidos-aluguel/cliente/{clienteId}
     */
    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<?> listarPorCliente(@PathVariable Long clienteId) {
        try {
            Cliente cliente = clienteService.buscarPorId(clienteId)
                    .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado"));
            List<PedidoAluguel> pedidos = pedidoAluguelService.listarPorCliente(cliente);
            return ResponseEntity.ok(pedidos);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
        }
    }
    
    /**
     * Lista pedidos por automóvel
     * GET /api/pedidos-aluguel/automovel/{automovelId}
     */
    @GetMapping("/automovel/{automovelId}")
    public ResponseEntity<?> listarPorAutomovel(@PathVariable Long automovelId) {
        try {
            Automovel automovel = automovelService.buscarPorId(automovelId)
                    .orElseThrow(() -> new IllegalArgumentException("Automóvel não encontrado"));
            List<PedidoAluguel> pedidos = pedidoAluguelService.listarPorAutomovel(automovel);
            return ResponseEntity.ok(pedidos);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
        }
    }
    
    /**
     * Lista pedidos por status
     * GET /api/pedidos-aluguel/status/{status}
     */
    @GetMapping("/status/{status}")
    public ResponseEntity<List<PedidoAluguel>> listarPorStatus(@PathVariable String status) {
        try {
            PedidoAluguel.StatusPedido statusEnum = PedidoAluguel.StatusPedido.valueOf(status.toUpperCase());
            List<PedidoAluguel> pedidos = pedidoAluguelService.listarPorStatus(statusEnum);
            return ResponseEntity.ok(pedidos);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    /**
     * Lista pedidos pendentes
     * GET /api/pedidos-aluguel/pendentes
     */
    @GetMapping("/pendentes")
    public ResponseEntity<List<PedidoAluguel>> listarPendentes() {
        List<PedidoAluguel> pedidos = pedidoAluguelService.listarPendentes();
        return ResponseEntity.ok(pedidos);
    }
    
    /**
     * Lista pedidos aprovados
     * GET /api/pedidos-aluguel/aprovados
     */
    @GetMapping("/aprovados")
    public ResponseEntity<List<PedidoAluguel>> listarAprovados() {
        List<PedidoAluguel> pedidos = pedidoAluguelService.listarAprovados();
        return ResponseEntity.ok(pedidos);
    }
    
    /**
     * Lista pedidos rejeitados
     * GET /api/pedidos-aluguel/rejeitados
     */
    @GetMapping("/rejeitados")
    public ResponseEntity<List<PedidoAluguel>> listarRejeitados() {
        List<PedidoAluguel> pedidos = pedidoAluguelService.listarRejeitados();
        return ResponseEntity.ok(pedidos);
    }
    
    /**
     * Lista pedidos cancelados
     * GET /api/pedidos-aluguel/cancelados
     */
    @GetMapping("/cancelados")
    public ResponseEntity<List<PedidoAluguel>> listarCancelados() {
        List<PedidoAluguel> pedidos = pedidoAluguelService.listarCancelados();
        return ResponseEntity.ok(pedidos);
    }
    
    /**
     * Lista pedidos finalizados
     * GET /api/pedidos-aluguel/finalizados
     */
    @GetMapping("/finalizados")
    public ResponseEntity<List<PedidoAluguel>> listarFinalizados() {
        List<PedidoAluguel> pedidos = pedidoAluguelService.listarFinalizados();
        return ResponseEntity.ok(pedidos);
    }
    
    /**
     * Lista pedidos por período
     * GET /api/pedidos-aluguel/periodo?inicio={dataInicio}&fim={dataFim}
     */
    @GetMapping("/periodo")
    public ResponseEntity<List<PedidoAluguel>> listarPorPeriodo(
            @RequestParam LocalDate inicio, 
            @RequestParam LocalDate fim) {
        List<PedidoAluguel> pedidos = pedidoAluguelService.listarPorPeriodo(inicio, fim);
        return ResponseEntity.ok(pedidos);
    }
    
    /**
     * Lista pedidos por faixa de valor
     * GET /api/pedidos-aluguel/valor?minimo={valorMinimo}&maximo={valorMaximo}
     */
    @GetMapping("/valor")
    public ResponseEntity<List<PedidoAluguel>> listarPorFaixaValor(
            @RequestParam BigDecimal minimo, 
            @RequestParam BigDecimal maximo) {
        List<PedidoAluguel> pedidos = pedidoAluguelService.listarPorFaixaValor(minimo, maximo);
        return ResponseEntity.ok(pedidos);
    }
    
    /**
     * Obtém estatísticas dos pedidos
     * GET /api/pedidos-aluguel/estatisticas
     */
    @GetMapping("/estatisticas")
    public ResponseEntity<EstatisticasResponse> obterEstatisticas() {
        long pendentes = pedidoAluguelService.contarPorStatus(PedidoAluguel.StatusPedido.PENDENTE);
        long aprovados = pedidoAluguelService.contarPorStatus(PedidoAluguel.StatusPedido.APROVADO);
        long rejeitados = pedidoAluguelService.contarPorStatus(PedidoAluguel.StatusPedido.REJEITADO);
        long cancelados = pedidoAluguelService.contarPorStatus(PedidoAluguel.StatusPedido.CANCELADO);
        long finalizados = pedidoAluguelService.contarPorStatus(PedidoAluguel.StatusPedido.FINALIZADO);
        
        BigDecimal valorTotalAprovados = pedidoAluguelService.calcularValorTotalPorStatus(PedidoAluguel.StatusPedido.APROVADO);
        BigDecimal valorTotalFinalizados = pedidoAluguelService.calcularValorTotalPorStatus(PedidoAluguel.StatusPedido.FINALIZADO);
        
        EstatisticasResponse estatisticas = new EstatisticasResponse(
                pendentes, aprovados, rejeitados, cancelados, finalizados,
                valorTotalAprovados, valorTotalFinalizados
        );
        return ResponseEntity.ok(estatisticas);
    }
    
    /**
     * Obtém estatísticas de pedidos por cliente
     * GET /api/pedidos-aluguel/cliente/{clienteId}/estatisticas
     */
    @GetMapping("/cliente/{clienteId}/estatisticas")
    public ResponseEntity<?> obterEstatisticasPorCliente(@PathVariable Long clienteId) {
        try {
            Cliente cliente = clienteService.buscarPorId(clienteId)
                    .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado"));
            
            long totalPedidos = pedidoAluguelService.contarPorCliente(cliente);
            BigDecimal valorTotal = pedidoAluguelService.calcularValorTotalPorCliente(cliente);
            
            EstatisticasClienteResponse estatisticas = new EstatisticasClienteResponse(
                    clienteId, cliente.getNome(), totalPedidos, valorTotal
            );
            return ResponseEntity.ok(estatisticas);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
        }
    }
    
    // Classes auxiliares para requests e responses
    public static class RejeicaoRequest {
        private String motivo;
        
        public String getMotivo() { return motivo; }
        public void setMotivo(String motivo) { this.motivo = motivo; }
    }
    
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
    
    public static class EstatisticasResponse {
        private final long pedidosPendentes;
        private final long pedidosAprovados;
        private final long pedidosRejeitados;
        private final long pedidosCancelados;
        private final long pedidosFinalizados;
        private final BigDecimal valorTotalAprovados;
        private final BigDecimal valorTotalFinalizados;
        
        public EstatisticasResponse(long pedidosPendentes, long pedidosAprovados, long pedidosRejeitados,
                                   long pedidosCancelados, long pedidosFinalizados,
                                   BigDecimal valorTotalAprovados, BigDecimal valorTotalFinalizados) {
            this.pedidosPendentes = pedidosPendentes;
            this.pedidosAprovados = pedidosAprovados;
            this.pedidosRejeitados = pedidosRejeitados;
            this.pedidosCancelados = pedidosCancelados;
            this.pedidosFinalizados = pedidosFinalizados;
            this.valorTotalAprovados = valorTotalAprovados;
            this.valorTotalFinalizados = valorTotalFinalizados;
        }
        
        public long getPedidosPendentes() { return pedidosPendentes; }
        public long getPedidosAprovados() { return pedidosAprovados; }
        public long getPedidosRejeitados() { return pedidosRejeitados; }
        public long getPedidosCancelados() { return pedidosCancelados; }
        public long getPedidosFinalizados() { return pedidosFinalizados; }
        public BigDecimal getValorTotalAprovados() { return valorTotalAprovados; }
        public BigDecimal getValorTotalFinalizados() { return valorTotalFinalizados; }
    }
    
    public static class EstatisticasClienteResponse {
        private final Long clienteId;
        private final String nomeCliente;
        private final long totalPedidos;
        private final BigDecimal valorTotal;
        
        public EstatisticasClienteResponse(Long clienteId, String nomeCliente, long totalPedidos, BigDecimal valorTotal) {
            this.clienteId = clienteId;
            this.nomeCliente = nomeCliente;
            this.totalPedidos = totalPedidos;
            this.valorTotal = valorTotal;
        }
        
        public Long getClienteId() { return clienteId; }
        public String getNomeCliente() { return nomeCliente; }
        public long getTotalPedidos() { return totalPedidos; }
        public BigDecimal getValorTotal() { return valorTotal; }
    }
}

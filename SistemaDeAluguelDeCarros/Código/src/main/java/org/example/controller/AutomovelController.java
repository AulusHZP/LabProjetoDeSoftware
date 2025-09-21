package org.example.controller;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.example.model.Automovel;
import org.example.service.AutomovelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
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
 * Controlador REST para operações CRUD de Automóvel
 * Implementa endpoints web baseados nas histórias de usuário do Administrador
 */
@RestController
@RequestMapping("/api/automoveis")
@CrossOrigin(origins = "*") // Para permitir acesso de diferentes origens
public class AutomovelController {
    
    @Autowired
    private AutomovelService automovelService;
    
    /**
     * Cadastra um novo automóvel
     * POST /api/automoveis
     * Atende à história: "Quero cadastrar novos automóveis no sistema"
     */
    @PostMapping
    public ResponseEntity<?> cadastrarAutomovel(@Valid @RequestBody Automovel automovel) {
        try {
            Automovel automovelCadastrado = automovelService.cadastrarAutomovel(automovel);
            return ResponseEntity.status(HttpStatus.CREATED).body(automovelCadastrado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Erro interno do servidor"));
        }
    }
    
    /**
     * Lista todos os automóveis
     * GET /api/automoveis
     */
    @GetMapping
    public ResponseEntity<List<Automovel>> listarTodos() {
        List<Automovel> automoveis = automovelService.listarTodos();
        return ResponseEntity.ok(automoveis);
    }
    
    /**
     * Lista apenas automóveis ativos
     * GET /api/automoveis/ativos
     */
    @GetMapping("/ativos")
    public ResponseEntity<List<Automovel>> listarAtivos() {
        List<Automovel> automoveis = automovelService.listarAtivos();
        return ResponseEntity.ok(automoveis);
    }
    
    /**
     * Lista apenas automóveis disponíveis
     * GET /api/automoveis/disponiveis
     */
    @GetMapping("/disponiveis")
    public ResponseEntity<List<Automovel>> listarDisponiveis() {
        List<Automovel> automoveis = automovelService.listarDisponiveis();
        return ResponseEntity.ok(automoveis);
    }
    
    /**
     * Busca automóvel por ID
     * GET /api/automoveis/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        Optional<Automovel> automovel = automovelService.buscarPorId(id);
        if (automovel.isPresent()) {
            return ResponseEntity.ok(automovel.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    /**
     * Busca automóvel por placa
     * GET /api/automoveis/placa/{placa}
     */
    @GetMapping("/placa/{placa}")
    public ResponseEntity<?> buscarPorPlaca(@PathVariable String placa) {
        Optional<Automovel> automovel = automovelService.buscarPorPlaca(placa);
        if (automovel.isPresent()) {
            return ResponseEntity.ok(automovel.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    /**
     * Busca automóveis por marca
     * GET /api/automoveis/marca/{marca}
     */
    @GetMapping("/marca/{marca}")
    public ResponseEntity<List<Automovel>> buscarPorMarca(@PathVariable String marca) {
        List<Automovel> automoveis = automovelService.buscarPorMarca(marca);
        return ResponseEntity.ok(automoveis);
    }
    
    /**
     * Busca automóveis por modelo
     * GET /api/automoveis/modelo/{modelo}
     */
    @GetMapping("/modelo/{modelo}")
    public ResponseEntity<List<Automovel>> buscarPorModelo(@PathVariable String modelo) {
        List<Automovel> automoveis = automovelService.buscarPorModelo(modelo);
        return ResponseEntity.ok(automoveis);
    }
    
    /**
     * Busca automóveis por categoria
     * GET /api/automoveis/categoria/{categoria}
     */
    @GetMapping("/categoria/{categoria}")
    public ResponseEntity<List<Automovel>> buscarPorCategoria(@PathVariable String categoria) {
        List<Automovel> automoveis = automovelService.buscarPorCategoria(categoria);
        return ResponseEntity.ok(automoveis);
    }
    
    /**
     * Busca automóveis disponíveis por categoria
     * GET /api/automoveis/categoria/{categoria}/disponiveis
     */
    @GetMapping("/categoria/{categoria}/disponiveis")
    public ResponseEntity<List<Automovel>> buscarDisponiveisPorCategoria(@PathVariable String categoria) {
        List<Automovel> automoveis = automovelService.buscarDisponiveisPorCategoria(categoria);
        return ResponseEntity.ok(automoveis);
    }
    
    /**
     * Busca automóveis por faixa de preço
     * GET /api/automoveis/preco?minimo={minimo}&maximo={maximo}
     */
    @GetMapping("/preco")
    public ResponseEntity<List<Automovel>> buscarPorFaixaPreco(
            @RequestParam BigDecimal minimo, 
            @RequestParam BigDecimal maximo) {
        List<Automovel> automoveis = automovelService.buscarPorFaixaPreco(minimo, maximo);
        return ResponseEntity.ok(automoveis);
    }
    
    /**
     * Busca automóveis por ano
     * GET /api/automoveis/ano/{ano}
     */
    @GetMapping("/ano/{ano}")
    public ResponseEntity<List<Automovel>> buscarPorAno(@PathVariable String ano) {
        List<Automovel> automoveis = automovelService.buscarPorAno(ano);
        return ResponseEntity.ok(automoveis);
    }
    
    /**
     * Busca automóveis por cor
     * GET /api/automoveis/cor/{cor}
     */
    @GetMapping("/cor/{cor}")
    public ResponseEntity<List<Automovel>> buscarPorCor(@PathVariable String cor) {
        List<Automovel> automoveis = automovelService.buscarPorCor(cor);
        return ResponseEntity.ok(automoveis);
    }
    
    /**
     * Busca automóveis por tipo de combustível
     * GET /api/automoveis/combustivel/{tipo}
     */
    @GetMapping("/combustivel/{tipo}")
    public ResponseEntity<List<Automovel>> buscarPorTipoCombustivel(@PathVariable String tipo) {
        List<Automovel> automoveis = automovelService.buscarPorTipoCombustivel(tipo);
        return ResponseEntity.ok(automoveis);
    }
    
    /**
     * Busca automóveis por transmissão
     * GET /api/automoveis/transmissao/{transmissao}
     */
    @GetMapping("/transmissao/{transmissao}")
    public ResponseEntity<List<Automovel>> buscarPorTransmissao(@PathVariable String transmissao) {
        List<Automovel> automoveis = automovelService.buscarPorTransmissao(transmissao);
        return ResponseEntity.ok(automoveis);
    }
    
    /**
     * Busca automóveis com ar condicionado
     * GET /api/automoveis/com-ar-condicionado
     */
    @GetMapping("/com-ar-condicionado")
    public ResponseEntity<List<Automovel>> buscarComArCondicionado() {
        List<Automovel> automoveis = automovelService.buscarComArCondicionado();
        return ResponseEntity.ok(automoveis);
    }
    
    /**
     * Busca automóveis com direção hidráulica
     * GET /api/automoveis/com-direcao-hidraulica
     */
    @GetMapping("/com-direcao-hidraulica")
    public ResponseEntity<List<Automovel>> buscarComDirecaoHidraulica() {
        List<Automovel> automoveis = automovelService.buscarComDirecaoHidraulica();
        return ResponseEntity.ok(automoveis);
    }
    
    /**
     * Busca automóveis com airbag
     * GET /api/automoveis/com-airbag
     */
    @GetMapping("/com-airbag")
    public ResponseEntity<List<Automovel>> buscarComAirbag() {
        List<Automovel> automoveis = automovelService.buscarComAirbag();
        return ResponseEntity.ok(automoveis);
    }
    
    /**
     * Busca automóveis com ABS
     * GET /api/automoveis/com-abs
     */
    @GetMapping("/com-abs")
    public ResponseEntity<List<Automovel>> buscarComAbs() {
        List<Automovel> automoveis = automovelService.buscarComAbs();
        return ResponseEntity.ok(automoveis);
    }
    
    /**
     * Busca automóveis por capacidade mínima
     * GET /api/automoveis/capacidade/{capacidade}
     */
    @GetMapping("/capacidade/{capacidade}")
    public ResponseEntity<List<Automovel>> buscarPorCapacidadeMinima(@PathVariable Integer capacidade) {
        List<Automovel> automoveis = automovelService.buscarPorCapacidadeMinima(capacidade);
        return ResponseEntity.ok(automoveis);
    }
    
    /**
     * Busca automóveis por quilometragem máxima
     * GET /api/automoveis/quilometragem/{quilometragem}
     */
    @GetMapping("/quilometragem/{quilometragem}")
    public ResponseEntity<List<Automovel>> buscarPorQuilometragemMaxima(@PathVariable Long quilometragem) {
        List<Automovel> automoveis = automovelService.buscarPorQuilometragemMaxima(quilometragem);
        return ResponseEntity.ok(automoveis);
    }
    
    /**
     * Atualiza dados de um automóvel
     * PUT /api/automoveis/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> atualizarAutomovel(@PathVariable Long id, @Valid @RequestBody Automovel automovel) {
        try {
            Automovel automovelAtualizado = automovelService.atualizarAutomovel(id, automovel);
            return ResponseEntity.ok(automovelAtualizado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Erro interno do servidor"));
        }
    }
    
    /**
     * Ativa um automóvel
     * PUT /api/automoveis/{id}/ativar
     */
    @PutMapping("/{id}/ativar")
    public ResponseEntity<?> ativarAutomovel(@PathVariable Long id) {
        try {
            Automovel automovel = automovelService.ativarAutomovel(id);
            return ResponseEntity.ok(automovel);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Erro interno do servidor"));
        }
    }
    
    /**
     * Desativa um automóvel (soft delete)
     * PUT /api/automoveis/{id}/desativar
     */
    @PutMapping("/{id}/desativar")
    public ResponseEntity<?> desativarAutomovel(@PathVariable Long id) {
        try {
            Automovel automovel = automovelService.desativarAutomovel(id);
            return ResponseEntity.ok(automovel);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Erro interno do servidor"));
        }
    }
    
    /**
     * Disponibiliza um automóvel para aluguel
     * PUT /api/automoveis/{id}/disponibilizar
     */
    @PutMapping("/{id}/disponibilizar")
    public ResponseEntity<?> disponibilizarAutomovel(@PathVariable Long id) {
        try {
            Automovel automovel = automovelService.disponibilizarAutomovel(id);
            return ResponseEntity.ok(automovel);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Erro interno do servidor"));
        }
    }
    
    /**
     * Indisponibiliza um automóvel para aluguel
     * PUT /api/automoveis/{id}/indisponibilizar
     */
    @PutMapping("/{id}/indisponibilizar")
    public ResponseEntity<?> indisponibilizarAutomovel(@PathVariable Long id) {
        try {
            Automovel automovel = automovelService.indisponibilizarAutomovel(id);
            return ResponseEntity.ok(automovel);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Erro interno do servidor"));
        }
    }
    
    /**
     * Remove automóvel permanentemente
     * DELETE /api/automoveis/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> removerAutomovel(@PathVariable Long id) {
        try {
            automovelService.removerAutomovel(id);
            return ResponseEntity.ok(new SuccessResponse("Automóvel removido com sucesso"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Erro interno do servidor"));
        }
    }
    
    /**
     * Obtém estatísticas dos automóveis
     * GET /api/automoveis/estatisticas
     */
    @GetMapping("/estatisticas")
    public ResponseEntity<EstatisticasResponse> obterEstatisticas() {
        long ativos = automovelService.contarAtivos();
        long disponiveis = automovelService.contarDisponiveis();
        
        EstatisticasResponse estatisticas = new EstatisticasResponse(ativos, disponiveis, ativos);
        return ResponseEntity.ok(estatisticas);
    }
    
    /**
     * Verifica se automóvel está disponível
     * GET /api/automoveis/{id}/disponivel
     */
    @GetMapping("/{id}/disponivel")
    public ResponseEntity<DisponibilidadeResponse> verificarDisponibilidade(@PathVariable Long id) {
        boolean disponivel = automovelService.isDisponivel(id);
        DisponibilidadeResponse response = new DisponibilidadeResponse(id, disponivel);
        return ResponseEntity.ok(response);
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
    
    public static class EstatisticasResponse {
        private final long automoveisAtivos;
        private final long automoveisDisponiveis;
        private final long totalAutomoveis;
        
        public EstatisticasResponse(long automoveisAtivos, long automoveisDisponiveis, long totalAutomoveis) {
            this.automoveisAtivos = automoveisAtivos;
            this.automoveisDisponiveis = automoveisDisponiveis;
            this.totalAutomoveis = totalAutomoveis;
        }
        
        public long getAutomoveisAtivos() { return automoveisAtivos; }
        public long getAutomoveisDisponiveis() { return automoveisDisponiveis; }
        public long getTotalAutomoveis() { return totalAutomoveis; }
    }
    
    public static class DisponibilidadeResponse {
        private final Long id;
        private final boolean disponivel;
        
        public DisponibilidadeResponse(Long id, boolean disponivel) {
            this.id = id;
            this.disponivel = disponivel;
        }
        
        public Long getId() { return id; }
        public boolean isDisponivel() { return disponivel; }
    }
}

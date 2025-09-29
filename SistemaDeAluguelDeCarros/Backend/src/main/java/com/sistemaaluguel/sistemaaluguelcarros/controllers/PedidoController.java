package com.sistemaaluguel.sistemaaluguelcarros.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sistemaaluguel.sistemaaluguelcarros.dto.PedidoDTO;
import com.sistemaaluguel.sistemaaluguelcarros.dto.PedidoResponseDTO;
import com.sistemaaluguel.sistemaaluguelcarros.entities.Pedido;
import com.sistemaaluguel.sistemaaluguelcarros.enums.StatusPedido;
import com.sistemaaluguel.sistemaaluguelcarros.repository.PedidoRepository;
import com.sistemaaluguel.sistemaaluguelcarros.services.PedidoService;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;
    
    @Autowired
    private PedidoRepository pedidoRepository;

    @GetMapping("/test")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("Test endpoint working");
    }
    
    @GetMapping("/simple")
    public ResponseEntity<List<PedidoResponseDTO>> findAllSimple() {
        try {
            List<PedidoResponseDTO> pedidos = pedidoService.findAllAsDTO();
            return ResponseEntity.ok(pedidos);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @GetMapping("/debug")
    public ResponseEntity<String> debug() {
        try {
            List<Object[]> results = pedidoRepository.findAllWithDetails();
            return ResponseEntity.ok("Found " + results.size() + " results");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }
    
    @GetMapping
    public ResponseEntity<List<PedidoResponseDTO>> findAll() {
        try {
            List<PedidoResponseDTO> pedidos = pedidoService.findAllAsDTO();
            return ResponseEntity.ok(pedidos);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/by-id/{id}")
    public ResponseEntity<Pedido> findById(@PathVariable Long id) {
        Optional<Pedido> pedido = pedidoService.findById(id);
        return pedido.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    

    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<Pedido>> findByClienteId(@PathVariable Long clienteId) {
        List<Pedido> pedidos = pedidoService.findByClienteId(clienteId);
        return ResponseEntity.ok(pedidos);
    }

    @GetMapping("/agente/{agenteId}")
    public ResponseEntity<List<Pedido>> findByAgenteId(@PathVariable Long agenteId) {
        List<Pedido> pedidos = pedidoService.findByAgenteId(agenteId);
        return ResponseEntity.ok(pedidos);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<Pedido>> findByStatus(@PathVariable StatusPedido status) {
        List<Pedido> pedidos = pedidoService.findByStatus(status);
        return ResponseEntity.ok(pedidos);
    }

    @PostMapping
    public ResponseEntity<Pedido> create(@RequestBody PedidoDTO pedidoDTO) {
        Pedido savedPedido = pedidoService.save(pedidoDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedPedido);
    }

    @PutMapping("/{id}/aprovar")
    public ResponseEntity<Pedido> aprovar(@PathVariable Long id) {
        Pedido pedido = pedidoService.aprovarPedido(id);
        if (pedido != null) {
            return ResponseEntity.ok(pedido);
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}/rejeitar")
    public ResponseEntity<Pedido> rejeitar(@PathVariable Long id) {
        Pedido pedido = pedidoService.rejeitarPedido(id);
        if (pedido != null) {
            return ResponseEntity.ok(pedido);
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}/cancelar")
    public ResponseEntity<Pedido> cancelar(@PathVariable Long id) {
        Pedido pedido = pedidoService.cancelarPedido(id);
        if (pedido != null) {
            return ResponseEntity.ok(pedido);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        pedidoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

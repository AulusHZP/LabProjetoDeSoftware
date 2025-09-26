package com.sistemaaluguel.sistemaaluguelcarros.controllers;

import com.sistemaaluguel.sistemaaluguelcarros.entities.Pedido;
import com.sistemaaluguel.sistemaaluguelcarros.dto.PedidoDTO;
import com.sistemaaluguel.sistemaaluguelcarros.services.PedidoService;
import com.sistemaaluguel.sistemaaluguelcarros.enums.StatusPedido;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/pedidos")
@CrossOrigin(origins = "*")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @GetMapping
    public ResponseEntity<List<Pedido>> findAll() {
        List<Pedido> pedidos = pedidoService.findAll();
        return ResponseEntity.ok(pedidos);
    }

    @GetMapping("/{id}")
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
        Pedido pedido = new Pedido();
        pedido.setDataInicio(pedidoDTO.getDataInicio());
        pedido.setDataFim(pedidoDTO.getDataFim());
        
        Pedido savedPedido = pedidoService.save(pedido);
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

package com.sistemaaluguel.sistemaaluguelcarros.services;

import com.sistemaaluguel.sistemaaluguelcarros.entities.Pedido;
import com.sistemaaluguel.sistemaaluguelcarros.enums.StatusPedido;
import com.sistemaaluguel.sistemaaluguelcarros.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    public List<Pedido> findAll() {
        return pedidoRepository.findAll();
    }

    public Optional<Pedido> findById(Long id) {
        return pedidoRepository.findById(id);
    }

    public List<Pedido> findByClienteId(Long clienteId) {
        return pedidoRepository.findByClienteId(clienteId);
    }

    public List<Pedido> findByAgenteId(Long agenteId) {
        return pedidoRepository.findByAgenteId(agenteId);
    }

    public List<Pedido> findByStatus(StatusPedido status) {
        return pedidoRepository.findByStatus(status);
    }

    public List<Pedido> findByDataInicioBetween(LocalDate dataInicio, LocalDate dataFim) {
        return pedidoRepository.findByDataInicioBetween(dataInicio, dataFim);
    }

    public Pedido save(Pedido pedido) {
        return pedidoRepository.save(pedido);
    }

    public void deleteById(Long id) {
        pedidoRepository.deleteById(id);
    }

    public Pedido aprovarPedido(Long id) {
        Optional<Pedido> pedidoOpt = findById(id);
        if (pedidoOpt.isPresent()) {
            Pedido pedido = pedidoOpt.get();
            pedido.aprovar();
            return save(pedido);
        }
        return null;
    }

    public Pedido rejeitarPedido(Long id) {
        Optional<Pedido> pedidoOpt = findById(id);
        if (pedidoOpt.isPresent()) {
            Pedido pedido = pedidoOpt.get();
            pedido.rejeitar();
            return save(pedido);
        }
        return null;
    }

    public Pedido cancelarPedido(Long id) {
        Optional<Pedido> pedidoOpt = findById(id);
        if (pedidoOpt.isPresent()) {
            Pedido pedido = pedidoOpt.get();
            pedido.cancelar();
            return save(pedido);
        }
        return null;
    }
}

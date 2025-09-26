package com.sistemaaluguel.sistemaaluguelcarros.repository;

import com.sistemaaluguel.sistemaaluguelcarros.entities.Pedido;
import com.sistemaaluguel.sistemaaluguelcarros.enums.StatusPedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    List<Pedido> findByClienteId(Long clienteId);
    List<Pedido> findByAgenteId(Long agenteId);
    List<Pedido> findByStatus(StatusPedido status);
    List<Pedido> findByDataInicioBetween(LocalDate dataInicio, LocalDate dataFim);
}

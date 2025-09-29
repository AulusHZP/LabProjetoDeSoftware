package com.sistemaaluguel.sistemaaluguelcarros.repository;

import com.sistemaaluguel.sistemaaluguelcarros.entities.Pedido;
import com.sistemaaluguel.sistemaaluguelcarros.enums.StatusPedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    List<Pedido> findByClienteId(Long clienteId);
    List<Pedido> findByAgenteId(Long agenteId);
    List<Pedido> findByStatus(StatusPedido status);
    List<Pedido> findByDataInicioBetween(LocalDate dataInicio, LocalDate dataFim);
    
    @Query(value = "SELECT p.id, p.data_inicio, p.data_fim, p.status, " +
                   "c.id as cliente_id, u.nome as cliente_nome, " +
                   "a.id as automovel_id, a.modelo as automovel_modelo, a.marca as automovel_marca, a.placa as automovel_placa " +
                   "FROM pedidos p " +
                   "LEFT JOIN clientes c ON p.cliente_id = c.id " +
                   "LEFT JOIN usuarios u ON c.id = u.id " +
                   "LEFT JOIN automoveis a ON p.automovel_id = a.id", 
           nativeQuery = true)
    List<Object[]> findAllWithDetails();
}

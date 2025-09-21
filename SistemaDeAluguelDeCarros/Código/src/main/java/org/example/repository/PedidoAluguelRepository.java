package org.example.repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.example.model.Automovel;
import org.example.model.Cliente;
import org.example.model.PedidoAluguel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Repositório para operações CRUD da entidade PedidoAluguel
 * Utiliza Spring Data JPA para implementação automática dos métodos básicos
 */
@Repository
public interface PedidoAluguelRepository extends JpaRepository<PedidoAluguel, Long> {
    
    /**
     * Busca pedidos por cliente
     * @param cliente cliente dos pedidos
     * @return lista de pedidos do cliente
     */
    List<PedidoAluguel> findByCliente(Cliente cliente);
    
    /**
     * Busca pedidos por automóvel
     * @param automovel automóvel dos pedidos
     * @return lista de pedidos do automóvel
     */
    List<PedidoAluguel> findByAutomovel(Automovel automovel);
    
    /**
     * Busca pedidos por status
     * @param status status dos pedidos
     * @return lista de pedidos com o status
     */
    List<PedidoAluguel> findByStatus(PedidoAluguel.StatusPedido status);
    
    /**
     * Busca pedidos pendentes
     * @return lista de pedidos pendentes
     */
    @Query("SELECT p FROM PedidoAluguel p WHERE p.status = 'PENDENTE'")
    List<PedidoAluguel> findPedidosPendentes();
    
    /**
     * Busca pedidos aprovados
     * @return lista de pedidos aprovados
     */
    @Query("SELECT p FROM PedidoAluguel p WHERE p.status = 'APROVADO'")
    List<PedidoAluguel> findPedidosAprovados();
    
    /**
     * Busca pedidos rejeitados
     * @return lista de pedidos rejeitados
     */
    @Query("SELECT p FROM PedidoAluguel p WHERE p.status = 'REJEITADO'")
    List<PedidoAluguel> findPedidosRejeitados();
    
    /**
     * Busca pedidos cancelados
     * @return lista de pedidos cancelados
     */
    @Query("SELECT p FROM PedidoAluguel p WHERE p.status = 'CANCELADO'")
    List<PedidoAluguel> findPedidosCancelados();
    
    /**
     * Busca pedidos finalizados
     * @return lista de pedidos finalizados
     */
    @Query("SELECT p FROM PedidoAluguel p WHERE p.status = 'FINALIZADO'")
    List<PedidoAluguel> findPedidosFinalizados();
    
    /**
     * Busca pedidos por cliente e status
     * @param cliente cliente dos pedidos
     * @param status status dos pedidos
     * @return lista de pedidos do cliente com o status
     */
    List<PedidoAluguel> findByClienteAndStatus(Cliente cliente, PedidoAluguel.StatusPedido status);
    
    /**
     * Busca pedidos por automóvel e status
     * @param automovel automóvel dos pedidos
     * @param status status dos pedidos
     * @return lista de pedidos do automóvel com o status
     */
    List<PedidoAluguel> findByAutomovelAndStatus(Automovel automovel, PedidoAluguel.StatusPedido status);
    
    /**
     * Busca pedidos por período de data de início
     * @param dataInicio data de início do período
     * @param dataFim data de fim do período
     * @return lista de pedidos no período
     */
    @Query("SELECT p FROM PedidoAluguel p WHERE p.dataInicio BETWEEN :dataInicio AND :dataFim")
    List<PedidoAluguel> findByDataInicioBetween(@Param("dataInicio") LocalDate dataInicio, 
                                               @Param("dataFim") LocalDate dataFim);
    
    /**
     * Busca pedidos por período de data de fim
     * @param dataInicio data de início do período
     * @param dataFim data de fim do período
     * @return lista de pedidos no período
     */
    @Query("SELECT p FROM PedidoAluguel p WHERE p.dataFim BETWEEN :dataInicio AND :dataFim")
    List<PedidoAluguel> findByDataFimBetween(@Param("dataInicio") LocalDate dataInicio, 
                                            @Param("dataFim") LocalDate dataFim);
    
    /**
     * Busca pedidos por período de cadastro
     * @param dataInicio data de início do período
     * @param dataFim data de fim do período
     * @return lista de pedidos cadastrados no período
     */
    @Query("SELECT p FROM PedidoAluguel p WHERE p.dataCadastro BETWEEN :dataInicio AND :dataFim")
    List<PedidoAluguel> findByDataCadastroBetween(@Param("dataInicio") LocalDateTime dataInicio, 
                                                 @Param("dataFim") LocalDateTime dataFim);
    
    /**
     * Busca pedidos por faixa de valor
     * @param valorMinimo valor mínimo
     * @param valorMaximo valor máximo
     * @return lista de pedidos na faixa de valor
     */
    @Query("SELECT p FROM PedidoAluguel p WHERE p.valorTotal BETWEEN :valorMinimo AND :valorMaximo")
    List<PedidoAluguel> findByValorTotalBetween(@Param("valorMinimo") BigDecimal valorMinimo, 
                                               @Param("valorMaximo") BigDecimal valorMaximo);
    
    /**
     * Busca pedidos por número de dias
     * @param numeroDias número de dias
     * @return lista de pedidos com o número de dias
     */
    List<PedidoAluguel> findByNumeroDias(Integer numeroDias);
    
    /**
     * Busca pedidos por número mínimo de dias
     * @param numeroDias número mínimo de dias
     * @return lista de pedidos com número de dias maior ou igual
     */
    @Query("SELECT p FROM PedidoAluguel p WHERE p.numeroDias >= :numeroDias")
    List<PedidoAluguel> findByNumeroDiasGreaterThanEqual(@Param("numeroDias") Integer numeroDias);
    
    /**
     * Busca pedidos por número máximo de dias
     * @param numeroDias número máximo de dias
     * @return lista de pedidos com número de dias menor ou igual
     */
    @Query("SELECT p FROM PedidoAluguel p WHERE p.numeroDias <= :numeroDias")
    List<PedidoAluguel> findByNumeroDiasLessThanEqual(@Param("numeroDias") Integer numeroDias);
    
    /**
     * Verifica se existe pedido ativo para o automóvel no período
     * @param automovel automóvel
     * @param dataInicio data de início
     * @param dataFim data de fim
     * @return true se existe conflito, false caso contrário
     */
    @Query("SELECT COUNT(p) > 0 FROM PedidoAluguel p WHERE p.automovel = :automovel " +
           "AND p.status IN ('APROVADO', 'PENDENTE') " +
           "AND ((p.dataInicio <= :dataFim AND p.dataFim >= :dataInicio))")
    boolean existsConflitoPeriodo(@Param("automovel") Automovel automovel, 
                                 @Param("dataInicio") LocalDate dataInicio, 
                                 @Param("dataFim") LocalDate dataFim);
    
    /**
     * Busca pedidos com conflito de período para um automóvel
     * @param automovel automóvel
     * @param dataInicio data de início
     * @param dataFim data de fim
     * @return lista de pedidos com conflito
     */
    @Query("SELECT p FROM PedidoAluguel p WHERE p.automovel = :automovel " +
           "AND p.status IN ('APROVADO', 'PENDENTE') " +
           "AND ((p.dataInicio <= :dataFim AND p.dataFim >= :dataInicio))")
    List<PedidoAluguel> findConflitosPeriodo(@Param("automovel") Automovel automovel, 
                                            @Param("dataInicio") LocalDate dataInicio, 
                                            @Param("dataFim") LocalDate dataFim);
    
    /**
     * Conta pedidos por status
     * @param status status dos pedidos
     * @return número de pedidos com o status
     */
    @Query("SELECT COUNT(p) FROM PedidoAluguel p WHERE p.status = :status")
    long countByStatus(@Param("status") PedidoAluguel.StatusPedido status);
    
    /**
     * Conta pedidos por cliente
     * @param cliente cliente dos pedidos
     * @return número de pedidos do cliente
     */
    @Query("SELECT COUNT(p) FROM PedidoAluguel p WHERE p.cliente = :cliente")
    long countByCliente(@Param("cliente") Cliente cliente);
    
    /**
     * Conta pedidos por automóvel
     * @param automovel automóvel dos pedidos
     * @return número de pedidos do automóvel
     */
    @Query("SELECT COUNT(p) FROM PedidoAluguel p WHERE p.automovel = :automovel")
    long countByAutomovel(@Param("automovel") Automovel automovel);
    
    /**
     * Calcula valor total de pedidos por status
     * @param status status dos pedidos
     * @return valor total dos pedidos com o status
     */
    @Query("SELECT COALESCE(SUM(p.valorTotal), 0) FROM PedidoAluguel p WHERE p.status = :status")
    BigDecimal sumValorTotalByStatus(@Param("status") PedidoAluguel.StatusPedido status);
    
    /**
     * Calcula valor total de pedidos por cliente
     * @param cliente cliente dos pedidos
     * @return valor total dos pedidos do cliente
     */
    @Query("SELECT COALESCE(SUM(p.valorTotal), 0) FROM PedidoAluguel p WHERE p.cliente = :cliente")
    BigDecimal sumValorTotalByCliente(@Param("cliente") Cliente cliente);
    
    /**
     * Busca pedidos pendentes de aprovação
     * @return lista de pedidos pendentes
     */
    @Query("SELECT p FROM PedidoAluguel p WHERE p.status = 'PENDENTE' ORDER BY p.dataCadastro ASC")
    List<PedidoAluguel> findPedidosPendentesAprovacao();
    
    /**
     * Busca pedidos aprovados recentemente
     * @param dataLimite data limite
     * @return lista de pedidos aprovados após a data limite
     */
    @Query("SELECT p FROM PedidoAluguel p WHERE p.status = 'APROVADO' AND p.dataAprovacao >= :dataLimite ORDER BY p.dataAprovacao DESC")
    List<PedidoAluguel> findPedidosAprovadosRecentes(@Param("dataLimite") LocalDateTime dataLimite);
    
    /**
     * Busca pedidos por cliente ordenados por data de cadastro
     * @param cliente cliente dos pedidos
     * @return lista de pedidos do cliente ordenados por data de cadastro
     */
    @Query("SELECT p FROM PedidoAluguel p WHERE p.cliente = :cliente ORDER BY p.dataCadastro DESC")
    List<PedidoAluguel> findByClienteOrderByDataCadastroDesc(@Param("cliente") Cliente cliente);
}

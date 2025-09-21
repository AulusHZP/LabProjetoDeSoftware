package org.example.repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.example.model.Agente;
import org.example.model.ContratoCredito;
import org.example.model.PedidoAluguel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Repositório para operações CRUD da entidade ContratoCredito
 * Utiliza Spring Data JPA para implementação automática dos métodos básicos
 */
@Repository
public interface ContratoCreditoRepository extends JpaRepository<ContratoCredito, Long> {
    
    /**
     * Busca contratos por pedido de aluguel
     * @param pedidoAluguel pedido de aluguel dos contratos
     * @return lista de contratos do pedido de aluguel
     */
    List<ContratoCredito> findByPedidoAluguel(PedidoAluguel pedidoAluguel);
    
    /**
     * Busca contratos por agente
     * @param agente agente dos contratos
     * @return lista de contratos do agente
     */
    List<ContratoCredito> findByAgente(Agente agente);
    
    /**
     * Busca contratos por status
     * @param status status dos contratos
     * @return lista de contratos com o status
     */
    List<ContratoCredito> findByStatus(ContratoCredito.StatusContrato status);
    
    /**
     * Busca contratos ativos
     * @return lista de contratos ativos
     */
    @Query("SELECT c FROM ContratoCredito c WHERE c.status = 'ATIVO'")
    List<ContratoCredito> findContratosAtivos();
    
    /**
     * Busca contratos quitados
     * @return lista de contratos quitados
     */
    @Query("SELECT c FROM ContratoCredito c WHERE c.status = 'QUITADO'")
    List<ContratoCredito> findContratosQuitados();
    
    /**
     * Busca contratos suspensos
     * @return lista de contratos suspensos
     */
    @Query("SELECT c FROM ContratoCredito c WHERE c.status = 'SUSPENSO'")
    List<ContratoCredito> findContratosSuspensos();
    
    /**
     * Busca contratos cancelados
     * @return lista de contratos cancelados
     */
    @Query("SELECT c FROM ContratoCredito c WHERE c.status = 'CANCELADO'")
    List<ContratoCredito> findContratosCancelados();
    
    /**
     * Busca contratos por agente e status
     * @param agente agente dos contratos
     * @param status status dos contratos
     * @return lista de contratos do agente com o status
     */
    List<ContratoCredito> findByAgenteAndStatus(Agente agente, ContratoCredito.StatusContrato status);
    
    /**
     * Busca contratos por período de data de início
     * @param dataInicio data de início do período
     * @param dataFim data de fim do período
     * @return lista de contratos no período
     */
    @Query("SELECT c FROM ContratoCredito c WHERE c.dataInicio BETWEEN :dataInicio AND :dataFim")
    List<ContratoCredito> findByDataInicioBetween(@Param("dataInicio") LocalDate dataInicio, 
                                                  @Param("dataFim") LocalDate dataFim);
    
    /**
     * Busca contratos por período de vencimento
     * @param dataInicio data de início do período
     * @param dataFim data de fim do período
     * @return lista de contratos com vencimento no período
     */
    @Query("SELECT c FROM ContratoCredito c WHERE c.dataVencimento BETWEEN :dataInicio AND :dataFim")
    List<ContratoCredito> findByDataVencimentoBetween(@Param("dataInicio") LocalDate dataInicio, 
                                                      @Param("dataFim") LocalDate dataFim);
    
    /**
     * Busca contratos por período de cadastro
     * @param dataInicio data de início do período
     * @param dataFim data de fim do período
     * @return lista de contratos cadastrados no período
     */
    @Query("SELECT c FROM ContratoCredito c WHERE c.dataCadastro BETWEEN :dataInicio AND :dataFim")
    List<ContratoCredito> findByDataCadastroBetween(@Param("dataInicio") LocalDateTime dataInicio, 
                                                    @Param("dataFim") LocalDateTime dataFim);
    
    /**
     * Busca contratos por faixa de valor financiado
     * @param valorMinimo valor mínimo
     * @param valorMaximo valor máximo
     * @return lista de contratos na faixa de valor
     */
    @Query("SELECT c FROM ContratoCredito c WHERE c.valorFinanciado BETWEEN :valorMinimo AND :valorMaximo")
    List<ContratoCredito> findByValorFinanciadoBetween(@Param("valorMinimo") BigDecimal valorMinimo, 
                                                       @Param("valorMaximo") BigDecimal valorMaximo);
    
    /**
     * Busca contratos por faixa de taxa de juros
     * @param taxaMinima taxa mínima
     * @param taxaMaxima taxa máxima
     * @return lista de contratos na faixa de taxa
     */
    @Query("SELECT c FROM ContratoCredito c WHERE c.taxaJuros BETWEEN :taxaMinima AND :taxaMaxima")
    List<ContratoCredito> findByTaxaJurosBetween(@Param("taxaMinima") BigDecimal taxaMinima, 
                                                 @Param("taxaMaxima") BigDecimal taxaMaxima);
    
    /**
     * Busca contratos por número de parcelas
     * @param numeroParcelas número de parcelas
     * @return lista de contratos com o número de parcelas
     */
    List<ContratoCredito> findByNumeroParcelas(Integer numeroParcelas);
    
    /**
     * Busca contratos por número mínimo de parcelas
     * @param numeroParcelas número mínimo de parcelas
     * @return lista de contratos com número de parcelas maior ou igual
     */
    @Query("SELECT c FROM ContratoCredito c WHERE c.numeroParcelas >= :numeroParcelas")
    List<ContratoCredito> findByNumeroParcelasGreaterThanEqual(@Param("numeroParcelas") Integer numeroParcelas);
    
    /**
     * Busca contratos por número máximo de parcelas
     * @param numeroParcelas número máximo de parcelas
     * @return lista de contratos com número de parcelas menor ou igual
     */
    @Query("SELECT c FROM ContratoCredito c WHERE c.numeroParcelas <= :numeroParcelas")
    List<ContratoCredito> findByNumeroParcelasLessThanEqual(@Param("numeroParcelas") Integer numeroParcelas);
    
    /**
     * Busca contratos vencidos
     * @return lista de contratos vencidos
     */
    @Query("SELECT c FROM ContratoCredito c WHERE c.dataVencimento < :dataAtual AND c.status = 'ATIVO'")
    List<ContratoCredito> findContratosVencidos(@Param("dataAtual") LocalDate dataAtual);
    
    /**
     * Busca contratos que vencem em breve
     * @param dataLimite data limite
     * @return lista de contratos que vencem até a data limite
     */
    @Query("SELECT c FROM ContratoCredito c WHERE c.dataVencimento <= :dataLimite AND c.status = 'ATIVO'")
    List<ContratoCredito> findContratosVencendoEmBreve(@Param("dataLimite") LocalDate dataLimite);
    
    /**
     * Conta contratos por status
     * @param status status dos contratos
     * @return número de contratos com o status
     */
    @Query("SELECT COUNT(c) FROM ContratoCredito c WHERE c.status = :status")
    long countByStatus(@Param("status") ContratoCredito.StatusContrato status);
    
    /**
     * Conta contratos por agente
     * @param agente agente dos contratos
     * @return número de contratos do agente
     */
    @Query("SELECT COUNT(c) FROM ContratoCredito c WHERE c.agente = :agente")
    long countByAgente(@Param("agente") Agente agente);
    
    /**
     * Conta contratos por pedido de aluguel
     * @param pedidoAluguel pedido de aluguel dos contratos
     * @return número de contratos do pedido de aluguel
     */
    @Query("SELECT COUNT(c) FROM ContratoCredito c WHERE c.pedidoAluguel = :pedidoAluguel")
    long countByPedidoAluguel(@Param("pedidoAluguel") PedidoAluguel pedidoAluguel);
    
    /**
     * Calcula valor total financiado por status
     * @param status status dos contratos
     * @return valor total financiado dos contratos com o status
     */
    @Query("SELECT COALESCE(SUM(c.valorFinanciado), 0) FROM ContratoCredito c WHERE c.status = :status")
    BigDecimal sumValorFinanciadoByStatus(@Param("status") ContratoCredito.StatusContrato status);
    
    /**
     * Calcula valor total financiado por agente
     * @param agente agente dos contratos
     * @return valor total financiado dos contratos do agente
     */
    @Query("SELECT COALESCE(SUM(c.valorFinanciado), 0) FROM ContratoCredito c WHERE c.agente = :agente")
    BigDecimal sumValorFinanciadoByAgente(@Param("agente") Agente agente);
    
    /**
     * Calcula valor total de parcelas por status
     * @param status status dos contratos
     * @return valor total de parcelas dos contratos com o status
     */
    @Query("SELECT COALESCE(SUM(c.valorParcela * c.numeroParcelas), 0) FROM ContratoCredito c WHERE c.status = :status")
    BigDecimal sumValorTotalParcelasByStatus(@Param("status") ContratoCredito.StatusContrato status);
    
    /**
     * Calcula valor total de parcelas por agente
     * @param agente agente dos contratos
     * @return valor total de parcelas dos contratos do agente
     */
    @Query("SELECT COALESCE(SUM(c.valorParcela * c.numeroParcelas), 0) FROM ContratoCredito c WHERE c.agente = :agente")
    BigDecimal sumValorTotalParcelasByAgente(@Param("agente") Agente agente);
    
    /**
     * Busca contratos ativos ordenados por data de vencimento
     * @return lista de contratos ativos ordenados por data de vencimento
     */
    @Query("SELECT c FROM ContratoCredito c WHERE c.status = 'ATIVO' ORDER BY c.dataVencimento ASC")
    List<ContratoCredito> findContratosAtivosOrderByDataVencimento();
    
    /**
     * Busca contratos por agente ordenados por data de cadastro
     * @param agente agente dos contratos
     * @return lista de contratos do agente ordenados por data de cadastro
     */
    @Query("SELECT c FROM ContratoCredito c WHERE c.agente = :agente ORDER BY c.dataCadastro DESC")
    List<ContratoCredito> findByAgenteOrderByDataCadastroDesc(@Param("agente") Agente agente);
    
    /**
     * Busca contratos quitados recentemente
     * @param dataLimite data limite
     * @return lista de contratos quitados após a data limite
     */
    @Query("SELECT c FROM ContratoCredito c WHERE c.status = 'QUITADO' AND c.dataQuitacao >= :dataLimite ORDER BY c.dataQuitacao DESC")
    List<ContratoCredito> findContratosQuitadosRecentes(@Param("dataLimite") LocalDateTime dataLimite);
    
    /**
     * Verifica se existe contrato ativo para o pedido de aluguel
     * @param pedidoAluguel pedido de aluguel
     * @return true se existe contrato ativo, false caso contrário
     */
    @Query("SELECT COUNT(c) > 0 FROM ContratoCredito c WHERE c.pedidoAluguel = :pedidoAluguel AND c.status = 'ATIVO'")
    boolean existsContratoAtivoByPedidoAluguel(@Param("pedidoAluguel") PedidoAluguel pedidoAluguel);
    
    /**
     * Busca contrato ativo por pedido de aluguel
     * @param pedidoAluguel pedido de aluguel
     * @return Optional contendo o contrato ativo se encontrado
     */
    @Query("SELECT c FROM ContratoCredito c WHERE c.pedidoAluguel = :pedidoAluguel AND c.status = 'ATIVO'")
    Optional<ContratoCredito> findContratoAtivoByPedidoAluguel(@Param("pedidoAluguel") PedidoAluguel pedidoAluguel);
}

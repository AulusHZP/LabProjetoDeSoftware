package org.example.repository;

import java.util.List;
import java.util.Optional;

import org.example.model.Agente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Repositório para operações CRUD da entidade Agente
 * Utiliza Spring Data JPA para implementação automática dos métodos básicos
 */
@Repository
public interface AgenteRepository extends JpaRepository<Agente, Long> {
    
    /**
     * Busca agente por email
     * @param email email do agente
     * @return Optional contendo o agente se encontrado
     */
    Optional<Agente> findByEmail(String email);
    
    /**
     * Busca agente por CPF
     * @param cpf CPF do agente
     * @return Optional contendo o agente se encontrado
     */
    Optional<Agente> findByCpf(String cpf);
    
    /**
     * Busca agente por CNPJ
     * @param cnpj CNPJ da empresa do agente
     * @return Optional contendo o agente se encontrado
     */
    Optional<Agente> findByCnpj(String cnpj);
    
    /**
     * Verifica se existe agente com o email informado
     * @param email email a ser verificado
     * @return true se existe, false caso contrário
     */
    boolean existsByEmail(String email);
    
    /**
     * Verifica se existe agente com o CPF informado
     * @param cpf CPF a ser verificado
     * @return true se existe, false caso contrário
     */
    boolean existsByCpf(String cpf);
    
    /**
     * Verifica se existe agente com o CNPJ informado
     * @param cnpj CNPJ a ser verificado
     * @return true se existe, false caso contrário
     */
    boolean existsByCnpj(String cnpj);
    
    /**
     * Busca agentes ativos
     * @return lista de agentes ativos
     */
    List<Agente> findByAtivoTrue();
    
    /**
     * Busca agentes inativos
     * @return lista de agentes inativos
     */
    List<Agente> findByAtivoFalse();
    
    /**
     * Busca agentes por tipo
     * @param tipoAgente tipo do agente
     * @return lista de agentes do tipo
     */
    List<Agente> findByTipoAgente(Agente.TipoAgente tipoAgente);
    
    /**
     * Busca agentes ativos por tipo
     * @param tipoAgente tipo do agente
     * @return lista de agentes ativos do tipo
     */
    List<Agente> findByTipoAgenteAndAtivoTrue(Agente.TipoAgente tipoAgente);
    
    /**
     * Busca agentes por nome da empresa
     * @param nomeEmpresa nome da empresa
     * @return lista de agentes da empresa
     */
    List<Agente> findByNomeEmpresa(String nomeEmpresa);
    
    /**
     * Busca agentes por nome da empresa (busca parcial, case insensitive)
     * @param nomeEmpresa nome ou parte do nome da empresa
     * @return lista de agentes que contém o nome da empresa
     */
    @Query("SELECT a FROM Agente a WHERE LOWER(a.nomeEmpresa) LIKE LOWER(CONCAT('%', :nomeEmpresa, '%'))")
    List<Agente> findByNomeEmpresaContainingIgnoreCase(@Param("nomeEmpresa") String nomeEmpresa);
    
    /**
     * Busca agentes por nome (busca parcial, case insensitive)
     * @param nome nome ou parte do nome do agente
     * @return lista de agentes que contém o nome
     */
    @Query("SELECT a FROM Agente a WHERE LOWER(a.nome) LIKE LOWER(CONCAT('%', :nome, '%'))")
    List<Agente> findByNomeContainingIgnoreCase(@Param("nome") String nome);
    
    /**
     * Busca agente ativo por email e senha (para autenticação)
     * @param email email do agente
     * @param senha senha do agente
     * @return Optional contendo o agente ativo se encontrado
     */
    @Query("SELECT a FROM Agente a WHERE a.email = :email AND a.senha = :senha AND a.ativo = true")
    Optional<Agente> findAtivoByEmailAndSenha(@Param("email") String email, @Param("senha") String senha);
    
    /**
     * Conta total de agentes ativos
     * @return número de agentes ativos
     */
    @Query("SELECT COUNT(a) FROM Agente a WHERE a.ativo = true")
    long countAtivos();
    
    /**
     * Conta total de agentes inativos
     * @return número de agentes inativos
     */
    @Query("SELECT COUNT(a) FROM Agente a WHERE a.ativo = false")
    long countInativos();
    
    /**
     * Conta agentes por tipo
     * @param tipoAgente tipo do agente
     * @return número de agentes do tipo
     */
    @Query("SELECT COUNT(a) FROM Agente a WHERE a.tipoAgente = :tipoAgente AND a.ativo = true")
    long countByTipoAgente(@Param("tipoAgente") Agente.TipoAgente tipoAgente);
    
    /**
     * Busca agentes com limite de aprovação maior ou igual ao valor informado
     * @param valor valor mínimo do limite de aprovação
     * @return lista de agentes que podem aprovar o valor
     */
    @Query("SELECT a FROM Agente a WHERE (a.limiteAprovacao IS NULL OR a.limiteAprovacao >= :valor) AND a.ativo = true")
    List<Agente> findAgentesComLimiteAprovacao(@Param("valor") java.math.BigDecimal valor);
    
    /**
     * Busca agentes bancários ativos
     * @return lista de agentes bancários ativos
     */
    @Query("SELECT a FROM Agente a WHERE a.tipoAgente = 'BANCO' AND a.ativo = true")
    List<Agente> findAgentesBancariosAtivos();
    
    /**
     * Busca agentes financeiros ativos
     * @return lista de agentes financeiros ativos
     */
    @Query("SELECT a FROM Agente a WHERE a.tipoAgente = 'FINANCEIRA' AND a.ativo = true")
    List<Agente> findAgentesFinanceirosAtivos();
    
    /**
     * Busca agentes empresariais ativos
     * @return lista de agentes empresariais ativos
     */
    @Query("SELECT a FROM Agente a WHERE a.tipoAgente = 'EMPRESA' AND a.ativo = true")
    List<Agente> findAgentesEmpresariaisAtivos();
    
    /**
     * Busca agentes cooperativos ativos
     * @return lista de agentes cooperativos ativos
     */
    @Query("SELECT a FROM Agente a WHERE a.tipoAgente = 'COOPERATIVA' AND a.ativo = true")
    List<Agente> findAgentesCooperativosAtivos();
    
    /**
     * Busca agentes cadastrados em um período
     * @param dataInicio data de início do período
     * @param dataFim data de fim do período
     * @return lista de agentes cadastrados no período
     */
    @Query("SELECT a FROM Agente a WHERE a.dataCadastro BETWEEN :dataInicio AND :dataFim")
    List<Agente> findByDataCadastroBetween(@Param("dataInicio") java.time.LocalDateTime dataInicio, 
                                          @Param("dataFim") java.time.LocalDateTime dataFim);
    
    /**
     * Busca agentes ordenados por nome da empresa
     * @return lista de agentes ordenados por nome da empresa
     */
    @Query("SELECT a FROM Agente a WHERE a.ativo = true ORDER BY a.nomeEmpresa ASC")
    List<Agente> findAtivosOrderByNomeEmpresa();
    
    /**
     * Busca agentes ordenados por tipo e nome
     * @return lista de agentes ordenados por tipo e nome
     */
    @Query("SELECT a FROM Agente a WHERE a.ativo = true ORDER BY a.tipoAgente ASC, a.nome ASC")
    List<Agente> findAtivosOrderByTipoAgenteAndNome();
}

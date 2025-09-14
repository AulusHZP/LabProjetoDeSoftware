package org.example.repository;

import org.example.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repositório para operações CRUD da entidade Cliente
 * Utiliza Spring Data JPA para implementação automática dos métodos básicos
 */
@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    
    /**
     * Busca cliente por email
     * @param email email do cliente
     * @return Optional contendo o cliente se encontrado
     */
    Optional<Cliente> findByEmail(String email);
    
    /**
     * Busca cliente por CPF
     * @param cpf CPF do cliente
     * @return Optional contendo o cliente se encontrado
     */
    Optional<Cliente> findByCpf(String cpf);
    
    /**
     * Verifica se existe cliente com o email informado
     * @param email email a ser verificado
     * @return true se existe, false caso contrário
     */
    boolean existsByEmail(String email);
    
    /**
     * Verifica se existe cliente com o CPF informado
     * @param cpf CPF a ser verificado
     * @return true se existe, false caso contrário
     */
    boolean existsByCpf(String cpf);
    
    /**
     * Busca clientes ativos
     * @return lista de clientes ativos
     */
    List<Cliente> findByAtivoTrue();
    
    /**
     * Busca clientes inativos
     * @return lista de clientes inativos
     */
    List<Cliente> findByAtivoFalse();
    
    /**
     * Busca clientes por nome (busca parcial, case insensitive)
     * @param nome nome ou parte do nome do cliente
     * @return lista de clientes que contém o nome informado
     */
    @Query("SELECT c FROM Cliente c WHERE LOWER(c.nome) LIKE LOWER(CONCAT('%', :nome, '%'))")
    List<Cliente> findByNomeContainingIgnoreCase(@Param("nome") String nome);
    
    /**
     * Busca clientes por cidade
     * @param cidade cidade do cliente
     * @return lista de clientes da cidade informada
     */
    List<Cliente> findByCidade(String cidade);
    
    /**
     * Busca clientes por estado
     * @param estado estado do cliente
     * @return lista de clientes do estado informado
     */
    List<Cliente> findByEstado(String estado);
    
    /**
     * Busca cliente por email e senha (para autenticação)
     * @param email email do cliente
     * @param senha senha do cliente
     * @return Optional contendo o cliente se encontrado
     */
    Optional<Cliente> findByEmailAndSenha(String email, String senha);
    
    /**
     * Busca cliente ativo por email e senha (para autenticação)
     * @param email email do cliente
     * @param senha senha do cliente
     * @return Optional contendo o cliente ativo se encontrado
     */
    @Query("SELECT c FROM Cliente c WHERE c.email = :email AND c.senha = :senha AND c.ativo = true")
    Optional<Cliente> findAtivoByEmailAndSenha(@Param("email") String email, @Param("senha") String senha);
    
    /**
     * Conta total de clientes ativos
     * @return número de clientes ativos
     */
    @Query("SELECT COUNT(c) FROM Cliente c WHERE c.ativo = true")
    long countAtivos();
    
    /**
     * Conta total de clientes inativos
     * @return número de clientes inativos
     */
    @Query("SELECT COUNT(c) FROM Cliente c WHERE c.ativo = false")
    long countInativos();
    
    /**
     * Busca clientes cadastrados em um período específico
     * @param dataInicio data de início do período
     * @param dataFim data de fim do período
     * @return lista de clientes cadastrados no período
     */
    @Query("SELECT c FROM Cliente c WHERE c.dataCadastro BETWEEN :dataInicio AND :dataFim")
    List<Cliente> findByDataCadastroBetween(@Param("dataInicio") java.time.LocalDateTime dataInicio, 
                                          @Param("dataFim") java.time.LocalDateTime dataFim);
}

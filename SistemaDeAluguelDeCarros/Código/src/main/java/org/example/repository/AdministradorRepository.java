package org.example.repository;

import java.util.List;
import java.util.Optional;

import org.example.model.Administrador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Repositório para operações CRUD da entidade Administrador
 * Utiliza Spring Data JPA para implementação automática dos métodos básicos
 */
@Repository
public interface AdministradorRepository extends JpaRepository<Administrador, Long> {
    
    /**
     * Busca administrador por email
     * @param email email do administrador
     * @return Optional contendo o administrador se encontrado
     */
    Optional<Administrador> findByEmail(String email);
    
    /**
     * Busca administrador por CPF
     * @param cpf CPF do administrador
     * @return Optional contendo o administrador se encontrado
     */
    Optional<Administrador> findByCpf(String cpf);
    
    /**
     * Verifica se existe administrador com o email informado
     * @param email email a ser verificado
     * @return true se existe, false caso contrário
     */
    boolean existsByEmail(String email);
    
    /**
     * Verifica se existe administrador com o CPF informado
     * @param cpf CPF a ser verificado
     * @return true se existe, false caso contrário
     */
    boolean existsByCpf(String cpf);
    
    /**
     * Busca administradores ativos
     * @return lista de administradores ativos
     */
    List<Administrador> findByAtivoTrue();
    
    /**
     * Busca administradores inativos
     * @return lista de administradores inativos
     */
    List<Administrador> findByAtivoFalse();
    
    /**
     * Busca administradores por nível de acesso
     * @param nivelAcesso nível de acesso
     * @return lista de administradores com o nível de acesso
     */
    List<Administrador> findByNivelAcesso(Administrador.NivelAcesso nivelAcesso);
    
    /**
     * Busca administradores ativos por nível de acesso
     * @param nivelAcesso nível de acesso
     * @return lista de administradores ativos com o nível de acesso
     */
    List<Administrador> findByNivelAcessoAndAtivoTrue(Administrador.NivelAcesso nivelAcesso);
    
    /**
     * Busca administradores por nome (busca parcial, case insensitive)
     * @param nome nome ou parte do nome do administrador
     * @return lista de administradores que contém o nome
     */
    @Query("SELECT a FROM Administrador a WHERE LOWER(a.nome) LIKE LOWER(CONCAT('%', :nome, '%'))")
    List<Administrador> findByNomeContainingIgnoreCase(@Param("nome") String nome);
    
    /**
     * Busca administrador ativo por email e senha (para autenticação)
     * @param email email do administrador
     * @param senha senha do administrador
     * @return Optional contendo o administrador ativo se encontrado
     */
    @Query("SELECT a FROM Administrador a WHERE a.email = :email AND a.senha = :senha AND a.ativo = true")
    Optional<Administrador> findAtivoByEmailAndSenha(@Param("email") String email, @Param("senha") String senha);
    
    /**
     * Conta total de administradores ativos
     * @return número de administradores ativos
     */
    @Query("SELECT COUNT(a) FROM Administrador a WHERE a.ativo = true")
    long countAtivos();
    
    /**
     * Conta total de administradores inativos
     * @return número de administradores inativos
     */
    @Query("SELECT COUNT(a) FROM Administrador a WHERE a.ativo = false")
    long countInativos();
    
    /**
     * Conta administradores por nível de acesso
     * @param nivelAcesso nível de acesso
     * @return número de administradores com o nível de acesso
     */
    @Query("SELECT COUNT(a) FROM Administrador a WHERE a.nivelAcesso = :nivelAcesso AND a.ativo = true")
    long countByNivelAcesso(@Param("nivelAcesso") Administrador.NivelAcesso nivelAcesso);
    
    /**
     * Busca super administradores ativos
     * @return lista de super administradores ativos
     */
    @Query("SELECT a FROM Administrador a WHERE a.nivelAcesso = 'SUPER_ADMINISTRADOR' AND a.ativo = true")
    List<Administrador> findSuperAdministradoresAtivos();
    
    /**
     * Busca administradores comuns ativos
     * @return lista de administradores comuns ativos
     */
    @Query("SELECT a FROM Administrador a WHERE a.nivelAcesso = 'ADMINISTRADOR' AND a.ativo = true")
    List<Administrador> findAdministradoresAtivos();
    
    /**
     * Busca operadores ativos
     * @return lista de operadores ativos
     */
    @Query("SELECT a FROM Administrador a WHERE a.nivelAcesso = 'OPERADOR' AND a.ativo = true")
    List<Administrador> findOperadoresAtivos();
    
    /**
     * Busca administradores cadastrados em um período
     * @param dataInicio data de início do período
     * @param dataFim data de fim do período
     * @return lista de administradores cadastrados no período
     */
    @Query("SELECT a FROM Administrador a WHERE a.dataCadastro BETWEEN :dataInicio AND :dataFim")
    List<Administrador> findByDataCadastroBetween(@Param("dataInicio") java.time.LocalDateTime dataInicio, 
                                                  @Param("dataFim") java.time.LocalDateTime dataFim);
    
    /**
     * Busca administradores que fizeram login recentemente
     * @param dataLimite data limite
     * @return lista de administradores que fizeram login após a data limite
     */
    @Query("SELECT a FROM Administrador a WHERE a.dataUltimoLogin >= :dataLimite AND a.ativo = true ORDER BY a.dataUltimoLogin DESC")
    List<Administrador> findAdministradoresComLoginRecente(@Param("dataLimite") java.time.LocalDateTime dataLimite);
    
    /**
     * Busca administradores que nunca fizeram login
     * @return lista de administradores que nunca fizeram login
     */
    @Query("SELECT a FROM Administrador a WHERE a.dataUltimoLogin IS NULL AND a.ativo = true")
    List<Administrador> findAdministradoresSemLogin();
    
    /**
     * Busca administradores ordenados por nome
     * @return lista de administradores ordenados por nome
     */
    @Query("SELECT a FROM Administrador a WHERE a.ativo = true ORDER BY a.nome ASC")
    List<Administrador> findAtivosOrderByNome();
    
    /**
     * Busca administradores ordenados por nível de acesso e nome
     * @return lista de administradores ordenados por nível de acesso e nome
     */
    @Query("SELECT a FROM Administrador a WHERE a.ativo = true ORDER BY a.nivelAcesso ASC, a.nome ASC")
    List<Administrador> findAtivosOrderByNivelAcessoAndNome();
    
    /**
     * Busca administradores ordenados por data de último login
     * @return lista de administradores ordenados por data de último login
     */
    @Query("SELECT a FROM Administrador a WHERE a.ativo = true ORDER BY a.dataUltimoLogin DESC NULLS LAST")
    List<Administrador> findAtivosOrderByDataUltimoLogin();
}

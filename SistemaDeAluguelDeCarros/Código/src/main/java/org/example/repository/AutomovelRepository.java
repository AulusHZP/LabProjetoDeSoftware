package org.example.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.example.model.Automovel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Repositório para operações CRUD da entidade Automóvel
 * Utiliza Spring Data JPA para implementação automática dos métodos básicos
 */
@Repository
public interface AutomovelRepository extends JpaRepository<Automovel, Long> {
    
    /**
     * Busca automóvel por placa
     * @param placa placa do automóvel
     * @return Optional contendo o automóvel se encontrado
     */
    Optional<Automovel> findByPlaca(String placa);
    
    /**
     * Verifica se existe automóvel com a placa informada
     * @param placa placa a ser verificada
     * @return true se existe, false caso contrário
     */
    boolean existsByPlaca(String placa);
    
    /**
     * Busca automóveis disponíveis
     * @return lista de automóveis disponíveis
     */
    List<Automovel> findByDisponivelTrueAndAtivoTrue();
    
    /**
     * Busca automóveis ativos
     * @return lista de automóveis ativos
     */
    List<Automovel> findByAtivoTrue();
    
    /**
     * Busca automóveis inativos
     * @return lista de automóveis inativos
     */
    List<Automovel> findByAtivoFalse();
    
    /**
     * Busca automóveis por marca
     * @param marca marca do automóvel
     * @return lista de automóveis da marca
     */
    List<Automovel> findByMarca(String marca);
    
    /**
     * Busca automóveis por modelo
     * @param modelo modelo do automóvel
     * @return lista de automóveis do modelo
     */
    List<Automovel> findByModelo(String modelo);
    
    /**
     * Busca automóveis por categoria
     * @param categoria categoria do automóvel
     * @return lista de automóveis da categoria
     */
    List<Automovel> findByCategoria(String categoria);
    
    /**
     * Busca automóveis por ano
     * @param ano ano do automóvel
     * @return lista de automóveis do ano
     */
    List<Automovel> findByAno(String ano);
    
    /**
     * Busca automóveis por cor
     * @param cor cor do automóvel
     * @return lista de automóveis da cor
     */
    List<Automovel> findByCor(String cor);
    
    /**
     * Busca automóveis por tipo de combustível
     * @param tipoCombustivel tipo de combustível
     * @return lista de automóveis do tipo de combustível
     */
    List<Automovel> findByTipoCombustivel(String tipoCombustivel);
    
    /**
     * Busca automóveis por transmissão
     * @param transmissao tipo de transmissão
     * @return lista de automóveis da transmissão
     */
    List<Automovel> findByTransmissao(String transmissao);
    
    /**
     * Busca automóveis por faixa de preço
     * @param precoMinimo preço mínimo
     * @param precoMaximo preço máximo
     * @return lista de automóveis na faixa de preço
     */
    @Query("SELECT a FROM Automovel a WHERE a.valorDiario BETWEEN :precoMinimo AND :precoMaximo AND a.ativo = true")
    List<Automovel> findByValorDiarioBetween(@Param("precoMinimo") BigDecimal precoMinimo, 
                                           @Param("precoMaximo") BigDecimal precoMaximo);
    
    /**
     * Busca automóveis com ar condicionado
     * @return lista de automóveis com ar condicionado
     */
    List<Automovel> findByArCondicionadoTrueAndAtivoTrue();
    
    /**
     * Busca automóveis com direção hidráulica
     * @return lista de automóveis com direção hidráulica
     */
    List<Automovel> findByDirecaoHidraulicaTrueAndAtivoTrue();
    
    /**
     * Busca automóveis com airbag
     * @return lista de automóveis com airbag
     */
    List<Automovel> findByAirbagTrueAndAtivoTrue();
    
    /**
     * Busca automóveis com ABS
     * @return lista de automóveis com ABS
     */
    List<Automovel> findByAbsTrueAndAtivoTrue();
    
    /**
     * Busca automóveis por capacidade de passageiros
     * @param capacidade capacidade mínima de passageiros
     * @return lista de automóveis com a capacidade ou superior
     */
    @Query("SELECT a FROM Automovel a WHERE a.capacidadePassageiros >= :capacidade AND a.ativo = true")
    List<Automovel> findByCapacidadePassageirosGreaterThanEqual(@Param("capacidade") Integer capacidade);
    
    /**
     * Busca automóveis por quilometragem máxima
     * @param quilometragem quilometragem máxima
     * @return lista de automóveis com quilometragem menor ou igual
     */
    @Query("SELECT a FROM Automovel a WHERE a.quilometragem <= :quilometragem AND a.ativo = true")
    List<Automovel> findByQuilometragemLessThanEqual(@Param("quilometragem") Long quilometragem);
    
    /**
     * Busca automóveis por marca e modelo
     * @param marca marca do automóvel
     * @param modelo modelo do automóvel
     * @return lista de automóveis da marca e modelo
     */
    List<Automovel> findByMarcaAndModelo(String marca, String modelo);
    
    /**
     * Busca automóveis por marca, modelo e ano
     * @param marca marca do automóvel
     * @param modelo modelo do automóvel
     * @param ano ano do automóvel
     * @return lista de automóveis da marca, modelo e ano
     */
    List<Automovel> findByMarcaAndModeloAndAno(String marca, String modelo, String ano);
    
    /**
     * Conta total de automóveis ativos
     * @return número de automóveis ativos
     */
    @Query("SELECT COUNT(a) FROM Automovel a WHERE a.ativo = true")
    long countAtivos();
    
    /**
     * Conta total de automóveis disponíveis
     * @return número de automóveis disponíveis
     */
    @Query("SELECT COUNT(a) FROM Automovel a WHERE a.disponivel = true AND a.ativo = true")
    long countDisponiveis();
    
    /**
     * Conta total de automóveis por categoria
     * @param categoria categoria do automóvel
     * @return número de automóveis da categoria
     */
    @Query("SELECT COUNT(a) FROM Automovel a WHERE a.categoria = :categoria AND a.ativo = true")
    long countByCategoria(@Param("categoria") String categoria);
    
    /**
     * Busca automóveis disponíveis por categoria
     * @param categoria categoria do automóvel
     * @return lista de automóveis disponíveis da categoria
     */
    List<Automovel> findByCategoriaAndDisponivelTrueAndAtivoTrue(String categoria);
    
    /**
     * Busca automóveis disponíveis por faixa de preço e categoria
     * @param categoria categoria do automóvel
     * @param precoMinimo preço mínimo
     * @param precoMaximo preço máximo
     * @return lista de automóveis disponíveis na faixa de preço e categoria
     */
    @Query("SELECT a FROM Automovel a WHERE a.categoria = :categoria AND a.valorDiario BETWEEN :precoMinimo AND :precoMaximo AND a.disponivel = true AND a.ativo = true")
    List<Automovel> findByCategoriaAndValorDiarioBetweenAndDisponivelTrueAndAtivoTrue(
            @Param("categoria") String categoria, 
            @Param("precoMinimo") BigDecimal precoMinimo, 
            @Param("precoMaximo") BigDecimal precoMaximo);
}

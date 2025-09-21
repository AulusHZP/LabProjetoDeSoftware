package org.example.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.example.model.Automovel;
import org.example.repository.AutomovelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Serviço para lógica de negócio relacionada ao Automóvel
 * Implementa as regras de negócio baseadas nas histórias de usuário do Administrador
 */
@Service
@Transactional
public class AutomovelService {
    
    @Autowired
    private AutomovelRepository automovelRepository;
    
    /**
     * Cadastra um novo automóvel no sistema
     * Atende à história de usuário: "Quero cadastrar novos automóveis no sistema"
     * 
     * @param automovel dados do automóvel a ser cadastrado
     * @return automóvel cadastrado com ID gerado
     * @throws IllegalArgumentException se placa já existir
     */
    public Automovel cadastrarAutomovel(Automovel automovel) {
        // Validações de negócio
        if (automovelRepository.existsByPlaca(automovel.getPlaca())) {
            throw new IllegalArgumentException("Placa já cadastrada no sistema");
        }
        
        // Define dados padrão
        automovel.setDisponivel(true);
        automovel.setAtivo(true);
        
        return automovelRepository.save(automovel);
    }
    
    /**
     * Busca automóvel por ID
     * 
     * @param id ID do automóvel
     * @return Optional contendo o automóvel se encontrado
     */
    @Transactional(readOnly = true)
    public Optional<Automovel> buscarPorId(Long id) {
        return automovelRepository.findById(id);
    }
    
    /**
     * Busca automóvel por placa
     * 
     * @param placa placa do automóvel
     * @return Optional contendo o automóvel se encontrado
     */
    @Transactional(readOnly = true)
    public Optional<Automovel> buscarPorPlaca(String placa) {
        return automovelRepository.findByPlaca(placa);
    }
    
    /**
     * Lista todos os automóveis
     * 
     * @return lista de todos os automóveis
     */
    @Transactional(readOnly = true)
    public List<Automovel> listarTodos() {
        return automovelRepository.findAll();
    }
    
    /**
     * Lista apenas automóveis ativos
     * 
     * @return lista de automóveis ativos
     */
    @Transactional(readOnly = true)
    public List<Automovel> listarAtivos() {
        return automovelRepository.findByAtivoTrue();
    }
    
    /**
     * Lista apenas automóveis disponíveis
     * 
     * @return lista de automóveis disponíveis
     */
    @Transactional(readOnly = true)
    public List<Automovel> listarDisponiveis() {
        return automovelRepository.findByDisponivelTrueAndAtivoTrue();
    }
    
    /**
     * Lista automóveis por marca
     * 
     * @param marca marca dos automóveis
     * @return lista de automóveis da marca
     */
    @Transactional(readOnly = true)
    public List<Automovel> buscarPorMarca(String marca) {
        return automovelRepository.findByMarca(marca);
    }
    
    /**
     * Lista automóveis por modelo
     * 
     * @param modelo modelo dos automóveis
     * @return lista de automóveis do modelo
     */
    @Transactional(readOnly = true)
    public List<Automovel> buscarPorModelo(String modelo) {
        return automovelRepository.findByModelo(modelo);
    }
    
    /**
     * Lista automóveis por categoria
     * 
     * @param categoria categoria dos automóveis
     * @return lista de automóveis da categoria
     */
    @Transactional(readOnly = true)
    public List<Automovel> buscarPorCategoria(String categoria) {
        return automovelRepository.findByCategoria(categoria);
    }
    
    /**
     * Lista automóveis disponíveis por categoria
     * 
     * @param categoria categoria dos automóveis
     * @return lista de automóveis disponíveis da categoria
     */
    @Transactional(readOnly = true)
    public List<Automovel> buscarDisponiveisPorCategoria(String categoria) {
        return automovelRepository.findByCategoriaAndDisponivelTrueAndAtivoTrue(categoria);
    }
    
    /**
     * Lista automóveis por faixa de preço
     * 
     * @param precoMinimo preço mínimo
     * @param precoMaximo preço máximo
     * @return lista de automóveis na faixa de preço
     */
    @Transactional(readOnly = true)
    public List<Automovel> buscarPorFaixaPreco(BigDecimal precoMinimo, BigDecimal precoMaximo) {
        return automovelRepository.findByValorDiarioBetween(precoMinimo, precoMaximo);
    }
    
    /**
     * Lista automóveis por ano
     * 
     * @param ano ano dos automóveis
     * @return lista de automóveis do ano
     */
    @Transactional(readOnly = true)
    public List<Automovel> buscarPorAno(String ano) {
        return automovelRepository.findByAno(ano);
    }
    
    /**
     * Lista automóveis por cor
     * 
     * @param cor cor dos automóveis
     * @return lista de automóveis da cor
     */
    @Transactional(readOnly = true)
    public List<Automovel> buscarPorCor(String cor) {
        return automovelRepository.findByCor(cor);
    }
    
    /**
     * Lista automóveis por tipo de combustível
     * 
     * @param tipoCombustivel tipo de combustível
     * @return lista de automóveis do tipo de combustível
     */
    @Transactional(readOnly = true)
    public List<Automovel> buscarPorTipoCombustivel(String tipoCombustivel) {
        return automovelRepository.findByTipoCombustivel(tipoCombustivel);
    }
    
    /**
     * Lista automóveis por transmissão
     * 
     * @param transmissao tipo de transmissão
     * @return lista de automóveis da transmissão
     */
    @Transactional(readOnly = true)
    public List<Automovel> buscarPorTransmissao(String transmissao) {
        return automovelRepository.findByTransmissao(transmissao);
    }
    
    /**
     * Lista automóveis com ar condicionado
     * 
     * @return lista de automóveis com ar condicionado
     */
    @Transactional(readOnly = true)
    public List<Automovel> buscarComArCondicionado() {
        return automovelRepository.findByArCondicionadoTrueAndAtivoTrue();
    }
    
    /**
     * Lista automóveis com direção hidráulica
     * 
     * @return lista de automóveis com direção hidráulica
     */
    @Transactional(readOnly = true)
    public List<Automovel> buscarComDirecaoHidraulica() {
        return automovelRepository.findByDirecaoHidraulicaTrueAndAtivoTrue();
    }
    
    /**
     * Lista automóveis com airbag
     * 
     * @return lista de automóveis com airbag
     */
    @Transactional(readOnly = true)
    public List<Automovel> buscarComAirbag() {
        return automovelRepository.findByAirbagTrueAndAtivoTrue();
    }
    
    /**
     * Lista automóveis com ABS
     * 
     * @return lista de automóveis com ABS
     */
    @Transactional(readOnly = true)
    public List<Automovel> buscarComAbs() {
        return automovelRepository.findByAbsTrueAndAtivoTrue();
    }
    
    /**
     * Lista automóveis por capacidade de passageiros
     * 
     * @param capacidade capacidade mínima de passageiros
     * @return lista de automóveis com a capacidade ou superior
     */
    @Transactional(readOnly = true)
    public List<Automovel> buscarPorCapacidadeMinima(Integer capacidade) {
        return automovelRepository.findByCapacidadePassageirosGreaterThanEqual(capacidade);
    }
    
    /**
     * Lista automóveis por quilometragem máxima
     * 
     * @param quilometragem quilometragem máxima
     * @return lista de automóveis com quilometragem menor ou igual
     */
    @Transactional(readOnly = true)
    public List<Automovel> buscarPorQuilometragemMaxima(Long quilometragem) {
        return automovelRepository.findByQuilometragemLessThanEqual(quilometragem);
    }
    
    /**
     * Atualiza dados de um automóvel existente
     * 
     * @param id ID do automóvel a ser atualizado
     * @param automovelAtualizado dados atualizados do automóvel
     * @return automóvel atualizado
     * @throws IllegalArgumentException se automóvel não for encontrado ou placa já existir
     */
    public Automovel atualizarAutomovel(Long id, Automovel automovelAtualizado) {
        Automovel automovelExistente = automovelRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Automóvel não encontrado com ID: " + id));
        
        // Valida se placa não está sendo usada por outro automóvel
        if (!automovelExistente.getPlaca().equals(automovelAtualizado.getPlaca()) && 
            automovelRepository.existsByPlaca(automovelAtualizado.getPlaca())) {
            throw new IllegalArgumentException("Placa já cadastrada por outro automóvel");
        }
        
        // Atualiza os dados
        automovelExistente.setMarca(automovelAtualizado.getMarca());
        automovelExistente.setModelo(automovelAtualizado.getModelo());
        automovelExistente.setAno(automovelAtualizado.getAno());
        automovelExistente.setPlaca(automovelAtualizado.getPlaca());
        automovelExistente.setCor(automovelAtualizado.getCor());
        automovelExistente.setCategoria(automovelAtualizado.getCategoria());
        automovelExistente.setValorDiario(automovelAtualizado.getValorDiario());
        automovelExistente.setQuilometragem(automovelAtualizado.getQuilometragem());
        automovelExistente.setCapacidadePassageiros(automovelAtualizado.getCapacidadePassageiros());
        automovelExistente.setTipoCombustivel(automovelAtualizado.getTipoCombustivel());
        automovelExistente.setTransmissao(automovelAtualizado.getTransmissao());
        automovelExistente.setArCondicionado(automovelAtualizado.getArCondicionado());
        automovelExistente.setDirecaoHidraulica(automovelAtualizado.getDirecaoHidraulica());
        automovelExistente.setAirbag(automovelAtualizado.getAirbag());
        automovelExistente.setAbs(automovelAtualizado.getAbs());
        
        return automovelRepository.save(automovelExistente);
    }
    
    /**
     * Ativa um automóvel
     * 
     * @param id ID do automóvel a ser ativado
     * @return automóvel ativado
     * @throws IllegalArgumentException se automóvel não for encontrado
     */
    public Automovel ativarAutomovel(Long id) {
        Automovel automovel = automovelRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Automóvel não encontrado com ID: " + id));
        
        automovel.ativar();
        return automovelRepository.save(automovel);
    }
    
    /**
     * Desativa um automóvel (soft delete)
     * 
     * @param id ID do automóvel a ser desativado
     * @return automóvel desativado
     * @throws IllegalArgumentException se automóvel não for encontrado
     */
    public Automovel desativarAutomovel(Long id) {
        Automovel automovel = automovelRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Automóvel não encontrado com ID: " + id));
        
        automovel.desativar();
        return automovelRepository.save(automovel);
    }
    
    /**
     * Disponibiliza um automóvel para aluguel
     * 
     * @param id ID do automóvel a ser disponibilizado
     * @return automóvel disponibilizado
     * @throws IllegalArgumentException se automóvel não for encontrado
     */
    public Automovel disponibilizarAutomovel(Long id) {
        Automovel automovel = automovelRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Automóvel não encontrado com ID: " + id));
        
        automovel.disponibilizar();
        return automovelRepository.save(automovel);
    }
    
    /**
     * Indisponibiliza um automóvel para aluguel
     * 
     * @param id ID do automóvel a ser indisponibilizado
     * @return automóvel indisponibilizado
     * @throws IllegalArgumentException se automóvel não for encontrado
     */
    public Automovel indisponibilizarAutomovel(Long id) {
        Automovel automovel = automovelRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Automóvel não encontrado com ID: " + id));
        
        automovel.indisponibilizar();
        return automovelRepository.save(automovel);
    }
    
    /**
     * Remove automóvel permanentemente do sistema
     * 
     * @param id ID do automóvel a ser removido
     * @throws IllegalArgumentException se automóvel não for encontrado
     */
    public void removerAutomovel(Long id) {
        if (!automovelRepository.existsById(id)) {
            throw new IllegalArgumentException("Automóvel não encontrado com ID: " + id);
        }
        automovelRepository.deleteById(id);
    }
    
    /**
     * Conta total de automóveis ativos
     * 
     * @return número de automóveis ativos
     */
    @Transactional(readOnly = true)
    public long contarAtivos() {
        return automovelRepository.countAtivos();
    }
    
    /**
     * Conta total de automóveis disponíveis
     * 
     * @return número de automóveis disponíveis
     */
    @Transactional(readOnly = true)
    public long contarDisponiveis() {
        return automovelRepository.countDisponiveis();
    }
    
    /**
     * Conta automóveis por categoria
     * 
     * @param categoria categoria dos automóveis
     * @return número de automóveis da categoria
     */
    @Transactional(readOnly = true)
    public long contarPorCategoria(String categoria) {
        return automovelRepository.countByCategoria(categoria);
    }
    
    /**
     * Verifica se automóvel está disponível para aluguel
     * 
     * @param id ID do automóvel
     * @return true se disponível, false caso contrário
     */
    @Transactional(readOnly = true)
    public boolean isDisponivel(Long id) {
        Optional<Automovel> automovel = automovelRepository.findById(id);
        return automovel.isPresent() && automovel.get().getDisponivel() && automovel.get().getAtivo();
    }
}

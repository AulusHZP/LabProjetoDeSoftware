package org.example.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

import org.example.model.Automovel;
import org.example.model.Cliente;
import org.example.model.PedidoAluguel;
import org.example.repository.AutomovelRepository;
import org.example.repository.ClienteRepository;
import org.example.repository.PedidoAluguelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Serviço para lógica de negócio relacionada ao PedidoAluguel
 * Implementa as regras de negócio baseadas nas histórias de usuário do Cliente
 */
@Service
@Transactional
public class PedidoAluguelService {
    
    @Autowired
    private PedidoAluguelRepository pedidoAluguelRepository;
    
    @Autowired
    private ClienteRepository clienteRepository;
    
    @Autowired
    private AutomovelRepository automovelRepository;
    
    /**
     * Cria um novo pedido de aluguel
     * Atende à história de usuário: "Quero criar um pedido de aluguel selecionando o automóvel e período"
     * 
     * @param pedidoAluguel dados do pedido de aluguel
     * @return pedido de aluguel criado
     * @throws IllegalArgumentException se dados inválidos ou conflitos
     */
    public PedidoAluguel criarPedidoAluguel(PedidoAluguel pedidoAluguel) {
        // Validações de negócio
        validarPedidoAluguel(pedidoAluguel);
        
        // Verifica se o automóvel está disponível no período
        if (pedidoAluguelRepository.existsConflitoPeriodo(
                pedidoAluguel.getAutomovel(), 
                pedidoAluguel.getDataInicio(), 
                pedidoAluguel.getDataFim())) {
            throw new IllegalArgumentException("Automóvel não está disponível no período solicitado");
        }
        
        // Calcula valor total e número de dias
        calcularValoresPedido(pedidoAluguel);
        
        // Define status inicial
        pedidoAluguel.setStatus(PedidoAluguel.StatusPedido.PENDENTE);
        
        return pedidoAluguelRepository.save(pedidoAluguel);
    }
    
    /**
     * Modifica detalhes de um pedido de aluguel
     * Atende à história de usuário: "Quero modificar detalhes do meu pedido de aluguel"
     * 
     * @param id ID do pedido a ser modificado
     * @param pedidoModificado dados modificados do pedido
     * @return pedido modificado
     * @throws IllegalArgumentException se pedido não pode ser modificado
     */
    public PedidoAluguel modificarPedidoAluguel(Long id, PedidoAluguel pedidoModificado) {
        PedidoAluguel pedidoExistente = pedidoAluguelRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Pedido não encontrado com ID: " + id));
        
        // Verifica se o pedido pode ser modificado
        if (!pedidoExistente.podeSerModificado()) {
            throw new IllegalArgumentException("Pedido não pode ser modificado no status atual: " + pedidoExistente.getStatus());
        }
        
        // Validações de negócio
        validarPedidoAluguel(pedidoModificado);
        
        // Verifica se o automóvel está disponível no novo período (excluindo o próprio pedido)
        List<PedidoAluguel> conflitos = pedidoAluguelRepository.findConflitosPeriodo(
                pedidoModificado.getAutomovel(), 
                pedidoModificado.getDataInicio(), 
                pedidoModificado.getDataFim());
        
        conflitos.removeIf(p -> p.getId().equals(id)); // Remove o próprio pedido da verificação
        
        if (!conflitos.isEmpty()) {
            throw new IllegalArgumentException("Automóvel não está disponível no período solicitado");
        }
        
        // Atualiza os dados
        pedidoExistente.setCliente(pedidoModificado.getCliente());
        pedidoExistente.setAutomovel(pedidoModificado.getAutomovel());
        pedidoExistente.setDataInicio(pedidoModificado.getDataInicio());
        pedidoExistente.setDataFim(pedidoModificado.getDataFim());
        pedidoExistente.setObservacoes(pedidoModificado.getObservacoes());
        
        // Recalcula valores
        calcularValoresPedido(pedidoExistente);
        
        return pedidoAluguelRepository.save(pedidoExistente);
    }
    
    /**
     * Cancela um pedido de aluguel
     * Atende à história de usuário: "Quero cancelar um pedido de aluguel"
     * 
     * @param id ID do pedido a ser cancelado
     * @return pedido cancelado
     * @throws IllegalArgumentException se pedido não pode ser cancelado
     */
    public PedidoAluguel cancelarPedidoAluguel(Long id) {
        PedidoAluguel pedido = pedidoAluguelRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Pedido não encontrado com ID: " + id));
        
        // Verifica se o pedido pode ser cancelado
        if (!pedido.podeSerCancelado()) {
            throw new IllegalArgumentException("Pedido não pode ser cancelado no status atual: " + pedido.getStatus());
        }
        
        pedido.cancelar();
        return pedidoAluguelRepository.save(pedido);
    }
    
    /**
     * Aprova um pedido de aluguel
     * 
     * @param id ID do pedido a ser aprovado
     * @return pedido aprovado
     * @throws IllegalArgumentException se pedido não pode ser aprovado
     */
    public PedidoAluguel aprovarPedidoAluguel(Long id) {
        PedidoAluguel pedido = pedidoAluguelRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Pedido não encontrado com ID: " + id));
        
        if (pedido.getStatus() != PedidoAluguel.StatusPedido.PENDENTE) {
            throw new IllegalArgumentException("Apenas pedidos pendentes podem ser aprovados");
        }
        
        // Verifica novamente se o automóvel está disponível
        if (pedidoAluguelRepository.existsConflitoPeriodo(
                pedido.getAutomovel(), 
                pedido.getDataInicio(), 
                pedido.getDataFim())) {
            throw new IllegalArgumentException("Automóvel não está mais disponível no período");
        }
        
        pedido.aprovar();
        return pedidoAluguelRepository.save(pedido);
    }
    
    /**
     * Rejeita um pedido de aluguel
     * 
     * @param id ID do pedido a ser rejeitado
     * @param motivo motivo da rejeição
     * @return pedido rejeitado
     * @throws IllegalArgumentException se pedido não pode ser rejeitado
     */
    public PedidoAluguel rejeitarPedidoAluguel(Long id, String motivo) {
        PedidoAluguel pedido = pedidoAluguelRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Pedido não encontrado com ID: " + id));
        
        if (pedido.getStatus() != PedidoAluguel.StatusPedido.PENDENTE) {
            throw new IllegalArgumentException("Apenas pedidos pendentes podem ser rejeitados");
        }
        
        pedido.rejeitar(motivo);
        return pedidoAluguelRepository.save(pedido);
    }
    
    /**
     * Finaliza um pedido de aluguel
     * 
     * @param id ID do pedido a ser finalizado
     * @return pedido finalizado
     * @throws IllegalArgumentException se pedido não pode ser finalizado
     */
    public PedidoAluguel finalizarPedidoAluguel(Long id) {
        PedidoAluguel pedido = pedidoAluguelRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Pedido não encontrado com ID: " + id));
        
        if (pedido.getStatus() != PedidoAluguel.StatusPedido.APROVADO) {
            throw new IllegalArgumentException("Apenas pedidos aprovados podem ser finalizados");
        }
        
        pedido.finalizar();
        return pedidoAluguelRepository.save(pedido);
    }
    
    /**
     * Busca pedido por ID
     * 
     * @param id ID do pedido
     * @return Optional contendo o pedido se encontrado
     */
    @Transactional(readOnly = true)
    public Optional<PedidoAluguel> buscarPorId(Long id) {
        return pedidoAluguelRepository.findById(id);
    }
    
    /**
     * Lista todos os pedidos
     * 
     * @return lista de todos os pedidos
     */
    @Transactional(readOnly = true)
    public List<PedidoAluguel> listarTodos() {
        return pedidoAluguelRepository.findAll();
    }
    
    /**
     * Lista pedidos por cliente
     * 
     * @param cliente cliente dos pedidos
     * @return lista de pedidos do cliente
     */
    @Transactional(readOnly = true)
    public List<PedidoAluguel> listarPorCliente(Cliente cliente) {
        return pedidoAluguelRepository.findByClienteOrderByDataCadastroDesc(cliente);
    }
    
    /**
     * Lista pedidos por automóvel
     * 
     * @param automovel automóvel dos pedidos
     * @return lista de pedidos do automóvel
     */
    @Transactional(readOnly = true)
    public List<PedidoAluguel> listarPorAutomovel(Automovel automovel) {
        return pedidoAluguelRepository.findByAutomovel(automovel);
    }
    
    /**
     * Lista pedidos por status
     * 
     * @param status status dos pedidos
     * @return lista de pedidos com o status
     */
    @Transactional(readOnly = true)
    public List<PedidoAluguel> listarPorStatus(PedidoAluguel.StatusPedido status) {
        return pedidoAluguelRepository.findByStatus(status);
    }
    
    /**
     * Lista pedidos pendentes
     * 
     * @return lista de pedidos pendentes
     */
    @Transactional(readOnly = true)
    public List<PedidoAluguel> listarPendentes() {
        return pedidoAluguelRepository.findPedidosPendentesAprovacao();
    }
    
    /**
     * Lista pedidos aprovados
     * 
     * @return lista de pedidos aprovados
     */
    @Transactional(readOnly = true)
    public List<PedidoAluguel> listarAprovados() {
        return pedidoAluguelRepository.findPedidosAprovados();
    }
    
    /**
     * Lista pedidos rejeitados
     * 
     * @return lista de pedidos rejeitados
     */
    @Transactional(readOnly = true)
    public List<PedidoAluguel> listarRejeitados() {
        return pedidoAluguelRepository.findPedidosRejeitados();
    }
    
    /**
     * Lista pedidos cancelados
     * 
     * @return lista de pedidos cancelados
     */
    @Transactional(readOnly = true)
    public List<PedidoAluguel> listarCancelados() {
        return pedidoAluguelRepository.findPedidosCancelados();
    }
    
    /**
     * Lista pedidos finalizados
     * 
     * @return lista de pedidos finalizados
     */
    @Transactional(readOnly = true)
    public List<PedidoAluguel> listarFinalizados() {
        return pedidoAluguelRepository.findPedidosFinalizados();
    }
    
    /**
     * Lista pedidos por período
     * 
     * @param dataInicio data de início
     * @param dataFim data de fim
     * @return lista de pedidos no período
     */
    @Transactional(readOnly = true)
    public List<PedidoAluguel> listarPorPeriodo(LocalDate dataInicio, LocalDate dataFim) {
        return pedidoAluguelRepository.findByDataInicioBetween(dataInicio, dataFim);
    }
    
    /**
     * Lista pedidos por faixa de valor
     * 
     * @param valorMinimo valor mínimo
     * @param valorMaximo valor máximo
     * @return lista de pedidos na faixa de valor
     */
    @Transactional(readOnly = true)
    public List<PedidoAluguel> listarPorFaixaValor(BigDecimal valorMinimo, BigDecimal valorMaximo) {
        return pedidoAluguelRepository.findByValorTotalBetween(valorMinimo, valorMaximo);
    }
    
    /**
     * Conta pedidos por status
     * 
     * @param status status dos pedidos
     * @return número de pedidos com o status
     */
    @Transactional(readOnly = true)
    public long contarPorStatus(PedidoAluguel.StatusPedido status) {
        return pedidoAluguelRepository.countByStatus(status);
    }
    
    /**
     * Conta pedidos por cliente
     * 
     * @param cliente cliente dos pedidos
     * @return número de pedidos do cliente
     */
    @Transactional(readOnly = true)
    public long contarPorCliente(Cliente cliente) {
        return pedidoAluguelRepository.countByCliente(cliente);
    }
    
    /**
     * Calcula valor total de pedidos por status
     * 
     * @param status status dos pedidos
     * @return valor total dos pedidos com o status
     */
    @Transactional(readOnly = true)
    public BigDecimal calcularValorTotalPorStatus(PedidoAluguel.StatusPedido status) {
        return pedidoAluguelRepository.sumValorTotalByStatus(status);
    }
    
    /**
     * Calcula valor total de pedidos por cliente
     * 
     * @param cliente cliente dos pedidos
     * @return valor total dos pedidos do cliente
     */
    @Transactional(readOnly = true)
    public BigDecimal calcularValorTotalPorCliente(Cliente cliente) {
        return pedidoAluguelRepository.sumValorTotalByCliente(cliente);
    }
    
    /**
     * Valida dados do pedido de aluguel
     * 
     * @param pedido pedido a ser validado
     * @throws IllegalArgumentException se dados inválidos
     */
    private void validarPedidoAluguel(PedidoAluguel pedido) {
        if (pedido.getCliente() == null || pedido.getCliente().getId() == null) {
            throw new IllegalArgumentException("Cliente é obrigatório");
        }
        
        if (pedido.getAutomovel() == null || pedido.getAutomovel().getId() == null) {
            throw new IllegalArgumentException("Automóvel é obrigatório");
        }
        
        if (pedido.getDataInicio() == null) {
            throw new IllegalArgumentException("Data de início é obrigatória");
        }
        
        if (pedido.getDataFim() == null) {
            throw new IllegalArgumentException("Data de fim é obrigatória");
        }
        
        if (pedido.getDataInicio().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Data de início deve ser no futuro");
        }
        
        if (pedido.getDataFim().isBefore(pedido.getDataInicio())) {
            throw new IllegalArgumentException("Data de fim deve ser posterior à data de início");
        }
        
        // Verifica se cliente existe e está ativo
        Cliente cliente = clienteRepository.findById(pedido.getCliente().getId())
                .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado"));
        
        if (!cliente.getAtivo()) {
            throw new IllegalArgumentException("Cliente não está ativo");
        }
        
        // Verifica se automóvel existe e está disponível
        Automovel automovel = automovelRepository.findById(pedido.getAutomovel().getId())
                .orElseThrow(() -> new IllegalArgumentException("Automóvel não encontrado"));
        
        if (!automovel.getAtivo()) {
            throw new IllegalArgumentException("Automóvel não está ativo");
        }
        
        if (!automovel.getDisponivel()) {
            throw new IllegalArgumentException("Automóvel não está disponível");
        }
    }
    
    /**
     * Calcula valores do pedido (número de dias e valor total)
     * 
     * @param pedido pedido para calcular valores
     */
    private void calcularValoresPedido(PedidoAluguel pedido) {
        // Calcula número de dias
        long dias = ChronoUnit.DAYS.between(pedido.getDataInicio(), pedido.getDataFim()) + 1;
        pedido.setNumeroDias((int) dias);
        
        // Calcula valor total
        BigDecimal valorDiario = pedido.getAutomovel().getValorDiario();
        BigDecimal valorTotal = valorDiario.multiply(BigDecimal.valueOf(dias));
        pedido.setValorTotal(valorTotal);
    }
}

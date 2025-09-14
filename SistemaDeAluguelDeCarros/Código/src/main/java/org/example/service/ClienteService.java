package org.example.service;

import org.example.model.Cliente;
import org.example.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Serviço para lógica de negócio relacionada ao Cliente
 * Implementa as regras de negócio baseadas nas histórias de usuário
 */
@Service
@Transactional
public class ClienteService {
    
    @Autowired
    private ClienteRepository clienteRepository;
    
    /**
     * Cadastra um novo cliente no sistema
     * Atende à história de usuário: "Quero me cadastrar no sistema fornecendo meus dados pessoais"
     * 
     * @param cliente dados do cliente a ser cadastrado
     * @return cliente cadastrado com ID gerado
     * @throws IllegalArgumentException se email ou CPF já existirem
     */
    public Cliente cadastrarCliente(Cliente cliente) {
        // Validações de negócio
        if (clienteRepository.existsByEmail(cliente.getEmail())) {
            throw new IllegalArgumentException("Email já cadastrado no sistema");
        }
        
        if (clienteRepository.existsByCpf(cliente.getCpf())) {
            throw new IllegalArgumentException("CPF já cadastrado no sistema");
        }
        
        // Define data de cadastro
        cliente.setDataCadastro(LocalDateTime.now());
        cliente.setAtivo(true);
        
        return clienteRepository.save(cliente);
    }
    
    /**
     * Busca cliente por ID
     * 
     * @param id ID do cliente
     * @return Optional contendo o cliente se encontrado
     */
    @Transactional(readOnly = true)
    public Optional<Cliente> buscarPorId(Long id) {
        return clienteRepository.findById(id);
    }
    
    /**
     * Busca cliente por email
     * 
     * @param email email do cliente
     * @return Optional contendo o cliente se encontrado
     */
    @Transactional(readOnly = true)
    public Optional<Cliente> buscarPorEmail(String email) {
        return clienteRepository.findByEmail(email);
    }
    
    /**
     * Busca cliente por CPF
     * 
     * @param cpf CPF do cliente
     * @return Optional contendo o cliente se encontrado
     */
    @Transactional(readOnly = true)
    public Optional<Cliente> buscarPorCpf(String cpf) {
        return clienteRepository.findByCpf(cpf);
    }
    
    /**
     * Lista todos os clientes
     * 
     * @return lista de todos os clientes
     */
    @Transactional(readOnly = true)
    public List<Cliente> listarTodos() {
        return clienteRepository.findAll();
    }
    
    /**
     * Lista apenas clientes ativos
     * 
     * @return lista de clientes ativos
     */
    @Transactional(readOnly = true)
    public List<Cliente> listarAtivos() {
        return clienteRepository.findByAtivoTrue();
    }
    
    /**
     * Lista apenas clientes inativos
     * 
     * @return lista de clientes inativos
     */
    @Transactional(readOnly = true)
    public List<Cliente> listarInativos() {
        return clienteRepository.findByAtivoFalse();
    }
    
    /**
     * Busca clientes por nome (busca parcial)
     * 
     * @param nome nome ou parte do nome
     * @return lista de clientes que contém o nome
     */
    @Transactional(readOnly = true)
    public List<Cliente> buscarPorNome(String nome) {
        return clienteRepository.findByNomeContainingIgnoreCase(nome);
    }
    
    /**
     * Busca clientes por cidade
     * 
     * @param cidade cidade dos clientes
     * @return lista de clientes da cidade
     */
    @Transactional(readOnly = true)
    public List<Cliente> buscarPorCidade(String cidade) {
        return clienteRepository.findByCidade(cidade);
    }
    
    /**
     * Busca clientes por estado
     * 
     * @param estado estado dos clientes
     * @return lista de clientes do estado
     */
    @Transactional(readOnly = true)
    public List<Cliente> buscarPorEstado(String estado) {
        return clienteRepository.findByEstado(estado);
    }
    
    /**
     * Atualiza dados de um cliente existente
     * Atende à história de usuário: "Quero atualizar meus dados pessoais e profissionais"
     * 
     * @param id ID do cliente a ser atualizado
     * @param clienteAtualizado dados atualizados do cliente
     * @return cliente atualizado
     * @throws IllegalArgumentException se cliente não for encontrado ou dados inválidos
     */
    public Cliente atualizarCliente(Long id, Cliente clienteAtualizado) {
        Cliente clienteExistente = clienteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado com ID: " + id));
        
        // Valida se email não está sendo usado por outro cliente
        if (!clienteExistente.getEmail().equals(clienteAtualizado.getEmail()) && 
            clienteRepository.existsByEmail(clienteAtualizado.getEmail())) {
            throw new IllegalArgumentException("Email já cadastrado por outro cliente");
        }
        
        // Valida se CPF não está sendo usado por outro cliente
        if (!clienteExistente.getCpf().equals(clienteAtualizado.getCpf()) && 
            clienteRepository.existsByCpf(clienteAtualizado.getCpf())) {
            throw new IllegalArgumentException("CPF já cadastrado por outro cliente");
        }
        
        // Atualiza os dados
        clienteExistente.setNome(clienteAtualizado.getNome());
        clienteExistente.setEmail(clienteAtualizado.getEmail());
        clienteExistente.setSenha(clienteAtualizado.getSenha());
        clienteExistente.setCpf(clienteAtualizado.getCpf());
        clienteExistente.setTelefone(clienteAtualizado.getTelefone());
        clienteExistente.setDataNascimento(clienteAtualizado.getDataNascimento());
        clienteExistente.setEndereco(clienteAtualizado.getEndereco());
        clienteExistente.setCidade(clienteAtualizado.getCidade());
        clienteExistente.setEstado(clienteAtualizado.getEstado());
        clienteExistente.setCep(clienteAtualizado.getCep());
        clienteExistente.setDataUltimaAtualizacao(LocalDateTime.now());
        
        return clienteRepository.save(clienteExistente);
    }
    
    /**
     * Autentica cliente por email e senha
     * Atende à história de usuário: "Quero fazer login no sistema com meu email e senha"
     * 
     * @param email email do cliente
     * @param senha senha do cliente
     * @return Optional contendo o cliente se autenticação for bem-sucedida
     */
    @Transactional(readOnly = true)
    public Optional<Cliente> autenticarCliente(String email, String senha) {
        return clienteRepository.findAtivoByEmailAndSenha(email, senha);
    }
    
    /**
     * Ativa um cliente
     * 
     * @param id ID do cliente a ser ativado
     * @return cliente ativado
     * @throws IllegalArgumentException se cliente não for encontrado
     */
    public Cliente ativarCliente(Long id) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado com ID: " + id));
        
        cliente.ativar();
        return clienteRepository.save(cliente);
    }
    
    /**
     * Desativa um cliente (soft delete)
     * 
     * @param id ID do cliente a ser desativado
     * @return cliente desativado
     * @throws IllegalArgumentException se cliente não for encontrado
     */
    public Cliente desativarCliente(Long id) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado com ID: " + id));
        
        cliente.desativar();
        return clienteRepository.save(cliente);
    }
    
    /**
     * Remove cliente permanentemente do sistema
     * 
     * @param id ID do cliente a ser removido
     * @throws IllegalArgumentException se cliente não for encontrado
     */
    public void removerCliente(Long id) {
        if (!clienteRepository.existsById(id)) {
            throw new IllegalArgumentException("Cliente não encontrado com ID: " + id);
        }
        clienteRepository.deleteById(id);
    }
    
    /**
     * Conta total de clientes ativos
     * 
     * @return número de clientes ativos
     */
    @Transactional(readOnly = true)
    public long contarAtivos() {
        return clienteRepository.countAtivos();
    }
    
    /**
     * Conta total de clientes inativos
     * 
     * @return número de clientes inativos
     */
    @Transactional(readOnly = true)
    public long contarInativos() {
        return clienteRepository.countInativos();
    }
    
    /**
     * Busca clientes cadastrados em um período
     * 
     * @param dataInicio data de início do período
     * @param dataFim data de fim do período
     * @return lista de clientes cadastrados no período
     */
    @Transactional(readOnly = true)
    public List<Cliente> buscarPorPeriodoCadastro(LocalDateTime dataInicio, LocalDateTime dataFim) {
        return clienteRepository.findByDataCadastroBetween(dataInicio, dataFim);
    }
}

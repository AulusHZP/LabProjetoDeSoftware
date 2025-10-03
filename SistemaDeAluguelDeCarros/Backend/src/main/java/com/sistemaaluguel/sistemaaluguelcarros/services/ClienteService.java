package com.sistemaaluguel.sistemaaluguelcarros.services;

import com.sistemaaluguel.sistemaaluguelcarros.entities.Cliente;
import com.sistemaaluguel.sistemaaluguelcarros.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository; // CONSIDERAR: Mudar para injeção por construtor (@Autowired em construtor) para melhor testabilidade e imutabilidade

    public List<Cliente> findAll() {
        return clienteRepository.findAll(); // CONSIDERAR: Adicionar paginação (Pageable) para melhor performance com muitos registros
    }

    public Optional<Cliente> findById(Long id) {
        // CONSIDERAR: Validar se id não é nulo
        // CONSIDERAR: Logar consulta por ID para auditoria
        return clienteRepository.findById(id);
    }

    public Optional<Cliente> findByCpf(String cpf) {
        // CONSIDERAR: Validar se CPF não é nulo ou vazio
        // CONSIDERAR: Aplicar formatação/unificação do CPF (remover pontos, traços)
        // CONSIDERAR: Lançar exceção específica para CPF inválido
        return clienteRepository.findByCpf(cpf);
    }

    public Optional<Cliente> findByRg(String rg) {
        // CONSIDERAR: Validar se RG não é nulo ou vazio
        // CONSIDERAR: Aplicar formatação/unificação do RG
        // CONSIDERAR: Lançar exceção específica para RG inválido
        return clienteRepository.findByRg(rg);
    }

    public Cliente save(Cliente cliente) {
        // CONSIDERAR: Validar objeto cliente não nulo
        // CONSIDERAR: Validar campos obrigatórios (nome, CPF, RG, etc.)
        // CONSIDERAR: Verificar se CPF já existe (usando existsByCpf)
        // CONSIDERAR: Verificar se RG já existe (usando existsByRg)
        // CONSIDERAR: Lançar exceção personalizada para dados duplicados
        // CONSIDERAR: Logar criação de novo cliente
        return clienteRepository.save(cliente);
    }

    public void deleteById(Long id) {
        // CONSIDERAR: Verificar se cliente existe antes de deletar
        // CONSIDERAR: Implementar exclusão lógica em vez de física
        // CONSIDERAR: Lançar exceção personalizada se cliente não existir
        // CONSIDERAR: Lançar exceção personalizada se cliente tiver aluguéis ativos
        // CONSIDERAR: Tratar EmptyResultDataAccessException
        // CONSIDERAR: Logar exclusão para auditoria
        clienteRepository.deleteById(id);
    }

    public boolean existsByCpf(String cpf) {
        // CONSIDERAR: Validar formato do CPF antes da consulta
        return clienteRepository.existsByCpf(cpf);
    }

    public boolean existsByRg(String rg) {
        // CONSIDERAR: Validar formato do RG antes da consulta
        return clienteRepository.existsByRg(rg);
    }
}
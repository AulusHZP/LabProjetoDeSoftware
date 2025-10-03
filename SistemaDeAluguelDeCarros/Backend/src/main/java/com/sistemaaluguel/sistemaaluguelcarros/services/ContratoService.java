package com.sistemaaluguel.sistemaaluguelcarros.services;

import com.sistemaaluguel.sistemaaluguelcarros.entities.Contrato;
import com.sistemaaluguel.sistemaaluguelcarros.enums.StatusContrato;
import com.sistemaaluguel.sistemaaluguelcarros.repository.ContratoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ContratoService {

    @Autowired
    private ContratoRepository contratoRepository; // CONSIDERAR: Injeção por construtor para melhor testabilidade

    public List<Contrato> findAll() {
        return contratoRepository.findAll(); // CONSIDERAR: Adicionar paginação para muitos registros
    }

    public Optional<Contrato> findById(Long id) {
        // CONSIDERAR: Validar se id não é nulo
        return contratoRepository.findById(id);
    }

    public List<Contrato> findByAgenteId(Long agenteId) {
        // CONSIDERAR: Validar se agenteId não é nulo
        return contratoRepository.findByAgenteId(agenteId);
    }

    public List<Contrato> findByStatus(StatusContrato status) {
        // CONSIDERAR: Validar se status não é nulo
        return contratoRepository.findByStatus(status);
    }

    public List<Contrato> findByDataAssinaturaBetween(LocalDate dataInicio, LocalDate dataFim) {
        // CONSIDERAR: Validar se datas não são nulas
        // CONSIDERAR: Validar se dataInicio é anterior a dataFim
        return contratoRepository.findByDataAssinaturaBetween(dataInicio, dataFim);
    }

    public Contrato save(Contrato contrato) {
        // CONSIDERAR: Validar objeto contrato não nulo
        // CONSIDERAR: Validar dados obrigatórios do contrato
        // CONSIDERAR: Tratar exceções de integridade do banco
        return contratoRepository.save(contrato);
    }

    public void deleteById(Long id) {
        // CONSIDERAR: Verificar se contrato existe antes de deletar
        // CONSIDERAR: Validar regras de negócio para exclusão
        // CONSIDERAR: Tratar exceção se contrato não existir
        contratoRepository.deleteById(id);
    }

    public Contrato assinarContrato(Long id) {
        Optional<Contrato> contratoOpt = findById(id);
        if (contratoOpt.isPresent()) {
            Contrato contrato = contratoOpt.get();
            contrato.assinar();
            return save(contrato);
        }
        return null; // CONSIDERAR: Lançar exceção personalizada em vez de retornar null
    }

    public Contrato cancelarContrato(Long id) {
        Optional<Contrato> contratoOpt = findById(id);
        if (contratoOpt.isPresent()) {
            Contrato contrato = contratoOpt.get();
            contrato.cancelar();
            return save(contrato);
        }
        return null; // CONSIDERAR: Lançar exceção personalizada em vez de retornar null
    }

    public Contrato finalizarContrato(Long id) {
        Optional<Contrato> contratoOpt = findById(id);
        if (contratoOpt.isPresent()) {
            Contrato contrato = contratoOpt.get();
            contrato.finalizar();
            return save(contrato);
        }
        return null; // CONSIDERAR: Lançar exceção personalizada em vez de retornar null
    }
}
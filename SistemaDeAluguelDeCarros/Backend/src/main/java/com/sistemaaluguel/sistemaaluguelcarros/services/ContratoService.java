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
    private ContratoRepository contratoRepository;

    public List<Contrato> findAll() {
        return contratoRepository.findAll();
    }

    public Optional<Contrato> findById(Long id) {
        return contratoRepository.findById(id);
    }

    public List<Contrato> findByAgenteId(Long agenteId) {
        return contratoRepository.findByAgenteId(agenteId);
    }

    public List<Contrato> findByStatus(StatusContrato status) {
        return contratoRepository.findByStatus(status);
    }

    public List<Contrato> findByDataAssinaturaBetween(LocalDate dataInicio, LocalDate dataFim) {
        return contratoRepository.findByDataAssinaturaBetween(dataInicio, dataFim);
    }

    public Contrato save(Contrato contrato) {
        return contratoRepository.save(contrato);
    }

    public void deleteById(Long id) {
        contratoRepository.deleteById(id);
    }

    public Contrato assinarContrato(Long id) {
        Optional<Contrato> contratoOpt = findById(id);
        if (contratoOpt.isPresent()) {
            Contrato contrato = contratoOpt.get();
            contrato.assinar();
            return save(contrato);
        }
        return null;
    }

    public Contrato cancelarContrato(Long id) {
        Optional<Contrato> contratoOpt = findById(id);
        if (contratoOpt.isPresent()) {
            Contrato contrato = contratoOpt.get();
            contrato.cancelar();
            return save(contrato);
        }
        return null;
    }

    public Contrato finalizarContrato(Long id) {
        Optional<Contrato> contratoOpt = findById(id);
        if (contratoOpt.isPresent()) {
            Contrato contrato = contratoOpt.get();
            contrato.finalizar();
            return save(contrato);
        }
        return null;
    }
}

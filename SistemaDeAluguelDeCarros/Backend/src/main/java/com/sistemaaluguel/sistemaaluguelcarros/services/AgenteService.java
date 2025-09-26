package com.sistemaaluguel.sistemaaluguelcarros.services;

import com.sistemaaluguel.sistemaaluguelcarros.entities.Agente;
import com.sistemaaluguel.sistemaaluguelcarros.enums.TipoAgente;
import com.sistemaaluguel.sistemaaluguelcarros.repository.AgenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AgenteService {

    @Autowired
    private AgenteRepository agenteRepository;

    public List<Agente> findAll() {
        return agenteRepository.findAll();
    }

    public Optional<Agente> findById(Long id) {
        return agenteRepository.findById(id);
    }

    public Optional<Agente> findByCnpj(String cnpj) {
        return agenteRepository.findByCnpj(cnpj);
    }

    public List<Agente> findByTipoAgente(TipoAgente tipoAgente) {
        return agenteRepository.findByTipoAgente(tipoAgente);
    }

    public Agente save(Agente agente) {
        return agenteRepository.save(agente);
    }

    public void deleteById(Long id) {
        agenteRepository.deleteById(id);
    }

    public boolean existsByCnpj(String cnpj) {
        return agenteRepository.existsByCnpj(cnpj);
    }
}

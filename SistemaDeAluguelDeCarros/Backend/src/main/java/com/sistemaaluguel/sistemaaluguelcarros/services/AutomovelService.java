package com.sistemaaluguel.sistemaaluguelcarros.services;

import com.sistemaaluguel.sistemaaluguelcarros.entities.Automovel;
import com.sistemaaluguel.sistemaaluguelcarros.repository.AutomovelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AutomovelService {

    @Autowired
    private AutomovelRepository automovelRepository;

    public List<Automovel> findAll() {
        return automovelRepository.findAll();
    }

    public Optional<Automovel> findById(Long id) {
        return automovelRepository.findById(id);
    }

    public Optional<Automovel> findByMatricula(String matricula) {
        return automovelRepository.findByMatricula(matricula);
    }

    public Optional<Automovel> findByPlaca(String placa) {
        return automovelRepository.findByPlaca(placa);
    }

    public List<Automovel> findByMarca(String marca) {
        return automovelRepository.findByMarca(marca);
    }

    public List<Automovel> findByModelo(String modelo) {
        return automovelRepository.findByModelo(modelo);
    }

    public List<Automovel> findByAno(Integer ano) {
        return automovelRepository.findByAno(ano);
    }

    public Automovel save(Automovel automovel) {
        return automovelRepository.save(automovel);
    }

    public void deleteById(Long id) {
        automovelRepository.deleteById(id);
    }

    public boolean existsByMatricula(String matricula) {
        return automovelRepository.existsByMatricula(matricula);
    }

    public boolean existsByPlaca(String placa) {
        return automovelRepository.existsByPlaca(placa);
    }
}

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

    // tratar o erro caso nao encontre o usuario com o id passado, nesse caso o sistema irá cair e não terá nenhum retorno sobre qual é o problema.
    public Optional<Automovel> findById(Long id) {
        return automovelRepository.findById(id);
    }

    // caso o valor da string seja passado errado ou nao encontrado, o sistema irá dar um erro e como nao esta sedno tratado, ele dará um erro e o sistema irá cair.
    public Optional<Automovel> findByMatricula(String matricula) {
        return automovelRepository.findByMatricula(matricula);
    }
// caso o valor da string seja passado errado ou nao encontrado, o sistema irá dar um erro e como nao esta sedno tratado, ele dará um erro e o sistema irá cair.
    public Optional<Automovel> findByPlaca(String placa) {
        return automovelRepository.findByPlaca(placa);
    }

    // caso o valor da string seja passado errado ou nao encontrado, o sistema irá dar um erro e como nao esta sedno tratado, ele dará um erro e o sistema irá cair.
    public List<Automovel> findByMarca(String marca) {
        return automovelRepository.findByMarca(marca);
    }

    // caso o valor da string seja passado errado ou nao encontrado, o sistema irá dar um erro e como nao esta sedno tratado, ele dará um erro e o sistema irá cair.
    public List<Automovel> findByModelo(String modelo) {
        return automovelRepository.findByModelo(modelo);
    }

    // caso o valor da inteiro seja passado errado ou nao encontrado, o sistema irá dar um erro e como nao esta sedno tratado, ele dará um erro e o sistema irá cair.
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

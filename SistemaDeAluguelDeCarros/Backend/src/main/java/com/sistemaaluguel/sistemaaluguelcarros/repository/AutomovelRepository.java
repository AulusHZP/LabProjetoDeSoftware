package com.sistemaaluguel.sistemaaluguelcarros.repository;

import com.sistemaaluguel.sistemaaluguelcarros.entities.Automovel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AutomovelRepository extends JpaRepository<Automovel, Long> {
    Optional<Automovel> findByMatricula(String matricula);
    Optional<Automovel> findByPlaca(String placa);
    List<Automovel> findByMarca(String marca);
    List<Automovel> findByModelo(String modelo);
    List<Automovel> findByAno(Integer ano);
    boolean existsByMatricula(String matricula);
    boolean existsByPlaca(String placa);
}

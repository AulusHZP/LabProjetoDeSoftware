package com.sistemaaluguel.sistemaaluguelcarros.repository;

import com.sistemaaluguel.sistemaaluguelcarros.entities.ContratoCredito;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContratoCreditoRepository extends JpaRepository<ContratoCredito, Long> {
    List<ContratoCredito> findByContratoId(Long contratoId);
}

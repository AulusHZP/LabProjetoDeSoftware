package com.sistemaaluguel.sistemaaluguelcarros.repository;

import com.sistemaaluguel.sistemaaluguelcarros.entities.Contrato;
import com.sistemaaluguel.sistemaaluguelcarros.enums.StatusContrato;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ContratoRepository extends JpaRepository<Contrato, Long> {
    List<Contrato> findByAgenteId(Long agenteId);
    List<Contrato> findByStatus(StatusContrato status);
    List<Contrato> findByDataAssinaturaBetween(LocalDate dataInicio, LocalDate dataFim);
}

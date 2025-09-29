package com.sistemaaluguel.sistemaaluguelcarros.repository;

import com.sistemaaluguel.sistemaaluguelcarros.entities.Rendimento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RendimentoRepository extends JpaRepository<Rendimento, Long> {
    List<Rendimento> findByClienteId(Long clienteId);
}

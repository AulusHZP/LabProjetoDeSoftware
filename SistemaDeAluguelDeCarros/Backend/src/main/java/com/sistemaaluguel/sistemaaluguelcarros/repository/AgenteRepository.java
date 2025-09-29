package com.sistemaaluguel.sistemaaluguelcarros.repository;

import com.sistemaaluguel.sistemaaluguelcarros.entities.Agente;
import com.sistemaaluguel.sistemaaluguelcarros.enums.TipoAgente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AgenteRepository extends JpaRepository<Agente, Long> {
    Optional<Agente> findByCnpj(String cnpj);
    List<Agente> findByTipoAgente(TipoAgente tipoAgente);
    boolean existsByCnpj(String cnpj);
}

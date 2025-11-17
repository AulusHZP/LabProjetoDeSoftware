package com.moedaestudantil.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.moedaestudantil.model.Transaction;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByProfessorIdOrderByCreatedAtDesc(Long professorId);
    List<Transaction> findByTypeOrderByCreatedAtDesc(String type);
    List<Transaction> findByStudentIdOrderByCreatedAtDesc(Long studentId);
}

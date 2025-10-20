package com.moedaestudantil.repository;

import com.moedaestudantil.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    
    @Query("SELECT t FROM Transaction t WHERE t.student.id = :studentId ORDER BY t.createdAt DESC")
    List<Transaction> findTransactionsByStudent(@Param("studentId") Long studentId);
    
    @Query("SELECT t FROM Transaction t WHERE t.professor.id = :professorId ORDER BY t.createdAt DESC")
    List<Transaction> findTransactionsByProfessor(@Param("professorId") Long professorId);
    
    @Query("SELECT t FROM Transaction t WHERE t.student.id = :studentId AND t.type = 'PROFESSOR_TO_STUDENT' ORDER BY t.createdAt DESC")
    List<Transaction> findReceivedTransactionsByStudent(@Param("studentId") Long studentId);
    
    @Query("SELECT t FROM Transaction t WHERE t.student.id = :studentId AND t.type = 'STUDENT_REDEMPTION' ORDER BY t.createdAt DESC")
    List<Transaction> findRedemptionTransactionsByStudent(@Param("studentId") Long studentId);
}

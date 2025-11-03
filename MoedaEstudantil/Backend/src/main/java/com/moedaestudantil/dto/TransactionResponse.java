package com.moedaestudantil.dto;

import java.time.LocalDateTime;

public class TransactionResponse {
    private Long id;
    private Long professorId;
    private Long studentId;
    private Integer amount;
    private String reason;
    private LocalDateTime createdAt;

    public TransactionResponse() {}

    public TransactionResponse(Long id, Long professorId, Long studentId, Integer amount, String reason, LocalDateTime createdAt) {
        this.id = id;
        this.professorId = professorId;
        this.studentId = studentId;
        this.amount = amount;
        this.reason = reason;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public Long getProfessorId() {
        return professorId;
    }

    public Long getStudentId() {
        return studentId;
    }

    public Integer getAmount() {
        return amount;
    }

    public String getReason() {
        return reason;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
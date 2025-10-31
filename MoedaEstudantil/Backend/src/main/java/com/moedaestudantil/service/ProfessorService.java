package com.moedaestudantil.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.moedaestudantil.dto.ProfessorLoginRequest;
import com.moedaestudantil.dto.ProfessorRegisterRequest;
import com.moedaestudantil.dto.ProfessorResponse;
import com.moedaestudantil.model.Institution;
import com.moedaestudantil.model.Professor;
import com.moedaestudantil.repository.InstitutionRepository;
import com.moedaestudantil.repository.ProfessorRepository;

@Service
public class ProfessorService {

    @Autowired
    private ProfessorRepository professorRepository;

    @Autowired
    private InstitutionRepository institutionRepository;

    @Transactional
    public ProfessorResponse registerProfessor(ProfessorRegisterRequest request) {
        if (professorRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email já cadastrado");
        }

        if (professorRepository.existsByCpf(request.getCpf())) {
            throw new RuntimeException("CPF já cadastrado");
        }

        Institution institution = institutionRepository.findById(request.getInstitutionId())
            .orElseThrow(() -> new RuntimeException("Instituição não encontrada"));

        Professor professor = new Professor(
            request.getName(),
            request.getCpf(),
            request.getDepartment(),
            institution,
            request.getPassword()
        );
        // set email separately (constructor doesn't include it)
        professor.setEmail(request.getEmail());

        Professor saved = professorRepository.save(professor);
        return new ProfessorResponse(saved);
    }

    public ProfessorResponse loginProfessor(ProfessorLoginRequest request) {
        return professorRepository.findByEmail(request.getEmail())
            .map(professor -> {
                if (!professor.getPassword().equals(request.getPassword())) {
                    return new ProfessorResponse("Credenciais inválidas");
                }
                return new ProfessorResponse(professor);
            })
            .orElseGet(() -> new ProfessorResponse("Credenciais inválidas"));
    }

    public List<ProfessorResponse> getAllProfessors() {
        return professorRepository.findAll().stream()
            .map(ProfessorResponse::new)
            .collect(Collectors.toList());
    }

    public ProfessorResponse getProfessorById(Long id) {
        Professor p = professorRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Professor não encontrado"));
        return new ProfessorResponse(p);
    }

    @Transactional
    public void deleteProfessor(Long id) {
        Professor p = professorRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Professor não encontrado"));
        professorRepository.delete(p);
    }
}

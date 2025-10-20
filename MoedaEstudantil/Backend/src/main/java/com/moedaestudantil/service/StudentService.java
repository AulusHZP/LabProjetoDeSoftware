package com.moedaestudantil.service;

import com.moedaestudantil.dto.StudentRequest;
import com.moedaestudantil.model.Institution;
import com.moedaestudantil.model.Student;
import com.moedaestudantil.repository.InstitutionRepository;
import com.moedaestudantil.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {
    
    @Autowired
    private StudentRepository studentRepository;
    
    @Autowired
    private InstitutionRepository institutionRepository;
    
    public Student createStudent(StudentRequest request) {
        // Verificar se email já existe
        if (studentRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email já cadastrado");
        }
        
        // Verificar se CPF já existe
        if (studentRepository.findByCpf(request.getCpf()).isPresent()) {
            throw new RuntimeException("CPF já cadastrado");
        }
        
        // Buscar instituição
        Institution institution = institutionRepository.findById(request.getInstitutionId())
            .orElseThrow(() -> new RuntimeException("Instituição não encontrada"));
        
        // Criar aluno
        Student student = new Student(
            request.getName(),
            request.getEmail(),
            request.getCpf(),
            request.getRg(),
            request.getAddress(),
            institution,
            request.getCourse(),
            request.getPassword()
        );
        
        return studentRepository.save(student);
    }
    
    public Student authenticateStudent(String email, String password) {
        Student student = studentRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("Aluno não encontrado"));
        
        if (!student.getPassword().equals(password)) {
            throw new RuntimeException("Senha incorreta");
        }
        
        return student;
    }
    
    public Student getStudentById(Long id) {
        return studentRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Aluno não encontrado"));
    }
    
    public List<Student> getStudentsByInstitution(Long institutionId) {
        return studentRepository.findStudentsByInstitution(institutionId);
    }
    
    public Student updateStudent(Long id, StudentRequest request) {
        Student student = studentRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Aluno não encontrado"));
        
        // Verificar se email já existe (exceto para o próprio aluno)
        Optional<Student> existingStudent = studentRepository.findByEmail(request.getEmail());
        if (existingStudent.isPresent() && !existingStudent.get().getId().equals(id)) {
            throw new RuntimeException("Email já cadastrado");
        }
        
        // Verificar se CPF já existe (exceto para o próprio aluno)
        Optional<Student> existingStudentByCpf = studentRepository.findByCpf(request.getCpf());
        if (existingStudentByCpf.isPresent() && !existingStudentByCpf.get().getId().equals(id)) {
            throw new RuntimeException("CPF já cadastrado");
        }
        
        // Buscar instituição
        Institution institution = institutionRepository.findById(request.getInstitutionId())
            .orElseThrow(() -> new RuntimeException("Instituição não encontrada"));
        
        // Atualizar dados
        student.setName(request.getName());
        student.setEmail(request.getEmail());
        student.setCpf(request.getCpf());
        student.setRg(request.getRg());
        student.setAddress(request.getAddress());
        student.setInstitution(institution);
        student.setCourse(request.getCourse());
        student.setPassword(request.getPassword());
        
        return studentRepository.save(student);
    }
    
    public void deleteStudent(Long id) {
        Student student = studentRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Aluno não encontrado"));
        
        studentRepository.delete(student);
    }
}

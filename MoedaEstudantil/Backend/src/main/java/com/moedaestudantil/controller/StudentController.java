package com.moedaestudantil.controller;

import com.moedaestudantil.dto.StudentRequest;
import com.moedaestudantil.dto.StudentResponse;
import com.moedaestudantil.model.Student;
import com.moedaestudantil.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/students")
@CrossOrigin(origins = "*")
public class StudentController {
    
    @Autowired
    private StudentService studentService;
    
    @PostMapping("/register")
    public ResponseEntity<?> registerStudent(@Valid @RequestBody StudentRequest request) {
        try {
            System.out.println("Registrando aluno: " + request.getName());
            Student student = studentService.createStudent(request);
            System.out.println("Aluno criado com ID: " + student.getId());
            
            StudentResponse response = new StudentResponse(
                student.getId(),
                student.getName(),
                student.getEmail(),
                student.getCpf(),
                student.getRg(),
                student.getAddress(),
                student.getInstitution().getId(),
                student.getInstitution().getName(),
                student.getCourse(),
                student.getCoinBalance(),
                student.getCreatedAt()
            );
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            System.out.println("Erro ao registrar aluno: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @PostMapping("/login")
    public ResponseEntity<?> loginStudent(@RequestBody Map<String, String> loginData) {
        try {
            String email = loginData.get("email");
            String password = loginData.get("password");
            
            System.out.println("Login de aluno: " + email);
            Student student = studentService.authenticateStudent(email, password);
            
            StudentResponse response = new StudentResponse(
                student.getId(),
                student.getName(),
                student.getEmail(),
                student.getCpf(),
                student.getRg(),
                student.getAddress(),
                student.getInstitution().getId(),
                student.getInstitution().getName(),
                student.getCourse(),
                student.getCoinBalance(),
                student.getCreatedAt()
            );
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            System.out.println("Erro no login do aluno: " + e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<?> getStudentById(@PathVariable Long id) {
        try {
            Student student = studentService.getStudentById(id);
            StudentResponse response = new StudentResponse(
                student.getId(),
                student.getName(),
                student.getEmail(),
                student.getCpf(),
                student.getRg(),
                student.getAddress(),
                student.getInstitution().getId(),
                student.getInstitution().getName(),
                student.getCourse(),
                student.getCoinBalance(),
                student.getCreatedAt()
            );
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @GetMapping("/institution/{institutionId}")
    public ResponseEntity<List<StudentResponse>> getStudentsByInstitution(@PathVariable Long institutionId) {
        try {
            List<Student> students = studentService.getStudentsByInstitution(institutionId);
            List<StudentResponse> response = students.stream()
                .map(student -> new StudentResponse(
                    student.getId(),
                    student.getName(),
                    student.getEmail(),
                    student.getCpf(),
                    student.getRg(),
                    student.getAddress(),
                    student.getInstitution().getId(),
                    student.getInstitution().getName(),
                    student.getCourse(),
                    student.getCoinBalance(),
                    student.getCreatedAt()
                ))
                .toList();
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<?> updateStudent(@PathVariable Long id, @Valid @RequestBody StudentRequest request) {
        try {
            Student student = studentService.updateStudent(id, request);
            StudentResponse response = new StudentResponse(
                student.getId(),
                student.getName(),
                student.getEmail(),
                student.getCpf(),
                student.getRg(),
                student.getAddress(),
                student.getInstitution().getId(),
                student.getInstitution().getName(),
                student.getCourse(),
                student.getCoinBalance(),
                student.getCreatedAt()
            );
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteStudent(@PathVariable Long id) {
        try {
            studentService.deleteStudent(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}

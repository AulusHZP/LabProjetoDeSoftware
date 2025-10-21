package com.moedaestudantil.dto;

public class StudentUpdateRequest {
    
    private String name;
    private String email;
    private String address;
    private String course;
    private String password;
    
    // Constructors
    public StudentUpdateRequest() {}
    
    public StudentUpdateRequest(String name, String email, String address, String course, String password) {
        this.name = name;
        this.email = email;
        this.address = address;
        this.course = course;
        this.password = password;
    }
    
    // Getters and Setters
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getAddress() {
        return address;
    }
    
    public void setAddress(String address) {
        this.address = address;
    }
    
    public String getCourse() {
        return course;
    }
    
    public void setCourse(String course) {
        this.course = course;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
}

package com.nchang.studentlogin.service;

import com.nchang.studentlogin.dto.StudentDto;
import com.nchang.studentlogin.model.Student;

import java.util.List;

public interface StudentService {
    void saveStudent(StudentDto studentDto);
    Student findStudentByEmail(String email);
    List<StudentDto> findAllStudents();
}


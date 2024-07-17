package com.nchang.studentlogin.impl;

import java.util.List;
import java.util.stream.Collectors;

import com.nchang.studentlogin.dto.StudentDto;
import com.nchang.studentlogin.model.Role;
import com.nchang.studentlogin.model.Student;
import com.nchang.studentlogin.repository.RoleRepository;
import com.nchang.studentlogin.repository.StudentRepository;
import com.nchang.studentlogin.service.StudentService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public StudentServiceImpl(StudentRepository studentRepository, RoleRepository roleRepository,
                              PasswordEncoder passwordEncoder) {
        super();
        this.studentRepository = studentRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void saveStudent(StudentDto studentDto) {
        Student student = new Student();

        student.setName(studentDto.getFirstName() + " " +    studentDto.getLastName());
        student.setEmail(studentDto.getEmail());

        // Encrypt the password using Spring Security
        student.setPassword(passwordEncoder.encode(studentDto.getPassword()));
        Role role = roleRepository.findByName("ROLE_ADMIN");
        if (role == null) {
            role = checkRoleExist();
        }
        student.setRoles(List.of(role));
        studentRepository.save(student);
    }
    private Role checkRoleExist() {
        Role role = new Role();
        role.setName("ROLE_ADMIN");
        return roleRepository.save(role);
    }

    @Override
    public Student findStudentByEmail(String email) {

        return studentRepository.findByEmail(email);
    }
    @Override
    public List<StudentDto> findAllStudents() {
        List<Student> students = studentRepository.findAll();

        return students.stream()
                .map(this::mapToStudentDto)
                .collect(Collectors.toList());
    }
    private StudentDto mapToStudentDto(Student student) {
        StudentDto studentDto = new StudentDto();

        String[] str = student.getName().split(" ");
        studentDto.setFirstName(str[0]);
        studentDto.setLastName(str[1]);
        studentDto.setEmail(student.getEmail());
        return studentDto;
    }
}


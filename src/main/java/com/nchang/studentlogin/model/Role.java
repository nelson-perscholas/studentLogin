package com.nchang.studentlogin.model;


import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"students"})
@Entity
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long id;

    @Column(nullable = false, unique = true)
    public String name;

    @ManyToMany(mappedBy = "roles")
    public List<Student> students = new ArrayList<>();

}

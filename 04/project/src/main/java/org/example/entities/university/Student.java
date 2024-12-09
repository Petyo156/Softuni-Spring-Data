package org.example.entities.university;

import org.dom4j.tree.AbstractEntity;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "students")
public class Student extends Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "average_grade")
    private Double averageGrade;

    @Basic
    private int attendance;

    @ManyToMany(mappedBy = "student")
    private Set<Course> courses;

    public Student() {
    }
}

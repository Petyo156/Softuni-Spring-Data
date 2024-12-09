package org.example.entities.university;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "courses")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Basic
    private String name;

    @Basic
    private String description;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Basic
    private String credits;

    @ManyToMany
    @JoinTable(
            name = "mapping_table_name",
            joinColumns = @JoinColumn(name = "course_to_mapping"),
            inverseJoinColumns = @JoinColumn(name = "student_to_mapping")
    )
    private Set<Student> student;

    @ManyToOne
    private Teacher teacher;

    public Course() {
    }
}

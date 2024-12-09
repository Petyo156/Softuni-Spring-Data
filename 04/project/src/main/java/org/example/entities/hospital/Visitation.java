package org.example.entities.hospital;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "visitations")
public class Visitation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Basic
    private LocalDate date;

    @Basic
    private String comments;

    @ManyToOne
    private Patient patient;

    public Visitation() {}
}

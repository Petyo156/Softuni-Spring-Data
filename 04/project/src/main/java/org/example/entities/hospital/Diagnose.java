package org.example.entities.hospital;

import javax.persistence.*;

@Entity
@Table(name = "diagnoses")
public class Diagnose {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Basic
    private String name;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String comment;

    public Diagnose() {}
}

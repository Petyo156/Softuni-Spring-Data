package org.example.entities.hospital;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "patients")
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Basic
    private String address;

    @Basic
    private String email;

    @Column(name = "date_of_birth")
    private LocalDate birthDate;

    @Basic
    private String picture;

    @Column(name = "has_medical_insurance")
    private boolean hasMedicalInsurance;

    @OneToMany(mappedBy = "patient")
    private List<Visitation> visitations;

    @OneToMany
    private List<Diagnose> diagnoses;

    @OneToMany
    private List<Medicament> medicaments;

    public Patient() {
        this.visitations = new ArrayList<>();
        this.diagnoses = new ArrayList<>();
        this.medicaments = new ArrayList<>();
    }
}

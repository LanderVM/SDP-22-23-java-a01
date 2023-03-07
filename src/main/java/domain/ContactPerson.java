package domain;

import jakarta.persistence.*;

@Entity
public class ContactPerson {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "contact_person_id")
    private int contactPersonId;

    private String email;
    private int phoneNumber;
}

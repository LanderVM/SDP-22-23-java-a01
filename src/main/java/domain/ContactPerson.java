package domain;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "contact_person")
public class ContactPerson {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "contact_person_id")
    private int contactPersonId;
    @Column(name = "email")
    private String email = "";
    @Column(name = "phone_number")
    private String phoneNumber = "";

    public ContactPerson(String email, String phoneNumber) {
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    protected ContactPerson() {
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ContactPerson that = (ContactPerson) o;
        return contactPersonId == that.contactPersonId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(contactPersonId);
    }

    @Override
    public String toString() {
        return "ContactPerson{" + "contactPersonId=" + contactPersonId + ", email='" + email + '\'' + ", phoneNumber=" + phoneNumber + '}';
    }
}
package domain;

import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;

@Entity
public class ContactPerson {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "contact_person_id")
    private int contactPersonId;

    private String email;
    private String phoneNumber;

    public ContactPerson(String email, String phoneNumber) {
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    protected ContactPerson() {
    }

    public int getContactPersonId() {
        return contactPersonId;
    }

    public String getEmail() {
        return email;
    }

    void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
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
        return "ContactPerson{" +
                "contactPersonId=" + contactPersonId +
                ", email='" + email + '\'' +
                ", phoneNumber=" + phoneNumber +
                '}';
    }
}
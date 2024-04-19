package org.example.sqbackend.models;


import jakarta.persistence.*;

import java.util.Date;
import java.util.Objects;

@Entity
public class Spectator {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idSpectator;

    private String firstname;
    private String lastname;
    private Date dateOfBirth;

    @Column(nullable = false, unique = true)
    private String email;

    private String address;

    public int getIdSpectator() {
        return idSpectator;
    }

    public void setIdSpectator(int idSpectator) {
        this.idSpectator = idSpectator;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Spectator spectator = (Spectator) o;
        return idSpectator == spectator.idSpectator &&
                Objects.equals(firstname, spectator.firstname) &&
                Objects.equals(lastname, spectator.lastname) &&
                Objects.equals(dateOfBirth, spectator.dateOfBirth) &&
                Objects.equals(email, spectator.email) &&
                Objects.equals(address, spectator.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idSpectator, firstname, lastname, dateOfBirth, email, address);
    }
}


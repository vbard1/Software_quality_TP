package org.example.sqbackend.models;


import jakarta.persistence.*;

import java.util.Date;

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
}


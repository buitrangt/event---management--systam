package org.aibles.eventmanagementsystem.entity;



import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String name;
    private String email;
    private String phone;
    private String dateOfBirth;
    private String gender;
    private String address;
    private String createdBy;
    private String createdAt;
    private String lastUpdatedBy;
    private String lastUpdatedAt;

    public User() {
    }

    public User(String id, String name, String email, String phone, String dateOfBirth, String gender, String address,
                String createdBy, String createdAt, String lastUpdatedBy, String lastUpdatedAt) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.address = address;
        this.createdBy = createdBy;
        this.createdAt = createdAt;
        this.lastUpdatedBy = lastUpdatedBy;
        this.lastUpdatedAt = lastUpdatedAt;
    }
}

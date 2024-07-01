package org.aibles.eventmanagementsystem.entity;


import jakarta.persistence.*;
import lombok.Data;



@Entity
@Data
@Table(name = "account_user")
public class AccountUser {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String accountId;
    private String userId;


}


package org.aibles.eventmanagementsystem.entity;


import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Entity
@Data
@Table(name = "account_role")
public class AccountRole {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String accountId;
    private String roleId;


}


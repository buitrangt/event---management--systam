package org.aibles.eventmanagementsystem.repository;


import io.lettuce.core.dynamic.annotation.Param;
import org.aibles.eventmanagementsystem.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;


public interface UserRepository extends JpaRepository <User, Long>{


    @Query("SELECT u FROM User u JOIN AccountUser au ON u.id = au.userId JOIN Account a ON au.accountId = a.id WHERE u.email = :email")
    User findUserWithAccounts(String email);
    boolean existsByEmail(String email);
    Optional<User> findByEmail(String email);

    void deleteById(String id);

    Optional<User> findById(String id);
    @Query("SELECT u FROM User u, AccountUser ua WHERE u.id = ua.userId AND ua.accountId = :accountId")
    User findUserByAccountId(@Param("accountId") String accountId);

}

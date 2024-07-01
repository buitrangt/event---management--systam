package org.aibles.eventmanagementsystem.repository;

import io.lettuce.core.dynamic.annotation.Param;
import org.aibles.eventmanagementsystem.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, String> {

    @Modifying
    @Transactional
    @Query("UPDATE Account a SET a.isActivated = :isActivated WHERE a.id = :id")
    void updateActivationStatus(@Param("id") String id, @Param("isActivated") boolean isActivated);

    @Query("SELECT a FROM Account a WHERE a.isActivated = :isActivated")
    List<Account> findByActivationStatus(@Param("isActivated") boolean isActivated);

    @Query("SELECT u.email FROM Account a JOIN AccountUser au ON a.id = au.accountId JOIN User u ON au.userId = u.id WHERE a.username = :username")
    Optional<String> findEmailByUsername(String username);

    @Query("SELECT a FROM Account a WHERE a.id = :id")
    Optional<Account> findById(@Param("id") String id);

    boolean existsByUsername(String username);

    Optional<Account> findByUsername(String username);

    @Query("SELECT a FROM Account a JOIN AccountUser au ON a.id = au.accountId WHERE au.userId = :userId")
    Optional<Account> findByUserId(@Param("userId") String userId);

    @Query("SELECT a FROM Account a, AccountUser ua WHERE a.id = ua.accountId AND ua.userId = :userId")
    Optional<Account> findAccountsByUserId(@Param("userId") String userId);

    @Query("SELECT a.password FROM Account a " +
            "JOIN AccountUser au ON a.id = au.accountId " +
            "JOIN User u ON au.userId = u.id " +
            "WHERE u.email = :email")
    String findPasswordByEmail(@Param("email") String email);

    @Modifying
    @Transactional
    @Query("UPDATE Account a SET a.password = :password " +
            "WHERE a.id = (SELECT au.accountId FROM AccountUser au " +
            "JOIN User u ON au.userId = u.id " +
            "WHERE u.email = :email)")
    void updatePasswordByEmail(@Param("email") String email, @Param("password") String password);
}

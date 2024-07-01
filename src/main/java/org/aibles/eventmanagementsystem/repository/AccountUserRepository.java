package org.aibles.eventmanagementsystem.repository;

import io.lettuce.core.dynamic.annotation.Param;
import org.aibles.eventmanagementsystem.entity.Account;
import org.aibles.eventmanagementsystem.entity.AccountUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountUserRepository extends JpaRepository<AccountUser, String> {
    @Query("SELECT a FROM Account a JOIN AccountUser au ON a.id = au.accountId WHERE au.userId = :userId")
    List<Account> findAccountsByUserId(@Param("userId") String userId);

}

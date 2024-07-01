package org.aibles.eventmanagementsystem.repository;

import io.lettuce.core.dynamic.annotation.Param;
import org.aibles.eventmanagementsystem.entity.Account;
import org.aibles.eventmanagementsystem.entity.AccountRole;
import org.aibles.eventmanagementsystem.entity.AccountUser;
import org.aibles.eventmanagementsystem.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRoleRepository extends JpaRepository<AccountRole, String> {

    @Query("SELECT a FROM Account a JOIN AccountRole ar ON a.id = ar.accountId WHERE ar.roleId = :roleId")
    List<Account> findAccountsByRoleId(@Param("roleId") String roleId);

    @Query("SELECT r FROM Role r JOIN AccountRole ar ON r.id = ar.roleId WHERE ar.accountId = :accountId")
    List<Role> findRolesByAccountId(@Param("accountId") String accountId);

    @Query("SELECT r.name FROM Role r JOIN AccountRole ar ON r.id = ar.roleId WHERE ar.accountId = :accountId")
    List<String> findRoleNamesByAccountId(@Param("accountId") String accountId);

    @Query("SELECT r.name FROM Role r JOIN AccountRole ar ON r.id = ar.roleId JOIN Account a ON ar.accountId = a.id WHERE a.username = :username")
    List<String> findRoleNamesByUsername( String username);


}

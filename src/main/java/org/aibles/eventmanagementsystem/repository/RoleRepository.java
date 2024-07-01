package org.aibles.eventmanagementsystem.repository;

import io.lettuce.core.dynamic.annotation.Param;
import org.aibles.eventmanagementsystem.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, String> {


}

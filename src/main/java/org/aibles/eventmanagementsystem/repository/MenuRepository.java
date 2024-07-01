package org.aibles.eventmanagementsystem.repository;


import org.aibles.eventmanagementsystem.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MenuRepository extends JpaRepository<Menu, String> {
    List<Menu> findByNameContainingOrDescriptionContaining(String nameKeyword, String descriptionKeyword);
}
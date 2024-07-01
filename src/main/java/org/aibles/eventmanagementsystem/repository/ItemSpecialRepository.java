package org.aibles.eventmanagementsystem.repository;

import org.aibles.eventmanagementsystem.entity.ItemSpecial;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemSpecialRepository extends JpaRepository<ItemSpecial, String> {
    List<ItemSpecial> findByItemIdContaining(String itemIdKeyword);
}

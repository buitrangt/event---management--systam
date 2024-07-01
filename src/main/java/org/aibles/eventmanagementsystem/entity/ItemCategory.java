package org.aibles.eventmanagementsystem.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "item_category")
@IdClass(ItemCategoryId.class)
@Data
public class ItemCategory {

    @Id
    private String itemId;
    @Id
    private String categoryId;
}

package org.aibles.eventmanagementsystem.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "item_ingredient")
@IdClass(ItemIngredientId.class)
@Data
public class ItemIngredient {

    @Id
    private String itemId;

    @Id
    private String ingredientId;

    private Long quantity;

    private Long createdAt;

    private String createdBy;

    private Long lastUpdatedAt;

    private String lastUpdatedBy;
}
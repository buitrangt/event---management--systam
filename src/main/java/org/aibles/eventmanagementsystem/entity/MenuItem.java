package org.aibles.eventmanagementsystem.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "menu_item")
@IdClass(MenuItemId.class)
@Data
public class MenuItem {

    @Id
    private String menuId;
    @Id
    private String itemId;
}

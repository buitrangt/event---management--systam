package org.aibles.eventmanagementsystem.entity;

import java.io.Serializable;
import java.util.Objects;

public class MenuItemId implements Serializable {

    private String menuId;

    private String itemId;

    public MenuItemId() {}

    public MenuItemId(String menuId, String itemId) {
        this.menuId = menuId;
        this.itemId = itemId;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MenuItemId that = (MenuItemId) o;
        return Objects.equals(menuId, that.menuId) &&
                Objects.equals(itemId, that.itemId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(menuId, itemId);
    }
}
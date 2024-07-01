package org.aibles.eventmanagementsystem.entity;

import java.io.Serializable;
import java.util.Objects;

public class ItemCategoryId implements Serializable {

    private String itemId;

    private String categoryId;

    public ItemCategoryId() {}

    public ItemCategoryId(String itemId, String categoryId) {
        this.itemId = itemId;
        this.categoryId = categoryId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemCategoryId that = (ItemCategoryId) o;
        return Objects.equals(itemId, that.itemId) &&
                Objects.equals(categoryId, that.categoryId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(itemId, categoryId);
    }

}

package org.aibles.eventmanagementsystem.entity;

import java.io.Serializable;
import java.util.Objects;

public class ItemIngredientId implements Serializable {

    private String itemId;

    private String ingredientId;

    public ItemIngredientId() {}

    public ItemIngredientId(String itemId, String ingredientId) {
        this.itemId = itemId;
        this.ingredientId = ingredientId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemIngredientId that = (ItemIngredientId) o;
        return Objects.equals(itemId, that.itemId) &&
                Objects.equals(ingredientId, that.ingredientId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(itemId, ingredientId);
    }
}

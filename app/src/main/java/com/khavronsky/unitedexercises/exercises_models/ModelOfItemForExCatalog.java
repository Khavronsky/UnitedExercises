package com.khavronsky.unitedexercises.exercises_models;


public class ModelOfItemForExCatalog {
    public enum ItemType {
        CAPITAL_LETTER,
        EXERCISE_TITLE
    }

    private String title;
    private ItemType type;

    public ModelOfItemForExCatalog(final String title, final ItemType type) {
        this.title = title;
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public ModelOfItemForExCatalog setTitle(final String title) {
        this.title = title;
        return this;
    }

    public ItemType getType() {
        return type;
    }

    public ModelOfItemForExCatalog setType(final ItemType type) {
        this.type = type;
        return this;
    }
}

package com.khavronsky.unitedexercises.presentation.exercise.exercises_catalogs.default_ex_catalog;


import com.khavronsky.unitedexercises.presentation.exercise.exercises_models.ExerciseModel;

public class ModelOfItemForExCatalog {

    public enum ItemType {
        CAPITAL_LETTER,
        EXERCISE_TITLE
    }

    private ExerciseModel mExercise;

    private String title;

    private ItemType mType;

    public ModelOfItemForExCatalog(final ItemType type) {
        mType = type;

    }

    public ExerciseModel getExercise() {
        return mExercise == null ? null : mExercise;
    }

    public ModelOfItemForExCatalog setExercise(final ExerciseModel exercise) {
        mExercise = exercise;
        title = exercise.getTitle();
        return this;
    }

    public String getTitle() {
        return title;
    }

    public ModelOfItemForExCatalog setTitle(final String title) {
        this.title = title;
        return this;
    }

    public ItemType getType() {
        return mType;
    }

    public ModelOfItemForExCatalog setType(final ItemType type) {
        this.mType = type;
        return this;
    }
}

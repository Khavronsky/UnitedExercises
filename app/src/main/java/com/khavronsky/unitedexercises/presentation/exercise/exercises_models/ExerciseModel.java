package com.khavronsky.unitedexercises.presentation.exercise.exercises_models;


import android.support.annotation.NonNull;

import java.io.Serializable;

public abstract class ExerciseModel implements Comparable<ExerciseModel>, Serializable, IModel {

    //region FIELDS

    private long id;

    private String title;

    private String description;

    private ExerciseType type;

    private boolean customExercise = true;

    private boolean active = true;

    //endregion

    public enum ExerciseType implements Serializable {
        CARDIO(),
        POWER()
    }

    //region id set/get

    @Override
    public long getId() {
        return id;
    }

    public void setId(final long id) {
        this.id = id;
    }

    //endregion

    //region title set/get
    public ExerciseModel setTitle(final String title) {
        this.title = title;
        return this;
    }

    public String getTitle() {
        return title;
    }
    //endregion

    //region description set/get
    public String getDescription() {
        return description;
    }

    public ExerciseModel setDescription(final String description) {
        this.description = description;
        return this;
    }
    //endregion

    //region type set/get
    void setType(ExerciseType type) {
        this.type = type;
    }

    public ExerciseType getType() {
        return type;
    }
    //endregion

    //region customExercise set/get
    public ExerciseModel setCustomExercise(final boolean customExercise) {
        this.customExercise = customExercise;
        return this;
    }

    public boolean isCustomExercise() {
        return customExercise;
    }
    //endregion

    //region active set/get
    public ExerciseModel setActive(final boolean active) {
        this.active = active;
        return this;
    }

    public boolean isActive() {
        return active;
    }
    //endregion

    public int compareTo(@NonNull ExerciseModel model) {
        return this.getTitle().compareTo(model.getTitle());
    }
}

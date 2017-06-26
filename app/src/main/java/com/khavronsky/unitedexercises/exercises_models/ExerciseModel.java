package com.khavronsky.unitedexercises.exercises_models;


import java.io.Serializable;

public abstract class ExerciseModel implements Serializable {

    //region FIELDS
    private static final String CARDIO_TAG = "Cardio";

    private static final String POWER_TAG = "Power";

    private String title;

    private ExerciseType type;

    private boolean customExercise;

    private boolean active = true;

    //endregion

    public enum ExerciseType {
        CARDIO(CARDIO_TAG),
        POWER(POWER_TAG);

        private final String typeTag;

        ExerciseType(final String typeTag) {
            this.typeTag = typeTag;
        }

        public String getTag() {
            return typeTag;
        }
    }

    //region title set/get
    public ExerciseModel setTitle(final String title) {
        this.title = title;
        return this;
    }

    public String getTitle() {
        return title;
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

}

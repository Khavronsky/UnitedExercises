package com.khavronsky.unitedexercises.exercises_models;


import android.os.Parcel;
import android.os.Parcelable;

public abstract class ExerciseModel implements Parcelable {

    private static final String CARDIO_TAG = "Cardio";

    private static final String POWER_TAG = "Power";

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

    private String title;

    private ExerciseType type;

    private boolean customExercise;

    protected ExerciseModel() {
    }

    public ExerciseModel setTitle(final String title) {
        this.title = title;
        return this;
    }

    public String getTitle() {
        return title;
    }

    void setType(ExerciseType type) {
        this.type = type;
    }

    public ExerciseType getType() {
        return type;
    }

    public ExerciseModel setCustomExercise(final boolean customExercise) {
        this.customExercise = customExercise;
        return this;
    }

    public boolean isCustomExercise() {
        return customExercise;
    }

    public ExerciseModel(final Parcel in) {
        title = in.readString();
        customExercise = in.readByte() != 0;
    }
}

package com.khavronsky.unitedexercises.exercises_models;


import java.io.Serializable;

public class ModelOfExercisePerformance implements Serializable {

    //region FIELDS
    private ExerciseModel mExercise;

    private long mLastChangedTime;

    private long mStartTime;

    private int mDuration;

    private String mNote;

    private float currentKcalPerHour;
    //endregion

    public float getCurrentKcalPerHour() {
        return currentKcalPerHour;
    }

    public ModelOfExercisePerformance(final ExerciseModel exercise) {
        mExercise = exercise;
    }

    public ModelOfExercisePerformance setCurrentKcalPerHour(final float currentKcalPerHour) {
        this.currentKcalPerHour = currentKcalPerHour;
        return this;
    }

    public ExerciseModel getExercise() {
        return mExercise;
    }

    public Long getStartTime() {
        return mStartTime;
    }

    public ModelOfExercisePerformance setStartTime(final Long startTime) {
        mStartTime = startTime;
        return this;
    }

    public int getDuration() {
        return mDuration;
    }

    public ModelOfExercisePerformance setDuration(final int duration) {
        mDuration = duration;
        return this;
    }

    public String getNote() {
        return mNote;
    }

    public ModelOfExercisePerformance setNote(final String note) {
        mNote = note;
        return this;
    }

    public long getLastChangedTime() {
        return mLastChangedTime;
    }

    public ModelOfExercisePerformance setLastChangedTime(final long lastChangedTime) {
        mLastChangedTime = lastChangedTime;
        return this;
    }
}

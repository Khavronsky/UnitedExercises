package com.khavronsky.unitedexercises.presentation.exercise.exercises_models;


import android.support.annotation.NonNull;

import java.io.Serializable;

public class ModelOfExercisePerformance implements Serializable, IModel, Comparable<ModelOfExercisePerformance> {

    //region FIELDS
    private long id;

    private ExerciseModel mExercise;

    private long mLastChangedTime;

    private long mStartTime;

    private int mDuration;

    private String mNote;

    private float currentKcalPerHour;
    //endregion

    //region id set/get

    @Override
    public long getId() {
        return id;
    }

    public void setId(final long id) {
        this.id = id;
    }

    //endregion

    public ModelOfExercisePerformance(final ExerciseModel exercise) {
        mExercise = exercise;
    }

    public float getCurrentKcalPerHour() {
        return currentKcalPerHour;
    }

    public ModelOfExercisePerformance setCurrentKcalPerHour(final float currentKcalPerHour) {
        this.currentKcalPerHour = currentKcalPerHour;
        return this;
    }

    public ModelOfExercisePerformance setExercise(
            final ExerciseModel exercise) {
        mExercise = exercise;
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

    @Override
    public int compareTo(@NonNull final ModelOfExercisePerformance o) {
        return (int) (-1*(this.getLastChangedTime() - o.getLastChangedTime()));
    }
}

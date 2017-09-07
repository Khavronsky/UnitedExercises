package com.khavronsky.unitedexercises.presentation.exercise.exercises_models;


import android.support.annotation.NonNull;

import java.io.Serializable;
import java.util.ArrayList;

public class ModelOfExercisePerformance implements Serializable, IModel, Comparable<ModelOfExercisePerformance> {

    //region FIELDS
    private long id;

    private ExerciseModel mExercise;

    private long mLastChangedTime;

    private long mStartTime;

    private int mDuration;

    private String mNote;

    private float currentKcalPerHour;

    @CardioExerciseModel.IntensityTypeIsSpecify
    private int currentIntensityType;
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

    public ModelOfExercisePerformance setCurrentIntensity(int currentIntensityType) {
        if (mExercise.getType() == ExerciseModel.ExerciseType.CARDIO) {
            this.currentIntensityType = currentIntensityType;
            this.currentKcalPerHour = ((CardioExerciseModel) mExercise).getBurningCalByIntensityType(currentIntensityType);
        }
        return this;
    }

    public int getCurrentIntensityType() {
        return currentIntensityType;
    }

    public ModelOfExercisePerformance setCurrentIntensityType(final int currentIntensityType) {
        this.currentIntensityType = currentIntensityType;
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

    private ArrayList<Approach> mApproachList = new ArrayList<>();

    public ArrayList<Approach> getApproachList() {
        return mApproachList;
    }

    public ArrayList<Approach> addApproach(final int repeats, final int weight) {
        mApproachList.add(new Approach(repeats, weight));
        return mApproachList;
    }

    public Approach getApproach (int index){
        return mApproachList.get(index);
    }

    public ArrayList<Approach> delApproach(int index){
        mApproachList.remove(index);
        return mApproachList;
    }

    public class Approach implements Serializable{

        private int repeats;

        private int weight;

        Approach() {
        }

        Approach(final int repeats, final int weight) {
            this.repeats = repeats;
            this.weight = weight;
        }

        public int getRepeats() {
            return repeats;
        }

        public void setRepeats(final int repeats) {
            this.repeats = repeats;
        }

        public int getWeight() {
            return weight;
        }

        public void setWeight(final int weight) {
            this.weight = weight;
        }

    }
}

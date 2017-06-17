package com.khavronsky.unitedexercises.exercises_models;


import android.os.Parcel;
import android.os.Parcelable;

public class ModelOfExercisePerformance implements Parcelable{
    private ExerciseModel mExercise;
    private Long mStartTime;
    private int mDuration;
    private String mNote;
    private float currentKcalPerHour;

    public float getCurrentKcalPerHour() {
        return currentKcalPerHour;
    }

    public void setCurrentKcalPerHour(final float currentKcalPerHour) {
        this.currentKcalPerHour = currentKcalPerHour;
    }

    public ModelOfExercisePerformance(final ExerciseModel exercise) {
        mExercise = exercise;
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

    /**
     * P A R C E L A B L E   I M P L E M E N T A T I O N
     * */
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(final Parcel dest, final int flags) {
        dest.writeString(mNote);
    }

    protected ModelOfExercisePerformance(Parcel in) {
        mNote = in.readString();
    }

    public static final Creator<ModelOfExercisePerformance> CREATOR = new Creator<ModelOfExercisePerformance>() {
        @Override
        public ModelOfExercisePerformance createFromParcel(Parcel in) {
            return new ModelOfExercisePerformance(in);
        }

        @Override
        public ModelOfExercisePerformance[] newArray(int size) {
            return new ModelOfExercisePerformance[size];
        }
    };
}

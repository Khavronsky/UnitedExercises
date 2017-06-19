package com.khavronsky.unitedexercises.exercises_models;


public class CustomExModel {
    private String mExTitle;
    private String mExSubTitle;
    private int mId;
    private boolean mActive;

    public String getExTitle() {
        return mExTitle;
    }

    public CustomExModel setExTitle(final String exTitle) {
        mExTitle = exTitle;
        return this;
    }

    public String getExSubTitle() {
        return mExSubTitle;
    }

    public CustomExModel setExSubTitle(final String exSubTitle) {
        mExSubTitle = exSubTitle;
        return this;
    }

    public int getId() {
        return mId;
    }

    public CustomExModel setId(final int id) {
        mId = id;
        return this;
    }

    public boolean isActive() {
        return mActive;
    }

    public CustomExModel setActive(final boolean active) {
        mActive = active;
        return this;
    }
}

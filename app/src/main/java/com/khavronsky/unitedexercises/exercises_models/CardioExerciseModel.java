package com.khavronsky.unitedexercises.exercises_models;


import android.support.annotation.IntDef;

import java.io.Serializable;

public class CardioExerciseModel extends ExerciseModel implements Serializable {

    private final static ExerciseType type = ExerciseType.CARDIO;

    @IntDef(
            flag = true,
            value = {TYPE_NOT_SPECIFY, TYPE_SPECIFY})
    public @interface IntensityType{}

    public static final int TYPE_NOT_SPECIFY = 0;
    public static final int TYPE_SPECIFY = 1;

    @IntDef(
            flag = true,
            value = {METHOD_MET_VALUES, METHOD_CAL_PER_HOUR})
    public @interface CountingCaloriesMethod{}

    public static final int METHOD_MET_VALUES = 0;
    public static final int METHOD_CAL_PER_HOUR = 1;

    @CountingCaloriesMethod
    private int mCountCalMethod;

    @IntensityType
    private int mIntensityType;

    private float high;

    private float middle;

    private float low;

    private float defValue;

    @IntensityType
    public int getIntensityType() {
        return mIntensityType;
    }

    public CardioExerciseModel setIntensityType(@IntensityType final int intensityType) {
        mIntensityType = intensityType;
        return this;
    }

    public CardioExerciseModel() {
        super.setType(type);
    }

    @CountingCaloriesMethod
    public int getCountCalMethod() {
        return mCountCalMethod;
    }

    public CardioExerciseModel setCountCalMethod(@CountingCaloriesMethod final int countCalMethod) {
        this.mCountCalMethod = countCalMethod;
        return this;
    }

    public float getHigh() {
        return high;
    }

    public CardioExerciseModel setHigh(final float high) {
        this.high = high;
        return this;
    }

    public float getMiddle() {
        return middle;
    }

    public CardioExerciseModel setMiddle(final float middle) {
        this.middle = middle;
        return this;
    }

    public float getLow() {
        return low;
    }

    public CardioExerciseModel setLow(final float low) {
        this.low = low;
        return this;
    }

    public float getDefValue() {
        return defValue;
    }

    public CardioExerciseModel setDefValue(final float defValue) {
        this.defValue = defValue;
        return this;
    }
}

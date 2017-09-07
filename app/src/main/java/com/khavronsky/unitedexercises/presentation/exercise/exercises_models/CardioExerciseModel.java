package com.khavronsky.unitedexercises.presentation.exercise.exercises_models;


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
            value = {TYPE_SPECIFY_LOW, TYPE_SPECIFY_MIDDLE, TYPE_SPECIFY_HIGH})
    public @interface IntensityTypeIsSpecify{}

    public static final int TYPE_SPECIFY_LOW = 0;
    public static final int TYPE_SPECIFY_MIDDLE = 1;
    public static final int TYPE_SPECIFY_HIGH = 2;

    @IntDef(
            flag = true,
            value = {METHOD_MET_VALUES, METHOD_CAL_PER_HOUR})
    public @interface CountingCaloriesMethod{}

    public static final int METHOD_MET_VALUES = 0;
    public static final int METHOD_CAL_PER_HOUR = 1;

    @IntensityType
    private int mIntensityType;

    @IntensityTypeIsSpecify
    private int mIntensityTypeIsSpecify;

    @CountingCaloriesMethod
    private int mCountCalMethod;

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

    @IntensityTypeIsSpecify
    public int getIntensityTypeIsSpecify() {
        return mIntensityTypeIsSpecify;
    }

    public CardioExerciseModel setIntensityTypeIsSpecify(@IntensityTypeIsSpecify final int intensityTypeIsSpecify) {
        mIntensityTypeIsSpecify = intensityTypeIsSpecify;
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

    public float getBurningCalByIntensityType(@IntensityTypeIsSpecify int intensityTypeIsSpecify){
        switch (intensityTypeIsSpecify){
            case TYPE_SPECIFY_LOW:
                return low;
            case TYPE_SPECIFY_MIDDLE:
                return middle;
            case TYPE_SPECIFY_HIGH:
                return high;
        }
        return -1;
    }
}

package com.khavronsky.unitedexercises.exercises_models;


import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.IntDef;

public class CardioExerciseModel extends ExerciseModel implements Parcelable {

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

    /**
     * P A R C E L A B L E   I M P L E M E N T A T I O N
     * */



    public static final Creator<CardioExerciseModel> CREATOR = new Creator<CardioExerciseModel>() {
        @Override
        public CardioExerciseModel createFromParcel(final Parcel source) {
            return new CardioExerciseModel();
        }

        @Override
        public CardioExerciseModel[] newArray(final int size) {
            return new CardioExerciseModel[size];
        }
    };

    protected CardioExerciseModel(final Parcel in) {
        super(in);
        this.high = in.readInt();
        this.middle = in.readInt();
        this.low = in.readInt();
        this.defValue = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(final Parcel dest, final int flags) {
        dest.writeFloat(high);
        dest.writeFloat(middle);
        dest.writeFloat(low);
        dest.writeFloat(defValue);
    }
}

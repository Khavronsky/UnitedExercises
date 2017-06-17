package com.khavronsky.unitedexercises.exercises_models;


import android.os.Parcel;
import android.os.Parcelable;


public class PowerExerciseModel extends ExerciseModel implements Parcelable {

    private final static ExerciseType type = ExerciseType.POWER;

    private int sets;
    private int repeats;
    private int weight;

    public PowerExerciseModel() {
        super.setType(type);
    }

    public int getSets() {
        return sets;
    }

    public PowerExerciseModel setSets(final int sets) {
        this.sets = sets;
        return this;
    }

    public int getRepeats() {
        return repeats;
    }

    public PowerExerciseModel setRepeats(final int repeats) {
        this.repeats = repeats;
        return this;
    }

    public int getWeight() {
        return weight;
    }

    public PowerExerciseModel setWeight(final int weight) {
        this.weight = weight;
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
        dest.writeInt(sets);
        dest.writeInt(repeats);
        dest.writeInt(weight);
    }

    protected PowerExerciseModel(Parcel in) {
        super(in);
        sets = in.readInt();
        repeats = in.readInt();
        weight = in.readInt();
    }

    public static final Creator<PowerExerciseModel> CREATOR = new Creator<PowerExerciseModel>() {
        @Override
        public PowerExerciseModel createFromParcel(Parcel in) {
            return new PowerExerciseModel(in);
        }

        @Override
        public PowerExerciseModel[] newArray(int size) {
            return new PowerExerciseModel[size];
        }
    };
}

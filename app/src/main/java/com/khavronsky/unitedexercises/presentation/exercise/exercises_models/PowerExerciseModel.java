package com.khavronsky.unitedexercises.presentation.exercise.exercises_models;


import java.io.Serializable;
import java.util.ArrayList;


public class PowerExerciseModel extends ExerciseModel implements Serializable {

    private final static ExerciseType type = ExerciseType.POWER;

    private ArrayList<Approach> mApproachList = new ArrayList<>();

    public PowerExerciseModel() {
        super.setType(type);
    }

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

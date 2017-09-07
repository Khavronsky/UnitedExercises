package com.khavronsky.unitedexercises.presentation.exercise.exercises_models;


import java.io.Serializable;


public class PowerExerciseModel extends ExerciseModel implements Serializable {

    private final static ExerciseType type = ExerciseType.POWER;

    public PowerExerciseModel() {
        super.setType(type);
    }

}

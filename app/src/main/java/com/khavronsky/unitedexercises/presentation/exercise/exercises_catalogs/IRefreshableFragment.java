package com.khavronsky.unitedexercises.presentation.exercise.exercises_catalogs;


import com.khavronsky.unitedexercises.presentation.exercise.exercises_models.ExerciseModel;

public interface IRefreshableFragment {

    void refresh(ExerciseModel.ExerciseType type);
}

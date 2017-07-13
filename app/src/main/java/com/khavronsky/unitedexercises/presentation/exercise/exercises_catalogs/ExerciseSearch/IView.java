package com.khavronsky.unitedexercises.presentation.exercise.exercises_catalogs.ExerciseSearch;


import com.khavronsky.unitedexercises.presentation.exercise.exercises_models.ExerciseModel;

import java.util.ArrayList;

public interface IView {

    void setAllExercises(ArrayList<ExerciseModel> exerciseModels);

}

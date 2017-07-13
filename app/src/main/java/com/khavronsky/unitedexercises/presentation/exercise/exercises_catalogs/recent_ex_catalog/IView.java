package com.khavronsky.unitedexercises.presentation.exercise.exercises_catalogs.recent_ex_catalog;


import com.khavronsky.unitedexercises.presentation.exercise.exercises_models.ModelOfExercisePerformance;

import java.util.List;

public interface IView {

    void show(List<ModelOfExercisePerformance> exModelList);


}

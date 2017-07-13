package com.khavronsky.unitedexercises.presentation.exercise.exercises_catalogs.custom_ex_catalog;


import com.khavronsky.unitedexercises.presentation.exercise.exercises_models.ExerciseModel;

import java.util.List;

interface IView {

    void show(List<ExerciseModel> exModelList);
}

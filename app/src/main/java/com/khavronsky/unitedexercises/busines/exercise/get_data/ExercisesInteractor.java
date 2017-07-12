package com.khavronsky.unitedexercises.busines.exercise.get_data;


import com.khavronsky.unitedexercises.presentation.exercise.exercises_models.ExerciseModel;
import com.khavronsky.unitedexercises.presentation.exercise.exercises_models.ModelOfExercisePerformance;

import java.util.List;

import rx.Observable;

public interface ExercisesInteractor {

    Observable<List<ExerciseModel>> getCustomExerciseList();

    Observable<List<ExerciseModel>> getDefaultExerciseList();

    Observable<List<ExerciseModel>> getAllExercises();

    Observable<List<ModelOfExercisePerformance>> getRecentExerciseList();

    Observable<Boolean> addCustomExercise(ExerciseModel exerciseModel);

    Observable<Boolean> editCustomExercise(ExerciseModel exerciseModel);

    Observable<Boolean> editExercisePerformance(ModelOfExercisePerformance exercisePerformance);

    Observable<Boolean> addExercisePerformance(ModelOfExercisePerformance exerciseModel);

    Observable<Boolean> delCustomExercise(long id);

    Observable<Boolean> delExercisePerformance(long id);
}

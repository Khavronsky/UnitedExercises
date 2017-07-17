package com.khavronsky.unitedexercises.busines.exercise.get_data;


import com.khavronsky.unitedexercises.presentation.exercise.exercises_models.ExerciseModel;
import com.khavronsky.unitedexercises.presentation.exercise.exercises_models.IModel;
import com.khavronsky.unitedexercises.presentation.exercise.exercises_models.ModelOfExercisePerformance;

import android.support.annotation.Nullable;

import java.util.List;

import rx.Observable;

public interface ExercisesInteractor {

    /**
     * @return Список упражнений, созданных пользователем
     */
    Observable<List<ExerciseModel>> getCustomExerciseList();

    /**
     * @return Список предустановленных упражнений
     */
    Observable<List<ExerciseModel>> getDefaultExerciseList();

    /**
     * @return Объединяет пользовательский и предустановленный списки
     */
    Observable<List<ExerciseModel>> getAllExercises();

    /**
     * @return Список последних выполненных упражнений
     */
    Observable<List<ModelOfExercisePerformance>> getRecentExerciseList();

    /**
     * @return Находит упражнение по ID в объединенном списке
     */
    @Nullable
    Observable<IModel> findExerciseByID(long id);

    /**
     * @return добавление пользовательского упражнения в список
     */
    Observable<Boolean> addCustomExercise(ExerciseModel exerciseModel);

    /**
     * @return изменение добавленного ранее пользовательского упражнения
     */
    Observable<Boolean> editCustomExercise(ExerciseModel exerciseModel);

    /**
     * @return добавление выполненного упражнения
     */
    Observable<Boolean> addExercisePerformance(ModelOfExercisePerformance exerciseModel);

    /**
     * @return изменение добавленного ранее выполненного упражнения
     */
    Observable<Boolean> editExercisePerformance(ModelOfExercisePerformance exercisePerformance);

    /**
     * @return удаление пользовательского упражнения по ID
     */
    Observable<Boolean> delCustomExercise(long id);

    /**
     * @return удаление выполненного упражнения по ID
     */
    Observable<Boolean> delExercisePerformance(long id);
}

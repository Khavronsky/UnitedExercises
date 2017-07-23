package com.khavronsky.unitedexercises.utils.di;

import com.khavronsky.unitedexercises.busines.exercise.get_data.IExercisesInteractor;
import com.khavronsky.unitedexercises.presentation.exercise.exercise_performance.ExercisePerformancePresenter;

import dagger.Module;
import dagger.Provides;

@Module
public class ExPerformModule {

    @Provides
    ExercisePerformancePresenter provideExercisePerformancePresenter (IExercisesInteractor interactor){
        return new ExercisePerformancePresenter(interactor);
    }
}

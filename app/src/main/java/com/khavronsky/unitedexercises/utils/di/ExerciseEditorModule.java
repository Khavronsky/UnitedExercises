package com.khavronsky.unitedexercises.utils.di;

import com.khavronsky.unitedexercises.busines.exercise.get_data.IExercisesInteractor;
import com.khavronsky.unitedexercises.presentation.exercise.create_new_exercises.new_cardio_exercise.CardioExerciseEditorPresenter;
import com.khavronsky.unitedexercises.presentation.exercise.create_new_exercises.new_power_exercise.PowerExerciseEditorPresenter;

import dagger.Module;
import dagger.Provides;

@Module
public class ExerciseEditorModule {

    @Provides
    CardioExerciseEditorPresenter provideCardioExerciseEditorPresenter(IExercisesInteractor interactor) {
        return new CardioExerciseEditorPresenter(interactor);
    }

    @Provides
    PowerExerciseEditorPresenter providePowerExerciseEditorPresenter(IExercisesInteractor interactor) {
        return new PowerExerciseEditorPresenter(interactor);
    }

}

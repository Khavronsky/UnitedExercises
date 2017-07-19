package com.khavronsky.unitedexercises.utils.di;

import com.khavronsky.unitedexercises.busines.exercise.get_data.ExerciseRX;
import com.khavronsky.unitedexercises.busines.exercise.get_data.IExercisesInteractor;
import com.khavronsky.unitedexercises.presentation.exercise.exercises_catalogs.custom_ex_catalog.CustomExPresenter;

import dagger.Module;
import dagger.Provides;

@Module
public class ExerciseModule {

    @Provides
    CustomExPresenter provideCustomExPresenter (IExercisesInteractor interactor){
        return new CustomExPresenter(interactor);
    }

    @Provides
    IExercisesInteractor provideExercisesInteractor(){
        return new ExerciseRX();
    }

}

package com.khavronsky.unitedexercises.utils.di;

import com.khavronsky.unitedexercises.busines.exercise.get_data.ExerciseRX;
import com.khavronsky.unitedexercises.busines.exercise.get_data.IExercisesInteractor;

import dagger.Module;
import dagger.Provides;

@Module
public class UnitedExerciseModule {

    @Provides
    IExercisesInteractor provideExercisesInteractor(){
        return new ExerciseRX();
    }

}

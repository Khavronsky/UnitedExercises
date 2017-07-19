package com.khavronsky.unitedexercises.utils.di;

import com.khavronsky.unitedexercises.busines.exercise.get_data.IExercisesInteractor;
import com.khavronsky.unitedexercises.presentation.exercise.exercises_catalogs.custom_ex_catalog.CustomExPresenter;
import com.khavronsky.unitedexercises.presentation.exercise.exercises_catalogs.default_ex_catalog.DefaultExPresenter;
import com.khavronsky.unitedexercises.presentation.exercise.exercises_catalogs.recent_ex_catalog.RecentExPresenter;

import dagger.Module;
import dagger.Provides;

@Module
public class ExerciseCatalogModule {

    @Provides
    CustomExPresenter provideCustomExPresenter (IExercisesInteractor interactor){
        return new CustomExPresenter(interactor);
    }

    @Provides
    RecentExPresenter provideRecentExPresenter(IExercisesInteractor interactor){
        return new RecentExPresenter(interactor);
    }

    @Provides
    DefaultExPresenter provideDefaultExPresenter(IExercisesInteractor interactor){
        return new DefaultExPresenter(interactor);
    }
}

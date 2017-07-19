package com.khavronsky.unitedexercises.utils.di;

import com.khavronsky.unitedexercises.presentation.exercise.create_new_exercises.new_cardio_exercise.CardioExerciseEditorActivity;
import com.khavronsky.unitedexercises.presentation.exercise.create_new_exercises.new_power_exercise.PowerExerciseEditorActivity;
import com.khavronsky.unitedexercises.presentation.exercise.exercise_performance.ExercisePerformActivity;
import com.khavronsky.unitedexercises.presentation.exercise.exercises_catalogs.custom_ex_catalog.CustomExercisesFragment;
import com.khavronsky.unitedexercises.presentation.exercise.exercises_catalogs.default_ex_catalog.DefaultExerciseFragment;
import com.khavronsky.unitedexercises.presentation.exercise.exercises_catalogs.recent_ex_catalog.RecentExercisesFragment;

import dagger.Component;

@Component(modules = {ExerciseCatalogModule.class, ExercisePerformanceModule.class, UnitedExerciseModule.class,
        ExerciseEditorModule.class})
public interface AppComponent {

    void getCustomExPresenter(CustomExercisesFragment customExercisesFragment);

    void getDefaultExPresenter(DefaultExerciseFragment defaultExerciseFragment);

    void getRecentExPresenter(RecentExercisesFragment recentExercisesFragment);

    void getExercisePerformancePresenter(ExercisePerformActivity exercisePerformActivity);

    void getCardioExerciseEditorPresenter(CardioExerciseEditorActivity cardioExerciseEditorActivity);

    void getPowerExerciseEditorPresenter(PowerExerciseEditorActivity powerExerciseEditorActivity);




}

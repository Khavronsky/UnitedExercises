package com.khavronsky.unitedexercises.utils.di;

import dagger.Component;

@Component(modules = {ExCatModule.class, ExPerformModule.class, UnitedExerciseModule.class,
        ExEditorModule.class})
public interface AppComponent {

//    void getCustomExPresenter(CustomExercisesFragment customExercisesFragment);
//
//    void getDefaultExPresenter(DefaultExerciseFragment defaultExerciseFragment);
//
//    void getRecentExPresenter(RecentExercisesFragment recentExercisesFragment);
//
//    void getExercisePerformancePresenter(ExercisePerformActivity exercisePerformActivity);
//
//    void getCardioExerciseEditorPresenter(CardioExerciseEditorActivity cardioExerciseEditorActivity);
//
//    void getPowerExerciseEditorPresenter(PowerExerciseEditorActivity powerExerciseEditorActivity);

    ExComponent createExComponent();

}

package com.khavronsky.unitedexercises.get_data;


import com.khavronsky.unitedexercises.exercises_models.ExerciseModel;
import com.khavronsky.unitedexercises.exercises_models.ModelOfExercisePerformance;

import android.support.annotation.NonNull;

import java.util.List;

import rx.Observable;


public class ExerciseRX {

    @NonNull
    public static Observable<List<ExerciseModel>> getCustomExerciseList() {
        return Observable.just(0)
                .map(integer -> FakeData.getCustomCatalog())
                ;
    }

    @NonNull
    public static Observable<List<ExerciseModel>> getDefaultExerciseList() {
        return Observable.just(0)
                .map(integer -> FakeData.getDefaultCatalog())
                ;
    }

    @NonNull
    public static Observable<List<ModelOfExercisePerformance>> getRecentExerciseList() {
        return Observable.just(0)
                .map(integer -> FakeData.getExercisePerformances())
                ;
    }


}

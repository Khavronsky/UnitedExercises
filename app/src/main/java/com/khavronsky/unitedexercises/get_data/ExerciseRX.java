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

    @NonNull
    public static Observable<Boolean> addCustomExercise(ExerciseModel exerciseModel) {
        return Observable.just(exerciseModel)
                .map(model -> FakeData.addCustomExercise(exerciseModel));
    }

    @NonNull
    public static Observable<Boolean> addExercisePerformance(ModelOfExercisePerformance exerciseModel) {
        return Observable.just(exerciseModel)
                .map(model -> FakeData.addExPerfofmance(exerciseModel));
    }

    @NonNull
    public static Observable<Boolean> delCustomExercise (long id){
        return Observable.just(id)
                .map(FakeData::delCustomExercise);
    }


}

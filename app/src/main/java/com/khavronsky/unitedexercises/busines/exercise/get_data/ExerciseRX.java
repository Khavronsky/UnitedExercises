package com.khavronsky.unitedexercises.busines.exercise.get_data;


import com.khavronsky.unitedexercises.presentation.exercise.exercises_models.ExerciseModel;
import com.khavronsky.unitedexercises.presentation.exercise.exercises_models.IModel;
import com.khavronsky.unitedexercises.presentation.exercise.exercises_models.ModelOfExercisePerformance;

import android.support.annotation.NonNull;

import java.util.List;

import rx.Observable;


public class ExerciseRX implements ExercisesInteractor {

    private final static String TAG = "KhS_RX";

    @NonNull
    public  Observable<List<ExerciseModel>> getCustomExerciseList() {
        return Observable.just(0)
                .map(integer -> FakeData.getCustomCatalog())
                ;
    }

    @NonNull
    public  Observable<List<ExerciseModel>> getDefaultExerciseList() {
        return Observable.just(0)
                .map(integer -> FakeData.getDefaultCatalog())
                ;
    }
    @NonNull
    public  Observable<List<ExerciseModel>> getAllExercises() {
        return Observable.just(0)
                .map(integer -> FakeData.getAllExercises())
                ;
    }

    @NonNull
    public  Observable<IModel> findExerciseByID(long id) {
        return Observable.just(id)
                .map(integer -> FakeData.findExerciseByID(id))
                ;
    }

    @NonNull
    public  Observable<List<ModelOfExercisePerformance>> getRecentExerciseList() {
        return Observable.just(0)
                .map(integer -> FakeData.getExercisePerformances())
                ;
    }

    @NonNull
    public  Observable<Boolean> addCustomExercise(ExerciseModel exerciseModel) {
        return Observable.just(exerciseModel)
                .map(model -> FakeData.addCustomExercise(exerciseModel));
    }

    @NonNull
    public  Observable<Boolean> editCustomExercise(ExerciseModel exerciseModel) {
        return Observable.just(exerciseModel)
                .map(model -> FakeData.editCustomExercise(exerciseModel));
    }

    @NonNull
    public  Observable<Boolean> editExercisePerformance(ModelOfExercisePerformance exercisePerformance) {
        return Observable.just(exercisePerformance)
                .map(model -> FakeData.editExercisePerformance(exercisePerformance));
    }

    @NonNull
    public  Observable<Boolean> addExercisePerformance(ModelOfExercisePerformance exerciseModel) {
        return Observable.just(exerciseModel)
                .map(model -> FakeData.addExPerformance(exerciseModel));
    }

    @NonNull
    public  Observable<Boolean> delCustomExercise(long id) {
        return Observable.just(id)
                .map(FakeData::delCustomExercise);
    }

    @NonNull
    public  Observable<Boolean> delExercisePerformance(long id) {
        return Observable.just(id)
                .map(FakeData::delExercisePerformance);
    }
}

package com.khavronsky.unitedexercises.exercise_performance;


import com.khavronsky.unitedexercises.exercises_models.CardioExerciseModel;
import com.khavronsky.unitedexercises.exercises_models.ModelOfExercisePerformance;
import com.khavronsky.unitedexercises.exercises_models.PowerExerciseModel;
import com.khavronsky.unitedexercises.get_data.ExerciseRX;
import com.khavronsky.unitedexercises.import_from_grand_project.AbstractPresenter;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class PresenterOfExercisePerformance extends AbstractPresenter<PresenterOfExercisePerformance.IView> {

    private ModelOfExercisePerformance modelOfExercisePerformance;

    void loadData() {
        
        if (getView() != null) {
            ModelOfExercisePerformance model;
            if (modelOfExercisePerformance != null) {
                model = modelOfExercisePerformance;
            } else {
                model = createFakeData3();
            }
            getView().show(model);
        }
    }

    //region FAKE DATA
    private ModelOfExercisePerformance createFakeData() {
        return new ModelOfExercisePerformance(
                new PowerExerciseModel()
                        .setRepeats(15)
                        .setSets(3)
                        .setWeight(500)
                        .setTitle("Мадагаскарский жим с обратным подворачиванием стоп")
                        .setCustomExercise(true))
                .setStartTime(12000L)
                .setDuration(50)
                .setNote("Заметка о самочуствии во время осуществления мадагаскарского жима");
    }

    private ModelOfExercisePerformance createFakeData2() {
        return new ModelOfExercisePerformance(
                new CardioExerciseModel()
                        .setCountCalMethod(CardioExerciseModel.METHOD_MET_VALUES)
                        .setIntensityType(CardioExerciseModel.TYPE_SPECIFY)
                        .setLow(1)
                        .setMiddle(2)
                        .setHigh(6)
                        .setTitle("Жим челюстями с фиксацией пищи обратным хватом")
                        .setCustomExercise(true))
                .setStartTime(12000L)
                .setDuration(70)
                .setNote("Заметка о самочуствии во время осуществления мадагаскарского жима");
    }

    private ModelOfExercisePerformance createFakeData3() {
        return new ModelOfExercisePerformance(
                new CardioExerciseModel()
                        .setCountCalMethod(CardioExerciseModel.METHOD_MET_VALUES)
                        .setIntensityType(CardioExerciseModel.TYPE_NOT_SPECIFY)
                        .setDefValue(8)
                        .setTitle("Жим челюстями с фиксацией пищи обратным хватом")
                        .setCustomExercise(true))
                .setStartTime(12000L)
                .setDuration(70)
                .setNote("Заметка о самочуствии во время осуществления мадагаскарского жима");
    }
    //endregion

    public void addExPerformance(ModelOfExercisePerformance modelOfExercisePerformance) {
        this.modelOfExercisePerformance = modelOfExercisePerformance;
        ExerciseRX.addExercisePerformance(modelOfExercisePerformance)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Boolean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(final Throwable e) {

                    }

                    @Override
                    public void onNext(final Boolean aBoolean) {

                    }
                });
    }

    public void editExPerformance(ModelOfExercisePerformance modelOfExercisePerformance) {
        this.modelOfExercisePerformance = modelOfExercisePerformance;
        ExerciseRX.editExercisePerformance(modelOfExercisePerformance)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Boolean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(final Throwable e) {

                    }

                    @Override
                    public void onNext(final Boolean aBoolean) {

                    }
                });
    }

    int getFakeWeight() {
        //STUB
        return 80;
    }

    interface IView {

        void show(ModelOfExercisePerformance modelOfExercisePerformance);
    }
}

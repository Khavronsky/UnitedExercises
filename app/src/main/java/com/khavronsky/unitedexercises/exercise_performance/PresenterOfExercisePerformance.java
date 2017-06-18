package com.khavronsky.unitedexercises.exercise_performance;


import com.khavronsky.unitedexercises.import_from_grand_project.AbstractPresenter;
import com.khavronsky.unitedexercises.exercises_models.CardioExerciseModel;
import com.khavronsky.unitedexercises.exercises_models.ModelOfExercisePerformance;
import com.khavronsky.unitedexercises.exercises_models.PowerExerciseModel;

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

    public void setData(ModelOfExercisePerformance modelOfExercisePerformance) {
        this.modelOfExercisePerformance = modelOfExercisePerformance;
        //куда-то сохраняем

    }

    interface IView {

        void show(ModelOfExercisePerformance modelOfExercisePerformance);
    }
}

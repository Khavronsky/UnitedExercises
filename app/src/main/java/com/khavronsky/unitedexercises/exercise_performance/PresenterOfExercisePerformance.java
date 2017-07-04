package com.khavronsky.unitedexercises.exercise_performance;


import com.khavronsky.unitedexercises.exercises_models.CardioExerciseModel;
import com.khavronsky.unitedexercises.exercises_models.ExerciseModel;
import com.khavronsky.unitedexercises.exercises_models.ModelOfExercisePerformance;
import com.khavronsky.unitedexercises.exercises_models.PowerExerciseModel;
import com.khavronsky.unitedexercises.get_data.ExerciseRX;
import com.khavronsky.unitedexercises.get_data.FakeData;
import com.khavronsky.unitedexercises.import_from_grand_project.AbstractPresenter;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.khavronsky.unitedexercises.exercises_models.CardioExerciseModel.TYPE_NOT_SPECIFY;

public class PresenterOfExercisePerformance extends AbstractPresenter<PresenterOfExercisePerformance.IView> {

    private static final int DEFAULT_DURATION = 60;

    private ModelOfExercisePerformance modelOfExercisePerformance;

    public void editExPerformance(final ModelOfExercisePerformance exercisePerformance) {
        if (getView()!=null){
            getView().show(exercisePerformance);
        }
    }

    void newExPerformance(ExerciseModel exerciseModel) {
        if (getView() != null) {
            ModelOfExercisePerformance model;
            model = new ModelOfExercisePerformance(exerciseModel);
            setCurrentBurnedKcal(model).setDuration(DEFAULT_DURATION);
            FakeData.setID(model);
            getView().show(model);
        }
    }


    private ModelOfExercisePerformance setCurrentBurnedKcal(ModelOfExercisePerformance model) {

        if (model.getExercise().getType() == ExerciseModel.ExerciseType.CARDIO) {
            CardioExerciseModel cardioModel = (CardioExerciseModel) model.getExercise();
            model.setCurrentKcalPerHour(cardioModel.getIntensityType() == TYPE_NOT_SPECIFY ?
                    cardioModel.getDefValue()
                    : cardioModel.getLow());
        }
        return model;
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
                        .setIntensityType(TYPE_NOT_SPECIFY)
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

    public void saveEditedExPerformance(ModelOfExercisePerformance modelOfExercisePerformance) {
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

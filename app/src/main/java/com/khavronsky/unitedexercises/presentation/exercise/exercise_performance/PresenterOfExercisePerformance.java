package com.khavronsky.unitedexercises.presentation.exercise.exercise_performance;


import com.khavronsky.unitedexercises.busines.exercise.get_data.ExerciseRX;
import com.khavronsky.unitedexercises.busines.exercise.get_data.ExercisesInteractor;
import com.khavronsky.unitedexercises.busines.exercise.get_data.FakeData;
import com.khavronsky.unitedexercises.presentation.exercise.exercises_models.CardioExerciseModel;
import com.khavronsky.unitedexercises.presentation.exercise.exercises_models.ExerciseModel;
import com.khavronsky.unitedexercises.presentation.exercise.exercises_models.ModelOfExercisePerformance;
import com.khavronsky.unitedexercises.presentation.exercise.exercises_models.PowerExerciseModel;
import com.khavronsky.unitedexercises.utils.import_from_grand_project.AbstractPresenter;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.khavronsky.unitedexercises.presentation.exercise.exercises_models.CardioExerciseModel.TYPE_NOT_SPECIFY;

public class PresenterOfExercisePerformance extends AbstractPresenter<PresenterOfExercisePerformance.IView> {

    private static final int DEFAULT_EXERCISE_DURATION = 60;

    private ExercisesInteractor mExercisesInteractor;

    private ModelOfExercisePerformance modelOfExercisePerformance;

    public PresenterOfExercisePerformance() {
        mExercisesInteractor = new ExerciseRX();
    }

    public void editExPerformance(final ModelOfExercisePerformance exercisePerformance) {
        if (getView()!=null){
            getView().show(exercisePerformance);
        }
    }

    void newExPerformance(ExerciseModel exerciseModel) {
        if (getView() != null) {
            ModelOfExercisePerformance model;
            model = new ModelOfExercisePerformance(exerciseModel);
            setCurrentBurnedKcal(model).setDuration(DEFAULT_EXERCISE_DURATION);
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
        mExercisesInteractor.addExercisePerformance(modelOfExercisePerformance)
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
        mExercisesInteractor.editExercisePerformance(modelOfExercisePerformance)
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

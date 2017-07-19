package com.khavronsky.unitedexercises.presentation.exercise.exercise_performance;


import com.khavronsky.unitedexercises.busines.exercise.get_data.ExerciseRX;
import com.khavronsky.unitedexercises.busines.exercise.get_data.IExercisesInteractor;
import com.khavronsky.unitedexercises.busines.exercise.get_data.FakeData;
import com.khavronsky.unitedexercises.presentation.exercise.exercises_models.CardioExerciseModel;
import com.khavronsky.unitedexercises.presentation.exercise.exercises_models.ExerciseModel;
import com.khavronsky.unitedexercises.presentation.exercise.exercises_models.ModelOfExercisePerformance;
import com.khavronsky.unitedexercises.utils.import_from_grand_project.AbstractPresenter;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.khavronsky.unitedexercises.presentation.exercise.exercises_models.CardioExerciseModel.TYPE_NOT_SPECIFY;

public class ExercisePerformancePresenter extends AbstractPresenter<IView> {

    private static final int DEFAULT_EXERCISE_DURATION = 60;

    private IExercisesInteractor mIExercisesInteractor;

    private ModelOfExercisePerformance modelOfExercisePerformance;

    public ExercisePerformancePresenter() {
        mIExercisesInteractor = new ExerciseRX();
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

    public void addExPerformance(ModelOfExercisePerformance modelOfExercisePerformance) {
        this.modelOfExercisePerformance = modelOfExercisePerformance;
        mIExercisesInteractor.addExercisePerformance(modelOfExercisePerformance)
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
        mIExercisesInteractor.editExercisePerformance(modelOfExercisePerformance)
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

    public void delExPerformance(ModelOfExercisePerformance modelOfExercisePerformance) {
        mIExercisesInteractor.delExercisePerformance(modelOfExercisePerformance.getId())
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
}

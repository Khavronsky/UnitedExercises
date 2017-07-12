package com.khavronsky.unitedexercises.presentation.exercise.create_new_exercises.new_power_exercise;

import com.khavronsky.unitedexercises.busines.exercise.get_data.ExerciseRX;
import com.khavronsky.unitedexercises.busines.exercise.get_data.ExercisesInteractor;
import com.khavronsky.unitedexercises.presentation.exercise.exercises_models.ExerciseModel;
import com.khavronsky.unitedexercises.utils.import_from_grand_project.AbstractPresenter;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class PresenterOfPowerExerciseEditor extends AbstractPresenter<IView> {

    private ExercisesInteractor mExercisesInteractor;

    public PresenterOfPowerExerciseEditor() {
        mExercisesInteractor = new ExerciseRX();
    }

    void saveData(ExerciseModel model) {

        mExercisesInteractor.addCustomExercise(model)
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


    void editData(ExerciseModel model) {

        mExercisesInteractor.editCustomExercise(model)
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
}

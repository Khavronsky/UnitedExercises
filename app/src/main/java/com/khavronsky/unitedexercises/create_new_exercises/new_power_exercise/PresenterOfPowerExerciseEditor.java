package com.khavronsky.unitedexercises.create_new_exercises.new_power_exercise;

import com.khavronsky.unitedexercises.exercises_models.ExerciseModel;
import com.khavronsky.unitedexercises.get_data.ExerciseRX;
import com.khavronsky.unitedexercises.import_from_grand_project.AbstractPresenter;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class PresenterOfPowerExerciseEditor extends AbstractPresenter<PresenterOfPowerExerciseEditor.IView> {

    void saveData(ExerciseModel model){

        ExerciseRX.addCustomExercise(model)
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

    public interface IView {

    }
}

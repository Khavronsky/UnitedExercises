package com.khavronsky.unitedexercises.presentation.exercise.exercises_catalogs.custom_ex_catalog;

import com.khavronsky.unitedexercises.busines.exercise.get_data.IExercisesInteractor;
import com.khavronsky.unitedexercises.presentation.exercise.exercises_models.ExerciseModel;
import com.khavronsky.unitedexercises.utils.import_from_grand_project.AbstractPresenter;

import java.util.ArrayList;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class CustomExPresenter extends AbstractPresenter<IView> {

    private ExerciseModel.ExerciseType type;

    private IExercisesInteractor mIExercisesInteractor;

//    public CustomExPresenter() {
//        mIExercisesInteractor = new ExerciseRX();
//    }

    public CustomExPresenter(IExercisesInteractor interactor) {
        mIExercisesInteractor = interactor;
    }

    public void loadData(ExerciseModel.ExerciseType type) {
        this.type = type;

        mIExercisesInteractor.getCustomExerciseList()
                .map(customExerciseModelList -> new ArrayList<ExerciseModel>() {{
                            for (ExerciseModel model :
                                    customExerciseModelList) {
                                if (model.getType() == type) {
                                    add(model);
                                }
                            }
                        }}
                )
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ArrayList<ExerciseModel>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(final Throwable e) {

                    }

                    @Override
                    public void onNext(final ArrayList<ExerciseModel> exerciseModels) {
                        if (getView() != null) {
                            getView().show(exerciseModels);
                        }
                    }
                });
    }

    public void delCustomExercise(long id) {
        mIExercisesInteractor.delCustomExercise(id)
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
                        loadData(type);
                    }
                });
    }
}

package com.khavronsky.unitedexercises.presentation.exercise.exercises_catalogs.ExerciseSearch;

import com.khavronsky.unitedexercises.presentation.exercise.exercises_models.ExerciseModel;
import com.khavronsky.unitedexercises.busines.exercise.get_data.ExerciseRX;
import com.khavronsky.unitedexercises.utils.import_from_grand_project.AbstractPresenter;

import java.util.ArrayList;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class SearchDialogPresenter extends AbstractPresenter<SearchDialogPresenter.IView> {

    private ArrayList<ExerciseModel> mExerciseModels;

    public SearchDialogPresenter(ExerciseModel.ExerciseType type) {
        getExercisesFromDB(type);
    }

    public void getExercisesFromDB(ExerciseModel.ExerciseType type) {

        ExerciseRX.getAllExercises()
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
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(final ArrayList<ExerciseModel> exerciseModels) {
                        if (getView() != null) {
                            getView().setAllExercises(exerciseModels);
                        }
                        mExerciseModels = exerciseModels;
                    }
                });
    }

    public interface IView {

        void setAllExercises(ArrayList<ExerciseModel> exerciseModels);
    }
}

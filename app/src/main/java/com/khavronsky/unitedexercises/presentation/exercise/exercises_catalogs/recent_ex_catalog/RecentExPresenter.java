package com.khavronsky.unitedexercises.presentation.exercise.exercises_catalogs.recent_ex_catalog;

import com.khavronsky.unitedexercises.busines.exercise.get_data.ExerciseRX;
import com.khavronsky.unitedexercises.busines.exercise.get_data.IExercisesInteractor;
import com.khavronsky.unitedexercises.presentation.exercise.exercises_models.ExerciseModel;
import com.khavronsky.unitedexercises.presentation.exercise.exercises_models.ModelOfExercisePerformance;
import com.khavronsky.unitedexercises.utils.import_from_grand_project.AbstractPresenter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class RecentExPresenter extends AbstractPresenter<IView> {


    private IExercisesInteractor mIExercisesInteractor;

    public RecentExPresenter() {
        mIExercisesInteractor = new ExerciseRX();
    }

    public void loadData(ExerciseModel.ExerciseType type) {
        mIExercisesInteractor.getRecentExerciseList()
                .map(modelOfExercisePerformances -> new ArrayList<ModelOfExercisePerformance>() {{
                    for (ModelOfExercisePerformance model :
                            modelOfExercisePerformances) {
                        if (model.getExercise().getType() == type) {
                            add(model);
                        }
                    }
                }})
                //сортируем по дате изменения
                .map(modelOfExercisePerformances -> {
                    Collections.sort(modelOfExercisePerformances);
                    return modelOfExercisePerformances;
                })
                //сортируем по уникальности ID
                .map(modelOfExercisePerformances -> new ArrayList<ModelOfExercisePerformance>() {{

                    ArrayList<Long> unicID = new ArrayList<>();
                    for (ModelOfExercisePerformance model :
                            modelOfExercisePerformances) {
                        long id = model.getExercise().getId();
                        boolean unic = true;

                        for (Long unID :
                                unicID) {
                            if (unID == id) {
                                unic = false;
                            }
                        }
                        if (unic) {
                            unicID.add(id);
                            add(model);
                        }
                    }
                }})
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<ModelOfExercisePerformance>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(final Throwable e) {

                    }

                    @Override
                    public void onNext(final List<ModelOfExercisePerformance> exerciseModels) {
                        if (getView() != null) {
                            getView().show(exerciseModels);
                        }
                    }
                });
    }
}

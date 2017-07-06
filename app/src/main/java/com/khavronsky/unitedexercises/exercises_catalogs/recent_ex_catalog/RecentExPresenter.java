package com.khavronsky.unitedexercises.exercises_catalogs.recent_ex_catalog;

import com.khavronsky.unitedexercises.exercises_models.ExerciseModel;
import com.khavronsky.unitedexercises.exercises_models.ModelOfExercisePerformance;
import com.khavronsky.unitedexercises.get_data.ExerciseRX;
import com.khavronsky.unitedexercises.import_from_grand_project.AbstractPresenter;

import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class RecentExPresenter extends AbstractPresenter<RecentExPresenter.IView> {


    private ExerciseModel.ExerciseType type;

    public void loadData(ExerciseModel.ExerciseType type) {
        this.type = type;
        ExerciseRX.getRecentExerciseList()
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
                    for (ModelOfExercisePerformance model :
                            modelOfExercisePerformances) {
                        long id = model.getExercise().getId();

                        for (ModelOfExercisePerformance model2 :
                                modelOfExercisePerformances) {
                            if (model2.getExercise().getId() == id) {
                                break;
                            }

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
                        Log.d("KhS", "loadData: CustomExPresenter ");
                        if (getView() != null) {
                            getView().show(exerciseModels);
                        }
                    }
                });
    }
//
//    private List<CustomExModel> createCustomExecList() {
//
//        return new ArrayList<CustomExModel>() {
//            {
//                add(new CustomExModel() {
//                    {
//                        this.setExTitle("SuperExercise")
//                                .setExSubTitle("SuperSubtitle")
//                                .setActive(true)
//                                .setId(1);
//                    }
//                });
//
//                add(new CustomExModel() {
//                    {
//                        this.setExTitle("PuperExercise")
//                                .setExSubTitle("PuperSubtitle")
//                                .setActive(true)
//                                .setId(2);
//                    }
//                });
//            }
//        };
//    }

    public interface IView {

        void show(List<ModelOfExercisePerformance> exModelList);
    }
}

package com.khavronsky.unitedexercises.exercises_catalogs.default_ex_catalog;

import com.khavronsky.unitedexercises.exercises_models.ExerciseModel;
import com.khavronsky.unitedexercises.get_data.ExerciseRX;
import com.khavronsky.unitedexercises.import_from_grand_project.AbstractPresenter;

import android.util.Log;

import java.util.ArrayList;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class DefaultExPresenter extends AbstractPresenter<DefaultExPresenter.IView> {


    public void loadData(final ExerciseModel.ExerciseType type) {
        ExerciseRX.getDefaultExerciseList()
                .map(defExModels -> new ArrayList<ExerciseModel>() {{
                    for (ExerciseModel model :
                            defExModels) {
                        if (model.getType() == type) {
                            add(model);
                        }
                    }
                }})
                .map(exerciseModelList -> new ArrayList<String>() {{
                            for (ExerciseModel model :
                                    exerciseModelList) {
                                add(model.getTitle());
                            }
                        }}
                )
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ArrayList<String>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(final Throwable e) {

                    }

                    @Override
                    public void onNext(final ArrayList<String> defaultExModels) {
                        Log.d("KhS", "loadData: DefaultExPresenter");
                        if (getView() != null) {
                            getView().show(defaultExModels);
                        }
                    }
                })
        ;
    }

    public interface IView {

        void show(ArrayList<String> exList);
    }
}

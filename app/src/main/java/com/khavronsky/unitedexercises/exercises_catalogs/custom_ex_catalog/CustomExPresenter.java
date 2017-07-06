package com.khavronsky.unitedexercises.exercises_catalogs.custom_ex_catalog;

import com.khavronsky.unitedexercises.exercises_models.ExerciseModel;
import com.khavronsky.unitedexercises.get_data.ExerciseRX;
import com.khavronsky.unitedexercises.import_from_grand_project.AbstractPresenter;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class CustomExPresenter extends AbstractPresenter<CustomExPresenter.IView> {

    private ExerciseModel.ExerciseType type;

    public void loadData(ExerciseModel.ExerciseType type) {
        this.type = type;
        ExerciseRX.getCustomExerciseList()
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
                        Log.d("KhS", "loadData: CustomExPresenter ");
                        if (getView() != null) {
                            getView().show(exerciseModels);
                        }
                    }
                });
    }

    public void delCustomExercise(long id){
        ExerciseRX.delCustomExercise(id)
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

    public interface IView{

        void show(List<ExerciseModel> exModelList);
    }

}
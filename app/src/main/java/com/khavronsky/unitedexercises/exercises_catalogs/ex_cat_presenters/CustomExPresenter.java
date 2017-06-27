package com.khavronsky.unitedexercises.exercises_catalogs.ex_cat_presenters;

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




    public void loadData(ExerciseModel.ExerciseType type) {
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

    public interface IView{

        void show(List<ExerciseModel> exModelList);
    }

}

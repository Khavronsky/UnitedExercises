package com.khavronsky.unitedexercises.presentation.exercise.exercises_catalogs.default_ex_catalog;

import com.khavronsky.unitedexercises.busines.exercise.get_data.ExerciseRX;
import com.khavronsky.unitedexercises.busines.exercise.get_data.ExercisesInteractor;
import com.khavronsky.unitedexercises.presentation.exercise.exercises_models.ExerciseModel;
import com.khavronsky.unitedexercises.utils.import_from_grand_project.AbstractPresenter;

import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.khavronsky.unitedexercises.presentation.exercise.exercises_catalogs.default_ex_catalog.ModelOfItemForExCatalog.ItemType.CAPITAL_LETTER;
import static com.khavronsky.unitedexercises.presentation.exercise.exercises_catalogs.default_ex_catalog.ModelOfItemForExCatalog.ItemType.EXERCISE_TITLE;


public class DefaultExPresenter extends AbstractPresenter<IView> {

    private ExercisesInteractor mExercisesInteractor;

    public DefaultExPresenter() {
        mExercisesInteractor = new ExerciseRX();
    }

    public void loadData(final ExerciseModel.ExerciseType type) {
        mExercisesInteractor.getDefaultExerciseList()
                .map(defExModels -> new ArrayList<ExerciseModel>() {{
                    for (ExerciseModel model :
                            defExModels) {
                        if (model.getType() == type) {
                            add(model);
                        }
                    }
                }})
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
                    public void onNext(final ArrayList<ExerciseModel> defaultExModels) {
                        Log.d("KhS", "loadData: DefaultExPresenter");
                        if (getView() != null) {
                            getView().show(convertToExCatModel(defaultExModels));
                        }
                    }
                })
        ;
    }

    private ArrayList<ModelOfItemForExCatalog> convertToExCatModel(ArrayList<ExerciseModel> models) {
        ArrayList<ModelOfItemForExCatalog> list = new ArrayList<>();

        Collections.sort(models);
        String x = "";
        String s = "";

        for (ExerciseModel model :
                models) {
            s = model.getTitle();
            if (!x.equals(s.substring(0, 1))) {
                x = s.substring(0, 1);
                list.add(new ModelOfItemForExCatalog(CAPITAL_LETTER)
                .setTitle(x));
            }
            list.add(new ModelOfItemForExCatalog(EXERCISE_TITLE)
            .setExercise(model));
        }
        return list;
    }
}

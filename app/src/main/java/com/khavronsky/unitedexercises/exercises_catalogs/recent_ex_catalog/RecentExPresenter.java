package com.khavronsky.unitedexercises.exercises_catalogs.recent_ex_catalog;

import com.khavronsky.unitedexercises.exercises_models.CustomExModel;
import com.khavronsky.unitedexercises.get_data.ExerciseRX;
import com.khavronsky.unitedexercises.import_from_grand_project.AbstractPresenter;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;


public class RecentExPresenter extends AbstractPresenter<RecentExPresenter.IView> {


    public void loadData() {
        ExerciseRX.getRecentExerciseList();

        Log.d("KhS", "loadData: RecentExPresenter");
        if (getView() != null) {
            getView().show(createCustomExecList());
        }
    }

    private List<CustomExModel> createCustomExecList() {

        return new ArrayList<CustomExModel>() {
            {
                add(new CustomExModel() {
                    {
                        this.setExTitle("SuperExercise")
                                .setExSubTitle("SuperSubtitle")
                                .setActive(true)
                                .setId(1);
                    }
                });

                add(new CustomExModel() {
                    {
                        this.setExTitle("PuperExercise")
                                .setExSubTitle("PuperSubtitle")
                                .setActive(true)
                                .setId(2);
                    }
                });
            }
        };
    }

    public interface IView {

        void show(List<CustomExModel> exModelList);
    }
}

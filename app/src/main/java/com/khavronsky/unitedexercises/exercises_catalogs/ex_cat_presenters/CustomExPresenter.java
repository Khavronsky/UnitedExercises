package com.khavronsky.unitedexercises.exercises_catalogs.ex_cat_presenters;

import com.khavronsky.unitedexercises.exercises_models.CustomExModel;
import com.khavronsky.unitedexercises.import_from_grand_project.AbstractPresenter;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;


public class CustomExPresenter extends AbstractPresenter<CustomExPresenter.IView> {


    public void loadData() {
        Log.d("PRES", "loadData: ");
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

    public interface IView{

        void show(List<CustomExModel> exModelList);
    }

}

package com.khavronsky.unitedexercises.create_new_exercises.new_cardio_exercise;

import com.khavronsky.unitedexercises.exercises_models.CardioExerciseModel;
import com.khavronsky.unitedexercises.import_from_grand_project.AbstractPresenter;


public class PresenterOfCardioExerciseEditor extends AbstractPresenter<PresenterOfCardioExerciseEditor.IView> {

    void loadData(){



        if (getView() != null) {
//            getView().show();
        }
    }

    public interface IView {

        void show(CardioExerciseModel cardioExerciseModel);
    }
}

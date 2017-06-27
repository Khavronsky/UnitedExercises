package com.khavronsky.unitedexercises.create_new_exercises.new_power_exercise;

import com.khavronsky.unitedexercises.exercises_models.PowerExerciseModel;
import com.khavronsky.unitedexercises.import_from_grand_project.AbstractPresenter;


public class PresenterOfPowerExerciseEditor extends AbstractPresenter<PresenterOfPowerExerciseEditor.IView> {

    public interface IView {

        void show(PowerExerciseModel powerExerciseModel);
    }
}

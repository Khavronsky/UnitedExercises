package com.khavronsky.unitedexercises.presentation.exercise.exercises_models;


public interface IEditCatalog {
    IModel editElements(IModel elements);
    void delElements (long id);

}

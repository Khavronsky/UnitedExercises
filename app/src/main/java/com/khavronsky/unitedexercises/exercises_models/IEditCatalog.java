package com.khavronsky.unitedexercises.exercises_models;


public interface IEditCatalog {
    IModel editElements(IModel elements);
    void delElements (long id);

}

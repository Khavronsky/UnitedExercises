package com.khavronsky.unitedexercises.exercises_models;


import java.io.Serializable;

public interface IModel extends Serializable {
    long getId();
    void setId(long id);

}

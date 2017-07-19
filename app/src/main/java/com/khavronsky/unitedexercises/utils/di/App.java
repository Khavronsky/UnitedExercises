package com.khavronsky.unitedexercises.utils.di;

import android.app.Application;


public class App extends Application {

    public static AppComponent getComponent() {
        return mComponent;
    }

    private static AppComponent mComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        mComponent = DaggerAppComponent.create();
    }
}

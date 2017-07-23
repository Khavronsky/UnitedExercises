package com.khavronsky.unitedexercises.utils.di;

import android.app.Application;


public class App extends Application {

    private static AppComponent sAppComponent;

    private static ExComponent sExComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        sAppComponent = DaggerAppComponent.create();
        sExComponent = sAppComponent.createExComponent();
    }

    public static ExComponent getComponent() {
        return sExComponent;
    }
}

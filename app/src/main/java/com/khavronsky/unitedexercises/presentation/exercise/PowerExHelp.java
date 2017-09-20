package com.khavronsky.unitedexercises.presentation.exercise;

import com.khavronsky.unitedexercises.R;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

/**
 * Справка - сообщает о невозможности подсчета сожженных калорий при выполнений силовых упражнений.
 */

public class PowerExHelp extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ex_power_help_activity);
        setToolbar();
    }

    void setToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Калории для силовых упражнений");
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> finish());
        toolbar.setNavigationIcon(R.drawable.ic_close);
    }
}
package com.khavronsky.unitedexercises;

import com.khavronsky.unitedexercises.create_new_exercises.new_cardio_exercise.CreateCardioExerciseActivity;
import com.khavronsky.unitedexercises.create_new_exercises.new_power_exercise.CreatePowerExerciseActivity;
import com.khavronsky.unitedexercises.exercise_performance.ExercisePerformActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.btn1)
    AppCompatButton mBtn1;

    @BindView(R.id.btn2)
    Button mBtn2;

    @BindView(R.id.btn3)
    AppCompatButton mBtn3;

    @OnClick({R.id.btn1, R.id.btn2, R.id.btn3})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn1:
                startActivity(new Intent(this, ExercisePerformActivity.class));
                break;
            case R.id.btn2:
                startActivity(new Intent(this, CreateCardioExerciseActivity.class));
                break;
            case R.id.btn3:
                startActivity(new Intent(this, CreatePowerExerciseActivity.class));
                break;
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);


    }


}
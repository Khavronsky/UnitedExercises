package com.khavronsky.unitedexercises;

import com.khavronsky.unitedexercises.presentation.exercise.create_new_exercises.new_cardio_exercise.CardioExerciseEditorActivity;
import com.khavronsky.unitedexercises.presentation.exercise.create_new_exercises.new_power_exercise.PowerExerciseEditorActivity;
import com.khavronsky.unitedexercises.presentation.exercise.exercise_performance.ExercisePerformActivity;
import com.khavronsky.unitedexercises.presentation.exercise.exercises_catalogs.ExerciseCatalogActivity;
import com.khavronsky.unitedexercises.presentation.exercise.exercises_models.ExerciseModel;
import com.khavronsky.unitedexercises.busines.exercise.get_data.FakeData;

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

    @BindView(R.id.btn4)
    AppCompatButton mBtn4;

    @BindView(R.id.btn5)
    AppCompatButton mBtn5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }


    @OnClick({R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4, R.id.btn5})
    public void onViewClicked(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.btn1:
                intent = new Intent(this, ExercisePerformActivity.class);
                intent.putExtra(ExercisePerformActivity.NEW_PERFORMANCE, true);
                intent.putExtra(ExercisePerformActivity.MODEL_OF_EXERCISE, FakeData.getDefaultCatalog().get(0));
                startActivity(intent);
                break;
            case R.id.btn2:
                intent = new Intent(this, CardioExerciseEditorActivity.class);
//                intent.putExtra();
                startActivity(intent);
                break;
            case R.id.btn3:
                intent = new Intent(this, PowerExerciseEditorActivity.class);
//                intent.putExtra();
                startActivity(intent);
                break;
            case R.id.btn4:
                intent = new Intent(this, ExerciseCatalogActivity.class);
                intent.putExtra("type", ExerciseModel.ExerciseType.CARDIO);
                startActivity(intent);
                break;
            case R.id.btn5:
                intent = new Intent(this, ExerciseCatalogActivity.class);
                intent.putExtra("type", ExerciseModel.ExerciseType.POWER);
                startActivity(intent);
                break;
        }
    }


}

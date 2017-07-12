package com.khavronsky.unitedexercises.presentation.exercise.exercise_performance;

import com.khavronsky.unitedexercises.R;
import com.khavronsky.unitedexercises.presentation.exercise.exercise_performance.custom_views.collapsing_card.CustomCollapsingView;
import com.khavronsky.unitedexercises.presentation.exercise.exercise_performance.fragments.CardioExPerformFragment;
import com.khavronsky.unitedexercises.presentation.exercise.exercise_performance.fragments.PowerExPerformFragment;
import com.khavronsky.unitedexercises.presentation.exercise.exercises_models.CardioExerciseModel;
import com.khavronsky.unitedexercises.presentation.exercise.exercises_models.ExerciseModel;
import com.khavronsky.unitedexercises.presentation.exercise.exercises_models.ModelOfExercisePerformance;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.khavronsky.unitedexercises.presentation.exercise.exercises_models.ExerciseModel.ExerciseType.CARDIO;
import static com.khavronsky.unitedexercises.presentation.exercise.exercises_models.ExerciseModel.ExerciseType.POWER;


public class ExercisePerformActivity extends AppCompatActivity implements View.OnClickListener,
        PresenterOfExercisePerformance.IView, CardioExPerformFragment.IExerciseListener {

    //region FIELDS
    public final static String NEW_PERFORMANCE = "new_performance";

    public final static String MODEL_OF_EXERCISE = "exercise_model";

    public final static String MODEL_OF_PERFORMANCE = "exercise_performance";

    @BindView(R.id.my_view)
    CustomCollapsingView mCustomCollapsingView;

    @BindView(R.id.ex_performance_add_btn)
    AppCompatButton addExButton;

    ModelOfExercisePerformance mModelOfExercisePerformance;

    private PresenterOfExercisePerformance mPresenter;

    private boolean newPerformance;
    //endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ex_perform_activity);
        ButterKnife.bind(this);
        if (mPresenter == null) {
            mPresenter = new PresenterOfExercisePerformance();
        }
        mPresenter.attachView(this);
        newPerformance = getIntent().getExtras().getBoolean(NEW_PERFORMANCE);
        if (newPerformance) {
            mPresenter.newExPerformance((ExerciseModel) getIntent().getSerializableExtra(MODEL_OF_EXERCISE));

        } else {
            mPresenter.editExPerformance((ModelOfExercisePerformance) getIntent().getExtras()
                    .getSerializable(MODEL_OF_PERFORMANCE));
        }
        setToolbar();
    }

    @Override
    public void updateModel(final ModelOfExercisePerformance modelOfExercisePerformance) {
        createMyView();
    }

    @Override
    public void show(final ModelOfExercisePerformance modelOfExercisePerformance) {
        this.mModelOfExercisePerformance = modelOfExercisePerformance;
        createMyView();
        startFragment();
        Log.d("qwert", "show: ");
    }

    @Override
    public void onClick(final View v) {
        onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_scrolling, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.delete) {
//            recreate();
            return true;
        }
        if (id == R.id.edit) {
//            showAlertView();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }

    private void startFragment() {
        String tag = mModelOfExercisePerformance
                .getExercise()
                .getType()
                .getTag();
        Fragment fragment;
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragment = fragmentManager.findFragmentById(R.id.fragment_container);
        Log.d("KhS", "startFragment22: ");
        if (fragmentManager.findFragmentByTag(tag) != null) {
            fragment = fragmentManager.findFragmentByTag(tag);
        } else if (Objects.equals(tag, CARDIO.getTag())) {
            Log.d("KhS", "startFragment: CARDIO");
            fragment = CardioExPerformFragment.newInstance(mModelOfExercisePerformance);
            ((CardioExPerformFragment) fragment).setListener(this);
        } else if (Objects.equals(tag, POWER.getTag())) {
            Log.d("KhS", "startFragment: POWER");
            fragment = PowerExPerformFragment.newInstance(mModelOfExercisePerformance);
        }
        fragmentManager.beginTransaction()
                .add(R.id.fragment_container, fragment,
                        tag)
                .commit();
    }

    private void setToolbar() {
        Toolbar toolbar = mCustomCollapsingView.getToolbar();
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        toolbar.setNavigationOnClickListener(this);
    }

    private void createMyView() {
        String subTitle = "";
        String value = "";
        String unit = "Ккал";
        String description = "";
        int kcalBurned = 0;
        int resID = R.drawable.ic_cardio_ex;
        if (mModelOfExercisePerformance.getExercise().getType() == POWER) {
            mCustomCollapsingView.showAlertView(true);
            subTitle = "Силовые";
        } else if (mModelOfExercisePerformance.getExercise().getType() == CARDIO) {
            mCustomCollapsingView.showAlertView(false);
            subTitle = "Кардио";
            kcalBurned = (int) (mModelOfExercisePerformance.getCurrentKcalPerHour() * mPresenter.getFakeWeight() *
                    mModelOfExercisePerformance.getDuration() / 60);
        }

        mCustomCollapsingView.setTextTitle(mModelOfExercisePerformance.getExercise().getTitle())
                .setTextSubtitle(subTitle)
                .setTextValue(String.valueOf(kcalBurned))
                .setTextUnit(unit)
                .setTextExtraDescription(description)
                .setImageID(resID)
                .applyChanges();
    }

    private void showAlertView() {
        mCustomCollapsingView
                .showAlertView(true)
                .applyChanges();
    }

    @OnClick(R.id.ex_performance_add_btn)
    void addExercise() {
        Log.d("KhS_ExPerform", "addExercise: ");
        mModelOfExercisePerformance.setLastChangedTime(Calendar.getInstance().getTimeInMillis());
        if(newPerformance){
            Toast.makeText(this, "SAVE ADD", Toast.LENGTH_SHORT).show();
            mPresenter.addExPerformance(mModelOfExercisePerformance);
        } else {
            Toast.makeText(this, "SAVE PERFORMANCE", Toast.LENGTH_SHORT).show();
            mPresenter.saveEditedExPerformance(mModelOfExercisePerformance);
        }
        onBackPressed();
    }

    int burnedKcalories(float burnPerHourValue, int duration,
            @CardioExerciseModel.CountingCaloriesMethod int countingMethod) {
        return (int) (burnPerHourValue * duration * mPresenter.getFakeWeight() / 60);
    }
}

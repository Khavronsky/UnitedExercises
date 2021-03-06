package com.khavronsky.unitedexercises.presentation.exercise.exercise_performance;

import com.khavronsky.unitedexercises.R;
import com.khavronsky.unitedexercises.presentation.exercise.exercise_performance.custom_views.collapsing_card.CustomCollapsingView;
import com.khavronsky.unitedexercises.presentation.exercise.exercise_performance.dialogs.ExerciseDescriptionDialog;
import com.khavronsky.unitedexercises.presentation.exercise.exercise_performance.fragments.CardioExPerformFragment;
import com.khavronsky.unitedexercises.presentation.exercise.exercise_performance.fragments.PowerExPerformFragment;
import com.khavronsky.unitedexercises.presentation.exercise.exercises_models.ExerciseModel;
import com.khavronsky.unitedexercises.presentation.exercise.exercises_models.ModelOfExercisePerformance;
import com.khavronsky.unitedexercises.utils.di.App;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.Calendar;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.khavronsky.unitedexercises.presentation.exercise.exercises_models.ExerciseModel.ExerciseType.CARDIO;
import static com.khavronsky.unitedexercises.presentation.exercise.exercises_models.ExerciseModel.ExerciseType.POWER;


public class ExercisePerformActivity extends AppCompatActivity implements View.OnClickListener,
        IView, CardioExPerformFragment.IExerciseListener {

    //region FIELDS
    public final static String NEW_PERFORMANCE = "new_performance";

    public final static String MODEL_OF_EXERCISE = "exercise_model";

    public final static String MODEL_OF_PERFORMANCE = "exercise_performance";

    @BindView(R.id.my_view)
    CustomCollapsingView mCustomCollapsingView;

    @BindView(R.id.ex_performance_add_btn)
    AppCompatButton addExButton;

    @Inject
    ExercisePerformancePresenter mPresenter;

    private ModelOfExercisePerformance mModelOfExercisePerformance;

    private ExerciseDescriptionDialog mDescriptionDialog;

    private boolean newPerformance;

    private Unbinder unbinder;
    //endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ex_perform_activity);
        unbinder = ButterKnife.bind(this);
        App.getComponent().getExercisePerformancePresenter(this);
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
    }

    @Override
    public void onClick(final View v) {
        onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.exercises_collapsing_menu, menu);
        boolean infoIconVisibility = mModelOfExercisePerformance.getExercise().getDescription() != null;
        boolean deleteIconVisibility = !newPerformance;
        menu.getItem(0).setVisible(infoIconVisibility);
        menu.getItem(1).setVisible(deleteIconVisibility);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.delete) {
            mPresenter.delExPerformance(mModelOfExercisePerformance);
            onBackPressed();
            return true;
        }
        if (id == R.id.info) {
            if (mDescriptionDialog == null) {
                mDescriptionDialog = ExerciseDescriptionDialog.newInstance(
                        mModelOfExercisePerformance.getExercise().getDescription());
                mDescriptionDialog.setOnDismissListener(() -> mDescriptionDialog = null);
                mDescriptionDialog.show(getSupportFragmentManager(),"description_dialog");
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            mModelOfExercisePerformance
                    .setExercise((ExerciseModel) data.getSerializableExtra(mModelOfExercisePerformance
                            .getExercise().getType().name()));
            show(mModelOfExercisePerformance);
        }
    }

    private void startFragment() {
        String tag = mModelOfExercisePerformance
                .getExercise()
                .getType()
                .name();
        Fragment fragment;
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (tag == CARDIO.name()) {
            fragment = CardioExPerformFragment.newInstance(mModelOfExercisePerformance);
        } else {
            fragment = PowerExPerformFragment.newInstance(mModelOfExercisePerformance);
        }
        fragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment,
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
        String unit = "Ккал";
        String description = "";
        int kcalBurned = 0;
        int resID;
        resID = R.drawable.ic_cardio_ex;
        if (mModelOfExercisePerformance.getExercise().getType() == POWER) {
            mCustomCollapsingView.showAlertView(true);
            subTitle = "Силовые";
            resID = R.drawable.ic_lift_ex;
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

    @OnClick(R.id.ex_performance_add_btn)
    void addExercise() {
        if (mModelOfExercisePerformance.getDuration() > 0) {
            mModelOfExercisePerformance.setLastChangedTime(Calendar.getInstance().getTimeInMillis());
            if (newPerformance) {
                mPresenter.addExPerformance(mModelOfExercisePerformance);
            } else {
                mPresenter.saveEditedExPerformance(mModelOfExercisePerformance);
            }
            onBackPressed();
        } else {
            Toast.makeText(this, "Введите продолжительность упражнения", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        mPresenter.detachView();
    }
}

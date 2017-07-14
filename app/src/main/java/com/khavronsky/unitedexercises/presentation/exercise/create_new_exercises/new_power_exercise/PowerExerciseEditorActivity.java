package com.khavronsky.unitedexercises.presentation.exercise.create_new_exercises.new_power_exercise;

import com.khavronsky.unitedexercises.R;
import com.khavronsky.unitedexercises.busines.exercise.get_data.FakeData;
import com.khavronsky.unitedexercises.presentation.exercise.exercises_models.PowerExerciseModel;
import com.khavronsky.unitedexercises.utils.import_from_grand_project.IntNumPickerFragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.khavronsky.unitedexercises.presentation.exercise.exercises_models.ExerciseModel.ExerciseType.POWER;

public class PowerExerciseEditorActivity extends AppCompatActivity implements View.OnClickListener, IView {

    //region FIELDS

    TextWatcher mTextWatcher;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.ex_title)
    EditText mExTitle;

    @BindView(R.id.ex_description)
    EditText mExDescription;

    @BindView(R.id.focusable_layout)
    LinearLayout mFocusableLayout;

    private PowerExerciseModel mPowerExerciseModel;

    private IntNumPickerFragment mIntNumPickerDialog;

    private String pickerOnScreen = "";

    private PowerExerciseEditorPresenter mPresenter;

    private boolean newExercise = true;

    private String title = "";
    //endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.create_power_ex_activity);
        ButterKnife.bind(this);
        if (mPresenter == null) {
            mPresenter = new PowerExerciseEditorPresenter();
        }
        mPresenter.attachView(this);
        if (getIntent().getExtras() != null) {
            mPowerExerciseModel = (PowerExerciseModel) getIntent().getExtras().getSerializable(
                            POWER.name());
            setEditText(mPowerExerciseModel);
            newExercise = false;
            title = "Редактировать упражнение";

        } else {
            title = "Новое упражнение";
            newExercise = true;
            mPowerExerciseModel = new PowerExerciseModel();
        }
        mFocusableLayout.setFocusableInTouchMode(true);

        setToolbar();
    }

    private void setEditText(PowerExerciseModel powerExerciseModel) {

        if (powerExerciseModel != null) {

            mExTitle.setText(powerExerciseModel.getTitle());
        }
        mTextWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(final CharSequence s, final int start, final int count,
                    final int after) {
            }

            @Override
            public void onTextChanged(final CharSequence s, final int start, final int before, final int count) {
            }

            @Override
            public void afterTextChanged(final Editable s) {
                try {
                    if (Integer.parseInt(String.valueOf(s)) == 0) {
                        s.clear();
                    }
                } catch (NumberFormatException e) {
                }
            }
        };
        mExTitle.addTextChangedListener(mTextWatcher);


    }

    void setToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle(title);
        setSupportActionBar(mToolbar);
        mToolbar.inflateMenu(R.menu.menu);
        mToolbar.setNavigationOnClickListener(this);
        mToolbar.setNavigationIcon(R.drawable.arrow_back);
    }

    private boolean saveExercise() {
        if (mExTitle.getText().length() == 0) {
            Toast.makeText(this, "Ойойой1", Toast.LENGTH_SHORT).show();
            return false;
        }

        mPowerExerciseModel
                .setTitle(String.valueOf(mExTitle.getText()))
                .setDescription(String.valueOf(mExDescription.getText()))
        ;

        if (newExercise) {

            FakeData.setID(mPowerExerciseModel);
            mPresenter.saveData(mPowerExerciseModel);
        } else {
            mPresenter.editData(mPowerExerciseModel);
        }

        Intent intent = new Intent();
        intent.putExtra(POWER.name(), mPowerExerciseModel);
        setResult(RESULT_OK, intent);
        finish();
        return true;
    }

    @Override
    public void onClick(final View v) {
        onBackPressed();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.save) {
            if (saveExercise()) {
                onBackPressed();
            }
        }
        return super.onOptionsItemSelected(item);
    }
}

package com.khavronsky.unitedexercises.presentation.exercise.create_new_exercises.new_cardio_exercise;

import com.khavronsky.unitedexercises.R;
import com.khavronsky.unitedexercises.busines.exercise.get_data.FakeData;
import com.khavronsky.unitedexercises.presentation.exercise.exercises_models.CardioExerciseModel;
import com.khavronsky.unitedexercises.utils.di.App;
import com.khavronsky.unitedexercises.utils.import_from_grand_project.FloatNumPickerFragment;
import com.khavronsky.unitedexercises.utils.import_from_grand_project.IDialogFragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.khavronsky.unitedexercises.presentation.exercise.exercise_performance.ExercisePerformActivity.MODEL_OF_EXERCISE;
import static com.khavronsky.unitedexercises.presentation.exercise.exercises_models.CardioExerciseModel.METHOD_CAL_PER_HOUR;
import static com.khavronsky.unitedexercises.presentation.exercise.exercises_models.CardioExerciseModel.METHOD_MET_VALUES;
import static com.khavronsky.unitedexercises.presentation.exercise.exercises_models.CardioExerciseModel.TYPE_NOT_SPECIFY;
import static com.khavronsky.unitedexercises.presentation.exercise.exercises_models.CardioExerciseModel.TYPE_SPECIFY;
import static com.khavronsky.unitedexercises.presentation.exercise.exercises_models.ExerciseModel.ExerciseType.CARDIO;
import static com.khavronsky.unitedexercises.utils.import_from_grand_project.FloatNumPickerFragment.EXTRA_DECIMAL_STEP_IS_01;

public class CardioExerciseEditorActivity extends AppCompatActivity implements View.OnClickListener,
        IView {

    //region FIELDS
    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.ex_cardio_title)
    EditText mExerciseTitle;

    @BindView(R.id.ex_cardio_burned_per_hour)
    EditText mBurnedPerHour;

    @BindView(R.id.ex_cardio_burned_per_hour_layout)
    TextInputLayout mBurnedPerHourTextLayout;

    @BindView(R.id.ex_cardio_intensity_fields)
    View mIntensityFields;

    @BindView(R.id.ex_cardio_low_intensity)
    EditText mLowIntensity;

    @BindView(R.id.ex_cardio_low_intensity_layout)
    TextInputLayout mLowIntensityTextLayout;

    @BindView(R.id.ex_cardio_middle_intensity)
    EditText mMiddleIntensity;

    @BindView(R.id.ex_cardio_middle_intensity_layout)
    TextInputLayout mMiddleIntensityTextLayout;

    @BindView(R.id.ex_cardio_high_intensity)
    EditText mHighIntensity;

    @BindView(R.id.ex_cardio_high_intensity_layout)
    TextInputLayout mHighIntensityTextLayout;

    @BindView(R.id.ex_cardio_count_method)
    Spinner mCountCalMethod;

    @BindView(R.id.ex_cardio_Intensity_type)
    Spinner mIntensityType;

    private View.OnClickListener editTextListener;

    private TextWatcher mTextWatcher;

    @Inject
    CardioExerciseEditorPresenter mPresenter;

    private CardioExerciseModel mCardioExerciseModel;

    private boolean newExercise = true;

    private String mPickerTag = "mPickerTag";

    public static final String CARDIO_MODEL_TAG = "cardio_model";

    private String mTitle = "";

    private Unbinder unbinder;
    //endregion


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ex_create_cardio_activity);
        unbinder = ButterKnife.bind(this);

        App.getComponent().getCardioExerciseEditorPresenter(this);
        mPresenter.attachView(this);
        if (getIntent().getExtras() != null) {
            mCardioExerciseModel = (CardioExerciseModel) getIntent().getExtras().getSerializable(
                    CARDIO.name());
            editExercise(mCardioExerciseModel);
            newExercise = false;
            mTitle = "Редактировать упражнение";
        } else {
            mCardioExerciseModel = new CardioExerciseModel();
            mTitle = "Новое упражнение";
        }
        setToolbar();

        setSpinners();
        editTextVisibility(mIntensityType.getSelectedItemPosition());
        setHintFromSelectedCountingMethod(mCountCalMethod.getSelectedItemPosition());
        createTextWatcher();

        editTextListener = v -> showPicker((EditText) v);
        setTextWatcher();
    }

    void showPicker(EditText editText) {
        FloatNumPickerFragment dialog = (FloatNumPickerFragment) getSupportFragmentManager()
                .findFragmentByTag(mPickerTag);
        if (dialog != null) {
            return;
        }
        dialog = new FloatNumPickerFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("min_value", 0);
        bundle.putInt("max_value", 200);
        float curVal = 1;
        if (editText.getText().length() != 0) {
            curVal = Float.parseFloat(String.valueOf(editText.getText()));
        }
        bundle.putFloat("current_value", curVal);
        bundle.putBoolean(EXTRA_DECIMAL_STEP_IS_01, true);
        dialog.setArguments(bundle);
        dialog.setCallback(new IDialogFragment() {
            @Override
            public void doButtonClick1(final Object o) {
                editText.setText(String.valueOf(o));
            }

            @Override
            public void doButtonClick2() {
            }

            @Override
            public void doByDismissed() {

            }
        });

        dialog.show(getSupportFragmentManager(), mPickerTag);
    }

    private void setTextWatcher() {
        mExerciseTitle.addTextChangedListener(mTextWatcher);
        mBurnedPerHour.setOnClickListener(editTextListener);
        mLowIntensity.setOnClickListener(editTextListener);
        mMiddleIntensity.setOnClickListener(editTextListener);
        mHighIntensity.setOnClickListener(editTextListener);
    }

    private void setSpinners() {
        ArrayAdapter<?> adapter = ArrayAdapter
                .createFromResource(this, R.array.calc_calories, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mCountCalMethod.setAdapter(adapter);
        mCountCalMethod.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(final AdapterView<?> parent, final View view, final int position,
                    final long id) {
                setHintFromSelectedCountingMethod(position);
            }

            @Override
            public void onNothingSelected(final AdapterView<?> parent) {
            }
        });

        ArrayAdapter<?> adapter2 = ArrayAdapter
                .createFromResource(this, R.array.type_of_intensity, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mIntensityType.setAdapter(adapter2);
        mIntensityType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(final AdapterView<?> parent, final View view, final int position,
                    final long id) {
                editTextVisibility(position);
            }

            @Override
            public void onNothingSelected(final AdapterView<?> parent) {
            }
        });
    }

    private void editExercise(CardioExerciseModel cardioExerciseModel) {
        if (cardioExerciseModel != null) {
            mExerciseTitle.setText(cardioExerciseModel.getTitle());
            mBurnedPerHour.setText(String.valueOf(cardioExerciseModel.getDefValue()));
            mLowIntensity.setText(String.valueOf(cardioExerciseModel.getLow()));
            mMiddleIntensity.setText(String.valueOf(cardioExerciseModel.getMiddle()));
            mHighIntensity.setText(String.valueOf(cardioExerciseModel.getHigh()));
        }
    }

    private void editTextVisibility(int specify) {
        int specVisibility;
        int notSpecVisibility;
        if (specify == TYPE_SPECIFY) {
            specVisibility = VISIBLE;
            notSpecVisibility = GONE;
        } else {
            specVisibility = GONE;
            notSpecVisibility = VISIBLE;
        }
        mBurnedPerHourTextLayout.setVisibility(notSpecVisibility);
        mIntensityFields.setVisibility(specVisibility);
    }

    private void setHintFromSelectedCountingMethod(int selectedCountingMethod) {
        String methodPrefix = "(значение МЕТ)";
        String hintBurnedCalWithoutSpec = "Значение МЕТ";
        if (selectedCountingMethod == METHOD_CAL_PER_HOUR) {
            methodPrefix = "(ккал/час)";
            hintBurnedCalWithoutSpec = "Сожжено калорий в час";
        }
        mBurnedPerHourTextLayout.setHint(hintBurnedCalWithoutSpec);
        mLowIntensityTextLayout.setHint("Низкая нагрузка " + methodPrefix);
        mMiddleIntensityTextLayout.setHint("Средняя нагрузка " + methodPrefix);
        mHighIntensityTextLayout.setHint("Высокая нагрузка " + methodPrefix);
    }

    private void createTextWatcher() {
        mTextWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(final CharSequence s, final int start, final int count,
                    final int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, final int start, final int before, final int count) {

            }

            @Override
            public void afterTextChanged(final Editable s) {
                // FIXME: 17.07.17 что можно и что нельзя писать в названии???
                try {
                    if (Integer.parseInt(String.valueOf(s)) == 0) {
                        s.clear();
                    }
                } catch (NumberFormatException e) {
                }
                if (s.length() > 25) {
                    Toast.makeText(CardioExerciseEditorActivity.this,
                            "Ой ой ой! \nОсеня мунога букавка твоя писать",
                            Toast.LENGTH_SHORT).show();
                    s.delete(0, 1);
                }
            }
        };
    }

    private void setToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.inflateMenu(R.menu.exercises_toolbar_menu);
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(mTitle);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        mToolbar.setNavigationOnClickListener(this);
    }

    private boolean saveExercise(int intensityType) {
        // FIXME: 17.07.17 какие тосты выводить???
        if (mExerciseTitle.getText().length() == 0) {
            Toast.makeText(this, "Необходимо заполнить все поля", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (intensityType == TYPE_SPECIFY) {

            if (mLowIntensity.getText().length() == 0) {
                Toast.makeText(this, "Необходимо заполнить все поля", Toast.LENGTH_SHORT).show();
                return false;
            }
            if (mMiddleIntensity.getText().length() == 0) {
                Toast.makeText(this, "Необходимо заполнить все поля", Toast.LENGTH_SHORT).show();
                return false;
            }
            if (mHighIntensity.getText().length() == 0) {
                Toast.makeText(this, "Необходимо заполнить все поля", Toast.LENGTH_SHORT).show();
                return false;
            }

            mCardioExerciseModel
                    .setIntensityType(TYPE_SPECIFY)
                    .setCountCalMethod(mCountCalMethod.getSelectedItemPosition() == 0 ? METHOD_CAL_PER_HOUR
                            : METHOD_MET_VALUES)
                    .setLow(Float.parseFloat(String.valueOf(mLowIntensity.getText())))
                    .setMiddle(Float.parseFloat(String.valueOf(mMiddleIntensity.getText())))
                    .setHigh(Float.parseFloat(String.valueOf(mHighIntensity.getText())))
                    .setTitle(String.valueOf(mExerciseTitle.getText()));
        } else {
            if (mBurnedPerHour.getText().length() == 0) {
                Toast.makeText(this, "Необходимо заполнить все поля", Toast.LENGTH_SHORT).show();
                return false;
            }
            mCardioExerciseModel
                    .setIntensityType(TYPE_NOT_SPECIFY)
                    .setDefValue(Float.parseFloat(String.valueOf(mBurnedPerHour.getText())))
                    .setTitle(String.valueOf(mExerciseTitle.getText()));
        }
        if (newExercise) {
            FakeData.setID(mCardioExerciseModel);
            mPresenter.saveData(mCardioExerciseModel);
        } else {
            mPresenter.editData(mCardioExerciseModel);
        }
        Intent intent = new Intent();
        intent.putExtra(MODEL_OF_EXERCISE, mCardioExerciseModel);
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
        getMenuInflater().inflate(R.menu.exercises_toolbar_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_save) {
            saveExercise(mIntensityType.getSelectedItemPosition());
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
        unbinder.unbind();

    }
}

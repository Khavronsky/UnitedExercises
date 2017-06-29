package com.khavronsky.unitedexercises.create_new_exercises.new_power_exercise;

import com.khavronsky.unitedexercises.R;
import com.khavronsky.unitedexercises.exercises_models.PowerExerciseModel;
import com.khavronsky.unitedexercises.get_data.FakeData;
import com.khavronsky.unitedexercises.import_from_grand_project.IDialogFragment;
import com.khavronsky.unitedexercises.import_from_grand_project.IntNumPickerFragment;

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
import butterknife.OnClick;

public class PowerExerciseEditorActivity extends AppCompatActivity implements View.OnClickListener,
        IDialogFragment, PresenterOfPowerExerciseEditor.IView {

    //region FIELDS
    private final static String SETS = "sets";

    private final static String REPEATS = "repeats";

    private final static String WEIGHT = "weight";

    TextWatcher mTextWatcher;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.ex_title)
    EditText mExTitle;

    @BindView(R.id.ex_sets)
    EditText mExSets;

    @BindView(R.id.ex_repeats)
    EditText mExRepeats;

    @BindView(R.id.ex_weight)
    EditText mExWeight;

    @BindView(R.id.focusable_layout)
    LinearLayout mFocusableLayout;

    private PowerExerciseModel mPowerExerciseModel;

    private IntNumPickerFragment mIntNumPickerDialog;

    private String pickerOnScreen = "";

    private PresenterOfPowerExerciseEditor mPresenter;
    //endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.create_power_ex_activity);
        if (mPresenter == null) {
            mPresenter = new PresenterOfPowerExerciseEditor();
        }
        mPresenter.attachView(this);
        ButterKnife.bind(this);
        mFocusableLayout.setFocusableInTouchMode(true);

        setToolbar();
        setEditText(mPowerExerciseModel);
        setPowerExerciseModel(new PowerExerciseModel());
    }

    public void setPowerExerciseModel(final PowerExerciseModel powerExerciseModel) {
        mPowerExerciseModel = powerExerciseModel;
    }

    @OnClick({R.id.ex_sets, R.id.ex_repeats, R.id.ex_weight})
    public void onViewClicked(View view) {
        mExTitle.clearFocus();
        switch (view.getId()) {
            case R.id.ex_sets:
                pickerOnScreen = SETS;
                showIntPicker(1, 20, mPowerExerciseModel.getSets(), 1);
                break;
            case R.id.ex_repeats:
                pickerOnScreen = REPEATS;
                showIntPicker(1, 50, mPowerExerciseModel.getRepeats(), 1);
                break;
            case R.id.ex_weight:
                //поменять на floatPicker??????
                pickerOnScreen = WEIGHT;
                showIntPicker(1, 500, mPowerExerciseModel.getWeight(), 1);
                break;
        }
    }

    //region IDialogFragment implementation
    @Override
    public void doButtonClick1(final Object o) {
        if (mIntNumPickerDialog != null) {
            mIntNumPickerDialog.dismiss();
            switch (pickerOnScreen) {
                case SETS:
//                    ((PowerExerciseModel) mModelOfExercisePerformance.getExercise()).setSets((int) o);
                    mExSets.setText(String.valueOf(o));
                    break;
                case REPEATS:
//                    ((PowerExerciseModel) mModelOfExercisePerformance.getExercise()).setRepeats((int) o);
                    mExRepeats.setText(String.valueOf(o));
                    break;
                case WEIGHT:
//                    ((PowerExerciseModel) mModelOfExercisePerformance.getExercise()).setWeight((int) o);
                    mExWeight.setText(String.valueOf(o));
                    break;
            }
            mIntNumPickerDialog = null;
        }
        pickerOnScreen = null;
    }

    @Override
    public void doButtonClick2() {

    }

    @Override
    public void doByDismissed() {

    }

    //endregion

    void showIntPicker(int min, int max, int currentVal, int onePointVal) {
        if (mIntNumPickerDialog == null) {
            mIntNumPickerDialog = IntNumPickerFragment.newInstance(min, max, currentVal, onePointVal);
            mIntNumPickerDialog.setCallback(this);
            mIntNumPickerDialog.show(getSupportFragmentManager(), "picker");
        }
    }

    private void setEditText(PowerExerciseModel powerExerciseModel) {

        if (powerExerciseModel != null) {

            mExTitle.setText(powerExerciseModel.getTitle());
            mExSets.setText(powerExerciseModel.getSets());
            mExRepeats.setText(powerExerciseModel.getRepeats());
            mExWeight.setText(powerExerciseModel.getWeight());
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
        mExSets.addTextChangedListener(mTextWatcher);
        mExRepeats.addTextChangedListener(mTextWatcher);
        mExWeight.addTextChangedListener(mTextWatcher);

    }

    void setToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("Новое упражнение");
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
        if (mExSets.getText().length() == 0) {
            Toast.makeText(this, "Ойойой2", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (mExRepeats.getText().length() == 0) {
            Toast.makeText(this, "Ойойой3", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (mExWeight.getText().length() == 0) {
            Toast.makeText(this, "Ойойой4", Toast.LENGTH_SHORT).show();
            return false;
        }
        mPowerExerciseModel
                .setSets(Integer.parseInt(String.valueOf(mExSets.getText())))
                .setRepeats(Integer.parseInt(String.valueOf(mExRepeats.getText())))
                .setWeight(Integer.parseInt(String.valueOf(mExWeight.getText())))
                .setTitle(String.valueOf(mExTitle.getText()))
        ;

        FakeData.setID(mPowerExerciseModel);
        mPresenter.saveData(mPowerExerciseModel);
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
            saveExercise();
            Toast.makeText(this, "SAVE", Toast.LENGTH_SHORT).show();
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}

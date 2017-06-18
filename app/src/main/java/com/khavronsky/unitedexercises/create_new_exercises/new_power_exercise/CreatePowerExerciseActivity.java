package com.khavronsky.unitedexercises.create_new_exercises.new_power_exercise;

import com.khavronsky.unitedexercises.R;
import com.khavronsky.unitedexercises.exercises_models.PowerExerciseModel;
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
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CreatePowerExerciseActivity extends AppCompatActivity implements View.OnClickListener {

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

    private PowerExerciseModel mPowerExerciseModel;

    private IntNumPickerFragment mIntNumPickerDialog;

    public void setPowerExerciseModel(final PowerExerciseModel powerExerciseModel) {
        mPowerExerciseModel = powerExerciseModel;
    }

    @OnClick({R.id.ex_sets, R.id.ex_repeats, R.id.ex_weight})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ex_sets:
                break;
            case R.id.ex_repeats:
                break;
            case R.id.ex_weight:
                break;
        }
    }

//    void showIntPicker(int min, int max, int currentVal, int onePointVal) {
//        if (mIntNumPickerDialog == null) {
//            mIntNumPickerDialog = IntNumPickerFragment.newInstance(min, max, currentVal, onePointVal);
//            mIntNumPickerDialog.setCallback(this);
//            mIntNumPickerDialog.show(getSupportFragmentManager(), "picker");
//        }
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.create_power_ex_activity);
        ButterKnife.bind(this);
        setToolbar();
        setEditText(mPowerExerciseModel);
        setPowerExerciseModel(new PowerExerciseModel());
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
            boolean qwe = saveExercise();
            if (qwe) {
                Toast.makeText(this, "SAVE", Toast.LENGTH_SHORT).show();
                finish();
            }
            return qwe;
        }
        return super.onOptionsItemSelected(item);
    }
}

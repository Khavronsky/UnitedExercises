package com.khavronsky.unitedexercises.exercise_performance.fragments;

import com.khavronsky.unitedexercises.R;
import com.khavronsky.unitedexercises.exercises_models.ExerciseModel;
import com.khavronsky.unitedexercises.exercises_models.ModelOfExercisePerformance;
import com.khavronsky.unitedexercises.exercises_models.PowerExerciseModel;
import com.khavronsky.unitedexercises.import_from_grand_project.IDialogFragment;
import com.khavronsky.unitedexercises.import_from_grand_project.IntNumPickerFragment;
import com.khavronsky.unitedexercises.import_from_grand_project.TimePickerDialogFragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class PowerExPerformFragment extends Fragment implements IDialogFragment, TextWatcher {

    //region fields
    public final static String FRAGMENT_TAG = ExerciseModel.ExerciseType.POWER.getTag();

    private final static String DURATION = "duration";

    private final static String SETS = "sets";

    private final static String REPEATS = "repeats";

    private final static String WEIGHT = "weight";

    @BindView(R.id.ex_power_perform_start_time)
    EditText mStartTime;

    @BindView(R.id.ex_power_perform_duration)
    EditText mDuration;

    @BindView(R.id.ex_power_perform_sets)
    EditText mSets;

    @BindView(R.id.ex_power_perform_repeats)
    EditText mRepeats;

    @BindView(R.id.ex_power_perform_weight)
    EditText mWeight;

    @BindView(R.id.ex_power_perform_note)
    EditText mNote;

    private Calendar date = Calendar.getInstance();

    private IntNumPickerFragment mIntNumPickerDialog;

    private TimePickerDialogFragment mTimePickerDialog;

    private Unbinder unbinder;

    private String pickerOnScreen = "";

    private ModelOfExercisePerformance mModelOfExercisePerformance;
    //endregion

    //region creation&initialization
    public static PowerExPerformFragment newInstance(ModelOfExercisePerformance modelOfExercisePerformance) {
        Bundle args = new Bundle();
        args.putSerializable("model", modelOfExercisePerformance);
        PowerExPerformFragment fragment = new PowerExPerformFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        Log.d("KhSY_NewWaterMainFrg", "onCreateView: ");
        View v = inflater.inflate(R.layout.power_ex_perform_fragment, container, false);
        mModelOfExercisePerformance = (ModelOfExercisePerformance) getArguments().getSerializable("model");
        unbinder = ButterKnife.bind(this, v);
        init(v);
        return v;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
    //endregion

    //region IDialogFragment implementation
    @Override
    public void doButtonClick1(final Object o) {
        if (mTimePickerDialog != null) {
            date = (Calendar) o;
            mTimePickerDialog.dismiss();
            mTimePickerDialog = null;
            mModelOfExercisePerformance.setStartTime(date.getTimeInMillis());
            setDate();
        }
        if (mIntNumPickerDialog != null) {
            mIntNumPickerDialog.dismiss();
            switch (pickerOnScreen) {
                case DURATION:
                    mModelOfExercisePerformance.setDuration((int) o);
                    mDuration.setText(o + " мин");
                    break;
                case SETS:
                    ((PowerExerciseModel) mModelOfExercisePerformance.getExercise()).setSets((int) o);
                    mSets.setText(String.valueOf(o));
                    break;
                case REPEATS:
                    ((PowerExerciseModel) mModelOfExercisePerformance.getExercise()).setRepeats((int) o);
                    mRepeats.setText(String.valueOf(o));
                    break;
                case WEIGHT:
                    ((PowerExerciseModel) mModelOfExercisePerformance.getExercise()).setWeight((int) o);
                    mWeight.setText(String.valueOf(o));
                    break;
            }
            mIntNumPickerDialog = null;
        }
        pickerOnScreen = null;
    }

    @Override
    public void doButtonClick2() {
        mTimePickerDialog = null;
        mIntNumPickerDialog = null;
    }

    @Override
    public void doByDismissed() {
        mIntNumPickerDialog = null;
        mTimePickerDialog = null;
    }
    //endregion

    //region TextWatcher implementation
    @Override
    public void beforeTextChanged(final CharSequence s, final int start, final int count, final int after) {

    }

    @Override
    public void onTextChanged(final CharSequence s, final int start, final int before, final int count) {
        mModelOfExercisePerformance.setNote(String.valueOf(s));
    }

    @Override
    public void afterTextChanged(final Editable s) {
    }

    private void init(final View v) {
        setDate();
        mDuration.setText(String.valueOf(
                mModelOfExercisePerformance
                        .getDuration()));
        mSets.setText(String.valueOf(
                ((PowerExerciseModel) mModelOfExercisePerformance
                        .getExercise())
                        .getSets()));
        mRepeats.setText(String.valueOf(
                ((PowerExerciseModel) mModelOfExercisePerformance
                        .getExercise())
                        .getRepeats()));
        mWeight.setText(String.valueOf(
                ((PowerExerciseModel) mModelOfExercisePerformance
                        .getExercise())
                        .getWeight()));
        mNote.setText(mModelOfExercisePerformance.getNote());
        mNote.addTextChangedListener(this);
    }

    private void setDate() {
        String dateText = DateUtils.formatDateTime(getActivity().getApplicationContext(),
                mModelOfExercisePerformance.getStartTime(), DateUtils.FORMAT_SHOW_TIME);
        mStartTime.setText(dateText);
    }

    @OnClick({R.id.ex_power_perform_start_time, R.id.ex_power_perform_duration, R.id.ex_power_perform_sets, R.id
            .ex_power_perform_repeats, R.id.ex_power_perform_weight})
    void showPicker(EditText v) {
        int id = v.getId();
        switch (id) {
            case R.id.ex_power_perform_start_time:
                if (mTimePickerDialog == null) {
                    mTimePickerDialog = TimePickerDialogFragment
                            .newInstance(date.get(Calendar.HOUR_OF_DAY), date.get(Calendar.MINUTE));
                    mTimePickerDialog.setCallback(this);
                    mTimePickerDialog.show(getActivity().getSupportFragmentManager(), "time_picker_dialog");
                }
                break;
            case R.id.ex_power_perform_duration:
                pickerOnScreen = DURATION;
                showIntPicker(0, 1440, mModelOfExercisePerformance.getDuration(), 1);
                break;
            case R.id.ex_power_perform_sets:
                pickerOnScreen = SETS;
                showIntPicker(1, 20, ((PowerExerciseModel) (mModelOfExercisePerformance.getExercise())).getSets(),
                        1);
                break;
            case R.id.ex_power_perform_repeats:
                pickerOnScreen = REPEATS;
                showIntPicker(1, 50,
                        ((PowerExerciseModel) (mModelOfExercisePerformance.getExercise())).getRepeats(), 1);
                break;
            case R.id.ex_power_perform_weight:
                //поменять на floatPicker??????
                pickerOnScreen = WEIGHT;
                showIntPicker(1, 500,
                        ((PowerExerciseModel) (mModelOfExercisePerformance.getExercise())).getWeight(), 1);
                break;
        }
    }
    //endregion

    void showIntPicker(int min, int max, int currentVal, int onePointVal) {
        if (mIntNumPickerDialog == null) {
            mIntNumPickerDialog = IntNumPickerFragment.newInstance(min, max, currentVal, onePointVal);
            mIntNumPickerDialog.setCallback(this);
            mIntNumPickerDialog.show(getFragmentManager(), "picker");
        }
    }
}

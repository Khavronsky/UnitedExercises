package com.khavronsky.unitedexercises.presentation.exercise.exercise_performance.fragments;

import com.khavronsky.unitedexercises.R;
import com.khavronsky.unitedexercises.presentation.exercise.exercises_models.ExerciseModel;
import com.khavronsky.unitedexercises.presentation.exercise.exercises_models.ModelOfExercisePerformance;
import com.khavronsky.unitedexercises.presentation.exercise.exercises_models.PowerExerciseModel;
import com.khavronsky.unitedexercises.utils.import_from_grand_project.BaseDialogFragment;
import com.khavronsky.unitedexercises.utils.import_from_grand_project.IDialogFragment;
import com.khavronsky.unitedexercises.utils.import_from_grand_project.IntNumPickerFragment;
import com.khavronsky.unitedexercises.utils.import_from_grand_project.TimePickerDialogFragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateUtils;
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
    public final static String FRAGMENT_TAG = ExerciseModel.ExerciseType.POWER.name();

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

    private BaseDialogFragment mDialog;

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
        View v = inflater.inflate(R.layout.power_ex_perform_fragment, container, false);
        mModelOfExercisePerformance = (ModelOfExercisePerformance) getArguments().getSerializable("model");
        unbinder = ButterKnife.bind(this, v);
        init(v);
        return v;
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
    //endregion

    //region IDialogFragment
    @OnClick({R.id.ex_power_perform_start_time, R.id.ex_power_perform_duration, R.id.ex_power_perform_sets, R.id
            .ex_power_perform_repeats, R.id.ex_power_perform_weight})
    void showPicker(EditText v) {
        int id = v.getId();
        switch (id) {
            case R.id.ex_power_perform_start_time:
                if (mDialog == null) {
                    mDialog = TimePickerDialogFragment
                            .newInstance(date.get(Calendar.HOUR_OF_DAY), date.get(Calendar.MINUTE));
                    mDialog.setCallback(this);
                    mDialog.show(getActivity().getSupportFragmentManager(), "time_picker_dialog");
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
                //fixme поменять на floatPicker??????
                pickerOnScreen = WEIGHT;
                showIntPicker(1, 500,
                        ((PowerExerciseModel) (mModelOfExercisePerformance.getExercise())).getWeight(), 1);
                break;
        }
    }

    void showIntPicker(int min, int max, int currentVal, int onePointVal) {
        if (mDialog == null) {
            mDialog = IntNumPickerFragment.newInstance(min, max, currentVal, onePointVal);
            mDialog.setCallback(this);
            mDialog.show(getFragmentManager(), "picker");
        }
    }

    @Override
    public void doButtonClick1(final Object o) {
        if (mDialog instanceof TimePickerDialogFragment) {
            date = (Calendar) o;
            mDialog.dismiss();
            mDialog = null;
            mModelOfExercisePerformance.setStartTime(date.getTimeInMillis());
            setDate();
        }
        if (mDialog instanceof IntNumPickerFragment) {
            mDialog.dismiss();
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
            mDialog = null;
        }
        pickerOnScreen = null;
    }

    @Override
    public void doButtonClick2() {
        mDialog = null;
    }
    @Override
    public void doByDismissed() {
        mDialog = null;
    }
    //endregion


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}

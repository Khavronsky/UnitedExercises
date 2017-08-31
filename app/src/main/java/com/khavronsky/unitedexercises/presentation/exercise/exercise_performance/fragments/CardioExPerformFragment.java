package com.khavronsky.unitedexercises.presentation.exercise.exercise_performance.fragments;

import com.khavronsky.unitedexercises.R;
import com.khavronsky.unitedexercises.presentation.exercise.exercises_models.CardioExerciseModel;
import com.khavronsky.unitedexercises.presentation.exercise.exercises_models.ExerciseModel;
import com.khavronsky.unitedexercises.presentation.exercise.exercises_models.ModelOfExercisePerformance;
import com.khavronsky.unitedexercises.utils.import_from_grand_project.BaseDialogFragment;
import com.khavronsky.unitedexercises.utils.import_from_grand_project.IDialogFragment;
import com.khavronsky.unitedexercises.utils.import_from_grand_project.IntNumPickerFragment;
import com.khavronsky.unitedexercises.utils.import_from_grand_project.TimePickerDialogFragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static android.view.View.GONE;
import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;


public class CardioExPerformFragment extends Fragment implements IDialogFragment, TextWatcher {

    //region fields

    public final static String FRAGMENT_TAG = ExerciseModel.ExerciseType.CARDIO.name();

    public static final int LETTERS_LIMIT = 10;


    @BindView(R.id.ex_cardio_perform_start_time)
    EditText mExCardioPerformStartTime;

    @BindView(R.id.ex_cardio_perform_duration)
    EditText mExCardioPerformDuration;

    @BindView(R.id.ex_cardio_Intensity_type)
    Spinner mCountCalMethod;

    @BindView(R.id.ex_cardio_perform_note)
    EditText mExCardioPerformNote;

    @BindView(R.id.ex_cardio_perform_note_layout)
    View layout;

    @BindView(R.id.ex_cardio_perform_intensity_area)
    View intensityArea;

    @BindView(R.id.cardio_ex_perform_letters_counter)
    View lettersCounter;

    @BindView(R.id.cardio_ex_number_of_letters)
    TextView numberOfLettersTV;

    @BindView(R.id.cardio_ex_letters_limit)
    TextView lettersLimitTV;

    private int numberOfLetters;

    private Calendar date = Calendar.getInstance();

    private BaseDialogFragment mDialog;

    private Unbinder unbinder;

    private ModelOfExercisePerformance mModelOfExercisePerformance;

    private IExerciseListener mListener;

    //endregion

    public static CardioExPerformFragment newInstance(ModelOfExercisePerformance modelOfExercisePerformance) {

        Bundle args = new Bundle();
        args.putSerializable("model", modelOfExercisePerformance);
        CardioExPerformFragment fragment = new CardioExPerformFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public void setListener(final IExerciseListener listener) {
        mListener = listener;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.cardio_ex_perform_fragment, container, false);
        mModelOfExercisePerformance = (ModelOfExercisePerformance) getArguments().getSerializable("model");
        unbinder = ButterKnife.bind(this, v);
        init(v);
        return v;
    }

    private void init(final View v) {
        setDate();
        mExCardioPerformDuration.setText(String.valueOf(mModelOfExercisePerformance.getDuration()));
        if (((CardioExerciseModel) mModelOfExercisePerformance.getExercise()).getIntensityType()
                == CardioExerciseModel.TYPE_SPECIFY) {
            setSpinners();
        } else {
            intensityArea.setVisibility(GONE);
            mModelOfExercisePerformance.setCurrentKcalPerHour(((CardioExerciseModel)
                    mModelOfExercisePerformance.getExercise()).getDefValue());

            if (mListener != null) {
                mListener.updateModel(mModelOfExercisePerformance);
            }
        }
        InputFilter[] filterArray = new InputFilter[1];
        filterArray[0] = new InputFilter.LengthFilter(LETTERS_LIMIT);
        mExCardioPerformNote.setText(mModelOfExercisePerformance.getNote());
        mExCardioPerformNote.addTextChangedListener(this);
        mExCardioPerformNote.setFilters(filterArray);
        lettersLimitTV.setText(String.valueOf(LETTERS_LIMIT));
//        layout.setFocusableInTouchMode(true);

        mExCardioPerformNote.setOnFocusChangeListener((v1, hasFocus) -> {
            int visibility = INVISIBLE;
            if (hasFocus) {
                visibility = VISIBLE;
            }
            lettersCounter.setVisibility(visibility);
        });
    }

    @Override
    public void beforeTextChanged(final CharSequence s, final int start, final int count, final int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, final int start, final int before, final int count) {
        numberOfLetters = s.length();
//        numberOfLetters = count;
        mModelOfExercisePerformance.setNote(String.valueOf(s));
        numberOfLettersTV.setText(String.valueOf(numberOfLetters));

    }

    @Override
    public void afterTextChanged(final Editable s) {
//        if (s.length() > LETTERS_LIMIT) {
//
//            s.delete(s.length() - 1, s.length());
//        }
    }

    @OnClick({R.id.ex_cardio_perform_start_time, R.id.ex_cardio_perform_duration})
    void showPickers(EditText eText) {
        int id = eText.getId();
        switch (id) {
            case R.id.ex_cardio_perform_start_time:
                if (mDialog == null) {
                    mDialog = TimePickerDialogFragment
                            .newInstance(date.get(Calendar.HOUR_OF_DAY), date.get(Calendar.MINUTE));
                    mDialog.setCallback(this);
                    mDialog.show(getActivity().getSupportFragmentManager(), "time_picker_dialog");
                }
                //TODO

                break;
            case R.id.ex_cardio_perform_duration:
//                if (mDialog == null) {
//                    mDialog = IntNumPickerFragment
//                            .newInstance(0, 1440, mModelOfExercisePerformance.getDuration(), 1);
//                    mDialog.setCallback(this);
//                    mDialog.show(getActivity().getSupportFragmentManager(), "int_picker_dialog");
//                }
                break;
        }
    }

    @Override
    public void doButtonClick1(final Object o) {
        if (mDialog instanceof TimePickerDialogFragment) {
            date = (Calendar) o;
            mDialog.dismiss();
            mDialog = null;
            setDate();
        }
        if (mDialog instanceof IntNumPickerFragment) {
            mModelOfExercisePerformance.setDuration((int) o);
            mDialog.dismiss();
            mDialog = null;
            mExCardioPerformDuration.setText(o + " мин");
        }
        mListener.updateModel(mModelOfExercisePerformance);
    }

    @Override
    public void doButtonClick2() {
        mDialog = null;
    }

    @Override
    public void doByDismissed() {
        mDialog = null;
    }

    private void setDate() {
        String dateText = DateUtils.formatDateTime(getActivity().getApplicationContext(),
                date.getTimeInMillis(), DateUtils.FORMAT_SHOW_TIME);
        mExCardioPerformStartTime.setText(dateText);
    }

    private void setSpinners() {
        ArrayAdapter<?> adapter = ArrayAdapter
                .createFromResource(getContext(), R.array.intensity_types, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mCountCalMethod.setAdapter(adapter);
        mCountCalMethod.setSelection(
                getSpinnerSelection((CardioExerciseModel) mModelOfExercisePerformance.getExercise()));
        mCountCalMethod.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(final AdapterView<?> parent, final View view, final int position,
                    final long id) {
                switch (position) {
                    case 0:
                        mModelOfExercisePerformance.setCurrentKcalPerHour(((CardioExerciseModel)
                                mModelOfExercisePerformance.getExercise()).getLow());
                        break;
                    case 1:
                        mModelOfExercisePerformance.setCurrentKcalPerHour(((CardioExerciseModel)
                                mModelOfExercisePerformance.getExercise()).getMiddle());
                        break;
                    case 2:
                        mModelOfExercisePerformance.setCurrentKcalPerHour(((CardioExerciseModel)
                                mModelOfExercisePerformance.getExercise()).getHigh());
                        break;
                }
                mListener.updateModel(mModelOfExercisePerformance);
            }

            @Override
            public void onNothingSelected(final AdapterView<?> parent) {
            }
        });
    }

    private int getSpinnerSelection(final CardioExerciseModel exercise) {
        if (exercise.getMiddle() == mModelOfExercisePerformance.getCurrentKcalPerHour()) {
            return 1;
        }
        if (exercise.getHigh() == mModelOfExercisePerformance.getCurrentKcalPerHour()) {
            return 2;
        }
        return 0;
    }

    public interface IExerciseListener {

        void updateModel(ModelOfExercisePerformance modelOfExercisePerformance);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}

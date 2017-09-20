package com.khavronsky.unitedexercises.presentation.exercise.exercise_performance.fragments;

import com.khavronsky.unitedexercises.R;
import com.khavronsky.unitedexercises.presentation.exercise.exercise_performance.PowerExSetsRVAdapter;
import com.khavronsky.unitedexercises.presentation.exercise.exercise_performance.dialogs.AddApproachDialog;
import com.khavronsky.unitedexercises.presentation.exercise.exercises_models.ExerciseModel;
import com.khavronsky.unitedexercises.presentation.exercise.exercises_models.ModelOfExercisePerformance;
import com.khavronsky.unitedexercises.utils.TextWatcherWithPostfix;
import com.khavronsky.unitedexercises.utils.import_from_grand_project.BaseDialogFragment;
import com.khavronsky.unitedexercises.utils.import_from_grand_project.IDialogFragment;
import com.khavronsky.unitedexercises.utils.import_from_grand_project.ItemClickSupport;
import com.khavronsky.unitedexercises.utils.import_from_grand_project.TimePickerDialogFragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class PowerExPerformFragment extends Fragment implements IDialogFragment, TextWatcher,
        View.OnClickListener {

    //region fields
    public final static String FRAGMENT_TAG = ExerciseModel.ExerciseType.POWER.name();

    private final static String POSTFIX_FOR_DURATION_FIELD = " мин";

    @BindView(R.id.ex_power_perform_start_time)
    EditText mStartTime;

    @BindView(R.id.ex_power_perform_duration)
    EditText mDuration;

    @BindView(R.id.ex_power_perform_note)
    EditText mNote;

    @BindView(R.id.ex_power_perform_frg)
    View rootLayout;

    @BindView(R.id.ex_power_perform_add_approach_btn)
    TextView addApproachBtn;

    @BindView(R.id.ex_power_perform_sets_list)
    RecyclerView mRecyclerView;

    private Calendar date = Calendar.getInstance();

    private BaseDialogFragment mDialog;

    private Unbinder unbinder;

    private ModelOfExercisePerformance mModelOfExercisePerformance;

    private PowerExSetsRVAdapter mRVAdapter;

    private AddApproachDialog approachDialog;

    private AddApproachDialog.IApproachAdded mApproachAddedListener;

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
        View v = inflater.inflate(R.layout.ex_power_perform_fragment, container, false);
        mModelOfExercisePerformance = (ModelOfExercisePerformance) getArguments().getSerializable("model");
        unbinder = ButterKnife.bind(this, v);
        init(v);
        return v;
    }

    @Override
    public void onClick(final View v) {
        showAddApproachDlg(true, mModelOfExercisePerformance.getApproachList().size(), 0, 0);
    }

    private void init(final View v) {
        setDate();
        mDuration.setText(String.valueOf(mModelOfExercisePerformance.getDuration()) + POSTFIX_FOR_DURATION_FIELD);
        TextWatcherWithPostfix textWatcherWithPostfix = new TextWatcherWithPostfix(" мин", mDuration);
        textWatcherWithPostfix.setListener(new TextWatcherWithPostfix.ITextWatcherListener() {
            @Override
            public void valueChanged(final int value) {
                mModelOfExercisePerformance.setDuration(value);
            }

            @Override
            public void textChanged(final String textWithPostfix) {
                mDuration.setText(textWithPostfix);
            }
        });
        addApproachBtn.setOnClickListener(this);
        mDuration.addTextChangedListener(textWatcherWithPostfix);
        mNote.setText(mModelOfExercisePerformance.getNote());
        mNote.addTextChangedListener(this);
        rootLayout.setFocusableInTouchMode(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(layoutManager);
        mRVAdapter = new PowerExSetsRVAdapter();
        mRVAdapter.setExercise(mModelOfExercisePerformance);
        mRecyclerView.setAdapter(mRVAdapter);

        ItemClickSupport.addTo(mRecyclerView).setOnItemLongClickListener(
                (recyclerView, position, v1) -> {
                    showAddApproachDlg(false, position,
                            mModelOfExercisePerformance
                                    .getApproach(position)
                                    .getRepeats(),
                            mModelOfExercisePerformance
                                    .getApproach(position)
                                    .getWeight());
                    return true;
                });
    }

    void showAddApproachDlg(boolean newApproach, int index, int repeats, int weight) {
        if (approachDialog == null) {
            approachDialog = AddApproachDialog.newInstance(newApproach, index, repeats, weight);
            approachDialog.setListener(new AddApproachDialog.IApproachAdded() {
                @Override
                public void deleteEvent(int index) {
                    if (index < mModelOfExercisePerformance.getApproachList
                            ().size()) {
                        mModelOfExercisePerformance.delApproach(index);
                    }
                    approachDialog = null;
                    mRVAdapter.notifyDataSetChanged();
                    ((CardioExPerformFragment.IExerciseListener)getActivity()).updateModel(mModelOfExercisePerformance);
                }

                @Override
                public void saveEvent(boolean newApproach, int index, int repeats, int weight) {
                    if (newApproach) {
                        mModelOfExercisePerformance.addApproach(0, 0);
                    }
                    Log.d("WTF", "saveEvent: ");
                    mModelOfExercisePerformance.getApproach(index)
                            .setRepeats(repeats);
                    mModelOfExercisePerformance.getApproach(index)
                            .setWeight(weight);
                    approachDialog = null;
                    mRVAdapter.notifyDataSetChanged();
                    ((CardioExPerformFragment.IExerciseListener)getActivity()).updateModel(mModelOfExercisePerformance);
                }

                @Override
                public void dismiss() {
                    approachDialog = null;
                }
            });
            approachDialog.show(getFragmentManager(), "ApproachDlg");
        }
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
    @OnClick({R.id.ex_power_perform_start_time, R.id.ex_power_perform_duration})
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
                break;
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

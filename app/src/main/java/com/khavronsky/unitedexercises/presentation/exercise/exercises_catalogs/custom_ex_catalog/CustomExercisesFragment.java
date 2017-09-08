package com.khavronsky.unitedexercises.presentation.exercise.exercises_catalogs.custom_ex_catalog;


import com.khavronsky.unitedexercises.R;
import com.khavronsky.unitedexercises.presentation.exercise.create_new_exercises.new_cardio_exercise.CardioExerciseEditorActivity;
import com.khavronsky.unitedexercises.presentation.exercise.create_new_exercises.new_power_exercise.PowerExerciseEditorActivity;
import com.khavronsky.unitedexercises.presentation.exercise.exercise_performance.ExercisePerformActivity;
import com.khavronsky.unitedexercises.presentation.exercise.exercises_catalogs.IRefreshableFragment;
import com.khavronsky.unitedexercises.presentation.exercise.exercises_models.ExerciseModel;
import com.khavronsky.unitedexercises.presentation.exercise.exercises_models.ExerciseModel.ExerciseType;
import com.khavronsky.unitedexercises.presentation.exercise.exercises_models.IEditCatalog;
import com.khavronsky.unitedexercises.presentation.exercise.exercises_models.IModel;
import com.khavronsky.unitedexercises.utils.di.App;
import com.khavronsky.unitedexercises.utils.import_from_grand_project.IDialogFragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static android.app.Activity.RESULT_OK;


public class CustomExercisesFragment extends Fragment implements IView, IEditCatalog, IRefreshableFragment {

    //region F I E L D S
    @BindView(R.id.custom_exercise_create_btn)
    TextView createBtn;

    @BindView(R.id.custom_exercise_not_found)
    TextView emptyCustExList;

    @Inject
    CustomExPresenter mCustomExPresenter;

    private static ExerciseType currentType;

    private RecyclerView recyclerView;

    private AdapterToCustomExerciseRecycler adapterToCustomExerciseRecycler;

    private Unbinder unbinder;

    private AddExerciseToExecuteDialog mExerciseToExecuteDialog;
    //endregion

    //region C R E A T E
    @Override
    public void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         App.getComponent().getCustomExPresenter(this);

        mCustomExPresenter.attachView(this);
        if (getArguments().getSerializable("type") != null) {
            currentType = (ExerciseType) getArguments().getSerializable("type");
        }
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container,
            @Nullable final Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.custom_exercises_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);
        recyclerView = (RecyclerView) view.findViewById(R.id.cardio_ex_custom_list);
        emptyCustExList.setVisibility(View.GONE);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        adapterToCustomExerciseRecycler = new AdapterToCustomExerciseRecycler(getFragmentManager());
        adapterToCustomExerciseRecycler.setEditor(this);
        recyclerView.setVisibility(View.VISIBLE);
        recyclerView.setLayoutManager(layoutManager);
        mCustomExPresenter.loadData(currentType);
        return view;
    }

    @Override
    public void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            mCustomExPresenter.loadData(currentType);
            adapterToCustomExerciseRecycler.notifyDataSetChanged();

            if (mExerciseToExecuteDialog == null) {
                mExerciseToExecuteDialog = new AddExerciseToExecuteDialog();
                mExerciseToExecuteDialog.setCallback(new IDialogFragment() {
                    @Override
                    public void doButtonClick1(final Object o) {

                        mExerciseToExecuteDialog =null;
                        Intent intent = new Intent(getContext(), ExercisePerformActivity.class);
                        intent.putExtra(ExercisePerformActivity.NEW_PERFORMANCE, true);
                        intent.putExtra(ExercisePerformActivity.MODEL_OF_EXERCISE, data.getSerializableExtra
                                (ExercisePerformActivity.MODEL_OF_EXERCISE));
                        startActivity(intent);
                    }

                    @Override
                    public void doButtonClick2() {
                        mExerciseToExecuteDialog =null;
                    }

                    @Override
                    public void doByDismissed() {
                        mExerciseToExecuteDialog =null;
                    }
                });
                mExerciseToExecuteDialog.show(getActivity().getSupportFragmentManager(), "Add_exe_to_exe_dialog");
            }
        }
    }
    //endregion

    @Override
    public void show(final List<ExerciseModel> exModelList) {
        adapterToCustomExerciseRecycler.setModelList(exModelList);
        recyclerView.setAdapter(adapterToCustomExerciseRecycler);
        adapterToCustomExerciseRecycler.notifyDataSetChanged();
    }

    @Override
    public void refresh(ExerciseType type) {
        currentType = type;
        mCustomExPresenter.loadData(currentType);

    }

    @Override
    public void onResume() {
        super.onResume();
        mCustomExPresenter.loadData(currentType);
    }

    @Override
    public IModel editElements(final IModel elements) {
        Intent intent;
        if (((ExerciseModel) elements).getType() == ExerciseType.CARDIO) {
            intent = new Intent(getContext(), CardioExerciseEditorActivity.class);
        } else {
            intent = new Intent(getContext(), PowerExerciseEditorActivity.class);
        }
        intent.putExtra(((ExerciseModel) elements).getType().name(), elements);
        startActivityForResult(intent, 0);
        return null;
    }

    @Override
    public void delElements(final long id) {
        mCustomExPresenter.delCustomExercise(id);
    }

    @OnClick(R.id.custom_exercise_create_btn)
    void createNewEx() {
        Intent intent;
        if (currentType == ExerciseType.CARDIO) {
            intent = new Intent(getActivity(), CardioExerciseEditorActivity.class);
        } else {
            intent = new Intent(getActivity(), PowerExerciseEditorActivity.class);
        }
        startActivityForResult(intent, 0);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        mCustomExPresenter.detachView();
    }
}

package com.khavronsky.unitedexercises.presentation.exercise.exercises_catalogs.custom_ex_catalog;


import com.khavronsky.unitedexercises.R;
import com.khavronsky.unitedexercises.presentation.exercise.create_new_exercises.new_cardio_exercise.CardioExerciseEditorActivity;
import com.khavronsky.unitedexercises.presentation.exercise.create_new_exercises.new_power_exercise.PowerExerciseEditorActivity;
import com.khavronsky.unitedexercises.presentation.exercise.exercises_models.ExerciseModel;
import com.khavronsky.unitedexercises.presentation.exercise.exercises_models.ExerciseModel.ExerciseType;
import com.khavronsky.unitedexercises.presentation.exercise.exercises_models.IEditCatalog;
import com.khavronsky.unitedexercises.presentation.exercise.exercises_models.IModel;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.app.Activity.RESULT_OK;


public class CustomExercisesFragment extends Fragment implements IView, IEditCatalog {

    //region F I E L D S
    @BindView(R.id.custom_exercise_create_btn)
    TextView createBtn;

    @BindView(R.id.custom_exercise_not_found)
    TextView emptyCustExList;

    private CustomExPresenter mCustomExPresenter;

    private static ExerciseType currentType;

    private RecyclerView recyclerView;

    AdapterToCustomExerciseRecycler adapterToCustomExerciseRecycler;
    //endregion


    //region C R E A T E
    @Override
    public void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("KhS", "onCreate: CustomExercisesFragment");
        if (mCustomExPresenter == null) {
            mCustomExPresenter = new CustomExPresenter();
        }
        mCustomExPresenter.attachView(this);
        if (getArguments().getSerializable("type") != null) {
            currentType = (ExerciseType) getArguments().getSerializable("type");
            Log.d("qwert", "CustomExercisesFragment " + currentType.getTag());
        }
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container,
            @Nullable final Bundle savedInstanceState) {
        Log.d("KhS", "onCreateView: CustomExercisesFragment");
        View view = inflater.inflate(R.layout.custom_exercises_fragment, container, false);
        ButterKnife.bind(this, view);
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
            Log.d("OAR", "onActivityResult: ");
            mCustomExPresenter.loadData(currentType);
            adapterToCustomExerciseRecycler.notifyDataSetChanged();
//            show((List<ExerciseModel>) data.getExtras().getSerializable("new_model"));
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
    public IModel editElements(final IModel elements) {
        Log.d("KhSS", "editElements: " + elements.getId());
        Intent intent;
        if (((ExerciseModel) elements).getType() == ExerciseType.CARDIO) {
            intent = new Intent(getContext(), CardioExerciseEditorActivity.class);
        } else {
            intent = new Intent(getContext(), PowerExerciseEditorActivity.class);
        }
        intent.putExtra(((ExerciseModel) elements).getType().getTag(), elements);
        startActivityForResult(intent, 0);
        return null;
    }

    @Override
    public void delElements(final long id) {
        Log.d("KhSS", "delElements: " + id);
        mCustomExPresenter.delCustomExercise(id);
    }

    @OnClick(R.id.custom_exercise_create_btn)
    void createNewEx() {
        Toast.makeText(getContext(), "CREATE NEW EXERCISE", Toast.LENGTH_SHORT).show();
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
        mCustomExPresenter.detachView();
    }
}

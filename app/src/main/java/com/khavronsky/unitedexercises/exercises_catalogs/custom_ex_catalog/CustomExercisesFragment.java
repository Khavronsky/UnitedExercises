package com.khavronsky.unitedexercises.exercises_catalogs.custom_ex_catalog;


import com.khavronsky.unitedexercises.R;
import com.khavronsky.unitedexercises.create_new_exercises.new_cardio_exercise.CardioExerciseEditorActivity;
import com.khavronsky.unitedexercises.exercises_models.ExerciseModel;
import com.khavronsky.unitedexercises.exercises_models.ExerciseModel.ExerciseType;

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

import static com.khavronsky.unitedexercises.exercises_models.ExerciseModel.ExerciseType.CARDIO;


public class CustomExercisesFragment extends Fragment implements CustomExPresenter.IView {

    //region F I E L D S
    @BindView(R.id.custom_exercise_create_btn)
    TextView createBtn;

    @BindView(R.id.custom_exercise_not_found)
    TextView emptyCustExList;

    private CustomExPresenter mCustomExPresenter;

    private ExerciseType currentType = CARDIO;

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
        recyclerView.setVisibility(View.VISIBLE);
        recyclerView.setLayoutManager(layoutManager);
        mCustomExPresenter.loadData(currentType);

        return view;
    }
    //endregion

    @Override
    public void show(final List<ExerciseModel> exModelList) {
        adapterToCustomExerciseRecycler.setModelList(exModelList);
        recyclerView.setAdapter(adapterToCustomExerciseRecycler);
        adapterToCustomExerciseRecycler.notifyDataSetChanged();
    }

    @OnClick(R.id.custom_exercise_create_btn)
    void showToast() {
        Toast.makeText(getContext(), "CREATE NEW EXERCISE", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(getActivity(), CardioExerciseEditorActivity.class));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mCustomExPresenter.detachView();
    }
}

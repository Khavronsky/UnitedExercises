package com.khavronsky.unitedexercises.presentation.exercise.exercises_catalogs.recent_ex_catalog;

import com.khavronsky.unitedexercises.R;
import com.khavronsky.unitedexercises.presentation.exercise.exercises_catalogs.IRefreshableFragment;
import com.khavronsky.unitedexercises.presentation.exercise.exercises_models.ModelOfExercisePerformance;

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

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.khavronsky.unitedexercises.presentation.exercise.exercises_models.ExerciseModel.ExerciseType;

public class RecentExercisesFragment extends Fragment implements IView, IRefreshableFragment {

    @BindView(R.id.recent_exercise_not_found)
    TextView emptyCustExList;

    private RecentExPresenter mRecentExPresenter;

    private static ExerciseType currentType;

    private RecyclerView recyclerView;

    AdapterToRecentExerciseRecycler adapterToCustomExerciseRecycler;

    @Override
    public void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (mRecentExPresenter == null) {
            mRecentExPresenter = new RecentExPresenter();
        }
        mRecentExPresenter.attachView(this);
        if (getArguments().getSerializable("type") != null) {
            currentType = (ExerciseType) getArguments().getSerializable("type");
        }
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container,
            @Nullable final Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recent_exercises_fragment, container, false);
        ButterKnife.bind(this, view);
        recyclerView = (RecyclerView) view.findViewById(R.id.recent_exercise_custom_list);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());

        recyclerView.setLayoutManager(layoutManager);
        adapterToCustomExerciseRecycler = new AdapterToRecentExerciseRecycler
                (getFragmentManager());
        recyclerView.setAdapter(adapterToCustomExerciseRecycler);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        mRecentExPresenter.loadData(currentType);
    }

    @Override
    public void show(final List<ModelOfExercisePerformance> exModelList) {
        if (exModelList.size() == 0) {
            recyclerView.setVisibility(View.GONE);
            emptyCustExList.setVisibility(View.VISIBLE);
        } else {
            emptyCustExList.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }
        adapterToCustomExerciseRecycler.setCustomExList(exModelList)
                .notifyDataSetChanged();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mRecentExPresenter.detachView();
    }

    @Override
    public void refresh(final ExerciseType type) {
        currentType = type;
        mRecentExPresenter.loadData(currentType);
    }
}

package com.khavronsky.unitedexercises.exercises_catalogs.ex_cat_fragments;

import com.khavronsky.unitedexercises.R;
import com.khavronsky.unitedexercises.exercises_catalogs.ex_cat_adapters.AdapterToRecentExerciseRecycler;
import com.khavronsky.unitedexercises.exercises_catalogs.ex_cat_presenters.RecentExPresenter;
import com.khavronsky.unitedexercises.exercises_models.CustomExModel;

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

public class RecentExercisesFragment extends Fragment implements RecentExPresenter.IView {

    @BindView(R.id.recent_exercise_not_found)
    TextView emptyCustExList;

    private RecentExPresenter mRecentExPresenter;

    private RecyclerView recyclerView;

    @Override
    public void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (mRecentExPresenter == null) {
            mRecentExPresenter = new RecentExPresenter();
        }
        mRecentExPresenter.attachView(this);

    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container,
            @Nullable final Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recent_exercises_fragment, container, false);
        ButterKnife.bind(this, view);
        recyclerView = (RecyclerView) view.findViewById(R.id.recent_exercise_custom_list);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
//        emptyCustExList.setVisibility(View.GONE);
//        recyclerView.setVisibility(View.VISIBLE);
        recyclerView.setLayoutManager(layoutManager);
        mRecentExPresenter.loadData();
        return view;
    }

    @Override
    public void show(final List<CustomExModel> exModelList) {
        AdapterToRecentExerciseRecycler adapterToCustomExerciseRecycler = new AdapterToRecentExerciseRecycler
                (exModelList, getFragmentManager());
        recyclerView.setAdapter(adapterToCustomExerciseRecycler);
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        mRecentExPresenter.detachView();
    }
}

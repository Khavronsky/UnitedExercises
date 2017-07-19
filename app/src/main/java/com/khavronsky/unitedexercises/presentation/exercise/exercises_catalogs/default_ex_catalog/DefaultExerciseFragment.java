package com.khavronsky.unitedexercises.presentation.exercise.exercises_catalogs.default_ex_catalog;

import com.khavronsky.unitedexercises.R;
import com.khavronsky.unitedexercises.presentation.exercise.exercises_catalogs.IRefreshableFragment;
import com.khavronsky.unitedexercises.presentation.exercise.exercises_models.ExerciseModel.ExerciseType;
import com.khavronsky.unitedexercises.utils.di.App;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import javax.inject.Inject;

public class DefaultExerciseFragment extends Fragment implements IView, IRefreshableFragment {

    @Inject
    DefaultExPresenter mDefaultExPresenter;

    private RecyclerView recyclerView;

    private DefaultCatalogRVAdapter mDefaultCatalogRVAdapter;

    private ExerciseType currentType;


    @Override
    public void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.getComponent().getDefaultExPresenter(this);
        mDefaultExPresenter.attachView(this);
        if (getArguments().getSerializable("type") != null) {
            currentType = (ExerciseType) getArguments().getSerializable("type");
        }
    }

    @Override
    public void refresh(ExerciseType type) {
        currentType = type;
        mDefaultExPresenter.loadData(currentType);
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container,
            @Nullable final Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.exercise_catalog_fragment, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.exercise_catalog_recycler);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        mDefaultCatalogRVAdapter = new DefaultCatalogRVAdapter();
        recyclerView.setLayoutManager(layoutManager);
        mDefaultExPresenter.loadData(currentType);
        return view;
    }

    @Override
    public void show(final ArrayList<DefaultCatalogModel> exList) {
        mDefaultCatalogRVAdapter.setExerciseCatalog(exList);
        recyclerView.setAdapter(mDefaultCatalogRVAdapter);
        mDefaultCatalogRVAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mDefaultExPresenter.detachView();
    }
}

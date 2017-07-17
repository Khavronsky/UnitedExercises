package com.khavronsky.unitedexercises.presentation.exercise.exercises_catalogs.default_ex_catalog;

import com.khavronsky.unitedexercises.R;
import com.khavronsky.unitedexercises.presentation.exercise.exercises_catalogs.IRefreshableFragment;
import com.khavronsky.unitedexercises.presentation.exercise.exercises_models.ExerciseModel.ExerciseType;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class ExerciseCatalogFragment extends Fragment implements IView, IRefreshableFragment {

    private DefaultExPresenter presenter;

    private RecyclerView recyclerView;

    private AdapterToExCatalogRecycler adapterToExCatalogRecycler;

    private ExerciseType currentType;


    @Override
    public void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (presenter == null) {
            presenter = new DefaultExPresenter();
        }
        presenter.attachView(this);
        if (getArguments().getSerializable("type") != null) {
            currentType = (ExerciseType) getArguments().getSerializable("type");
        }
    }

    @Override
    public void refresh(ExerciseType type) {
        currentType = type;
        presenter.loadData(currentType);
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container,
            @Nullable final Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.exercise_catalog_fragment, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.exercise_catalog_recycler);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        adapterToExCatalogRecycler = new AdapterToExCatalogRecycler();
        recyclerView.setLayoutManager(layoutManager);
        presenter.loadData(currentType);
        return view;
    }

    @Override
    public void show(final ArrayList<ModelOfItemForExCatalog> exList) {
        adapterToExCatalogRecycler.setExerciseCatalog(exList);
        recyclerView.setAdapter(adapterToExCatalogRecycler);
        adapterToExCatalogRecycler.notifyDataSetChanged();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }
}

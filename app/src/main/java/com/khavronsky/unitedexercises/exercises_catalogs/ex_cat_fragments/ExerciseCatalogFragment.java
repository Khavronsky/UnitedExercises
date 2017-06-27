package com.khavronsky.unitedexercises.exercises_catalogs.ex_cat_fragments;

import com.khavronsky.unitedexercises.R;
import com.khavronsky.unitedexercises.exercises_catalogs.ex_cat_adapters.AdapterToExCatalogRecycler;
import com.khavronsky.unitedexercises.exercises_catalogs.ex_cat_presenters.DefaultExPresenter;
import com.khavronsky.unitedexercises.exercises_models.ExerciseModel;
import com.khavronsky.unitedexercises.exercises_models.ModelOfItemForExCatalog;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.khavronsky.unitedexercises.exercises_models.ExerciseModel.ExerciseType.CARDIO;
import static com.khavronsky.unitedexercises.exercises_models.ModelOfItemForExCatalog.ItemType.CAPITAL_LETTER;
import static com.khavronsky.unitedexercises.exercises_models.ModelOfItemForExCatalog.ItemType.EXERCISE_TITLE;

public class ExerciseCatalogFragment extends Fragment implements DefaultExPresenter.IView {

    private DefaultExPresenter presenter;

    private RecyclerView recyclerView;

    private AdapterToExCatalogRecycler adapterToExCatalogRecycler;

    private ExerciseModel.ExerciseType currentType = CARDIO;


    @Override
    public void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("KhS", "onCreate: ExerciseCatalogFragment");
        if (presenter == null) {
            presenter = new DefaultExPresenter();
        }
        presenter.attachView(this);
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container,
            @Nullable final Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.exercise_catalog_fragment, container, false);
        Log.d("KhS", "onCreateView: ExerciseCatalogFragment");
        recyclerView = (RecyclerView) view.findViewById(R.id.exercise_catalog_recycler);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        adapterToExCatalogRecycler = new AdapterToExCatalogRecycler();
        recyclerView.setLayoutManager(layoutManager);
        presenter.loadData(currentType);
        return view;
    }

    @Override
    public void show(final ArrayList<String> exList) {
        adapterToExCatalogRecycler.setExerciseCatalog(convertToExCatModel(exList));
        recyclerView.setAdapter(adapterToExCatalogRecycler);
        adapterToExCatalogRecycler.notifyDataSetChanged();
    }

    List<ModelOfItemForExCatalog> convertToExCatModel(ArrayList<String> exList) {
        List<ModelOfItemForExCatalog> list = new ArrayList<>();
        Collections.sort(exList);
        String x = "";
        for (String s :
                exList) {
            if (!x.equals(s.substring(0, 1))) {
                x = s.substring(0, 1);
                list.add(new ModelOfItemForExCatalog(x, CAPITAL_LETTER));
            }
            list.add(new ModelOfItemForExCatalog(s, EXERCISE_TITLE));
        }
        return list;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }
}

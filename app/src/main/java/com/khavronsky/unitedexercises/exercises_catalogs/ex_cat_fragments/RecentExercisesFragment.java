package com.khavronsky.unitedexercises.exercises_catalogs.ex_cat_fragments;

import com.khavronsky.unitedexercises.R;
import com.khavronsky.unitedexercises.exercises_catalogs.ex_cat_adapters.AdapterToRecentExerciseRecycler;
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

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecentExercisesFragment extends Fragment {

    @BindView(R.id.recent_exercise_not_found)
    TextView emptyCustExList;



    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container,
            @Nullable final Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recent_exercises_fragment, container, false);
        ButterKnife.bind(this, view);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recent_exercise_custom_list);
        AdapterToRecentExerciseRecycler adapterToCustomExerciseRecycler = new AdapterToRecentExerciseRecycler
                (createCustomExecList(), getFragmentManager());
//        emptyCustExList.setVisibility(View.GONE);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
//        recyclerView.setVisibility(View.VISIBLE);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapterToCustomExerciseRecycler);
        return view;
    }

    private List<CustomExModel> createCustomExecList() {

        return new ArrayList<CustomExModel>() {
            {
                add(new CustomExModel() {
                    {
                        this.setExTitle("SuperExercise")
                                .setExSubTitle("SuperSubtitle")
                                .setActive(true)
                                .setId(1);
                    }
                });

                add(new CustomExModel() {
                    {
                        this.setExTitle("PuperExercise")
                                .setExSubTitle("PuperSubtitle")
                                .setActive(true)
                                .setId(2);
                    }
                });
            }
        };
    }
}

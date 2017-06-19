package com.khavronsky.unitedexercises.exercises_catalogs.ex_cat_fragments;


import com.khavronsky.unitedexercises.R;
import com.khavronsky.unitedexercises.exercises_catalogs.ex_cat_adapters.AdapterToCustomExerciseRecycler;
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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;



public class CustomExercisesFragment extends Fragment {

    @BindView(R.id.custom_exercise_create_btn)
    TextView createBtn;

    @BindView(R.id.custom_exercise_not_found)
    TextView emptyCustExList;

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container,
            @Nullable final Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.custom_exercises_fragment, container, false);
        ButterKnife.bind(this, view);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.cardio_ex_custom_list);
        AdapterToCustomExerciseRecycler adapterToCustomExerciseRecycler = new AdapterToCustomExerciseRecycler
                (createCustomExecList(), getFragmentManager());
        emptyCustExList.setVisibility(View.GONE);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setVisibility(View.VISIBLE);
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

    @OnClick(R.id.custom_exercise_create_btn)
    void showToast() {
        Toast.makeText(getContext(), "CREATE NEW EXERCISE", Toast.LENGTH_SHORT).show();
    }

}

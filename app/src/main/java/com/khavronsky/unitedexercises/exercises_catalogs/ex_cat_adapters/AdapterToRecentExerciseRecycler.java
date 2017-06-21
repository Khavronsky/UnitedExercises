package com.khavronsky.unitedexercises.exercises_catalogs.ex_cat_adapters;


import com.khavronsky.unitedexercises.R;
import com.khavronsky.unitedexercises.exercise_performance.ExercisePerformActivity;
import com.khavronsky.unitedexercises.exercises_models.CustomExModel;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AdapterToRecentExerciseRecycler
        extends RecyclerView.Adapter<AdapterToRecentExerciseRecycler.CustomExerciseHolder> {

    private List<CustomExModel> customExList;

    private FragmentManager mFragmentManager;

    public AdapterToRecentExerciseRecycler(final List<CustomExModel> customExList,
            FragmentManager fragmentManager) {
        this.customExList = customExList;
        mFragmentManager = fragmentManager;

    }

    @Override
    public CustomExerciseHolder onCreateViewHolder(final ViewGroup parent,
            final int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.recent_exercise_recycler_item, parent, false);
        return new CustomExerciseHolder(view).setFragmentManager(mFragmentManager);
    }

    @Override
    public void onBindViewHolder(final CustomExerciseHolder holder, final int position) {
        holder.setText(customExList.get(position).getExTitle(), customExList.get(position).getExSubTitle());
    }

    @Override
    public int getItemCount() {
        return customExList == null ? 0 : customExList.size();
    }

    /**
     * V I E W   H O L D E R
     */
    class CustomExerciseHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.recent_exercise_item_title)
        TextView itemTitle;

        @BindView(R.id.recent_exercise_item_sub_title)
        TextView itemSubtitle;

        FragmentManager mFragmentManager;

        public CustomExerciseHolder setFragmentManager(final FragmentManager fragmentManager) {
            mFragmentManager = fragmentManager;
            return this;
        }

        CustomExerciseHolder(final View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
        @OnClick({R.id.recent_exercise_item_title, R.id.recent_exercise_item_sub_title})
        @Override
        public void onClick(final View v) {
            Toast.makeText(v.getContext(), "show exercise", Toast.LENGTH_SHORT).show();
            v.getContext().startActivity(new Intent(v.getContext(), ExercisePerformActivity.class));
        }

        void setText(String title, String subTitle) {
            itemTitle.setText(title);
            itemSubtitle.setText(subTitle);
        }

    }
}

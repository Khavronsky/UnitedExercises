package com.khavronsky.unitedexercises.presentation.exercise.exercise_performance;


import com.khavronsky.unitedexercises.R;
import com.khavronsky.unitedexercises.presentation.exercise.exercises_models.PowerExerciseModel;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class PowerExSetsRVAdapter extends RecyclerView.Adapter<PowerExSetsRVAdapter.PowerExSetsHolder> {

    private PowerExerciseModel mExercise;

    public void setExercise(PowerExerciseModel exercise) {
        mExercise = exercise;
    }

    @Override
    public PowerExSetsHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.power_ex_approach_item, parent, false);
        return new PowerExSetsHolder(view);
    }

    @Override
    public void onBindViewHolder(final PowerExSetsHolder holder, final int position) {

        String repeatPlural = holder.itemView.getResources().getQuantityString(R.plurals.approaches,
                mExercise.getApproach(position).getRepeats(),
                mExercise.getApproach(position).getRepeats());
        holder.setApproach(repeatPlural, mExercise.getApproach(position).getWeight());
    }

    @Override
    public int getItemCount() {
        return mExercise == null ? 0 : mExercise.getApproachList().size();
    }

    /**
     * V I E W   H O L D E R
     */
    class PowerExSetsHolder extends RecyclerView.ViewHolder {

        private TextView mIndex;

        private TextView mRepeats;

        private TextView mWeight;

        private View mWeightArea;


        PowerExSetsHolder(final View itemView) {
            super(itemView);
            mIndex = (TextView) itemView.findViewById(R.id.approach_item_index);
            mRepeats = (TextView) itemView.findViewById(R.id.approach_item_repeats);
            mWeight = (TextView) itemView.findViewById(R.id.approach_item_weight);
            mWeightArea = itemView.findViewById(R.id.approach_item_weight_area);
            mIndex.setText(String.valueOf(getAdapterPosition()));
        }

        void setApproach(String repeats, int weight) {
            mRepeats.setText(repeats);
            if (weight > 0) {
                mWeight.setText(String.valueOf(weight));
            } else {
                mWeightArea.setVisibility(View.INVISIBLE);
            }
        }
    }
}

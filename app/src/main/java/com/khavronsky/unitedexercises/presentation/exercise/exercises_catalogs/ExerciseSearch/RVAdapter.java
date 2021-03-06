package com.khavronsky.unitedexercises.presentation.exercise.exercises_catalogs.ExerciseSearch;


import com.khavronsky.unitedexercises.R;
import com.khavronsky.unitedexercises.presentation.exercise.exercises_models.ExerciseModel;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

class RVAdapter extends RecyclerView.Adapter<RVAdapter.VHolder> {

    private List<ExerciseModel> list = new ArrayList<>();

    public ExerciseModel getItem(final int position) {
        return list.get(position);
    }

    void setList(final List<ExerciseModel> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public VHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.ex_rv_adapter_search_item, parent, false);
        return new VHolder(view);
    }

    @Override
    public void onBindViewHolder(final VHolder holder, final int position) {
        holder.setText(list.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return list == null? 0 : list.size();
    }

    class VHolder extends RecyclerView.ViewHolder {

        TextView mTextView;

        VHolder(final View itemView) {
            super(itemView);
            mTextView = (TextView) itemView.findViewById(R.id.rv_item);
        }

        void setText(final String text) {
            mTextView.setText(text);
        }
    }

}

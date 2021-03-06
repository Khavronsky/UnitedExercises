package com.khavronsky.unitedexercises.presentation.exercise.exercises_catalogs.default_ex_catalog;

import com.khavronsky.unitedexercises.R;
import com.khavronsky.unitedexercises.presentation.exercise.exercise_performance.ExercisePerformActivity;
import com.khavronsky.unitedexercises.utils.import_from_grand_project.RecyclerItemClickListener;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class DefaultCatalogRVAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
        implements RecyclerItemClickListener.OnItemClickListener {

    private List<DefaultCatalogModel> exerciseCatalog = new ArrayList<>();

    private Context mContext;

    public DefaultCatalogRVAdapter setExerciseCatalog(final List<DefaultCatalogModel> exerciseCatalog) {
        this.exerciseCatalog = exerciseCatalog;
        return this;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        mContext = parent.getContext();
        if (viewType == 0) {
            View view = LayoutInflater
                    .from(parent.getContext())
                    .inflate(R.layout.exercise_catalog_recycler_capital_letter_item, parent, false);
            return new CapitalLetterItem(view);
        } else {
            View view = LayoutInflater
                    .from(parent.getContext())
                    .inflate(R.layout.exercise_catalog_recycler_item, parent, false);
            return new DefaultExerciseItem(view);
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof CapitalLetterItem) {
            ((CapitalLetterItem) holder).setText(exerciseCatalog.get(position).getTitle());
        } else {
            ((DefaultExerciseItem) holder).setText(exerciseCatalog.get(position).getTitle());
            ((DefaultExerciseItem) holder).setListener(this);
        }
    }

    @Override
    public int getItemViewType(final int position) {
        return exerciseCatalog.get(position).getType() == DefaultCatalogModel.ItemType.CAPITAL_LETTER ? 0 : 1;
    }

    @Override
    public int getItemCount() {
        return exerciseCatalog == null ? 0 : exerciseCatalog.size();
    }

    @Override
    public void onItemClick(final View view, final int pos) {
        Intent intent = new Intent(mContext, ExercisePerformActivity.class);
        intent.putExtra(ExercisePerformActivity.NEW_PERFORMANCE, true);
        intent.putExtra(ExercisePerformActivity.MODEL_OF_EXERCISE, exerciseCatalog.get(pos).getExercise());
        mContext.startActivity(intent);
    }

    /**
     * V I E W   H O L D E R S
     */
    private class DefaultExerciseItem extends RecyclerView.ViewHolder {

        TextView mDefExerciseTitle;

        RecyclerItemClickListener.OnItemClickListener mListener;

        DefaultExerciseItem(final View itemView) {
            super(itemView);
            mDefExerciseTitle = (TextView) itemView.findViewById(R.id.exercise_name);
            mDefExerciseTitle.setOnClickListener(
                    v -> mListener.onItemClick(itemView, getAdapterPosition()));
        }

        void setText(String text) {
            mDefExerciseTitle.setText(text);
        }

        void setListener(
                final RecyclerItemClickListener.OnItemClickListener listener) {
            mListener = listener;
        }
    }

    private class CapitalLetterItem extends RecyclerView.ViewHolder {

        TextView mCapitalLetter;

        CapitalLetterItem(final View itemView) {
            super(itemView);
            mCapitalLetter = (TextView) itemView.findViewById(R.id.exercise_catalog_capital_letter);
        }

        void setText(String text) {
            mCapitalLetter.setText(text);
        }

    }

}
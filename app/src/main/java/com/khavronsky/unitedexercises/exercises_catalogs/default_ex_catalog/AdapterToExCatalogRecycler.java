package com.khavronsky.unitedexercises.exercises_catalogs.default_ex_catalog;

import com.khavronsky.unitedexercises.R;
import com.khavronsky.unitedexercises.exercise_performance.ExercisePerformActivity;
import com.khavronsky.unitedexercises.exercises_models.ModelOfItemForExCatalog;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class AdapterToExCatalogRecycler
        extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements
        ExerciseCatalogHolder.IDefaultCatalogListener{

    private List<ModelOfItemForExCatalog> exerciseCatalog = new ArrayList<>();

    private Context mContext;

    public AdapterToExCatalogRecycler setExerciseCatalog(final List<ModelOfItemForExCatalog> exerciseCatalog) {
        this.exerciseCatalog = exerciseCatalog;
        Log.d("KhS", "setExerciseCatalog: ");
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
            return new ExerciseCatalogHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof CapitalLetterItem) {
            ((CapitalLetterItem) holder).setText(exerciseCatalog.get(position).getTitle());
        } else {
            ((ExerciseCatalogHolder) holder).setText(exerciseCatalog.get(position).getTitle());
            ((ExerciseCatalogHolder) holder).setListener(this);
            Log.d("KhS", "onBindViewHolder: " + exerciseCatalog.get(position));
        }
    }

    @Override
    public int getItemViewType(final int position) {
        return exerciseCatalog.get(position).getType() == ModelOfItemForExCatalog.ItemType.CAPITAL_LETTER ? 0 : 1;
    }

    @Override
    public int getItemCount() {
        return exerciseCatalog == null ? 0 : exerciseCatalog.size();
    }

    @Override
    public void startExPerformanceActivity(final int pos) {
        Intent intent = new Intent(mContext, ExercisePerformActivity.class);
        intent.putExtra(ExercisePerformActivity.NEW_PERFORMANCE, true);
        intent.putExtra(ExercisePerformActivity.MODEL_OF_EXERCISE, exerciseCatalog.get(pos).getExercise());
        mContext.startActivity(intent);
    }
}

/**
 * V I E W   H O L D E R
 */
class ExerciseCatalogHolder extends RecyclerView.ViewHolder {

    TextView mTextView;

    IDefaultCatalogListener mListener;

    public ExerciseCatalogHolder(final View itemView) {
        super(itemView);
        mTextView = (TextView) itemView.findViewById(R.id.exercise_name);
        Log.d("KhS", "ExerciseCatalogHolder: ");
        mTextView.setOnClickListener(
                v -> mListener.startExPerformanceActivity(getAdapterPosition()));
    }

    void setText(String text) {
        mTextView.setText(text);
    }

    public void setListener(
            final IDefaultCatalogListener listener) {
        mListener = listener;
    }

    interface IDefaultCatalogListener {

        void startExPerformanceActivity(int pos);

    }
}

class CapitalLetterItem extends RecyclerView.ViewHolder {

    TextView mTextView;

    public CapitalLetterItem(final View itemView) {
        super(itemView);
        mTextView = (TextView) itemView.findViewById(R.id.exercise_catalog_capital_letter);

    }

    void setText(String text) {
        mTextView.setText(text);
    }

}


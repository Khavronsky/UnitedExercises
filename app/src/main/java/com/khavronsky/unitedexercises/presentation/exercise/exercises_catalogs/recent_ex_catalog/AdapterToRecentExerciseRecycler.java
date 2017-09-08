package com.khavronsky.unitedexercises.presentation.exercise.exercises_catalogs.recent_ex_catalog;


import com.khavronsky.unitedexercises.R;
import com.khavronsky.unitedexercises.presentation.exercise.exercise_performance.ExercisePerformActivity;
import com.khavronsky.unitedexercises.presentation.exercise.exercises_models.CardioExerciseModel;
import com.khavronsky.unitedexercises.presentation.exercise.exercises_models.ExerciseModel;
import com.khavronsky.unitedexercises.presentation.exercise.exercises_models.ModelOfExercisePerformance;
import com.khavronsky.unitedexercises.utils.import_from_grand_project.RecyclerItemClickListener;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.khavronsky.unitedexercises.presentation.exercise.exercises_models.CardioExerciseModel.TYPE_SPECIFY_HIGH;
import static com.khavronsky.unitedexercises.presentation.exercise.exercises_models.CardioExerciseModel.TYPE_SPECIFY_LOW;
import static com.khavronsky.unitedexercises.presentation.exercise.exercises_models.CardioExerciseModel.TYPE_SPECIFY_MIDDLE;

public class AdapterToRecentExerciseRecycler
        extends RecyclerView.Adapter<AdapterToRecentExerciseRecycler.CustomExerciseHolder>
        implements RecyclerItemClickListener.OnItemClickListener {

    private List<ModelOfExercisePerformance> customExList;

    private Context mContext;

    public AdapterToRecentExerciseRecycler setCustomExList(final List<ModelOfExercisePerformance> customExList) {
        this.customExList = customExList;
        return this;
    }

    @Override
    public CustomExerciseHolder onCreateViewHolder(final ViewGroup parent,
            final int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.recent_exercise_recycler_item, parent, false);
        mContext = parent.getContext();
        return new CustomExerciseHolder(view);
    }

    @Override
    public void onItemClick(final View view, final int pos) {
        Intent intent = new Intent(mContext, ExercisePerformActivity.class);
        intent.putExtra(ExercisePerformActivity.NEW_PERFORMANCE, false);
        intent.putExtra(ExercisePerformActivity.MODEL_OF_PERFORMANCE, customExList.get(pos));
        mContext.startActivity(intent);
    }


    private String createDescription(final ModelOfExercisePerformance exerciseModel) {
        if (exerciseModel.getExercise().getType() == ExerciseModel.ExerciseType.CARDIO) {
            if (((CardioExerciseModel) exerciseModel.getExercise()).getIntensityType() == CardioExerciseModel
                    .TYPE_NOT_SPECIFY) {
                return "Интенсивность по умолчанию";
            } else {
                switch (exerciseModel.getCurrentIntensityType()) {
                    case TYPE_SPECIFY_LOW:
                        return "Низкая интенсивность";
                    case TYPE_SPECIFY_MIDDLE:
                        return "Средняя интенсивность";
                    case TYPE_SPECIFY_HIGH:
                        return "Высокая интенсивность";
                }
            }
        }
        if (exerciseModel.getExercise().getType() == ExerciseModel.ExerciseType.POWER) {
            int currentApproaches = exerciseModel.getApproachList().size();
            if (currentApproaches == 0){
                return null;
            }
            return mContext.getResources().getQuantityString(R.plurals.approaches,
                    currentApproaches,
                    currentApproaches);
        }
        return "___";
    }

    @Override
    public void onBindViewHolder(final CustomExerciseHolder holder, final int position) {
        if (customExList.size() > 0) {
            Log.d("WTF", "pos " + position + "; type " + customExList.get(position).getExercise().getType());
            holder.setText(customExList.get(position).getExercise().getTitle(),
                    createDescription(customExList.get(position)),
                    customExList.get(position).getDuration() + " мин",
                    customExList.get(position).getExercise().getType() == ExerciseModel.ExerciseType.CARDIO ?
                            String.valueOf(customExList.get(position).getCurrentKcalPerHour()) : null);
            holder.setListener(this);
        }
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

        @BindView(R.id.recent_exercise_item_show_perform_description)
        TextView mPerformDescription;

        @BindView(R.id.recent_exercise_item_show_duration)
        TextView mExerciseDuration;

        @BindView(R.id.recent_exercise_item_show_intensity_value)
        TextView mIntensity;

        @BindView(R.id.recent_exercise_item_click_area)
        View clickArea;

        private RecyclerItemClickListener.OnItemClickListener mListener;

        CustomExerciseHolder(final View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.recent_exercise_item_click_area)
        @Override
        public void onClick(final View v) {
            mListener.onItemClick(v, getAdapterPosition());
        }

        void setText(String title,
                String description,
                String duration,
                String intensity) {

            itemTitle.setText(title);
            mExerciseDuration.setText(duration);
            if (description == null) {
                mPerformDescription.setVisibility(View.GONE);
            } else {
                mPerformDescription.setText(description);
                mPerformDescription.setVisibility(View.VISIBLE);
            }
            if (intensity == null) {
                mIntensity.setVisibility(View.GONE);
            } else {
                mIntensity.setText(intensity);
                mIntensity.setVisibility(View.VISIBLE);
            }
        }

        public void setListener(final RecyclerItemClickListener.OnItemClickListener listener) {
            mListener = listener;
        }
    }
}
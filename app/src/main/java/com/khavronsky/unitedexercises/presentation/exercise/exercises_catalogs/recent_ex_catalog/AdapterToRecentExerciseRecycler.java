package com.khavronsky.unitedexercises.presentation.exercise.exercises_catalogs.recent_ex_catalog;


import com.khavronsky.unitedexercises.R;
import com.khavronsky.unitedexercises.presentation.exercise.exercise_performance.ExercisePerformActivity;
import com.khavronsky.unitedexercises.presentation.exercise.exercises_models.CardioExerciseModel;
import com.khavronsky.unitedexercises.presentation.exercise.exercises_models.ExerciseModel;
import com.khavronsky.unitedexercises.presentation.exercise.exercises_models.ModelOfExercisePerformance;
import com.khavronsky.unitedexercises.presentation.exercise.exercises_models.PowerExerciseModel;
import com.khavronsky.unitedexercises.utils.import_from_grand_project.RecyclerItemClickListener;

import android.content.Context;
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

import static com.khavronsky.unitedexercises.presentation.exercise.exercises_models.CardioExerciseModel.METHOD_MET_VALUES;

public class AdapterToRecentExerciseRecycler
        extends RecyclerView.Adapter<AdapterToRecentExerciseRecycler.CustomExerciseHolder>
        implements RecyclerItemClickListener.OnItemClickListener {

    private List<ModelOfExercisePerformance> customExList;

    private FragmentManager mFragmentManager;

    private Context mContext;

    public AdapterToRecentExerciseRecycler(FragmentManager fragmentManager) {
        mFragmentManager = fragmentManager;

    }

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
        return new CustomExerciseHolder(view).setFragmentManager(mFragmentManager);
    }

    @Override
    public void onItemClick(final View view, final int pos) {
        Intent intent = new Intent(mContext, ExercisePerformActivity.class);
        intent.putExtra(ExercisePerformActivity.NEW_PERFORMANCE, false);
        intent.putExtra(ExercisePerformActivity.MODEL_OF_PERFORMANCE, customExList.get(pos));
        mContext.startActivity(intent);
    }


    private String createDescription(final ExerciseModel exerciseModel) {
        if (exerciseModel.getType() == ExerciseModel.ExerciseType.CARDIO) {
            return ((CardioExerciseModel) exerciseModel).getIntensityType() == CardioExerciseModel.TYPE_SPECIFY ?
                    "Расчет по типу интенсивности"
                    : String.valueOf(((CardioExerciseModel) exerciseModel).getDefValue())
                            + (((CardioExerciseModel) exerciseModel).getCountCalMethod() == METHOD_MET_VALUES ?
                            " MET"
                            : " ккал/час")
                    ;
        }
        if (exerciseModel.getType() == ExerciseModel.ExerciseType.POWER) {
            return ((PowerExerciseModel) exerciseModel).getSets() + " подходов, "
                    + ((PowerExerciseModel) exerciseModel).getRepeats() + " повторов, "
                    + ((PowerExerciseModel) exerciseModel).getWeight() + " кг";
        }
        return "Упс, не нашли...";
    }

    @Override
    public void onBindViewHolder(final CustomExerciseHolder holder, final int position) {
//        holder.setText(customExList.get(position).getExTitle(), customExList.get(position).getExSubTitle());
        if (customExList.size() > 0) {
            holder.setText(customExList.get(position).getExercise().getTitle(), createDescription(customExList.get
                    (position).getExercise()));
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

        @BindView(R.id.recent_exercise_item_sub_title)
        TextView itemSubtitle;

        private FragmentManager mFragmentManager;

        private RecyclerItemClickListener.OnItemClickListener mListener;

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
            mListener.onItemClick(v, getAdapterPosition());
            Toast.makeText(v.getContext(), "show exercise", Toast.LENGTH_SHORT).show();
        }

        void setText(String title, String subTitle) {
            itemTitle.setText(title);
            itemSubtitle.setText(subTitle);
        }

        public void setListener(final RecyclerItemClickListener.OnItemClickListener listener) {
            mListener = listener;
        }
    }
}

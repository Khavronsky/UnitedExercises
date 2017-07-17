package com.khavronsky.unitedexercises.presentation.exercise.exercises_catalogs.custom_ex_catalog;

import com.khavronsky.unitedexercises.R;
import com.khavronsky.unitedexercises.presentation.exercise.exercise_performance.ExercisePerformActivity;
import com.khavronsky.unitedexercises.presentation.exercise.exercises_models.CardioExerciseModel;
import com.khavronsky.unitedexercises.presentation.exercise.exercises_models.ExerciseModel;
import com.khavronsky.unitedexercises.presentation.exercise.exercises_models.IEditCatalog;
import com.khavronsky.unitedexercises.utils.import_from_grand_project.RecyclerItemClickListener;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.khavronsky.unitedexercises.presentation.exercise.exercises_models.CardioExerciseModel.METHOD_MET_VALUES;

public class AdapterToCustomExerciseRecycler
        extends RecyclerView.Adapter<AdapterToCustomExerciseRecycler.CustomExerciseHolder>
        implements OnClickDialogItemListener, RecyclerItemClickListener.OnItemClickListener {

    private List<ExerciseModel> customExList;

    private FragmentManager mFragmentManager;

    private IEditCatalog mEditor;

    private Context mContext;

    public AdapterToCustomExerciseRecycler(FragmentManager fragmentManager) {
        mFragmentManager = fragmentManager;
    }

    public void setEditor(final IEditCatalog editor) {
        mEditor = editor;
    }

    @Override
    public CustomExerciseHolder onCreateViewHolder(final ViewGroup parent,
            final int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.custom_exercise_recycler_item, parent, false);
        mContext = parent.getContext();
        return new CustomExerciseHolder(view).setFragmentManager(mFragmentManager);
    }

    @Override
    public void onBindViewHolder(final CustomExerciseHolder holder, final int position) {
        holder.setText(customExList.get(position).getTitle(), createDescription(customExList.get(position)));
        holder.setCatalogListeners(this, this);
    }

    @Override
    public void pressDel(int pos) {
        mEditor.delElements(customExList.get(pos).getId());
    }

    @Override
    public void pressEdit(int pos) {
        mEditor.editElements(customExList.get(pos));
    }

    @Override
    public void onItemClick(final View view, final int pos) {
        Intent intent = new Intent(mContext, ExercisePerformActivity.class);
        intent.putExtra(ExercisePerformActivity.NEW_PERFORMANCE, true);
        intent.putExtra(ExercisePerformActivity.MODEL_OF_EXERCISE, customExList.get(pos));
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
            return "";
        }
        return "Упс, не нашли...";
    }

    @Override
    public int getItemCount() {
        return customExList == null ? 0 : customExList.size();
    }

    public void setModelList(final List<ExerciseModel> modelList) {
        customExList = modelList;
    }


    /**
     * V I E W   H O L D E R
     */
    class CustomExerciseHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.custom_exercise_item_title)
        TextView itemTitle;

        @BindView(R.id.custom_exercise_item_sub_title)
        TextView itemSubtitle;

        @BindView(R.id.custom_exercise_menu)
        ImageView itemMenu;

        @BindView(R.id.anchor)
        View mView;

        @BindView(R.id.custom_exercise_menu_click_area)
        View mClickArea;

        FragmentManager mFragmentManager;

        private OnClickDialogItemListener mCatalogListener;

        private RecyclerItemClickListener.OnItemClickListener mOnItemClickListener;


        public CustomExerciseHolder setFragmentManager(final FragmentManager fragmentManager) {
            mFragmentManager = fragmentManager;
            return this;
        }

        CustomExerciseHolder(final View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick({R.id.custom_exercise_item_title, R.id.custom_exercise_item_sub_title})
        @Override
        public void onClick(final View v) {
            mOnItemClickListener.onItemClick(v, getAdapterPosition());

        }

        public void setCatalogListeners(final OnClickDialogItemListener catalogListener, final
        RecyclerItemClickListener.OnItemClickListener onItemClickListener) {
            mCatalogListener = catalogListener;
            mOnItemClickListener = onItemClickListener;
        }

        void setText(String title, String subTitle) {
            itemTitle.setText(title);
            itemSubtitle.setText(subTitle);
        }

        @OnClick(R.id.custom_exercise_menu_click_area)
        void showMenu() {
            PopupMenu popupMenu = new PopupMenu(mView.getContext(), mView, Gravity.END);
            popupMenu.inflate(R.menu.popup_menu);
            popupMenu
                    .setOnMenuItemClickListener(item -> {
                        switch (item.getItemId()) {
                            case R.id.custom_exercise_item_menu_del:
                                popupMenu.dismiss();
                                mCatalogListener.pressDel(getAdapterPosition());
                                return true;
                            case R.id.custom_exercise_item_menu_edit:
                                popupMenu.dismiss();
                                mCatalogListener.pressEdit(getAdapterPosition());
                                return true;
                            default:
                                return false;
                        }
                    });
            popupMenu.show();
        }

    }
}
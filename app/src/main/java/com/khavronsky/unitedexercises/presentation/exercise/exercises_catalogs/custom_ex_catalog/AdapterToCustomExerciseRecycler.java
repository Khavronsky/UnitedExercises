package com.khavronsky.unitedexercises.presentation.exercise.exercises_catalogs.custom_ex_catalog;

import com.khavronsky.unitedexercises.R;
import com.khavronsky.unitedexercises.presentation.exercise.exercise_performance.ExercisePerformActivity;
import com.khavronsky.unitedexercises.presentation.exercise.exercises_models.CardioExerciseModel;
import com.khavronsky.unitedexercises.presentation.exercise.exercises_models.ExerciseModel;
import com.khavronsky.unitedexercises.presentation.exercise.exercises_models.IEditCatalog;
import com.khavronsky.unitedexercises.presentation.exercise.exercises_models.PowerExerciseModel;

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
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.khavronsky.unitedexercises.presentation.exercise.exercises_models.CardioExerciseModel.METHOD_MET_VALUES;

public class AdapterToCustomExerciseRecycler extends RecyclerView.Adapter<CustomExerciseHolder>
        implements CustomExerciseHolder.ICustomCatalogListener {

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
//        holder.setText(customExList.get(position).getTitle(), customExList.get(position).getType().toString());
        holder.setText(customExList.get(position).getTitle(), createDescription(customExList.get(position)));
        holder.setCatalogListener(this);

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
    public void startExPerformanceActivity(final int pos) {

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
            return ((PowerExerciseModel) exerciseModel).getSets() + " подходов, "
                    + ((PowerExerciseModel) exerciseModel).getRepeats() + " повторов, "
                    + ((PowerExerciseModel) exerciseModel).getWeight() + " кг";
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

    FragmentManager mFragmentManager;

    private ICustomCatalogListener mCatalogListener;

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
        Toast.makeText(v.getContext(), "show exercise", Toast.LENGTH_SHORT).show();
        mCatalogListener.startExPerformanceActivity(getAdapterPosition());

    }

    public void setCatalogListener(final ICustomCatalogListener catalogListener) {
        mCatalogListener = catalogListener;
    }

    void setText(String title, String subTitle) {
        itemTitle.setText(title);
        itemSubtitle.setText(subTitle);
    }

    @OnClick(R.id.custom_exercise_menu)
    void showMenu() {
        PopupMenu popupMenu = new PopupMenu(mView.getContext(), mView, Gravity.END);
        popupMenu.inflate(R.menu.popup_menu);

        popupMenu
                .setOnMenuItemClickListener(item -> {
                    switch (item.getItemId()) {

                        case R.id.custom_exercise_item_menu_del:
                            Toast.makeText(itemMenu.getContext(), "ГУМНО", Toast.LENGTH_SHORT).show();
                            popupMenu.dismiss();

                            mCatalogListener.pressDel(getAdapterPosition());
                            return true;
                        case R.id.custom_exercise_item_menu_edit:
                            Toast.makeText(itemMenu.getContext(), "EDIT", Toast.LENGTH_SHORT).show();

                            mCatalogListener.pressEdit(getAdapterPosition());
                            return true;
                        default:
                            return false;
                    }
                });

        popupMenu.setOnDismissListener(menu -> Toast.makeText(itemMenu.getContext(), "onDismiss",
                Toast.LENGTH_SHORT).show());
        popupMenu.show();
    }

    interface ICustomCatalogListener {

        void pressDel(int pos);

        void pressEdit(int pos);

        void startExPerformanceActivity(int pos);
    }

}

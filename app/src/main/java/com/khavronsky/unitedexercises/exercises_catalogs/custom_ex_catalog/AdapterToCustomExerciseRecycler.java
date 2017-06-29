package com.khavronsky.unitedexercises.exercises_catalogs.custom_ex_catalog;

import com.khavronsky.unitedexercises.R;
import com.khavronsky.unitedexercises.create_new_exercises.new_cardio_exercise.CardioExerciseEditorActivity;
import com.khavronsky.unitedexercises.exercise_performance.ExercisePerformActivity;
import com.khavronsky.unitedexercises.exercises_models.CardioExerciseModel;
import com.khavronsky.unitedexercises.exercises_models.ExerciseModel;
import com.khavronsky.unitedexercises.exercises_models.IEditCatalog;
import com.khavronsky.unitedexercises.exercises_models.PowerExerciseModel;

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

import static com.khavronsky.unitedexercises.exercises_models.CardioExerciseModel.METHOD_MET_VALUES;

public class AdapterToCustomExerciseRecycler extends RecyclerView.Adapter<CustomExerciseHolder>
        implements CustomExerciseHolder.ICustomExEditor {

    private List<ExerciseModel> customExList;

    private FragmentManager mFragmentManager;

    private IEditCatalog mEditor;

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
        return new CustomExerciseHolder(view).setFragmentManager(mFragmentManager);
    }

    @Override
    public void onBindViewHolder(final CustomExerciseHolder holder, final int position) {
//        holder.setText(customExList.get(position).getTitle(), customExList.get(position).getType().toString());
        holder.setText(customExList.get(position).getTitle(), createDescription(customExList.get(position)));
        holder.setEditor(this);

    }

    @Override
    public void pressDel(int pos) {
        mEditor.delElements(customExList.get(pos).getId());
    }

    @Override
    public void pressEdit(int pos) {

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

    private ICustomExEditor mEditor;

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
        v.getContext().startActivity(new Intent(v.getContext(), ExercisePerformActivity.class));
    }

    public void setEditor(final ICustomExEditor editor) {
        mEditor = editor;
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
                            Toast.makeText(itemMenu.getContext(),
                                    "ГУМНО",
                                    Toast.LENGTH_SHORT).show();
                            popupMenu.dismiss();

                            mEditor.pressDel(getAdapterPosition());
                            return true;
                        case R.id.custom_exercise_item_menu_edit:
                            Toast.makeText(itemMenu.getContext(),
                                    "EDIT",
                                    Toast.LENGTH_SHORT).show();

                            mEditor.pressEdit(getAdapterPosition());
                            return true;
                        default:
                            return false;
                    }
                });

        popupMenu.setOnDismissListener(menu -> Toast.makeText(itemMenu.getContext(), "onDismiss",
                Toast.LENGTH_SHORT).show());
        popupMenu.show();
    }

    interface ICustomExEditor {

        void pressDel(int pos);

        void pressEdit(int pos);
    }

}

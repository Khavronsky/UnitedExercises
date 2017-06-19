package com.khavronsky.unitedexercises.exercises_catalogs.ex_cat_fragments;


import com.khavronsky.unitedexercises.R;
import com.khavronsky.unitedexercises.import_from_grand_project.BaseDialogFragment;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.OnClick;


public class ExerciseCatalogItemMenuDialog extends BaseDialogFragment {
    @BindView(R.id.custom_exercise_item_menu_del_btn)
    View delBtn;

    @BindView(R.id.custom_exercise_item_menu_edit_btn)
    View editBtn;

    @NonNull
    @Override
    public Dialog onCreateDialog(final Bundle savedInstanceState) {
        return super.onCreateDialog(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container,
            @Nullable final Bundle savedInstanceState) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.custom_exercise_item_menu, null);

        return view ;
    }

    @OnClick({R.id.custom_exercise_item_menu_del_btn, R.id.custom_exercise_item_menu_edit_btn})
    @Override
    public void onClick(final View v) {
        int id = v.getId();
        switch (id){
            case R.id.custom_exercise_item_menu_del_btn:
                callback.doButtonClick1(null);
                break;
            case R.id.custom_exercise_item_menu_edit_btn:
                callback.doButtonClick2();
                break;
        }
    }
}

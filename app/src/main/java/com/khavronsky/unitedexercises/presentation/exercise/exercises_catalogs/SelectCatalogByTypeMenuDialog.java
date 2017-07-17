package com.khavronsky.unitedexercises.presentation.exercise.exercises_catalogs;


import com.khavronsky.unitedexercises.R;
import com.khavronsky.unitedexercises.presentation.exercise.exercises_models.ExerciseModel;
import com.khavronsky.unitedexercises.utils.import_from_grand_project.BaseDialogFragment;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.OnClick;


public class SelectCatalogByTypeMenuDialog extends BaseDialogFragment {
    @BindView(R.id.select_catalog_by_type_cardio)
    View cardioCatalog;

    @BindView(R.id.select_catalog_by_type_power)
    View powerCatalog;

    @NonNull
    @Override
    public Dialog onCreateDialog(final Bundle savedInstanceState) {
        return super.onCreateDialog(savedInstanceState);
//        ButterKnife.bind();
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container,
            @Nullable final Bundle savedInstanceState) {
        return getActivity().getLayoutInflater().inflate(R.layout.select_catalog_by_type_dialog_menu, null);
    }

    @OnClick({R.id.select_catalog_by_type_cardio, R.id.select_catalog_by_type_power})
    @Override
    public void onClick(final View v) {
        int id = v.getId();
        Intent intent = new Intent(v.getContext(), ExerciseCatalogActivity.class);
        switch (id){
            case R.id.select_catalog_by_type_cardio:
                intent.putExtra("type", ExerciseModel.ExerciseType.CARDIO);
                startActivity(intent);
                dismiss();
                break;
            case R.id.select_catalog_by_type_power:
                intent.putExtra("type", ExerciseModel.ExerciseType.POWER);
                startActivity(intent);
                dismiss();
                break;
        }
    }
}

package com.khavronsky.unitedexercises.presentation.exercise.exercises_catalogs.custom_ex_catalog;


import com.khavronsky.unitedexercises.R;
import com.khavronsky.unitedexercises.utils.import_from_grand_project.BaseDialogFragment;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

public class AddExerciseToExecuteDialog extends BaseDialogFragment {

    private TextView mPositiveBtn;
    private TextView mNegativeBtn;





    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = new Dialog(getContext(), R.style.FullScreenDialog);
        if (dialog.getWindow() != null) {
            dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        }
        return dialog;
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container,
            @Nullable final Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.add_exercise_to_execute_dialog, container, false);
        mPositiveBtn = (TextView) v.findViewById(R.id.add_ex_to_exe_ok_btn);
        mNegativeBtn = (TextView) v.findViewById(R.id.add_ex_to_exe_cancel_btn);

        mPositiveBtn.setOnClickListener(this);
        mNegativeBtn.setOnClickListener(this);
        return v;
    }

    @Override
    public void onClick(final View v) {
        int id = v.getId();
        dismiss();
        switch (id){
            case R.id.add_ex_to_exe_ok_btn:
                getCallback().doButtonClick1(null);
                break;
            case R.id.add_ex_to_exe_cancel_btn:
                getCallback().doButtonClick2();
                break;
        }
    }
}

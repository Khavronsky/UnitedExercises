package com.khavronsky.unitedexercises.presentation.exercise.exercise_performance.dialogs;


import com.khavronsky.unitedexercises.R;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ExerciseDescriptionDialog extends DialogFragment {
    private static final String DESCRIPTION_TAG = "description";


    public interface OnDismissListener {

        void doByDismissed();
    }

    private OnDismissListener mOnDismissListener;

    public void setOnDismissListener(
            final OnDismissListener onDismissListener) {
        mOnDismissListener = onDismissListener;
    }

    public ExerciseDescriptionDialog() {
    }

    public static ExerciseDescriptionDialog newInstance (String description){
        ExerciseDescriptionDialog dialog = new ExerciseDescriptionDialog();
        Bundle args = new Bundle();
        args.putString(DESCRIPTION_TAG, description);
        dialog.setArguments(args);
        return dialog;
    }

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
        View v = inflater.inflate(R.layout.exercice_description_dialog, container, false);
        TextView descriptionTV = (TextView) v.findViewById(R.id.exercise_description_text);
        if (getArguments() != null) {
            descriptionTV.setText(getArguments().getString(DESCRIPTION_TAG));
        }
        LinearLayout ctvOK = (LinearLayout) v.findViewById(R.id.ctvOK);
        ctvOK.setOnClickListener(v1 -> {
            mOnDismissListener.doByDismissed();
            dismiss();
        });
        return v;
    }

    @Override
    public void onDismiss(final DialogInterface dialog) {
        mOnDismissListener.doByDismissed();
        super.onDismiss(dialog);
    }
}

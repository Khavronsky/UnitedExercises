package com.khavronsky.unitedexercises.presentation.exercise.exercise_performance.dialogs;

import com.khavronsky.unitedexercises.R;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class AddApproachDialog extends DialogFragment implements View.OnClickListener {

    private static final String NEW_APPROACH = "new_approach";

    private static final String INDEX = "index";

    private static final String REPEATS = "repeats";

    private static final String WEIGHT = "weight";

    private TextView mTitle;

    private EditText mRepeats;

    private EditText mWeight;

    private TextView mSaveBtn;

    private TextView mDelBtn;

    private TextView mCancelBtn;

    private IApproachAdded listener;

    private int index;

    private boolean newApproach;

    public AddApproachDialog() {
    }

    public static AddApproachDialog newInstance(boolean newApproach, int index, int repeats, int weight) {
        AddApproachDialog dialog = new AddApproachDialog();
        Bundle args = new Bundle();
        args.putBoolean(NEW_APPROACH, newApproach);
        args.putInt(INDEX, index);
        args.putInt(REPEATS, repeats);
        args.putInt(WEIGHT, weight);
        dialog.setArguments(args);
        return dialog;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final Dialog dialog = new Dialog(getContext(), R.style.FullScreenDialog);

        if (dialog.getWindow() != null) {
            dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        }
        index = getArguments().getInt(INDEX);
        newApproach = getArguments().getBoolean(NEW_APPROACH);
        return dialog;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_approach_dlg, null);
        init(view);
        return view;
    }

    private void init(final View view) {
        mTitle = (TextView) view.findViewById(R.id.add_approach_dialog_title);
        mRepeats = (EditText) view.findViewById(R.id.add_approach_dialog_repeats);
        mWeight = (EditText) view.findViewById(R.id.add_approach_dialog_weight);
        mSaveBtn = (TextView) view.findViewById(R.id.add_approach_dialog_save_btn);
        mDelBtn = (TextView) view.findViewById(R.id.add_approach_dialog_delete_btn);
        mCancelBtn = (TextView) view.findViewById(R.id.add_approach_dialog_cancel_btn);

        if (getArguments() != null) {
            String title = " подход";
            if (getArguments().getInt(INDEX) >= 0 && getArguments().getInt(INDEX) < 20) {
                String[] approachArr = getResources().getStringArray(R.array.approach_items);
                title = approachArr[getArguments().getInt(INDEX)] + title;

            } else {
                title = getArguments().getInt(INDEX) + "-й" + title;
            }
            mTitle.setText(title);
            if (getArguments().getInt(REPEATS) > 0) {
                mRepeats.setText(String.valueOf(getArguments().getInt(REPEATS)));
                if (getArguments().getInt(WEIGHT) > 0) {
                    mWeight.setText(String.valueOf(getArguments().getInt(WEIGHT)));
                }
            }
        }
        mSaveBtn.setOnClickListener(this);
        mDelBtn.setOnClickListener(this);
        mCancelBtn.setOnClickListener(this);
        if (newApproach) {
            mDelBtn.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onClick(final View v) {
        int id = v.getId();
        switch (id) {
            case R.id.add_approach_dialog_save_btn:
                int repeats;
                int weight;
                try {
                    repeats = Integer.parseInt(String.valueOf(mRepeats.getText()));

                } catch (Exception e) {
                    repeats = 0;
                }
                try {
                    weight = Integer.parseInt(String.valueOf(mWeight.getText()));
                } catch (Exception e){
                    weight = 0;
                }

                if (repeats > 0) {
                    listener.saveEvent(newApproach, index, repeats, weight);
                    dismiss();
                } else {
                    Toast.makeText(getContext(), "Повторов должно быть больше 0", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.add_approach_dialog_delete_btn:
                dismiss();
                listener.deleteEvent(index);
                break;
            case R.id.add_approach_dialog_cancel_btn:
                dismiss();
                listener.dismiss();
                break;
        }


    }

    public void setListener(IApproachAdded listener) {
        this.listener = listener;
    }

    public interface IApproachAdded {

        void deleteEvent(int index);

        void saveEvent(boolean newApproach, int index, int repeats, int weight);

        void dismiss();
    }
}

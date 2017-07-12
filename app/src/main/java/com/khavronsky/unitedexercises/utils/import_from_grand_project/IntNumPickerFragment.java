package com.khavronsky.unitedexercises.utils.import_from_grand_project;


import com.khavronsky.unitedexercises.R;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.EditText;

public class IntNumPickerFragment extends BaseDialogFragment {

    private BaseNumberPicker nPicker1;

    boolean isKeySotfShown = false;

    private static int onePointValue;

    public IntNumPickerFragment() {
    }

    public static IntNumPickerFragment newInstance(int minVal, int maxVal, int curVal, int onePointVal) {
        IntNumPickerFragment dialog = new IntNumPickerFragment();
        Bundle args = new Bundle();
        args.putInt("min_value", minVal);
        args.putInt( "max_value" , maxVal);
        args.putInt( "current_value" , curVal);
        args.putInt( "one_point_value" , onePointVal);
        dialog.setArguments(args);
        return dialog;
    }

    @Override
    public void onStart() {
        super.onStart();
        ((AlertDialog) getDialog()).getButton(AlertDialog.BUTTON_POSITIVE)
                .setTextColor(getResources().getColor(R.color.gold3));
        ((AlertDialog) getDialog()).getButton(AlertDialog.BUTTON_NEGATIVE)
                .setTextColor(getResources().getColor(R.color.gold3));

    }

    @Override
    public Dialog onCreateDialog(final Bundle savedInstanceState) {
        int curVal = getArguments().getInt("current_value");
        int maxVal = getArguments().getInt("max_value" );
        int minVal = getArguments().getInt("min_value");

        if (curVal > maxVal) {
            curVal = maxVal;
        }

        if (curVal < minVal) {
            curVal = minVal;
        }

        onePointValue = getArguments().getInt("one_point_value");

        if (onePointValue != 0) {
            curVal = Math.round(curVal / onePointValue);
            maxVal = Math.round(maxVal / onePointValue);
            minVal = Math.round(minVal / onePointValue);
        }

        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_int_num_picker, null);
        nPicker1 = (BaseNumberPicker) view.findViewById(R.id.integer);

        AlertDialog.Builder dialogBdr = new AlertDialog.Builder(getActivity())
                .setView(view)

                .setPositiveButton("OK", (dialog, which) -> {
                    dismiss();
                    callback.doButtonClick1(Integer.parseInt(
                            ((EditText) nPicker1.getChildAt(0)).getEditableText().toString()));
                })
                .setNegativeButton("CANCEL", (dialog, which) -> {
                    dismiss();
                    callback.doByDismissed();
                });
        final AlertDialog dialog = dialogBdr.create();

        nPicker1.setMinValue(minVal);
        nPicker1.setMaxValue(maxVal);
        nPicker1.setValue(curVal);

        String[] valAsString = getPickerValues(maxVal, minVal, onePointValue);
        if (valAsString != null && valAsString.length != 0) {
            nPicker1.setDisplayedValues(valAsString);
        }
        if (minVal != 0 && maxVal != 0) {
            nPicker1.setWrapSelectorWheel(true);
        }

        nPicker1.clearFocus();
        nPicker1.setDividerColor(getResources().getColor(R.color.gold3));

        return dialog;
    }

    @NonNull
    public String[] getPickerValues(int maxVal, int minVal, int onePointValue) {
        if (onePointValue != 0 && (maxVal > minVal)) {
            int[] val = new int[maxVal - minVal
                    + 1];  // чтобы получить кол-во элементов в списке максюзн- мин. зн.
            String[] valAsString = new String[maxVal - minVal + 1];
            for (int i = 0; i < val.length; i++) {
                val[i] = (minVal * onePointValue) + (onePointValue * (i));
                valAsString[i] = val[i] + "";
            }
            return valAsString;
        } else {
            return null;
        }
    }

    @Override
    public void onClick(final View v) {
    }


}

package com.khavronsky.unitedexercises.utils.import_from_grand_project;


import com.khavronsky.unitedexercises.R;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.NumberPicker;

public class FloatNumPickerFragment extends BaseDialogFragment {

	public static final String EXTRA_DECIMAL_STEP_IS_01 = ".EXTRA_DECIMAL_STEP_IS_01";


	private BaseNumberPicker nPicker1;
	private BaseNumberPicker nPicker2;

	public static FloatNumPickerFragment newInstance(float currentValue) {
		return newInstance(currentValue, false);
	}

	public static FloatNumPickerFragment newInstance(float currentValue, boolean isDecimalStep01) {

		Bundle args = new Bundle();
		args.putFloat("current_value", currentValue);
		args.putBoolean(EXTRA_DECIMAL_STEP_IS_01, isDecimalStep01);

		FloatNumPickerFragment fragment = new FloatNumPickerFragment();
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		boolean isDecimalType = getArguments().getBoolean(EXTRA_DECIMAL_STEP_IS_01, false);
		float curVal = getArguments().getFloat("current_value");
		int MaxVal = getArguments().getInt("max_value");
		if (MaxVal==0) MaxVal=99;
		int MinVal = getArguments().getInt("min_value");
		View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_float_num_picker, null);
		nPicker1 = (BaseNumberPicker) view.findViewById(R.id.integer);
		nPicker2 = (BaseNumberPicker) view.findViewById(R.id.fract);
		nPicker1.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
		nPicker2.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);

		AlertDialog.Builder dialogBdr = new AlertDialog.Builder(getActivity())
				.setView(view)
				.setPositiveButton("OK", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dismiss();
						callback.doButtonClick1("" + nPicker1.getValue() + (nPicker2.getValue() == 0 ? "" : "." + nPicker2.getDisplayedValues()[nPicker2.getValue()]));
					}
				})
				.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dismiss();
						callback.doByDismissed();
					}
				});

		final AlertDialog dialog = dialogBdr.create();

		nPicker1.setMinValue(MinVal);
		nPicker1.setMaxValue(MaxVal);
		nPicker1.setValue((int) curVal);
		nPicker1.clearFocus();
		nPicker1.setDividerColor(getResources().getColor(R.color.gold3));
		nPicker2.setDividerColor(getResources().getColor(R.color.gold3));
		String[] val;
		if (!isDecimalType) {
			val = new String[]{"0", "25", "50", "75"};
		} else {
			val = new String[]{"0", "10", "20", "30", "40", "50", "60", "70", "80", "90"};
		}
		nPicker2.setMinValue(0);
		nPicker2.setMaxValue(val.length - 1);
		nPicker2.setDisplayedValues(val);
		nPicker2.setWrapSelectorWheel(true);
		if (curVal == 0) {
			nPicker2.setValue(0);
		} else {
			float decPart = (curVal - ((int) curVal));
			String str = "0";
			if (decPart != 0) {
				if (isDecimalType) {
					decPart = ((float) Math.round(decPart * 10)) / 10;
					str = String.valueOf(decPart).substring(2)+"0";
				} else {
					int accurateDecPart = Math.round(decPart * 1000);
					if (accurateDecPart < 125) {
						str = "0";
					} else if (accurateDecPart < 375) {
						str = "25";
					} else if (accurateDecPart < 625) {
						str = "50";
					} else  {  // if (accurateDecPart < 875)
						str = "75";
					}
				}
			}
			for (int i = 1; i < val.length; i++) {
				if (str.equals(val[i])) {
					nPicker2.setValue(i);
				}
			}
		}
		return dialog;
	}

	@Override
	public void onClick(final View v) {
	}
	@Override
	public void onStart() {
		super.onStart();
		((AlertDialog) getDialog()).getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.gold3));
		((AlertDialog) getDialog()).getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(getResources().getColor(R.color.gold3));

	}
}

package com.khavronsky.unitedexercises.import_from_grand_project;


import com.khavronsky.unitedexercises.R;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.NumberPicker;

import java.text.DecimalFormat;

public class TimePickerFragment extends BaseDialogFragment {
	private BaseNumberPicker nPicker1;
	private BaseNumberPicker nPicker2;
	private static int onePointValue;


	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {

		//minutes
		int curVal = getArguments().getInt("cur_value", 0);
		onePointValue =  getArguments().getInt("one_point", 1);

		int curVal1 = (int)curVal/60;

		int curVal2 = Math.round(curVal%60/onePointValue) ;

		View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_float_num_picker, null);
		nPicker1 = (BaseNumberPicker) view.findViewById(R.id.integer);
		nPicker2 = (BaseNumberPicker) view.findViewById(R.id.fract);
		nPicker1.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
		nPicker2.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
		nPicker1.setDividerColor(getResources().getColor(R.color.gold3));
		nPicker2.setDividerColor(getResources().getColor(R.color.gold3));
		AlertDialog.Builder dialogBdr = new AlertDialog.Builder(getActivity())
				.setView(view)
				.setPositiveButton(R.string.save, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dismiss();
						int minutes2 = onePointValue==0? nPicker2.getValue(): nPicker2.getValue()*onePointValue;
						callback.doButtonClick1(nPicker1.getValue()*60+minutes2); //"" + nPicker1.getValue() + (nPicker2.getValue() == 0 ? "" : "." + nPicker2.getDisplayedValues()[nPicker2.getValue()])
					}
				})
				.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dismiss();
						callback.doByDismissed();
					}
				});
		final AlertDialog dialog = dialogBdr.create();


		nPicker1.setMinValue(1);
		nPicker1.setMaxValue(12);

		nPicker2.setMinValue(0);
		nPicker2.setMaxValue(60);

		nPicker1.setDisplayedValues(getHourValues());
		nPicker1.setValue(curVal1);

		nPicker1.clearFocus();


		nPicker2.setMinValue(0);
		nPicker2.setValue(curVal2);

		if(onePointValue!=0){

			nPicker2.setMaxValue(getMinuteValues(onePointValue).length-1);
			nPicker2.setDisplayedValues(getMinuteValues(onePointValue));
		}
		nPicker2.setWrapSelectorWheel(true);

		return dialog;
	}

	public String[] getMinuteValues(int onePointValue){
		String[] val = {"0", "15", "30", "45"};
		if(onePointValue!=0&&(60%onePointValue==0)) {
			int[] val2 = new int[60/onePointValue];  // чтобы получить кол-во элементов в списке максюзн- мин. зн.
			String[] valAsString = new String[60/onePointValue];

			for (int i = 0; i < val2.length; i++) {
				val2[i] = (onePointValue*(i));

				DecimalFormat mFormat = new DecimalFormat("00");
				valAsString[i] = mFormat.format(val2[i]);
			}
			return valAsString;
		}
		else return val;
	}

	public String[] getHourValues(){
		String [] hours = new String[12];
		for (int i=0; i<12;i++){
			DecimalFormat mFormat = new DecimalFormat("00");
			hours[i]=mFormat.format(i+1);
		}
		return hours;
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

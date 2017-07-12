package com.khavronsky.unitedexercises.utils.import_from_grand_project;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.TimePicker;

import java.util.Calendar;

public class TimePickerDialogFragment extends BaseDialogFragment implements TimePickerDialog.OnTimeSetListener {

    public TimePickerDialogFragment() {
    }

    public static TimePickerDialogFragment newInstance(int hourOfDay, int minute) {
        TimePickerDialogFragment dialog = new TimePickerDialogFragment();
        Bundle args = new Bundle();
        args.putInt("hourOfDay", hourOfDay);
        args.putInt("minute", minute);
        dialog.setArguments(args);
        return dialog;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        int hour = getArguments().getInt("hourOfDay");
        int minute = getArguments().getInt("minute");
        return new TimePickerDialog(getActivity(), this, hour, minute,
                DateFormat.is24HourFormat(getActivity()));
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        calendar.set(Calendar.MINUTE, minute);
        this.callback.doButtonClick1(calendar);
    }

    @Override
    public void onClick(View v) {
    }
}

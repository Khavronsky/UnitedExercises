package com.khavronsky.unitedexercises.import_from_grand_project;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.DatePicker;

import java.util.Calendar;

public class DatePickerFragment extends BaseDialogFragment implements OnDateSetListener {

    public DatePickerFragment() {
    }

    public static DatePickerFragment newInstance(int day, int month, int year, long min) {
        DatePickerFragment dialog = new DatePickerFragment();
        Bundle args = new Bundle();
        args.putInt("day", day);
        args.putInt("month", month);
        args.putInt("year", year);
        args.putLong("min", min);
        dialog.setArguments(args);
        return dialog;
    }

    public static DatePickerFragment newInstance(int day, int month, int year, long min, long max) {
        DatePickerFragment dialog = new DatePickerFragment();
        Bundle args = new Bundle();
        args.putInt("day", day);
        args.putInt("month", month);
        args.putInt("year", year);
        args.putLong("min", min);
        args.putLong("max", max);
        dialog.setArguments(args);
        return dialog;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar cal = Calendar.getInstance();
        final long min = getArguments().getLong("min");
        final long max = getArguments().getLong("max", 0);
        CustomDatePickerDialog dialog = new CustomDatePickerDialog(getActivity(),
                this,
                getArguments().getInt("year"),
                getArguments().getInt("month"),
                getArguments().getInt("day"));
        cal.set(Calendar.HOUR_OF_DAY, cal.getMaximum(Calendar.HOUR_OF_DAY));
        cal.set(Calendar.MINUTE, cal.getMaximum(Calendar.MINUTE));
        cal.set(Calendar.SECOND, cal.getMaximum(Calendar.SECOND));
        cal.set(Calendar.MILLISECOND, cal.getMaximum(Calendar.MILLISECOND));
        if (max != 0) {
            dialog.getDatePicker().setMaxDate(cal.getTimeInMillis() + max);
        } else {
            dialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        }
        dialog.setCancelable(true);

        setLimitation(dialog, min);

        return dialog;
    }

    protected void setLimitation(CustomDatePickerDialog dialog, long minDate) {
        Calendar cal = Calendar.getInstance();
        long currentMinDate = 0;
        long currentMaxDate = dialog.getDatePicker().getMaxDate();

        if (minDate > 0) {
            cal.set(Calendar.HOUR_OF_DAY, cal.getMinimum(Calendar.HOUR_OF_DAY));
            cal.set(Calendar.MINUTE, cal.getMinimum(Calendar.MINUTE));
            cal.set(Calendar.SECOND, cal.getMinimum(Calendar.SECOND));
            cal.set(Calendar.MILLISECOND, cal.getMinimum(Calendar.MILLISECOND));
            currentMinDate = cal.getTimeInMillis() - minDate;
        } else {
//            currentMinDate = Prefs.ACCOUNT_CREATING_DATE.getLong();
        }

        // минимальная дата должна быть обязательно быть меньше максимальной и недолжны совпадать (по этому отнимаем еще 1 сек)
        dialog.getDatePicker().setMinDate(Math.min(currentMinDate, currentMaxDate) - 1000);
    }

    @Override
    public void onClick(View v) {
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        final Calendar c = Calendar.getInstance();
        c.set(year, monthOfYear, dayOfMonth);
        this.callback.doButtonClick1(c);
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
        this.callback.doByDismissed();
    }

    public static class CustomDatePickerDialog extends DatePickerDialog {

        public CustomDatePickerDialog(Context context, OnDateSetListener callBack, int year, int monthOfYear,
                int dayOfMonth) {
            super(context, callBack, year, monthOfYear, dayOfMonth);
        }

        @Override
        protected void onStop() {
            return;
        }
    }
}

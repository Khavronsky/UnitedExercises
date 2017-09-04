package com.khavronsky.unitedexercises.utils;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;


public class TextWatcherWithPostfix implements TextWatcher {

    private String postfix;

    private EditText mEditText;

    private ITextWatcherListener listener;

    public TextWatcherWithPostfix(final String postfix, EditText editText) {
        this.postfix = postfix;
        this.mEditText = editText;
    }

    public void setListener(ITextWatcherListener listener) {
        this.listener = listener;
    }

    @Override
    public void beforeTextChanged(final CharSequence s, final int start, final int count, final int after) {

    }

    @Override
    public void onTextChanged(final CharSequence s, final int start, final int before, final int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

        String arr[] = s.toString().split(" ");
        if (arr.length == 2 && postfix.equals(" " + arr[1]) && isNumber(arr[0])) {

            if (Integer.parseInt(arr[0]) == 0) {
                s.clear();
            }
            Log.d("WTF", "TextChanged1: " + Integer.parseInt(arr[0]) + "  " + arr[0] + " " + arr[1]);
            listener.valueChanged(Integer.parseInt(arr[0]));

        } else if (arr.length == 2 && "".equals(arr[0]) && postfix.equals(" " + arr[1])) {

        } else {
            StringBuilder sb = new StringBuilder();
            String buf = s.toString();

            for (int i = 0; i < s.length(); i++) {
                if (Character.isDigit(s.charAt(i))) {
                    sb.append(s.charAt(i));
                } else {
                    break;
                }
            }
//            mEditText.setText(sb.toString() + postfix);
//            Log.d("WTF", "TextChanged2: " + Integer.parseInt(sb.toString()) + " " + sb.toString() + postfix);
            listener.textChanged(sb.toString() + postfix);
        }
    }

    private boolean isNumber(String s) {
        boolean result = true;
        try {
            Integer.parseInt(s);

        } catch (Exception e) {
            result = false;
        }

        return result;
    }

    public interface ITextWatcherListener {

        void valueChanged(int value);

        void textChanged(String textWithPostfix);
    }
}

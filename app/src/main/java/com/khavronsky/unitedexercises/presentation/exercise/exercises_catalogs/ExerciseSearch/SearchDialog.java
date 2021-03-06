package com.khavronsky.unitedexercises.presentation.exercise.exercises_catalogs.ExerciseSearch;


import com.khavronsky.unitedexercises.R;
import com.khavronsky.unitedexercises.presentation.exercise.exercise_performance.ExercisePerformActivity;
import com.khavronsky.unitedexercises.presentation.exercise.exercises_models.ExerciseModel;
import com.khavronsky.unitedexercises.utils.import_from_grand_project.RecyclerItemClickListener;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static android.view.WindowManager.LayoutParams.*;


public class SearchDialog extends DialogFragment
        implements View.OnClickListener, TextWatcher, TextView.OnEditorActionListener,
        IView {

    private ExerciseModel.ExerciseType mExerciseType;

    private EditText searchEditText;

    private RecyclerView recyclerView; //recycler

    private ImageButton buttonClearText;

    private ImageButton buttonMicrophone;

    private RVAdapter searchRecyclerViewAdapter; //adapter

    private RecyclerView.LayoutManager mLayoutManager;

    public static final String SEARCH_DIALOG_TAG = "SearchDialog";

    public static final String EX_TYPE_TAG = "ExerciseType";

    private List<ExerciseModel> dataList = new ArrayList<>();

    SearchDialogPresenter searchDialogPresenter;

    @Override
    public Dialog onCreateDialog(final Bundle savedInstanceState) {

        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        dialog.setContentView(R.layout.ex_search_dialog);

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.getWindow()
                .setLayout(MATCH_PARENT, MATCH_PARENT);
        dialog.getWindow().setSoftInputMode(SOFT_INPUT_STATE_VISIBLE
                | SOFT_INPUT_ADJUST_PAN);

        if (getArguments() != null) {
            mExerciseType = (ExerciseModel.ExerciseType) getArguments().getSerializable(EX_TYPE_TAG);
        }

        searchDialogPresenter = new SearchDialogPresenter(mExerciseType);
        searchDialogPresenter.attachView(this);

        ImageButton buttonBack = (ImageButton) dialog.findViewById(R.id.back_button);
        buttonClearText = (ImageButton) dialog.findViewById(R.id.clear_text);
        buttonMicrophone = (ImageButton) dialog.findViewById(R.id.microphone_button);
        recyclerView = (RecyclerView) dialog.findViewById(R.id.recycler_view);
        mLayoutManager = new LinearLayoutManager(getActivity());

        buttonMicrophone.getDrawable().setColorFilter(Color.BLACK, PorterDuff.Mode.MULTIPLY);
        buttonClearText.setOnClickListener(this);
        buttonBack.setOnClickListener(this);
        buttonMicrophone.setOnClickListener(this);
        recyclerView.setLayoutManager(mLayoutManager);
        searchRecyclerViewAdapter = new RVAdapter();
        setDataList(dataList);
        recyclerView.setAdapter(searchRecyclerViewAdapter);
        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(
                        getActivity(), (view, position) -> {
                    ExerciseModel item = ((RVAdapter) recyclerView.getAdapter()).getItem(position);
                    Intent intent = new Intent(getActivity(), ExercisePerformActivity.class);
                    intent.putExtra(ExercisePerformActivity.NEW_PERFORMANCE, true);
                    intent.putExtra(ExercisePerformActivity.MODEL_OF_EXERCISE, item);
                    startActivity(intent);
                    dismiss();
                })
        );

        searchEditText = (EditText) dialog.findViewById(R.id.search_editText);
        searchEditText.addTextChangedListener(this);
        searchEditText.setOnEditorActionListener(this);
        return dialog;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_button:
                dismiss();
                break;
            case R.id.clear_text:
                searchEditText.setText("");
                break;
            case R.id.microphone_button:
                Toast.makeText(getActivity(), "", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    public void setDataList(List<ExerciseModel> list) {
        if (list != null) {
            searchRecyclerViewAdapter.setList(list);
            searchRecyclerViewAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void show(FragmentManager manager, String tag) {
        if (manager.findFragmentByTag(SEARCH_DIALOG_TAG) == null) {
            super.show(manager, tag);
        }
    }

    /**
     * Google speech input dialog??????
     * fixme ОЙОЙОЙ
     */
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (s.toString().equals("")) {
            buttonMicrophone.setVisibility(View.VISIBLE);
            buttonClearText.setVisibility(View.INVISIBLE);
            recyclerView.setVisibility(View.INVISIBLE);
        } else {
            buttonMicrophone.setVisibility(View.INVISIBLE);
            buttonClearText.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {
        search(s);
    }

    @Override
    public void setAllExercises(final ArrayList<ExerciseModel> exerciseModels) {
        dataList = exerciseModels;
    }

    void search(CharSequence s) {
        if (dataList != null) {

            List<ExerciseModel> resultList = new ArrayList<>(dataList);
            for (ExerciseModel model :
                    dataList) {
                String str1 = model.getTitle().toLowerCase();
                String str2 = String.valueOf(s).toLowerCase();

                if (!str1.contains(str2)) {
                    resultList.remove(model);
                }
            }
            setDataList(resultList);
        }

    }

    //
    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        return false;
    }
}


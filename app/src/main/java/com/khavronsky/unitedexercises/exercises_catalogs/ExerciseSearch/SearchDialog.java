package com.khavronsky.unitedexercises.exercises_catalogs.ExerciseSearch;


import com.khavronsky.unitedexercises.R;
import com.khavronsky.unitedexercises.exercise_performance.ExercisePerformActivity;
import com.khavronsky.unitedexercises.import_from_grand_project.RecyclerItemClickListener;

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
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class SearchDialog extends DialogFragment
        implements View.OnClickListener, TextWatcher, TextView.OnEditorActionListener {

    private EditText searchEditText;

    private RecyclerView recyclerView; //recycler

    private ImageButton buttonClearText;

    private ImageButton buttonMicrophone;

    private RVAdapter searchRecyclerViewAdapter; //adapter

    private RecyclerView.LayoutManager mLayoutManager;

    public static final int REQ_CODE_SPEECH_INPUT = 100;

    public static final String SEARCH_DIALOG_TAG = "SearchDialog";

    List<String> dataList = new ArrayList<>();

    private IResultListener mListener;

    private List<String> resultList = new ArrayList<>();

//    private onGenerateList onGenerateList;


//    public static SearchDialog newInstance() {
//
//        Bundle args = new Bundle();
////        args.putBundle("ex_search", bundle);
//        SearchDialog fragment = new SearchDialog();
//        fragment.setArguments(args);
//        return fragment;
//    }

    public interface IResultListener {

        void getListID (int id);
    }

    void setListener(IResultListener resultListener){
        this.mListener = resultListener;

    }


    @Override
    public Dialog onCreateDialog(final Bundle savedInstanceState) {

        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        dialog.setContentView(R.layout.search_dialog);

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.getWindow()
                .setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE
                | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

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
        initList();
        setDataList(dataList);
        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(
                        getActivity(), (view, position) -> {
                    String item = ((RVAdapter) recyclerView.getAdapter()).getItem(position);
//                    searchEditText.setText(item);
//                    searchEditText.setSelection(searchEditText.getText().length());
                    startActivity(new Intent(getActivity(), ExercisePerformActivity.class));
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
//                promptSpeechInput();
                break;
        }
    }

    public void setDataList(List<String> list) {
        searchRecyclerViewAdapter = new RVAdapter(list);
        recyclerView.setAdapter(searchRecyclerViewAdapter);
    }

    @Override
    public void show(FragmentManager manager, String tag) {
        if (manager.findFragmentByTag(SEARCH_DIALOG_TAG) == null) {
            super.show(manager, tag);
        }
    }

    /**
     * Showing google speech input dialog
     */
    //region google speech
//    private void promptSpeechInput() {
//        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
//        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
//                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
//        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
//        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
//                "Произнесите название блюда");
//        try {
//            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
//        } catch (ActivityNotFoundException a) {
//            Toast.makeText(getActivity().getApplicationContext(),
//                    "К сожалению, ваш телефон не поддерживает распознавание речи.",
//                    Toast.LENGTH_SHORT).show();
//        }
//    }
//
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        switch (requestCode) {
//            case REQ_CODE_SPEECH_INPUT: {
//                if (resultCode == RESULT_OK && null != data) {
//
//                    ArrayList<String> result = data
//                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
//                    searchEditText.setText(result.get(0));
//                    searchEditText.setSelection(searchEditText.getText().length());
//                    InputMethodManager imm = (InputMethodManager) getActivity()
//                            .getSystemService(Context.INPUT_METHOD_SERVICE);
//                    imm.showSoftInput(searchEditText, InputMethodManager.SHOW_IMPLICIT);
//                }
//                break;
//            }
//
//        }
//    }
    //endregion

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        // до
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        // во время
        if (s.toString().equals("")) {
            buttonMicrophone.setVisibility(View.VISIBLE);
            buttonClearText.setVisibility(View.INVISIBLE);
            recyclerView.setVisibility(View.INVISIBLE);
        } else {
            buttonMicrophone.setVisibility(View.INVISIBLE);
            buttonClearText.setVisibility(View.VISIBLE);
//            if (onGenerateList != null) {
//                setDataList(onGenerateList.generateList(s));
//            }
            recyclerView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {
        search(s);
        // после
    }

    void search(CharSequence s) {
        resultList = new ArrayList<>(dataList);
        for (String str :
                dataList) {
            String str2 = str.toLowerCase();
            String s2 = String.valueOf(s).toLowerCase();
            if (!str2.contains(s2)) {
                resultList.remove(str);
            }
        }
        searchRecyclerViewAdapter.setList(resultList);
    }
//
//    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
//
//            String request = searchEditText.getText().toString();
//            if (request.equals("") || request.equals(" ")) {
//                Toast.makeText(getActivity(), "Название блюда не должно быть пустым!", Toast.LENGTH_SHORT).show();
//            } else {
//                insertNewSearchRequest(searchEditText.getText().toString());
//                Intent intent = new Intent(getActivity(), FoodCatalogListOfItemsActivity.class);
//                intent.putExtra(FoodCatalogItemFrg.BUNDLE_FOOD_DATE_TAG,
//                        getArguments().getBundle(FoodCatalogItemFrg.BUNDLE_FOOD_DATE_TAG));
//                intent.putExtra(FoodCatalogListOfItemsActivity.SEARCH_TEXT_KEY,
//                        searchEditText.getText().toString());
//                intent.putExtra(FoodCatalogListOfItemsActivity.SEARCH_FOOD_TYPE_KEY,
//                        FoodCatalogListOfItemsViewModel.SearchType.SEARCH);
//                startActivity(intent);
//            }
//            return true;
//        }
        return false;
    }

    private void initList() {
        dataList.add("Австралия");
        dataList.add("Китай");
        dataList.add("Япония");
        dataList.add("Испания");
        dataList.add("Монако");
        dataList.add("Канада");
        dataList.add("Австрия");
        dataList.add("Великобритания");
        dataList.add("Венгрия");
        dataList.add("Бельгия");
        dataList.add("Италия");
        dataList.add("Сингапур");
        dataList.add("Малайзия");
        dataList.add("Япония");
        dataList.add("США");
        dataList.add("Мексика");
        dataList.add("Бразилия");
        dataList.add("Абу-Даби");


    }
//
//    protected void insertNewSearchRequest(String request) {
////        if(!blockInsertToDb) {
//        request = request.toLowerCase();
//
//        OneTrakLifeApplication.searchRequestMap.put(request, System.currentTimeMillis());
//        //searchBox.setVariantList(FoodActivity.searchRequestMap);
//
//        final ContentProviderOperation.Builder builder;
//        final ArrayList<ContentProviderOperation> batch = new ArrayList<ContentProviderOperation>();
//
//        builder = ContentProviderOperation.newInsert(LastSearchRequestContract.getContentURI());
//        builder.withValue(LastSearchRequestContract.Contract.CHANGED.name(), System.currentTimeMillis());
//        builder.withValue(LastSearchRequestContract.Contract.REQUEST.name(), request);
//
//        batch.add(builder.build());
//
//        try {
//            getActivity().getContentResolver().applyBatch(OneTrakLifeConsts.CONTENT_AUTHORITY, batch);
//        } catch (RemoteException e) {
//            e.printStackTrace();
//        } catch (OperationApplicationException e) {
//            e.printStackTrace();
//        }
////        }
//    }
}


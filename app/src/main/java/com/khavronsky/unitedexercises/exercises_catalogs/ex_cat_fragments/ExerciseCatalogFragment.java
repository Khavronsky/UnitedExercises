package com.khavronsky.unitedexercises.exercises_catalogs.ex_cat_fragments;

import com.khavronsky.unitedexercises.R;
import com.khavronsky.unitedexercises.exercises_catalogs.ex_cat_adapters.AdapterToExCatalogRecycler;
import com.khavronsky.unitedexercises.exercises_models.ModelOfItemForExCatalog;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.khavronsky.unitedexercises.exercises_models.ModelOfItemForExCatalog.ItemType.CAPITAL_LETTER;
import static com.khavronsky.unitedexercises.exercises_models.ModelOfItemForExCatalog.ItemType.EXERCISE_TITLE;

public class ExerciseCatalogFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container,
            @Nullable final Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.exercise_catalog_fragment, container, false);
        Log.d("KhS", "onCreateView: ");
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.exercise_catalog_recycler);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        AdapterToExCatalogRecycler adapterToExCatalogRecycler = new AdapterToExCatalogRecycler();

        adapterToExCatalogRecycler.setExerciseCatalog(convertToExCatModel((ArrayList<String>) createFakeExList()));

//        adapterToExCatalogRecycler.setExerciseCatalog(new ArrayList<ModelOfItemForExCatalog>() {
//            {
//                add(new ModelOfItemForExCatalog("A", CAPITAL_LETTER));
//
//                for (int i = 0; i < 20; i++) {
//                    add(new ModelOfItemForExCatalog(("abc" + i), EXERCISE_TITLE));
//
//                }
//            }
//        });

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapterToExCatalogRecycler);
        adapterToExCatalogRecycler.notifyDataSetChanged();
        return view;
    }

    List<ModelOfItemForExCatalog> convertToExCatModel(ArrayList<String> exList) {
        List<ModelOfItemForExCatalog> list = new ArrayList<>();
        Collections.sort(exList);
        String x = "";
        for (String s :
                exList) {
            if (!x.equals(s.substring(0, 1))) {
                x = s.substring(0, 1);
                list.add(new ModelOfItemForExCatalog(x, CAPITAL_LETTER));
            }
            list.add(new ModelOfItemForExCatalog(s, EXERCISE_TITLE));
        }
        return list;
    }

    ArrayList createFakeExList(){
        return new ArrayList<String>(){{
            add("qwe");
            add("qrt");
            add("wty");
            add("wyu");
            add("asdf");
            add("afh");
            add("xcvb");
            add("xbmmb");
            add("sfdg");
            add("sgf");
            add("dgh");
            add("zxcv");
            add("zvb");
            add("5sgfds");
            add("hjkl");
            add("hl;");
            add("fgjh");
            add("vnm");
            add("nm,.");
            add("hjkl");
            add("djh");
            add("cbvn");
            add("uiop");
            add("4fghj");
            add("4fghj");
            add("4fghj");
            add("0gfds");
            add("5sgfds");
            add("5sgfds");
            add("1");
            add("1sfd");
            add("5sgfds");
            add("1ahafhda");
            add("1wer");
            add("1hfdadfh");
            add("g");
            add("0gfds");
            add("0gfds");
            add("0gfds");
        }};
    }

}

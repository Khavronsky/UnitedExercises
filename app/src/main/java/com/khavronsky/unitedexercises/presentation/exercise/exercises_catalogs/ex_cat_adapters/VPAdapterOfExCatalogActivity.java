package com.khavronsky.unitedexercises.presentation.exercise.exercises_catalogs.ex_cat_adapters;

import com.khavronsky.unitedexercises.presentation.exercise.exercises_catalogs.IRefreshableFragment;
import com.khavronsky.unitedexercises.presentation.exercise.exercises_catalogs.custom_ex_catalog.CustomExercisesFragment;
import com.khavronsky.unitedexercises.presentation.exercise.exercises_catalogs.default_ex_catalog.ExerciseCatalogFragment;
import com.khavronsky.unitedexercises.presentation.exercise.exercises_catalogs.recent_ex_catalog.RecentExercisesFragment;
import com.khavronsky.unitedexercises.presentation.exercise.exercises_models.ExerciseModel;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class VPAdapterOfExCatalogActivity extends FragmentPagerAdapter {

    private Fragment mFragment1;

    private Fragment mFragment2;

    private Fragment mFragment3;

    private String[] tabsTitle = {"НЕДАВНИЕ", "МОИ УПРАЖНЕНИЯ", "КАТАЛОГ"};

    private ExerciseModel.ExerciseType mType;

    public VPAdapterOfExCatalogActivity(final FragmentManager fm, final ExerciseModel.ExerciseType type) {
        super(fm);
        Bundle args = new Bundle();
        mType = type;
        args.putSerializable("type", mType);
        mFragment1 = new ExerciseCatalogFragment();
        mFragment1.setArguments(args);
        mFragment2 = new CustomExercisesFragment();
        mFragment2.setArguments(args);
        mFragment3 = new RecentExercisesFragment();
        mFragment3.setArguments(args);
    }

    public VPAdapterOfExCatalogActivity setType(
            final ExerciseModel.ExerciseType type) {
        mType = type;
        return this;
    }
    
    public void refreshPager(final ExerciseModel.ExerciseType type){
        mType = type;
        recreateFragment((IRefreshableFragment) mFragment1);
        recreateFragment((IRefreshableFragment) mFragment2);
        recreateFragment((IRefreshableFragment) mFragment3);
    }

    void recreateFragment(IRefreshableFragment fragment){
        fragment.refresh(mType);
    }

    @Override
    public int getItemPosition(final Object object) {
        return POSITION_NONE;
    }

    @Override
    public Fragment getItem(final int position) {
        switch (position) {
            case 0:
                return mFragment3;
            case 1:
                return mFragment2;
            case 2:
                return mFragment1;
        }
        return mFragment2;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(final int position) {
        return tabsTitle[position];
    }

}

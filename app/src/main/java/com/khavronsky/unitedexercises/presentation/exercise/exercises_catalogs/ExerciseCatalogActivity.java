package com.khavronsky.unitedexercises.presentation.exercise.exercises_catalogs;

import com.khavronsky.unitedexercises.R;
import com.khavronsky.unitedexercises.presentation.exercise.exercises_catalogs.ExerciseSearch.SearchDialog;
import com.khavronsky.unitedexercises.presentation.exercise.exercises_catalogs.ex_cat_adapters.VPAdapterOfExCatalogActivity;
import com.khavronsky.unitedexercises.presentation.exercise.exercises_models.ExerciseModel;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ExerciseCatalogActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TOOLBAR_TITLE_CARDIO = "Кардио упражнения";

    private static final String TOOLBAR_TITLE_POWER = "Силовые упражнения";

    @BindView(R.id.cust_toolbar)
    Toolbar mToolbar;

    @BindView(R.id.ex_catalog_toolbar_title)
    TextView mToolbarTitle;

    @BindView(R.id.ex_catalog_toolbar_click_area)
    View mToolbarClickArea;

    @BindView(R.id.ex_catalog_dropdown_menu)
    ImageView mDropDownMenu;

    @BindView(R.id.ex_cat_anchor)
    View mAnchor;

    private ExerciseModel.ExerciseType mExerciseType;

    private VPAdapterOfExCatalogActivity pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ex_catalog_activity);
        ButterKnife.bind(this);
        if (getIntent().getExtras().getSerializable("type") != null) {
            mExerciseType = (ExerciseModel.ExerciseType) getIntent().getExtras().getSerializable("type");
        }
        setToolbar();
        ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        pagerAdapter = new VPAdapterOfExCatalogActivity(getSupportFragmentManager(), mExerciseType);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setOffscreenPageLimit(3);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setSelectedTabIndicatorColor(ContextCompat.getColor(this, R.color.new_balance));
        viewPager.addOnPageChangeListener(getListener());
    }

    @NonNull
    private ViewPager.OnPageChangeListener getListener() {
        return new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(final int position, final float positionOffset,
                    final int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(final int position) {
            }

            @Override
            public void onPageScrollStateChanged(final int state) {
            }
        };
    }

    void setToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.cust_toolbar);
        mToolbar.setTitle("");
        mToolbarTitle.setText(mExerciseType == ExerciseModel.ExerciseType.CARDIO ?
                TOOLBAR_TITLE_CARDIO
                : TOOLBAR_TITLE_POWER);
        setSupportActionBar(mToolbar);
        mToolbar.inflateMenu(R.menu.search_menu);
        mToolbar.setNavigationOnClickListener(this);
        mToolbar.setNavigationIcon(R.drawable.arrow_back);
    }

    @Override
    public void onClick(final View v) {
        onBackPressed();
    }

    @OnClick(R.id.ex_catalog_toolbar_click_area)
    public void showSelectExCatMenu() {
        PopupMenu popupMenu = new PopupMenu(this, mAnchor, Gravity.END);
        popupMenu.inflate(R.menu.switch_menu);
        popupMenu
                .setOnMenuItemClickListener(item -> {
                    switch (item.getItemId()) {
                        case R.id.select_catalog_by_type_cardio:
                            mExerciseType = ExerciseModel.ExerciseType.CARDIO;
                            mToolbarTitle.setText(TOOLBAR_TITLE_CARDIO);
                            refreshVPager();
                            popupMenu.dismiss();
                            return true;
                        case R.id.select_catalog_by_type_power:
                            mExerciseType = ExerciseModel.ExerciseType.POWER;
                            mToolbarTitle.setText(TOOLBAR_TITLE_POWER);
                            refreshVPager();
                            popupMenu.dismiss();
                            return true;
                        default:
                            return false;
                    }
                });
        popupMenu.show();
    }

    public void refreshVPager(){
        pagerAdapter.refreshPager(mExerciseType);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.search) {
            Bundle args = new Bundle();
            args.putSerializable(SearchDialog.EX_TYPE_TAG, mExerciseType);
            SearchDialog searchDialog = new SearchDialog();
            searchDialog.setArguments(args);
            searchDialog.show(getFragmentManager(), "searchDialog");
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
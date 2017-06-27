package com.khavronsky.unitedexercises.exercises_catalogs;

import com.khavronsky.unitedexercises.R;
import com.khavronsky.unitedexercises.exercises_catalogs.ExerciseSearch.SearchDialog;
import com.khavronsky.unitedexercises.exercises_catalogs.ex_cat_adapters.VPAdapterOfMainExerciseScreen;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class ExerciseCatalogActivity extends AppCompatActivity implements View.OnClickListener  {

    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("KhS", "onCreate: ExerciseCatalogActivity");
        setContentView(R.layout.ex_catalog_activity);
        setToolbar();
        ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        PagerAdapter pagerAdapter = new VPAdapterOfMainExerciseScreen(getSupportFragmentManager());
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
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("Кардио упражнение");
        setSupportActionBar(mToolbar);
        mToolbar.inflateMenu(R.menu.search_menu);
        mToolbar.setNavigationOnClickListener(this);
        mToolbar.setNavigationIcon(R.drawable.arrow_back);
    }

    @Override
    public void onClick(final View v) {
        onBackPressed();
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
                Toast.makeText(this, "SEARCH", Toast.LENGTH_SHORT).show();
            SearchDialog searchDialog = new SearchDialog();
            searchDialog.show(getFragmentManager(),"searchDialog");
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
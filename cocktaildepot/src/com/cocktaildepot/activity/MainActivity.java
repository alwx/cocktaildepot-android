package com.cocktaildepot.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import com.cocktaildepot.R;
import com.cocktaildepot.base.BasicActivity;
import com.cocktaildepot.fragment.CategoriesFragment;
import com.cocktaildepot.fragment.IngredientsFragment;
import com.cocktaildepot.fragment.SettingsFragment;
import com.cocktaildepot.utilities.Constants;
import com.cocktaildepot.utilities.PagesAdapter;
import com.viewpagerindicator.TitlePageIndicator;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BasicActivity implements Constants {
    ViewPager mPager;
    TitlePageIndicator mIndicator;
    List<Fragment> fragments = new ArrayList<Fragment>();
    List<String> strings = new ArrayList<String>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPager = (ViewPager) findViewById(R.id.pager);
        mIndicator = (TitlePageIndicator) findViewById(R.id.indicator);

        initFragments();
    }

    private void initFragments() {
        addFragment(getString(R.string.main_ingredients), new IngredientsFragment());
        addFragment(getString(R.string.main_categories), new CategoriesFragment());
        addFragment(getString(R.string.main_settings), new SettingsFragment());

        PagesAdapter adapter = new PagesAdapter(getSupportFragmentManager(), fragments, strings);
        mPager.setAdapter(adapter);
        mPager.setCurrentItem(1);
        mIndicator.setViewPager(mPager);
    }

    private void addFragment(String title, Fragment fragment) {
        fragments.add(fragment);
        strings.add(title);
    }
}

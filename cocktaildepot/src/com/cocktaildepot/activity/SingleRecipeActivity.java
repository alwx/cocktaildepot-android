package com.cocktaildepot.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import com.cocktaildepot.R;
import com.cocktaildepot.base.BasicActivity;
import com.cocktaildepot.fragment.CategoriesFragment;
import com.cocktaildepot.fragment.CommentsFragment;
import com.cocktaildepot.fragment.SingleRecipeFragment;
import com.cocktaildepot.utilities.Constants;
import com.cocktaildepot.utilities.PagesAdapter;
import com.viewpagerindicator.TitlePageIndicator;

import java.util.ArrayList;
import java.util.List;

public class SingleRecipeActivity extends BasicActivity implements Constants {
    ViewPager mPager;
    TitlePageIndicator mIndicator;
    List<Fragment> fragments = new ArrayList<Fragment>();
    List<String> strings = new ArrayList<String>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_recipe);

        overridePendingTransition(R.anim.fadein, R.anim.fadeout);

        mPager = (ViewPager) findViewById(R.id.pager);
        mIndicator = (TitlePageIndicator) findViewById(R.id.indicator);

        initFragments();
    }

    @Override
    public void onPause() {
        super.onPause();
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
    }

    private void initFragments() {
        addFragment(getString(R.string.recipe_main), new SingleRecipeFragment());
        addFragment(getString(R.string.recipe_comments), new CommentsFragment());

        PagesAdapter adapter = new PagesAdapter(getSupportFragmentManager(), fragments, strings);
        mPager.setAdapter(adapter);
        mPager.setCurrentItem(0);
        mIndicator.setViewPager(mPager);
    }

    private void addFragment(String title, Fragment fragment) {
        fragments.add(fragment);
        strings.add(title);
    }
}

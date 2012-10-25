package com.cocktaildepot.activity;

import android.os.Bundle;
import com.cocktaildepot.R;
import com.cocktaildepot.base.BasicActivity;
import com.cocktaildepot.utilities.Constants;

public class RecipesActivity extends BasicActivity implements Constants {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes);

        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
    }

    @Override
    public void onPause() {
        super.onPause();
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
    }
}

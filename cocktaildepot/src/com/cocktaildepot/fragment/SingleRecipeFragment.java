package com.cocktaildepot.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.cocktaildepot.R;
import com.cocktaildepot.activity.RecipesActivity;
import com.cocktaildepot.activity.SingleRecipeActivity;
import com.cocktaildepot.base.BasicActivity;
import com.cocktaildepot.models.Recipe;
import com.cocktaildepot.models.Response;
import com.cocktaildepot.utilities.Constants;
import com.cocktaildepot.utilities.LocalUtilities;
import com.cocktaildepot.utilities.ServerUtilities;

import java.io.FileInputStream;

public class SingleRecipeFragment extends Fragment implements Constants {
    Context mContext;
    AsyncTask<Void, Void, Response> connection;
    int recipeId;
    Recipe currentRecipe;

    LinearLayout loaderPlaceholder;
    ProgressBar loaderIndicator;
    TextView loaderFailed;
    Button loaderRepeat;

    ScrollView recipe;
    ImageView recipeImage;
    TextView recipeName, recipeDescription;
    Button recipeCategoryName;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext = getActivity().getApplicationContext();
        recipeId = getActivity().getIntent().getIntExtra(RECIPE_ID_TAG, 0);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_single_recipe, null);

        loaderPlaceholder = (LinearLayout) view.findViewById(R.id.loaderPlaceholder);
        loaderIndicator = (ProgressBar) view.findViewById(R.id.loaderIndicator);
        loaderFailed = (TextView) view.findViewById(R.id.loaderFailed);
        loaderRepeat = (Button) view.findViewById(R.id.loaderRepeat);
        recipe = (ScrollView) view.findViewById(R.id.recipe);
        recipeImage = (ImageView) view.findViewById(R.id.recipeImage);
        recipeName = (TextView) view.findViewById(R.id.recipeName);
        recipeCategoryName = (Button) view.findViewById(R.id.recipeCategoryName);
        recipeDescription = (TextView) view.findViewById(R.id.recipeDescription);
        load();

        recipe.setVerticalScrollBarEnabled(false);
        loaderRepeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                load();
            }
        });

        return view;
    }

    private void load() {
        loaderPlaceholder.setVisibility(View.VISIBLE);
        recipe.setVisibility(View.GONE);

        connection = new AsyncTask<Void, Void, Response>() {
            @Override
            protected Response doInBackground(Void... voids) {
                return ServerUtilities.getRecipe(recipeId);
            }

            protected void onPostExecute(Response response) {
                super.onPostExecute(response);

                if (response.getCode() == RESPONSE_CODE_OK) {
                    currentRecipe = (Recipe) response.getObject();

                    loaderPlaceholder.setVisibility(View.GONE);
                    recipe.setVisibility(View.VISIBLE);

                    recipeName.setText(currentRecipe.getName());
                    recipeDescription.setText(currentRecipe.getDescription());
                    recipeCategoryName.setText(currentRecipe.getCategoryName());
                    recipeCategoryName.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(mContext, RecipesActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.putExtra(CATEGORY_ID_TAG, currentRecipe.getCategoryId());
                            mContext.startActivity(intent);
                        }
                    });

                    LocalUtilities.setImageFromURL(currentRecipe.getFullImage(), recipeImage);
                } else {
                    loaderIndicator.setVisibility(View.GONE);
                    loaderFailed.setVisibility(View.VISIBLE);
                    loaderRepeat.setVisibility(View.VISIBLE);

                    ((BasicActivity) getActivity()).errorsHandling(response, false);
                }
            }
        }.execute((Void) null);
    }
}
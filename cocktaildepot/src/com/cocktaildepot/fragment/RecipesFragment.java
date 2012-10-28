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
import com.cocktaildepot.activity.SingleRecipeActivity;
import com.cocktaildepot.base.BasicActivity;
import com.cocktaildepot.models.Recipe;
import com.cocktaildepot.models.Response;
import com.cocktaildepot.utilities.Constants;
import com.cocktaildepot.utilities.LocalUtilities;
import com.cocktaildepot.utilities.ServerUtilities;

import java.util.List;
import java.util.Map;

public class RecipesFragment extends Fragment implements Constants {
    Context mContext;
    AsyncTask<Void, Void, Response> connection;
    List<Recipe> recipes;
    int ingredientId, categoryId;

    LinearLayout loaderPlaceholder;
    ProgressBar loaderIndicator;
    TextView loaderFailed;
    Button loaderRepeat;
    ListView recipesList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext = getActivity().getApplicationContext();
        ingredientId = getActivity().getIntent().getIntExtra(INGREDIENT_ID_TAG, 0);
        categoryId = getActivity().getIntent().getIntExtra(CATEGORY_ID_TAG, 0);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipes, null);

        loaderPlaceholder = (LinearLayout) view.findViewById(R.id.loaderPlaceholder);
        loaderIndicator = (ProgressBar) view.findViewById(R.id.loaderIndicator);
        loaderFailed = (TextView) view.findViewById(R.id.loaderFailed);
        loaderRepeat = (Button) view.findViewById(R.id.loaderRepeat);
        recipesList = (ListView) view.findViewById(R.id.recipesList);
        load();

        recipesList.setOnItemClickListener(clickListener);
        loaderRepeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                load();
            }
        });

        return view;
    }

    AdapterView.OnItemClickListener clickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            Object o = recipesList.getItemAtPosition(i);
            if (o != null) {
                Intent intent = new Intent(mContext, SingleRecipeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra(RECIPE_ID_TAG, ((Recipe) o).getId());
                mContext.startActivity(intent);
            }
        }
    };

    private void load() {
        loaderPlaceholder.setVisibility(View.VISIBLE);
        recipesList.setVisibility(View.GONE);

        connection = new AsyncTask<Void, Void, Response>() {
            @Override
            protected Response doInBackground(Void... voids) {
                return ServerUtilities.getRecipes(categoryId, ingredientId);
            }

            protected void onPostExecute(Response response) {
                super.onPostExecute(response);

                if (response.getCode() == RESPONSE_CODE_OK) {
                    recipes = (List<Recipe>) response.getObject();

                    if (recipes.size() > 0) {
                        loaderPlaceholder.setVisibility(View.GONE);
                        recipesList.setVisibility(View.VISIBLE);

                        recipesList.setAdapter(new RecipeAdapter(mContext));
                    } else {
                        loaderIndicator.setVisibility(View.GONE);
                        loaderFailed.setVisibility(View.VISIBLE);
                        loaderFailed.setText(getString(R.string.recipes_empty));
                    }
                } else {
                    loaderIndicator.setVisibility(View.GONE);
                    loaderFailed.setVisibility(View.VISIBLE);
                    loaderRepeat.setVisibility(View.VISIBLE);

                    ((BasicActivity) getActivity()).errorsHandling(response, false);
                }
            }
        }.execute((Void) null);
    }

    private class RecipeAdapter extends BaseAdapter {
        Context mContext;
        LayoutInflater lInflater;

        RecipeAdapter(Context context) {
            mContext = context;
            lInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return recipes.size();
        }

        @Override
        public Object getItem(int position) {
            return recipes.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            final RecipeHolder holder;

            View listView = view;
            if (listView == null) {
                listView = lInflater.inflate(R.layout.item_recipe, null, true);
                holder = new RecipeHolder();
                holder.name = (TextView) listView.findViewById(R.id.name);
                holder.image = (ImageView) listView.findViewById(R.id.image);
                holder.ingredients = (TextView) listView.findViewById(R.id.ingredients);

                listView.setTag(holder);
            } else {
                holder = (RecipeHolder) listView.getTag();
            }

            final Recipe currentRecipe = recipes.get(i);
            holder.name.setText(currentRecipe.getName());
            LocalUtilities.setImageFromURL(currentRecipe.getThumbImage(), holder.image);

            // load ingredients
            StringBuilder strIngredients = new StringBuilder();
            Map<Integer, String> ingredients = currentRecipe.getIngredients();
            for (Map.Entry<Integer, String> entry : ingredients.entrySet()) {
                strIngredients.append(entry.getValue());
                strIngredients.append(", ");
            }
            holder.ingredients.setText(strIngredients.substring(0, strIngredients.length() - 2));
            return listView;
        }
    }

    private static class RecipeHolder {
        TextView name;
        ImageView image;
        TextView ingredients;
    }
}
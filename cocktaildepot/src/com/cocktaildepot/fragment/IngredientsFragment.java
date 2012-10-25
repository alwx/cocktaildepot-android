package com.cocktaildepot.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.cocktaildepot.R;
import com.cocktaildepot.activity.RecipesActivity;
import com.cocktaildepot.base.BasicActivity;
import com.cocktaildepot.models.Ingredient;
import com.cocktaildepot.models.Response;
import com.cocktaildepot.utilities.Constants;
import com.cocktaildepot.utilities.LocalUtilities;
import com.cocktaildepot.utilities.ServerUtilities;

import java.util.List;

public class IngredientsFragment extends Fragment implements Constants {
    Context mContext;
    AsyncTask<Void, Void, Response> connection;
    List<Ingredient> ingredients;

    LinearLayout loaderPlaceholder;
    ProgressBar loaderIndicator;
    TextView loaderFailed;
    Button loaderRepeat;
    ListView ingredientsList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext = getActivity().getApplicationContext();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_categories, null);

        loaderPlaceholder = (LinearLayout) view.findViewById(R.id.loaderPlacholder);
        loaderIndicator = (ProgressBar) view.findViewById(R.id.loaderIndicator);
        loaderFailed = (TextView) view.findViewById(R.id.loaderFailed);
        loaderRepeat = (Button) view.findViewById(R.id.loaderRepeat);
        ingredientsList = (ListView) view.findViewById(R.id.categoriesList);
        load();

        ingredientsList.setOnItemClickListener(clickListener);
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
            Object o = ingredientsList.getItemAtPosition(i);
            if (o != null) {
                Intent intent = new Intent(mContext, RecipesActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra(INGREDIENT_ID_TAG, ((Ingredient) o).getId());
                mContext.startActivity(intent);
            }
        }
    };

    private void load() {
        loaderPlaceholder.setVisibility(View.VISIBLE);
        ingredientsList.setVisibility(View.GONE);

        connection = new AsyncTask<Void, Void, Response>() {
            @Override
            protected Response doInBackground(Void... voids) {
                return ServerUtilities.getIngredients();
            }

            protected void onPostExecute(Response response) {
                super.onPostExecute(response);

                if (response.getCode() == RESPONSE_CODE_OK) {
                    loaderPlaceholder.setVisibility(View.GONE);
                    ingredientsList.setVisibility(View.VISIBLE);

                    ingredients = (List<Ingredient>) response.getObject();
                    ingredientsList.setAdapter(new IngredientAdapter(mContext));
                } else {
                    loaderIndicator.setVisibility(View.GONE);
                    loaderFailed.setVisibility(View.VISIBLE);
                    loaderRepeat.setVisibility(View.VISIBLE);

                    ((BasicActivity) getActivity()).errorsHandling(response, false);
                }
            }
        }.execute((Void) null);
    }

    private class IngredientAdapter extends BaseAdapter {
        Context mContext;
        LayoutInflater lInflater;

        IngredientAdapter(Context context) {
            mContext = context;
            lInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return ingredients.size();
        }

        @Override
        public Object getItem(int position) {
            return ingredients.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            final IngredientHolder holder;

            View listView = view;
            if (listView == null) {
                listView = lInflater.inflate(R.layout.item_category, null, true);
                holder = new IngredientHolder();
                holder.name = (TextView) listView.findViewById(R.id.name);
                holder.description = (TextView) listView.findViewById(R.id.description);
                holder.image = (ImageView) listView.findViewById(R.id.image);

                listView.setTag(holder);
            } else {
                holder = (IngredientHolder) listView.getTag();
            }

            final Ingredient currentIngredient = ingredients.get(i);
            holder.name.setText(currentIngredient.getName());
            holder.description.setText(currentIngredient.getDescription());
            holder.image.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.default_image));
            LocalUtilities.setImageFromURL(currentIngredient.getImage(), holder.image);

            return listView;
        }
    }

    private static class IngredientHolder {
        TextView name;
        TextView description;
        ImageView image;
    }
}

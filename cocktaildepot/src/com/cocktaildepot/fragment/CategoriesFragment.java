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
import com.cocktaildepot.base.BasicActivity;
import com.cocktaildepot.models.Category;
import com.cocktaildepot.models.Response;
import com.cocktaildepot.utilities.Constants;
import com.cocktaildepot.utilities.LocalUtilities;
import com.cocktaildepot.utilities.ServerUtilities;

import java.util.List;

import static android.widget.AdapterView.OnItemClickListener;

public class CategoriesFragment extends Fragment implements Constants {
    Context mContext;
    AsyncTask<Void, Void, Response> connection;
    List<Category> categories;

    LinearLayout loaderPlaceholder;
    ProgressBar loaderIndicator;
    TextView loaderFailed;
    Button loaderRepeat;
    ListView categoriesList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext = getActivity().getApplicationContext();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_categories, null);

        loaderPlaceholder = (LinearLayout) view.findViewById(R.id.loaderPlaceholder);
        loaderIndicator = (ProgressBar) view.findViewById(R.id.loaderIndicator);
        loaderFailed = (TextView) view.findViewById(R.id.loaderFailed);
        loaderRepeat = (Button) view.findViewById(R.id.loaderRepeat);
        categoriesList = (ListView) view.findViewById(R.id.categoriesList);
        load();

        categoriesList.setOnItemClickListener(clickListener);
        loaderRepeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                load();
            }
        });

        return view;
    }

    OnItemClickListener clickListener = new OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            Object o = categoriesList.getItemAtPosition(i);
            if (o != null) {
                Intent intent = new Intent(mContext, RecipesActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra(CATEGORY_ID_TAG, ((Category) o).getId());
                mContext.startActivity(intent);
            }
        }
    };

    private void load() {
        loaderPlaceholder.setVisibility(View.VISIBLE);
        categoriesList.setVisibility(View.GONE);

        connection = new AsyncTask<Void, Void, Response>() {
            @Override
            protected Response doInBackground(Void... voids) {
                return ServerUtilities.getCategories();
            }

            protected void onPostExecute(Response response) {
                super.onPostExecute(response);

                if (response.getCode() == RESPONSE_CODE_OK) {
                    loaderPlaceholder.setVisibility(View.GONE);
                    categoriesList.setVisibility(View.VISIBLE);

                    categories = (List<Category>) response.getObject();
                    categoriesList.setAdapter(new CategoryAdapter(mContext));
                } else {
                    loaderIndicator.setVisibility(View.GONE);
                    loaderFailed.setVisibility(View.VISIBLE);
                    loaderRepeat.setVisibility(View.VISIBLE);

                    ((BasicActivity) getActivity()).errorsHandling(response, false);
                }
            }
        }.execute((Void) null);
    }

    private class CategoryAdapter extends BaseAdapter {
        Context mContext;
        LayoutInflater lInflater;

        CategoryAdapter(Context context) {
            mContext = context;
            lInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return categories.size();
        }

        @Override
        public Object getItem(int position) {
            return categories.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            final CategoryHolder holder;

            View listView = view;
            if (listView == null) {
                listView = lInflater.inflate(R.layout.item_category, null, true);
                holder = new CategoryHolder();
                holder.name = (TextView) listView.findViewById(R.id.name);
                holder.description = (TextView) listView.findViewById(R.id.description);
                holder.image = (ImageView) listView.findViewById(R.id.image);

                listView.setTag(holder);
            } else {
                holder = (CategoryHolder) listView.getTag();
            }

            final Category currentCategory = categories.get(i);
            holder.name.setText(currentCategory.getName());
            holder.description.setText(currentCategory.getDescription());
            LocalUtilities.setImageFromURL(currentCategory.getImage(), holder.image);

            return listView;
        }
    }

    private static class CategoryHolder {
        TextView name;
        TextView description;
        ImageView image;
    }
}

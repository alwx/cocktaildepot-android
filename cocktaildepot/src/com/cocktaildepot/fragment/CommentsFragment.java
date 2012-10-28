package com.cocktaildepot.fragment;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.cocktaildepot.R;
import com.cocktaildepot.models.Response;
import com.cocktaildepot.utilities.Constants;

public class CommentsFragment extends Fragment implements Constants {
    Context mContext;
    AsyncTask<Void, Void, Response> connection;
    int recipeId;

    LinearLayout loaderPlaceholder;
    ProgressBar loaderIndicator;
    TextView loaderFailed;
    Button loaderRepeat;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        recipeId = getActivity().getIntent().getIntExtra(RECIPE_ID_TAG, 0);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_single_recipe, null);

        loaderPlaceholder = (LinearLayout) view.findViewById(R.id.loaderPlaceholder);
        loaderIndicator = (ProgressBar) view.findViewById(R.id.loaderIndicator);
        loaderFailed = (TextView) view.findViewById(R.id.loaderFailed);
        loaderRepeat = (Button) view.findViewById(R.id.loaderRepeat);

        return view;
    }
}
package com.cocktaildepot.fragment;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.cocktaildepot.R;
import com.cocktaildepot.base.BasicActivity;
import com.cocktaildepot.models.Ingredient;
import com.cocktaildepot.models.Response;
import com.cocktaildepot.utilities.Constants;
import com.cocktaildepot.utilities.LocalUtilities;
import com.cocktaildepot.utilities.ServerUtilities;
import com.cocktaildepot.utilities.preferences.EntryAdapter;
import com.cocktaildepot.utilities.preferences.EntryItem;
import com.cocktaildepot.utilities.preferences.Item;
import com.cocktaildepot.utilities.preferences.SectionItem;

import java.util.ArrayList;
import java.util.List;

public class SettingsFragment extends Fragment implements Constants {
    Context mContext;
    ArrayList<Item> items = new ArrayList<Item>();

    ListView settingsList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext = getActivity().getApplicationContext();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, null);

        settingsList = (ListView) view.findViewById(R.id.settingsList);
        generateSettingsList();
        settingsList.setVerticalScrollBarEnabled(false);

        return view;
    }

    private void generateSettingsList() {
        items.clear();
        items.add(new SectionItem(getString(R.string.settings_social)));
        items.add(new EntryItem(getString(R.string.settings_social_vk), null, 0));
        items.add(new EntryItem(getString(R.string.settings_social_twitter), null, 0));
        items.add(new EntryItem(getString(R.string.settings_social_facebook), null, 0));

        final EntryAdapter adapter = new EntryAdapter(getActivity(), items);
        settingsList.setAdapter(adapter);
    }
}

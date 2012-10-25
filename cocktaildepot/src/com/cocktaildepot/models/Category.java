package com.cocktaildepot.models;

import com.cocktaildepot.utilities.Constants;
import com.cocktaildepot.utilities.JSON;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Category implements JSON {
    int id;
    String name, description, image;

    public Category(JSONObject response) throws JSONException {
        this.id = response.getInt(CATEGORY_ID);
        this.name = response.getString(CATEGORY_NAME);
        this.description = response.getString(CATEGORY_DESCRIPTION);
        this.image = response.getString(CATEGORY_IMAGE);
    }

    public static List<Category> createCategoryList(JSONArray jsonArray) throws JSONException {
        List<Category> list = new ArrayList<Category>();
        int numCategories = 0;

        for (int i = 0; i < jsonArray.length(); i++) {
            list.add(new Category(jsonArray.getJSONObject(i)));
        }
        return list;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getImage() {
        return Constants.API_URL + image;
    }
}

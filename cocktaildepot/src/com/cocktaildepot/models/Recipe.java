package com.cocktaildepot.models;

import com.cocktaildepot.utilities.Constants;
import com.cocktaildepot.utilities.JSON;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Recipe implements JSON {
    int id, category_id;
    String name, description, thumb_image, full_image, category_name;
    Map<Integer, String> ingredients = new HashMap<Integer, String>();
    Map<Integer, String> tags = new HashMap<Integer, String>();

    public Recipe(JSONObject response) throws JSONException {
        JSONObject category = response.getJSONObject(RECIPE_CATEGORY);
        JSONArray ingredientsArray = response.getJSONArray(RECIPE_INGREDIENTS);
        JSONArray tagsArray = response.getJSONArray(RECIPE_TAGS);

        this.id = response.getInt(RECIPE_ID);
        this.name = response.getString(RECIPE_NAME);
        this.description = response.optString(RECIPE_DESCRIPTION, "");
        this.thumb_image = response.optString(RECIPE_THUMB_IMAGE);
        this.full_image = response.optString(RECIPE_FULL_IMAGE, "");
        this.category_id = category.getInt(RECIPE_SUBITEM_ID);
        this.category_name = category.getString(RECIPE_SUBITEM_NAME);

        for (int i = 0; i < ingredientsArray.length(); i++) {
            JSONObject obj = ingredientsArray.getJSONObject(i);
            this.ingredients.put(obj.getInt(RECIPE_SUBITEM_ID), obj.getString(RECIPE_SUBITEM_NAME));
        }

        for (int i = 0; i < tagsArray.length(); i++) {
            JSONObject obj = tagsArray.getJSONObject(i);
            this.tags.put(obj.getInt(RECIPE_SUBITEM_ID), obj.getString(RECIPE_SUBITEM_NAME));
        }
    }

    public static List<Recipe> createRecipeList(JSONArray jsonArray) throws JSONException {
        List<Recipe> list = new ArrayList<Recipe>();

        for (int i = 0; i < jsonArray.length(); i++) {
            list.add(new Recipe(jsonArray.getJSONObject(i)));
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

    public int getCategoryId() {
        return category_id;
    }

    public String getCategoryName() {
        return category_name;
    }

    public String getThumbImage() {
        return Constants.API_URL + thumb_image;
    }

    public String getFullImage() {
        return Constants.API_URL + full_image;
    }

    public Map<Integer, String> getIngredients() {
        return ingredients;
    }

    public Map<Integer, String> getTags() {
        return tags;
    }
}

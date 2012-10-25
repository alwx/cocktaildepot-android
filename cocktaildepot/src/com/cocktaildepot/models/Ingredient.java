package com.cocktaildepot.models;

import com.cocktaildepot.utilities.Constants;
import com.cocktaildepot.utilities.JSON;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Ingredient implements JSON {
    int id;
    String name, description, image;

    public Ingredient(JSONObject response) throws JSONException {
        this.id = response.getInt(INGREDIENT_ID);
        this.name = response.getString(INGREDIENT_NAME);
        this.description = response.getString(INGREDIENT_DESCRIPTION);
        this.image = response.getString(INGREDIENT_IMAGE);
    }

    public static List<Ingredient> createIngredientList(JSONArray jsonArray) throws JSONException {
        List<Ingredient> list = new ArrayList<Ingredient>();

        for (int i = 0; i < jsonArray.length(); i++) {
            list.add(new Ingredient(jsonArray.getJSONObject(i)));
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

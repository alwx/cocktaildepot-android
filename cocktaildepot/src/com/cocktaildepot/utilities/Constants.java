package com.cocktaildepot.utilities;

import java.net.HttpURLConnection;

public interface Constants {
    // API urls
    public static final String API_URL = "http://cocktaildepot.ru/";
    public static final String API_GET_VALUE = API_URL + "api/value/";
    public static final String API_GET_CATEGORIES = API_URL + "api/categories";
    public static final String API_GET_TAGS = API_URL + "api/tags";
    public static final String API_GET_INGREDIENTS = API_URL + "api/ingredients";
    public static final String API_GET_RECIPES = API_URL + "api/recipes";
    public static final String API_GET_RECIPES_BY_CATEGORY = "category_id=";
    public static final String API_GET_RECIPES_BY_INGREDIENT = "ingredient_id=";

    // conection response codes
    public static final int UNKNOWN_ERROR = -1;
    public static final int JSON_PARSING_ERROR = -2;
    public static final int INTERNET_CONNECTION_ERROR = -3;
    public static final int RESPONSE_CODE_OK = HttpURLConnection.HTTP_OK;

    // tags
    public static final String DEBUG_TAG = "Cocktail Depot";
    public static final String DIALOG_FRAGMENT_TAG = "dialog_tag";
    public static final String CATEGORY_ID_TAG = "category_id";
    public static final String INGREDIENT_ID_TAG = "ingredient_id";
    public static final String RECIPE_ID_TAG = "recipe_id";

    // preferences
    public static final String PREFERENCES = "com.cocktaildepot.preferences";
    public static final String API_LAST_UPDATE = "last_update";
}

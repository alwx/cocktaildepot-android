package com.cocktaildepot.utilities;

import com.cocktaildepot.models.Category;
import com.cocktaildepot.models.Ingredient;
import com.cocktaildepot.models.Recipe;
import com.cocktaildepot.models.Response;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.List;

public class ServerUtilities implements Constants {

    // send get and returns Response object
    private static Response sendGetRequest(String url, String params) {
        HttpClient httpclient = new DefaultHttpClient();
        HttpGet httpget = new HttpGet(url + "?" + params);
        try {
            HttpResponse response = httpclient.execute(httpget);

            BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            StringBuffer sb = new StringBuffer();
            String line;
            while ((line = rd.readLine()) != null) {
                sb.append(line + "\n");
            }
            if (rd != null) {
                rd.close();
            }
            return new Response(response.getStatusLine().getStatusCode(), sb.toString());
        } catch (Exception e) {
            if (e instanceof UnknownHostException || e instanceof ConnectException || e instanceof SocketException) {
                return new Response(INTERNET_CONNECTION_ERROR, null);
            }
            return new Response(UNKNOWN_ERROR, null);
        }
    }

    // get value from API key-value storage
    public static Response getValue(String key) {
        Response response = sendGetRequest(API_GET_VALUE + key, "");

        if (response.getCode() == RESPONSE_CODE_OK && response.getMessage() != null) {
            try {
                JSONObject jObject = new JSONObject(response.getMessage());

                return new Response(response.getCode(), jObject.getString(JSON.RESPONSE_TAG).toString());
            } catch (JSONException e) {
                return new Response(JSON_PARSING_ERROR, "");
            }
        }
        return new Response(response.getCode(), "");
    }

    // get ingredients list
    public static Response getIngredients() {
        Response response = sendGetRequest(API_GET_INGREDIENTS, "");

        if (response.getCode() == RESPONSE_CODE_OK && response.getMessage() != null) {
            try {
                JSONArray jArray = new JSONArray(response.getMessage());
                List<Ingredient> list = Ingredient.createIngredientList(jArray);

                return new Response(response.getCode(), list);
            } catch (JSONException e) {
                return new Response(JSON_PARSING_ERROR, "");
            }
        }
        return new Response(response.getCode(), "");
    }

    // get ingredients list
    public static Response getCategories() {
        Response response = sendGetRequest(API_GET_CATEGORIES, "");

        if (response.getCode() == RESPONSE_CODE_OK && response.getMessage() != null) {
            try {
                JSONArray jArray = new JSONArray(response.getMessage());
                List<Category> list = Category.createCategoryList(jArray);

                return new Response(response.getCode(), list);
            } catch (JSONException e) {
                return new Response(JSON_PARSING_ERROR, "");
            }
        }
        return new Response(response.getCode(), "");
    }

    // get recipes list
    public static Response getRecipes(int categoryId, int ingredientId) {
        StringBuilder params = new StringBuilder();
        if (categoryId != 0) params.append(API_GET_RECIPES_BY_CATEGORY).append(categoryId);
        if (ingredientId != 0) params.append(API_GET_RECIPES_BY_INGREDIENT).append(ingredientId);

        Response response = sendGetRequest(API_GET_RECIPES, params.toString());

        if (response.getCode() == RESPONSE_CODE_OK && response.getMessage() != null) {
            try {
                JSONArray jArray = new JSONArray(response.getMessage());
                List<Recipe> list = Recipe.createRecipeList(jArray);

                return new Response(response.getCode(), list);
            } catch (JSONException e) {
                return new Response(JSON_PARSING_ERROR, "");
            }
        }
        return new Response(response.getCode(), "");
    }
}

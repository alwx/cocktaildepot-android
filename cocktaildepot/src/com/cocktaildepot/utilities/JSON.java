package com.cocktaildepot.utilities;

public interface JSON {
    public static final String RESPONSE_TAG = "response";

    // ingredients
    public static final String INGREDIENT_ID = "id";
    public static final String INGREDIENT_NAME = "name";
    public static final String INGREDIENT_DESCRIPTION = "description";
    public static final String INGREDIENT_IMAGE = "thumb_image";

    // categories
    public static final String CATEGORY_ID = "id";
    public static final String CATEGORY_NAME = "name";
    public static final String CATEGORY_DESCRIPTION = "description";
    public static final String CATEGORY_IMAGE = "thumb_image";

    // recipes
    public static final String RECIPE_ID = "id";
    public static final String RECIPE_NAME = "name";
    public static final String RECIPE_DESCRIPTION = "description";
    public static final String RECIPE_THUMB_IMAGE = "thumb_image";
    public static final String RECIPE_FULL_IMAGE = "full_image";
    public static final String RECIPE_CATEGORY = "category";
    public static final String RECIPE_INGREDIENTS = "ingredients";
    public static final String RECIPE_TAGS = "tags";
    public static final String RECIPE_SUBITEM_ID = "id";
    public static final String RECIPE_SUBITEM_NAME = "name";
}

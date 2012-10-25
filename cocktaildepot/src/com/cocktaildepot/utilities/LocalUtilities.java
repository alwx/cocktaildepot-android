package com.cocktaildepot.utilities;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.ImageView;
import com.cocktaildepot.base.BasicActivity;

public class LocalUtilities {
    private static SharedPreferences getSharedPreferences(Context context, String prefName) {
        return context.getSharedPreferences(prefName, Context.MODE_PRIVATE);
    }
    public static String getSharedPreferencesStringField(Context context, String prefName, String fieldName) {
        SharedPreferences settings = getSharedPreferences(context, prefName);
        return settings.getString(fieldName, "");
    }

    public static void setSharedPreferencesStringField(Context context, String prefName, String fieldName, String data) {
        SharedPreferences.Editor editor = getSharedPreferences(context, prefName).edit();
        editor.putString(fieldName, data);
        editor.commit();
    }


    // set image by given url
    public static void setImageFromURL(String fullUrl, ImageView destinationView) {
        if (destinationView != null && BasicActivity.getImageLoader() != null) {
            BasicActivity.getImageLoader().displayImage(fullUrl, destinationView);
        }
    }
}

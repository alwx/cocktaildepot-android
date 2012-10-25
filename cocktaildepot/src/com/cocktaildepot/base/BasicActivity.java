package com.cocktaildepot.base;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import com.cocktaildepot.R;
import com.cocktaildepot.fragment.AlertDialogFragment;
import com.cocktaildepot.models.Response;
import com.cocktaildepot.utilities.Constants;
import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.download.URLConnectionImageDownloader;

public class BasicActivity extends FragmentActivity implements Constants {
    private static ImageLoader imageLoader = ImageLoader.getInstance();
    boolean closeActivity;
    boolean isShowing = false;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .cacheInMemory()
                .cacheOnDisc()
                .imageScaleType(ImageScaleType.POWER_OF_2)
                .build();

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
                .discCacheExtraOptions(900, 1600, Bitmap.CompressFormat.JPEG, 80)
                .threadPoolSize(5)
                .threadPriority(Thread.MIN_PRIORITY + 2)
                .denyCacheImageMultipleSizesInMemory()
                .memoryCache(new UsingFreqLimitedMemoryCache(2000000))
                .discCacheSize(8000000)
                .imageDownloader(new URLConnectionImageDownloader(5000, 20000))
                .defaultDisplayImageOptions(options)
                .enableLogging()
                .build();

        imageLoader.init(config);
    }

    public void showDialog(String title, String message) {
        if (title != null && message !=null){
            AlertDialogFragment.newInstance(title, message).show(getSupportFragmentManager(), DIALOG_FRAGMENT_TAG);
        }
    }

    public void doPositiveClick() {
        Fragment dialog = getSupportFragmentManager().findFragmentByTag(DIALOG_FRAGMENT_TAG);
        if (dialog != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
            ft.remove(dialog);
            ft.commit();
        }
        isShowing = false;
        if (closeActivity) finish();
    }

    public void errorsHandling(Response response, boolean closeActivity) {
        this.closeActivity = closeActivity;

        if (response != null && isShowing == false) {
            if (response.getCode() == UNKNOWN_ERROR) {
                showDialog(getString(R.string.error), getString(R.string.error_unknown));
            }
            else if (response.getCode() == INTERNET_CONNECTION_ERROR) {
                showDialog(getString(R.string.error), getString(R.string.error_connection));
            }
            else if (!response.getMessage().isEmpty()) {
                showDialog(getString(R.string.error), response.getMessage());
            }
            else {
                showDialog(getString(R.string.error), getString(R.string.error_other));
            }
            isShowing = true;
        }
    }

    public static ImageLoader getImageLoader() {
        return imageLoader;
    }
}

package com.cocktaildepot.activity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import com.cocktaildepot.R;
import com.cocktaildepot.base.BasicActivity;
import com.cocktaildepot.models.Response;
import com.cocktaildepot.utilities.Constants;
import com.cocktaildepot.utilities.ServerUtilities;

public class EnterActivity extends BasicActivity implements Constants {
    Context mContext;
    private AsyncTask<Void, Void, Response> connection;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter);
        mContext = getApplicationContext();

        connection = new AsyncTask<Void,Void,Response>(){
            @Override
            protected Response doInBackground(Void... params) {
                Response response;
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    Log.d(DEBUG_TAG, "Thread in interrupted in EnterActivity. " + e);
                }
                response = ServerUtilities.getValue(API_LAST_UPDATE);
                return response;
            }

            protected void onPostExecute (Response response) {
                super.onPostExecute(response);

                if (response != null && response.getCode() == RESPONSE_CODE_OK) {
                    Intent intent = new Intent(EnterActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    errorsHandling(response, true);
                }
            }
        }.execute((Void) null);
    }
}

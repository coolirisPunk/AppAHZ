package com.punkmkt.formula1;

import android.app.Application;
import android.content.Context;

import com.parse.Parse;
import com.parse.ParseInstallation;
import com.raizlabs.android.dbflow.config.FlowManager;

/**
 * Created by germanpunk on 20/07/15.
 */
public class InitializeApplication extends Application {
    private static Context context;
    @Override
    public void onCreate() {
        super.onCreate();
        InitializeApplication.context = getApplicationContext();
        FlowManager.init(getApplicationContext());
        Parse.initialize(this,
                getResources().getString(R.string.parse_application_id),
                getResources().getString(R.string.parse_client_key));
        ParseInstallation.getCurrentInstallation().saveInBackground();

    }


    public static Context getAppContext() {
        return InitializeApplication.context;
    }

}

package com.punkmkt.formula1;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseInstallation;
import com.raizlabs.android.dbflow.config.FlowManager;

/**
 * Created by germanpunk on 20/07/15.
 */
public class InitializeApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        FlowManager.init(getApplicationContext());
        Parse.initialize(this,
                getResources().getString(R.string.parse_application_id),
                getResources().getString(R.string.parse_client_key));
        ParseInstallation.getCurrentInstallation().saveInBackground();

    }

}

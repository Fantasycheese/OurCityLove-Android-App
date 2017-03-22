package org.ourcitylove.oclapp;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.firebase.analytics.FirebaseAnalytics;


public class OclApp extends Application {

    public static FirebaseAnalytics fa;
    public static SharedPreferences pref;
    public static LocationManager loc;

    @Override
    public void onCreate() {
        super.onCreate();
        fa = FirebaseAnalytics.getInstance(this);
        pref = PreferenceManager.getDefaultSharedPreferences(this);
        loc = new LocationManager.Builder().setLog(true).build();
    }
}

package org.ourcitylove.oclapp;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.karumi.dexter.Dexter;

import org.acra.ACRA;

public class OclApp extends Application {
    public static final String TAG = OclApp.class.getSimpleName();
    protected static FirebaseAnalytics mFirebaseAnalytics;
    public static SharedPreferences pref;
    public static Context ctx;

    @Override
    public void onCreate() {
        super.onCreate();
        ACRA.init(this);
        Dexter.initialize(this);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        pref = PreferenceManager.getDefaultSharedPreferences(this);
        ctx = this;
    }

    public void trackScreen(String name) {
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, name);
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.VIEW_ITEM, bundle);
    }
}

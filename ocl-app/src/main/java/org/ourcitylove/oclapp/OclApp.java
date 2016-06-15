package org.ourcitylove.oclapp;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.karumi.dexter.Dexter;

import org.acra.ACRA;

@SuppressWarnings("unused")
public class OclApp extends Application {
    private static final String TAG = OclApp.class.getSimpleName();

    public static FirebaseAnalytics mFirebaseAnalytics;
    public static SharedPreferences pref;
    public static LocationManager loc;

    @Override
    public void onCreate() {
        super.onCreate();
        ACRA.init(this);
        Dexter.initialize(this);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        pref = PreferenceManager.getDefaultSharedPreferences(this);
        loc = new LocationManager.Builder().build();
    }
}

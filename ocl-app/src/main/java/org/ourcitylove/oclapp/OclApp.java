package org.ourcitylove.oclapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.Settings;

import com.github.pwittchen.reactivenetwork.library.ConnectivityStatus;
import com.github.pwittchen.reactivenetwork.library.ReactiveNetwork;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.karumi.dexter.Dexter;

import org.acra.ACRA;

@SuppressWarnings("unused")
public class OclApp extends Application {
    private static final String TAG = OclApp.class.getSimpleName();

    public static FirebaseAnalytics mFirebaseAnalytics;
    public static SharedPreferences pref;
    public static Loc loc;

    @Override
    public void onCreate() {
        super.onCreate();
        ACRA.init(this);
        Dexter.initialize(this);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        pref = PreferenceManager.getDefaultSharedPreferences(this);
        loc = new Loc.Builder().build();
    }

    public void trackScreen(String name) {
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, name);
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.VIEW_ITEM, bundle);
    }

    public void checkConnectivity(Activity activity, String msg) {
        ConnectivityStatus status = new ReactiveNetwork().getConnectivityStatus(activity, true);
        if (status == ConnectivityStatus.OFFLINE) {
            new AlertDialog.Builder(activity)
                    .setMessage(msg)
                    .setPositiveButton(R.string.setting, (dialog, which) ->
                            activity.startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS)))
                    .setNegativeButton(android.R.string.cancel, null)
                    .create().show();
        }
    }
}

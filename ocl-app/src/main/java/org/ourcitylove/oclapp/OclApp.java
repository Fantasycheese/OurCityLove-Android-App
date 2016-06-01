package org.ourcitylove.oclapp;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Toast;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.karumi.dexter.Dexter;

import org.acra.ACRA;

import io.nlopez.smartlocation.OnLocationUpdatedListener;
import io.nlopez.smartlocation.SmartLocation;
import io.nlopez.smartlocation.location.config.LocationParams;

public class OclApp extends Application {

    public static FirebaseAnalytics mFirebaseAnalytics;
    public static SharedPreferences pref;
    private static SmartLocation.LocationControl smartLoc;

    @Override
    public void onCreate() {
        super.onCreate();
        ACRA.init(this);
        Dexter.initialize(this);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        pref = PreferenceManager.getDefaultSharedPreferences(this);
        smartLoc = SmartLocation.with(this).location().config(BuildConfig.DEBUG ? LocationParams.NAVIGATION : LocationParams.BEST_EFFORT);
    }

    public void trackScreen(String name) {
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, name);
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.VIEW_ITEM, bundle);
    }

    public static void startLoc(Context context, OnLocationUpdatedListener listener) {
        if (Dexter.isRequestOngoing()) return;
        Dexter.checkPermission(RationalePermissionListener.Builder.with(context)
                .withRunOnGranted(()->{
                    if (!smartLoc.state().locationServicesEnabled()) {
                        openLocationSetting(context); return;
                    }
                    if (!smartLoc.state().isAnyProviderAvailable()) {
                        Toast.makeText(context, "無法取得您的位置", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    smartLoc.start(listener);
                }).build(), Manifest.permission.ACCESS_FINE_LOCATION);
    }

    public static void stopLoc() { smartLoc.stop(); }

    public static void openLocationSetting(Context context) {
        boolean bTW = true;
        new AlertDialog.Builder(context)
                .setTitle(bTW?"設定位置服務":"Location access setting")
                .setMessage(bTW?"請開啟Network or GPS位置服務(隨後跳轉)":"Please turn on the access to my location.")
                .setNegativeButton(android.R.string.cancel, null)
                .setPositiveButton(android.R.string.ok,
                        (dialog, which) ->
                                context.startActivity(new Intent(
                                        android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS)))
                .show();
    }
}

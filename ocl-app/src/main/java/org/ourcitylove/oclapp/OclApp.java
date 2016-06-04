package org.ourcitylove.oclapp;

import android.Manifest;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.karumi.dexter.Dexter;

import org.acra.ACRA;

import io.nlopez.smartlocation.OnLocationUpdatedListener;
import io.nlopez.smartlocation.SmartLocation;
import io.nlopez.smartlocation.location.config.LocationParams;
import io.nlopez.smartlocation.location.providers.LocationGooglePlayServicesProvider;

@SuppressWarnings("unused")
public class OclApp extends Application {

    public static FirebaseAnalytics mFirebaseAnalytics;
    public static SharedPreferences pref;
    private SmartLocation.LocationControl smartLoc;
    private LocationGooglePlayServicesProvider locProvider;

    @Override
    public void onCreate() {
        super.onCreate();
        ACRA.init(this);
        Dexter.initialize(this);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        pref = PreferenceManager.getDefaultSharedPreferences(this);
    }

    public void initSmartLoc(Activity activity, LocationParams params) {
        locProvider = new LocationGooglePlayServicesProvider();
        locProvider.setCheckLocationSettings(true);
        smartLoc = SmartLocation.with(activity).location(locProvider)
                .config(params);
    }

    public void startLoc(Context context, OnLocationUpdatedListener listener) {
        if (Dexter.isRequestOngoing()) return;
        Dexter.checkPermission(RationalePermissionListener.Builder.with(context)
                .withRunOnGranted(()-> smartLoc.start(listener)).build(),
                Manifest.permission.ACCESS_FINE_LOCATION);
    }

    public void stopLoc() { smartLoc.stop(); }

    public void trackScreen(String name) {
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, name);
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.VIEW_ITEM, bundle);
    }

    public LocationGooglePlayServicesProvider getLocProvider() {
        return locProvider;
    }
}

package org.ourcitylove.oclapp;

import android.app.Application;
import android.os.Bundle;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.karumi.dexter.Dexter;

import org.acra.ACRA;

public class OclApp extends Application {

    protected static FirebaseAnalytics mFirebaseAnalytics;

    @Override
    public void onCreate() {
        super.onCreate();
        ACRA.init(this);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        Dexter.initialize(this);
    }

    public void trackScreen(String name) {
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, name);
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.VIEW_ITEM, bundle);
    }
}

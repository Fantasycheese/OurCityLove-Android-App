package org.ourcitylove.oclapp;

import android.os.Bundle;

import com.google.firebase.analytics.FirebaseAnalytics;

public class Firebase {

    public static void trackScreen(String name) {
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, name);
        OclApp.mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
    }
}

package org.ourcitylove.oclapp;

import android.app.Activity;

import com.github.javiersantos.appupdater.AppUpdater;

/**
 * Created by Vegetable on 2016/9/23.
 */

public class VersionManager {

    public static void start(Activity ac) {
        new AppUpdater(ac)
                .setTitleOnUpdateAvailable("Update available")
                .setContentOnUpdateAvailable(ac.getString(R.string.ContentOnUpdateAvailable,ac.getString(R.string.app_name)))
                .setButtonUpdate(ac.getString(R.string.UpdateGo))
                .setButtonDismiss(ac.getString(R.string.UpdateNextTime))
                .setButtonDoNotShowAgain("")
//				.setButtonDoNotShowAgain("Huh, not interested")
//                .setIcon(R.mipmap.balloon)
                .showAppUpdated(false)
                .start();// Notification icon
}
}

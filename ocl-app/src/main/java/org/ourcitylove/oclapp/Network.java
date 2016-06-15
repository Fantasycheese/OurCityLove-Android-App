package org.ourcitylove.oclapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.provider.Settings;
import android.text.TextUtils;

import com.github.pwittchen.reactivenetwork.library.ConnectivityStatus;
import com.github.pwittchen.reactivenetwork.library.ReactiveNetwork;

public class Network {

    private static int defaultMsgResId = R.string.network_error_msg_default;

    public static void checkConnectivity(Activity activity, String msg) {
        ConnectivityStatus status = new ReactiveNetwork().getConnectivityStatus(activity, true);
        if (status == ConnectivityStatus.OFFLINE) {
            new AlertDialog.Builder(activity)
                    .setMessage(TextUtils.isEmpty(msg) ? activity.getString(defaultMsgResId) : msg)
                    .setPositiveButton(R.string.setting, (dialog, which) ->
                            activity.startActivity(new Intent(Settings.ACTION_SETTINGS)))
                    .setNegativeButton(android.R.string.cancel, null)
                    .create().show();
        }
    }
}

package org.ourcitylove.oclapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.text.TextUtils;

public class Network {

    private static int defaultMsgResId = R.string.network_error_msg_default;

    public static boolean checkConnectivity(Activity activity, String msg) {
        ConnectivityManager cm =
                (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        boolean connected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        if (!connected) {
            activity.runOnUiThread(() ->
                    new AlertDialog.Builder(activity)
                            .setMessage(TextUtils.isEmpty(msg) ? activity.getString(defaultMsgResId) : msg)
                            .setPositiveButton(org.ourcitylove.oclapp.R.string.setting, (dialog, which) -> {
                                dialog.dismiss();
                                activity.startActivity(new Intent(Settings.ACTION_SETTINGS));
                            })
                            .setNegativeButton(android.R.string.cancel, null)
                            .create().show());
        }
        return connected;
    }
}

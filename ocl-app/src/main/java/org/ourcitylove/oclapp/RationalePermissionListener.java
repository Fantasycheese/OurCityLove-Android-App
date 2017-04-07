package org.ourcitylove.oclapp;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.PermissionGroupInfo;
import android.content.pm.PermissionInfo;
import android.support.v4.util.SparseArrayCompat;

import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.util.HashMap;
import java.util.Map;

public class RationalePermissionListener implements PermissionListener {

    final Context context;
    final Runnable runOnGranted;
    final Runnable runOnDenied;
//    final Map<String, Integer> permissionDisplayNames;
    final SparseArrayCompat<Integer> permissionRationaleActions;

    private String rationaleMsg;

    public RationalePermissionListener(Context context, Runnable runOnGranted, Runnable runOnDenied, String rationaleMsg) {
        this.context = context;
        this.runOnGranted = runOnGranted;
        this.runOnDenied = runOnDenied;
        this.rationaleMsg = rationaleMsg;
//        this.permissionDisplayNames = initPermissionNameMap();
        this.permissionRationaleActions = initPermissionRationaleAction();
    }

    @Override
    public void onPermissionGranted(PermissionGrantedResponse response) {
        if (runOnGranted != null) runOnGranted.run();
    }

    @Override public void onPermissionDenied(PermissionDeniedResponse response) {
        if (runOnDenied != null) runOnDenied.run();
    }

    @Override
    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, final PermissionToken token) {
        int resPermissionGroup = 0;

    //    String permissionDisplayName = "";
    //    if (permissionDisplayNames.containsKey(permission.getName())) {
    //      resPermissionGroup = permissionDisplayNames.get(permission.getName());
    //      permissionDisplayName = context.getString(resPermissionGroup);
    //    }
        PackageManager pm = context.getPackageManager();
        String permissionDisplayName = null;
        try {
            PermissionInfo info = pm.getPermissionInfo(permission.getName(), 0);
            PermissionGroupInfo groupInfo = pm.getPermissionGroupInfo(info.group, 0);
            permissionDisplayName = groupInfo.loadLabel(pm).toString();
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }


        int resPermissionAction = permissionRationaleActions.get(resPermissionGroup, R.string.permission_action_default);
        String action = context.getString(resPermissionAction);
        if (rationaleMsg == null)
            rationaleMsg = context.getString(R.string.permission_rationale_default_msg, permissionDisplayName, action);

        AlertDialog alertDialog = new AlertDialog.Builder(context).setTitle(context.getString(R.string.permission_rationale_title, permissionDisplayName))
                .setMessage(rationaleMsg)
                .setNegativeButton(android.R.string.cancel, (dialog, which) -> {
                    dialog.dismiss();
                    token.cancelPermissionRequest();
                })
                .setPositiveButton(android.R.string.ok, (dialog, which) -> {
                    dialog.dismiss();
                    token.continuePermissionRequest();
                }).create();
        alertDialog.setOnDismissListener(dialog -> token.cancelPermissionRequest());
        alertDialog.show();
    }

    public static class Builder {
        final Context context;
        Runnable runOnGranted;
        Runnable runOnDenied;
        String rationaleMsg;

        Builder(Context context) {
          this.context = context;
        }

        public static Builder with(Context context) {
          return new Builder(context);
        }

        public Builder withRunOnGranted(Runnable run) {
            this.runOnGranted = run;
            return this;
        }

        public Builder withRunOnDenied(Runnable run) {
            this.runOnDenied = run;
            return this;
        }

        public Builder withRationaleMsg(String msg) {
            this.rationaleMsg = msg;
            return this;
        }

        public RationalePermissionListener build() {
            return new RationalePermissionListener(context, runOnGranted, runOnDenied, rationaleMsg);
        }
    }

//    private Map<String, Integer> initPermissionNameMap() {
//        Map<String, Integer> permissionDisplayNames = new HashMap<>();
//
//        permissionDisplayNames.put("android.permission.READ_CALENDAR", R.string.permission_calendar);
//        permissionDisplayNames.put("android.permission.WRITE_CALENDAR", R.string.permission_calendar);
//
//    permissionDisplayNames.put("android.permission.CAMERA", R.string.permission_camera);
//
//    permissionDisplayNames.put("android.permission.READ_CONTACTS", R.string.permission_contacts);
//    permissionDisplayNames.put("android.permission.WRITE_CONTACTS", R.string.permission_contacts);
//    permissionDisplayNames.put("android.permission.GET_ACCOUNTS", R.string.permission_contacts);
//
//    permissionDisplayNames.put("android.permission.ACCESS_FINE_LOCATION", R.string.permission_location);
//    permissionDisplayNames.put("android.permission.ACCESS_COARSE_LOCATION", R.string.permission_location);
//
//    permissionDisplayNames.put("android.permission.RECORD_AUDIO", R.string.permission_microphone);
//
//    permissionDisplayNames.put("android.permission.READ_PHONE_STATE", R.string.permission_phone);
//    permissionDisplayNames.put("android.permission.CALL_PHONE", R.string.permission_phone);
//    permissionDisplayNames.put("android.permission.READ_CALL_LOG", R.string.permission_phone);
//    permissionDisplayNames.put("android.permission.WRITE_CALL_LOG", R.string.permission_phone);
//    permissionDisplayNames.put("android.permission.ADD_VOICEMAIL", R.string.permission_phone);
//    permissionDisplayNames.put("android.permission.USE_SIP", R.string.permission_phone);
//    permissionDisplayNames.put("android.permission.PROCESS_OUTGOING_CALLS", R.string.permission_phone);
//
//    permissionDisplayNames.put("android.permission.BODY_SENSORS", R.string.permission_sensors);
//
//    permissionDisplayNames.put("android.permission.SEND_SMS", R.string.permission_sms);
//    permissionDisplayNames.put("android.permission.RECEIVE_SMS", R.string.permission_sms);
//    permissionDisplayNames.put("android.permission.READ_SMS", R.string.permission_sms);
//    permissionDisplayNames.put("android.permission.RECEIVE_WAP_PUSH", R.string.permission_sms);
//    permissionDisplayNames.put("android.permission.RECEIVE_MMS", R.string.permission_sms);
//
//    permissionDisplayNames.put("android.permission.READ_EXTERNAL_STORAGE", R.string.permission_storage);
//    permissionDisplayNames.put("android.permission.WRITE_EXTERNAL_STORAGE", R.string.permission_storage);
//
//    return permissionDisplayNames;
//  }

  private SparseArrayCompat<Integer> initPermissionRationaleAction() {
    SparseArrayCompat<Integer> map = new SparseArrayCompat<>();
//    map.put(R.string.permission_calendar,);
//    map.put(R.string.permission_camera,);
//    map.put(R.string.permission_contacts,);
    map.put(R.string.permission_location, R.string.permission_action_location);
//    map.put(R.string.permission_microphone,);
//    map.put(R.string.permission_phone,);
//    map.put(R.string.permission_sensors,);
//    map.put(R.string.permission_sms,);
//    map.put(R.string.permission_storage,);
    return map;
  }
}

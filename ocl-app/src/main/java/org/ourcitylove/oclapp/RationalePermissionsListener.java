package org.ourcitylove.oclapp;

import android.content.Context;

import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.util.List;

@SuppressWarnings({"unused", "WeakerAccess"})
public class RationalePermissionsListener extends RationalePermissionListener implements MultiplePermissionsListener {

    private RationalePermissionsListener(Context context, Runnable runOnGranted, Runnable runOnDenied, String rationaleMsg) {
        super(context, runOnGranted, runOnDenied, rationaleMsg);
    }

    @Override
    public void onPermissionsChecked(MultiplePermissionsReport report) {
        if (report.areAllPermissionsGranted()) {
            if (runOnGranted != null)
                runOnGranted.run();
        } else if (report.isAnyPermissionPermanentlyDenied()) {
            if (runOnDenied != null)
                runOnDenied.run();
        }
    }

    @Override
    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
        for (PermissionRequest permission : permissions)
            onPermissionRationaleShouldBeShown(permission, token);
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

        public RationalePermissionsListener build() {
            return new RationalePermissionsListener(context, runOnGranted, runOnDenied, rationaleMsg);
        }
    }
}
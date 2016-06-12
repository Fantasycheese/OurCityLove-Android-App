package org.ourcitylove.oclapp;

import android.Manifest;
import android.app.Activity;

import com.karumi.dexter.Dexter;

import io.nlopez.smartlocation.OnLocationUpdatedListener;
import io.nlopez.smartlocation.SmartLocation;
import io.nlopez.smartlocation.location.config.LocationAccuracy;
import io.nlopez.smartlocation.location.config.LocationParams;

public class Loc {

    private final LocationParams locParams;
    private final String permissionMsg;
    private SmartLocation.LocationControl loc;

    public Loc(LocationParams locParams, String permissionMsg) {
        this.locParams = locParams;
        this.permissionMsg = permissionMsg;
    }
    
    public void start(Activity activity, OnLocationUpdatedListener listener) {
        if (Dexter.isRequestOngoing()) return;
        Dexter.checkPermission(RationalePermissionListener.Builder.with(activity)
                        .withRunOnGranted(() -> {
                            stop();
                            loc = SmartLocation.with(activity).location().config(locParams);
                            loc.start(listener);
                        })
                        .withRationaleMsg(permissionMsg).build(),
                Manifest.permission.ACCESS_FINE_LOCATION);
    }

    public void stop() {
        if (loc != null) loc.stop();
    }

    public static class Builder {

        private LocationParams locParams;
        private String permissionMsg;

        public Builder() {
            this.locParams = new LocationParams.Builder()
                    .setAccuracy(LocationAccuracy.LOW)
                    .setDistance(0f)
                    .setInterval(5000).build();
        }

        public Builder setLocParams(LocationParams locParams) {
            this.locParams = locParams;
            return this;
        }

        public Builder setPermissionMsg(String permissionMsg) {
            this.permissionMsg = permissionMsg;
            return this;
        }

        public Loc build() {
            return new Loc(locParams, permissionMsg);
        }
    }
}

package org.ourcitylove.oclapp;

import android.Manifest;
import android.app.Activity;
import android.content.Context;

import com.karumi.dexter.Dexter;

import io.nlopez.smartlocation.SmartLocation;
import io.nlopez.smartlocation.location.config.LocationAccuracy;
import io.nlopez.smartlocation.location.config.LocationParams;
import io.nlopez.smartlocation.location.providers.LocationGooglePlayServicesProvider;
import io.nlopez.smartlocation.rx.ObservableFactory;
import rx.Observable;
import rx.Subscription;

public class Location {
    private static final double EARTH_RADIUS = 3958.75;
    private static final int METER_CONVERSION = 1609;

    private final LocationParams locParams;
    private final String permissionMsg;
    private Subscription locSubscription;

    public Location(LocationParams locParams, String permissionMsg) {
        this.locParams = locParams;
        this.permissionMsg = permissionMsg;
    }
    
    public Observable<android.location.Location> start(Activity activity) {
        return Observable.create(subscriber -> {
            if (Dexter.isRequestOngoing()) return;
            Dexter.checkPermission(RationalePermissionListener.Builder.with(activity)
                    .withRationaleMsg(permissionMsg)
                    .withRunOnGranted(() -> {
                        stop();
                        LocationGooglePlayServicesProvider lp = new LocationGooglePlayServicesProvider();
                        lp.setCheckLocationSettings(true);
                        SmartLocation.LocationControl lc = SmartLocation.with(activity)
                                .location(lp).config(locParams);
                        locSubscription = ObservableFactory.from(lc).subscribe(
                                subscriber::onNext,
                                subscriber::onError,
                                subscriber::onCompleted
                        );
                    }).build(), Manifest.permission.ACCESS_FINE_LOCATION);
        });
    }

    public void stop() {
        if (locSubscription != null && !locSubscription.isUnsubscribed())
            locSubscription.unsubscribe();
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

        public Location build() {
            return new Location(locParams, permissionMsg);
        }
    }

    public static double getDistance(double lat_a, double lng_a, double lat_b, double lng_b) {

        double latDiff = Math.toRadians(lat_b - lat_a);
        double lngDiff = Math.toRadians(lng_b - lng_a);
        double a = Math.sin(latDiff / 2) * Math.sin(latDiff / 2)
                + Math.cos(Math.toRadians(lat_a))
                * Math.cos(Math.toRadians(lat_b)) * Math.sin(lngDiff / 2)
                * Math.sin(lngDiff / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return EARTH_RADIUS * c * METER_CONVERSION;
    }

    public static String getDisplayDistance(Context context, Double fDistance) {
        if(fDistance == null || fDistance <= 0 || fDistance>5000000) return null;

        boolean far = fDistance >= 1000;
        String unit = context.getString(far ? R.string.unit_kilometer : R.string.unit_meter);
        String format = far ? "%.1f%s" : "%.0f%s";
        if (far) fDistance /= 1000;
        return String.format(format, fDistance, unit);
    }
}

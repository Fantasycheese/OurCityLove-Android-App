package org.ourcitylove.oclapp;

import android.Manifest;
import android.app.Activity;
import android.location.Location;

import com.karumi.dexter.Dexter;

import io.nlopez.smartlocation.SmartLocation;
import io.nlopez.smartlocation.location.config.LocationAccuracy;
import io.nlopez.smartlocation.location.config.LocationParams;
import io.nlopez.smartlocation.location.providers.LocationGooglePlayServicesProvider;
import io.nlopez.smartlocation.rx.ObservableFactory;
import rx.Observable;
import rx.Subscription;

public class Loc {

    private final LocationParams locParams;
    private final String permissionMsg;
    private Subscription locSubscription;

    public Loc(LocationParams locParams, String permissionMsg) {
        this.locParams = locParams;
        this.permissionMsg = permissionMsg;
    }
    
    public Observable<Location> start(Activity activity) {
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

        public Loc build() {
            return new Loc(locParams, permissionMsg);
        }
    }
}

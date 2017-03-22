package org.ourcitylove.oclapp;

import android.content.Intent;
import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class LocationActivity extends AppCompatActivity {
    private LocationListener locationListener;

    @Override
    protected void onStart() {
        super.onStart();
        OclApp.loc.lastAndUpdate(this, false)
                .subscribe(location -> {
                    Log.d("LOCATION", location.toString());
                    if (locationListener != null)
                        locationListener.onLocationUpdate(location);
                });
    }

    // receive location setting result to restart location update
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        OclApp.loc.lp.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onStop() {
        super.onStop();
        OclApp.loc.stop();
    }

    public void setLocationListener(LocationListener locationListener) {
        this.locationListener = locationListener;
    }

    public interface LocationListener {
        void onLocationUpdate(Location location);
    }
}

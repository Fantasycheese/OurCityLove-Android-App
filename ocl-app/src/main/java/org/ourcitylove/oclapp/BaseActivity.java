package org.ourcitylove.oclapp;

import android.content.Intent;
import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class BaseActivity extends AppCompatActivity {
    private static final String TAG = BaseActivity.class.getCanonicalName();
    private boolean needLocation;

    // set this before on start
    public void setNeedLocation(boolean needLocation) {
        this.needLocation = needLocation;
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (needLocation) {
            OclApp.loc.lastAndUpdate(this, false)
                    .filter(location -> location != null)
                    .subscribe(this::onLocation);
        }
//            Observable.just(OclApp.loc.last(this))
//                    .concatWith(OclApp.loc.update(this))
//                    .filter(location -> location != null)
//                    .subscribe(this::onLocation);
    }

    protected void onLocation(Location location) {
        Log.d(TAG, "onLocation: "+location.toString());
    }

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
}

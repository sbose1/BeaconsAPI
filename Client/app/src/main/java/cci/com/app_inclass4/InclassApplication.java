package cci.com.app_inclass4;

import android.app.Application;

import com.estimote.coresdk.service.BeaconManager;

/**
 * Created by Aliandro on 9/28/2018.
 */

public class InclassApplication extends Application {
    private BeaconManager beaconManager;

    @Override
    public void onCreate() {
        super.onCreate();

        beaconManager = new BeaconManager(getApplicationContext());
    }

}

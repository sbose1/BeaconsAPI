package cci.com.app_inclass4;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.estimote.coresdk.common.requirements.SystemRequirementsChecker;
import com.estimote.coresdk.observation.region.beacon.BeaconRegion;
import com.estimote.coresdk.recognition.packets.Beacon;
import com.estimote.coresdk.service.BeaconManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {
    private BeaconManager beaconManager;
    private BeaconRegion region;
    private final int badRSSI  = -50;
    private final int goodRSSI =-30;
    private Beacon currentBeacon =null;
    private String beaconUUID="B9407F30-F5F8-466E-AFF9-25556B57FE6D";
    public static Map<String, List<String>> PLACES_BY_BEACONS = null;
    static {
        Map<String, List<String>> placesByBeacons = new HashMap<>();
        placesByBeacons.put("55125:738", new ArrayList<String>() {{
            add("Heavenly Sandwiches");
            // read as: "Heavenly Sandwiches" is closest
            // to the beacon with major 22504 and minor 48827
            add("Green & Green Salads");
            // "Green & Green Salads" is the next closest
            add("Mini Panini");
            // "Mini Panini" is the furthest away
        }});
        placesByBeacons.put("59599:33091", new ArrayList<String>() {{
            add("Mini Panini");
            add("Green & Green Salads");
            add("Heavenly Sandwiches");
        }});
        placesByBeacons.put("1564:34409", new ArrayList<String>() {{
            add("Produce Panini");
            add("Produce Green Salads");
            add("Produce Sandwiches");
        }});
        PLACES_BY_BEACONS = Collections.unmodifiableMap(placesByBeacons);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        beaconManager = new BeaconManager(this);
        region = new BeaconRegion("ranged region",
                UUID.fromString(beaconUUID), null, null);
        beaconManager.setRangingListener(new BeaconManager.BeaconRangingListener() {
            @Override
            public void onBeaconsDiscovered(BeaconRegion region, List<Beacon> list) {
                if (!list.isEmpty()) {
                    if (currentBeacon == null )
                     currentBeacon = list.get(0) ;
                    Beacon temp= list.get( list.indexOf(currentBeacon)) ;
                    for (Beacon item:list
                    ) {
                        if(item.getRssi()>goodRSSI && (temp ==null ||temp.getRssi() <badRSSI))
                        {
                            currentBeacon= item;
                        }

                    }
                    List<String> places = placesNearBeacon(currentBeacon);
                    // TODO: update the UI here
                    Log.d("test", "Nearest places: " + places);
                }
            }
        });

    }
    private List<String> placesNearBeacon(Beacon beacon) {
        String beaconKey = String.format("%d:%d", beacon.getMajor(), beacon.getMinor());
        if (PLACES_BY_BEACONS.containsKey(beaconKey)) {
            return PLACES_BY_BEACONS.get(beaconKey);
        }
        return Collections.emptyList();
    }
    @Override
    protected void onResume() {
        super.onResume();

        SystemRequirementsChecker.checkWithDefaultDialogs(this);
        beaconManager.connect(new BeaconManager.ServiceReadyCallback() {
            @Override
            public void onServiceReady() {
                beaconManager.startRanging(region);
            }
        });
    }
    @Override
    protected void onPause() {
        beaconManager.stopRanging(region);

        super.onPause();
    }
}

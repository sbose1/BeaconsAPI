package com.example.preranasingh.inclass04;
import android.support.v7.app.ActionBar;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.estimote.coresdk.common.requirements.SystemRequirementsChecker;
import com.estimote.coresdk.observation.region.beacon.BeaconRegion;
import com.estimote.coresdk.recognition.packets.Beacon;
import com.estimote.coresdk.service.BeaconManager;
import com.estimote.mgmtsdk.repackaged.jtar.TarOutputStream;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class MainActivity extends AppCompatActivity implements ProductAsyncTask.IData{
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private BeaconManager beaconManager;
    private BeaconRegion region;
    private final int badRSSI  = 50;
    private final int goodRSSI =70;
    private Boolean initialized=false;
    private Beacon currentBeacon =null;
    private String beaconUUID="B9407F30-F5F8-466E-AFF9-25556B57FE6D";

    String apiURL;
    public static String remoteIP="http://18.223.110.166:5000";
    ArrayList<Product> data=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        apiURL="http://18.223.110.166:5000/get/getDiscounts";

        beaconManager = new BeaconManager(this);
        region = new BeaconRegion("region",
                UUID.fromString(beaconUUID), null, null);
        getProductList("");
        beaconManager.setRangingListener(new BeaconManager.BeaconRangingListener() {
            @Override
            public void onBeaconsDiscovered(BeaconRegion region, List<Beacon> list) {
                if (!list.isEmpty()) {

                    Beacon temp=null;
                    Boolean noneOfThese=true;
                    if(initialized)
                        temp = list.indexOf(currentBeacon)>=0? list.get(list.indexOf(currentBeacon)):null ;
                    for (Beacon item:list
                            ) {
                        Log.d("beacon", "onBeaconsDiscovered: "+item.toString());
                        Log.d("test", "onBeaconsDiscovered: "+ item.getMinor());

                        Log.d("test", "beacons rssi: "+ item.getRssi());
                        Log.d("test", "beacons measuredpower: "+ item.getMeasuredPower());
                        switch (item.getMinor()) {
                                    case 738:
                                        noneOfThese=false;
                                        if (!initialized)
                                        {currentBeacon = item;
                                        temp=item;
                                        initialized=true;
                                        }

                                        if((temp==null || temp.getRssi()<badRSSI)&&item.getRssi()*(-1)>goodRSSI) {
                                            currentBeacon = item;
                                            // Woodward 333F
                                            Toast.makeText(MainActivity.this, "near grocery", Toast.LENGTH_SHORT).show();
                                            getProductList("grocery");
                                        }
                                        break;
                                    case 33091:
                                        noneOfThese=false;
                                        if (!initialized)  {currentBeacon = item;
                                            temp=item;
                                            initialized=true;
                                        }
                                        if((temp==null || temp.getRssi()<badRSSI)&&item.getRssi()*(-1)>goodRSSI) {
                                            currentBeacon = item;
                                            // makerspace lab
                                            Toast.makeText(MainActivity.this, "near lifestyle", Toast.LENGTH_SHORT).show();

                                            getProductList("lifestyle");
                                        }
                                        break;
                                    case 34409:
                                        noneOfThese=false;
                                        if (!initialized)  {currentBeacon = item;
                                            initialized=true;
                                            temp=item;
                                        }
                                        if((temp==null || temp.getRssi()<badRSSI)&&item.getRssi()*(-1)>goodRSSI) {
                                            currentBeacon = item;
                                            //elevator
                                            Toast.makeText(MainActivity.this, "near produce", Toast.LENGTH_SHORT).show();

                                            getProductList("produce");
                                        }
                                        break;
//                                    default:
//                                        if(temp==null || temp.getRssi()<badRSSI)
//                                        getProductList("");
//                                        break;

                            }

                    }
                    if(  noneOfThese&&( temp==null || temp.getRssi()<badRSSI)) {
                        Toast.makeText(MainActivity.this, "None of the categories are nearby.", Toast.LENGTH_SHORT).show();
                        getProductList("");
                    }



                }else  {
                    Toast.makeText(MainActivity.this, "couldnt find any beacon", Toast.LENGTH_SHORT).show();
                    getProductList("");

                }
            }
        });


      //  new ProductAsnycTask(this).execute(apiURL);
        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        mRecyclerView.setHasFixedSize(true);
        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);


    }

    private void getProductList(String filter){
        if(isConnectedOnline()){
            RequestParams params=new RequestParams("POST",apiURL);
            params.addParam("region",filter);

            new ProductAsyncTask(this).execute(params);
        }else{
            Toast.makeText(MainActivity.this,"No Network Connection",Toast.LENGTH_LONG).show();
        }
    }
    //to check if the network is connected-permission
    private boolean isConnectedOnline(){
        ConnectivityManager cm= (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if(networkInfo!= null && networkInfo.isConnected())
            return true;
        return false;
    }

    @Override
    public void setUpData(ArrayList<Product> productsArrayList) {
        mAdapter=new ProductAdapter(productsArrayList,getApplicationContext());
        mRecyclerView.setAdapter(mAdapter);
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

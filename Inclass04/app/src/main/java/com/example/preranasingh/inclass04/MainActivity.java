package com.example.preranasingh.inclass04;

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
    private final int badRSSI  = -50;
    private final int goodRSSI =-30;
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

     //   getProductList("produce");
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
                        if((temp ==null ||temp.getRssi() <badRSSI))
                        {
                            if(item.getRssi()>goodRSSI ) {
                                currentBeacon = item;
                                switch (currentBeacon.getMajor()) {
                                    case 55125:
                                        getProductList("grocery");
                                        break;
                                    case 59599:
                                        getProductList("lifestyle");
                                        break;
                                    case 1564:
                                        getProductList("produce");
                                        break;
                                    default:
                                        getProductList("");
                                }
                            }else {
                                //no beacon in range case
                                getProductList("");
                            }

                        }

                    }



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

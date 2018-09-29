package com.example.preranasingh.inclass04;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements ProductAsyncTask.IData{
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    String apiURL;
    ArrayList<Product> data=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        apiURL=" http://18.223.110.166:5000/get/productlist";
        if(isConnectedOnline()){
            RequestParams params=new RequestParams("POST",apiURL);
            params.addParam("region","Grocery");

            new ProductAsyncTask(this).execute(params);
        }else{
            Toast.makeText(MainActivity.this,"No Network Connection",Toast.LENGTH_LONG).show();
        }
      //  new ProductAsnycTask(this).execute(apiURL);
        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        mRecyclerView.setHasFixedSize(true);
        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);


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
}

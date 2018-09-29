package com.example.preranasingh.inclass04;

import android.os.AsyncTask;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.util.ArrayList;

class ProductAsyncTask extends AsyncTask<RequestParams, Void, ArrayList<Product>> {
   IData activity;
    public ProductAsyncTask(IData activity) {
        this.activity = activity;
    }

    @Override
    protected void onPostExecute(ArrayList<Product> products) {
        if(products != null)
            activity.setUpData(products);
    }

    @Override
    protected ArrayList<Product> doInBackground(RequestParams... params) {
        BufferedReader reader=null;

        try {
            HttpURLConnection con=params[0].setupConnection();
            reader =new BufferedReader(new InputStreamReader(con.getInputStream()));
            StringBuilder sb=new StringBuilder();
            String line="";
            while((line=reader.readLine())!=null){
                sb.append(line+"\n");
            }
            return ProductUtil.ProductJSONParser.parseProducts(sb.toString());
        } catch (MalformedURLException e){
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    public static interface IData {
        public void setUpData(ArrayList<Product> productsArrayList);

    }


}

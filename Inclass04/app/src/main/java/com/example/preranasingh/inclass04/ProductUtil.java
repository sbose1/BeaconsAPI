package com.example.preranasingh.inclass04;

import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

class ProductUtil {
    static public class ProductJSONParser{

        static ArrayList<Product> parseProducts(String in)throws JSONException{

            ArrayList<Product> productList=new ArrayList<Product>();
if (in!=null) {
 //   Log.d("test", "parseProducts: "+in);
    JSONObject root = new JSONObject(in);
    JSONArray productJSONArray = root.getJSONArray("product");


if (productJSONArray.length()>0) {
    for (int i = 0; i < productJSONArray.length(); i++) {

        JSONObject JSONProductObject = productJSONArray.getJSONObject(i);
        //  Person person= Person.createPerson(JSONPersonObject);

        Product product = new Product();
        product.setId(JSONProductObject.getString("_id"));
        product.setRegion(JSONProductObject.getString("region"));
        product.setphoto(JSONProductObject.getString("photo"));
        product.setname(JSONProductObject.getString("name"));
        product.setDiscount(JSONProductObject.getDouble("discount"));
        product.setPrice(JSONProductObject.getDouble("price"));


        productList.add(product);
    }
}else{
  //  Toast.makeText(MainActivity.this, "THere are ano products to display at this time", Toast.LENGTH_SHORT).show();
}
}

            return productList;
        }
    }
}

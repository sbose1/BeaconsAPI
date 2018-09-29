package com.example.preranasingh.inclass04;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {
    ArrayList<Product> mData;
    private Context context;

    public ProductAdapter(ArrayList<Product> mData,Context context) {
        this.mData = mData;
        this.context=context;
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflating the recycler view structure
        View itemview= LayoutInflater.from(parent.getContext()).inflate(R.layout.product,parent,false);
        //passing the inflated view as a parameter in viewHolder
       // ViewHolder viewHolder = new ViewHolder(view);
        return new ProductViewHolder(itemview);
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {
       Product product=mData.get(position);

       holder.txtProductName.setText(product.name);
       holder.txtDiscount.setText(String.valueOf(product.discount));
       holder.txtPrice.setText(String.valueOf(product.price));
       holder.txtRegion.setText(String.valueOf(product.region));
       Picasso.with(context).load(product.photo).into(holder.imgProduct);
    }


    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder{
        TextView txtProductName,txtDiscount,txtPrice,txtRegion;
        ImageView imgProduct;
        public ProductViewHolder(View itemView) {
            super(itemView);
            txtProductName= (TextView) itemView.findViewById(R.id.txtProductName);
            txtDiscount= (TextView) itemView.findViewById(R.id.txtDiscount);
            txtPrice= (TextView) itemView.findViewById(R.id.txtPrice);
            txtRegion=(TextView) itemView.findViewById(R.id.txtRegion);
            imgProduct=itemView.findViewById(R.id.imgView);
        }
    }
}

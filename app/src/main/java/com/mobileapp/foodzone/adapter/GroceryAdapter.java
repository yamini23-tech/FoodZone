package com.mobileapp.foodzone.adapter;

/**
 * Created by sandy on 2/7/2018.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.foodzone.R;
import com.mobileapp.foodzone.listeners.UpdateCartListener;
import com.mobileapp.foodzone.model.GroceryDo;
import com.mobileapp.foodzone.utills.PreferenceUtils;

import java.util.ArrayList;

public class GroceryAdapter extends RecyclerView.Adapter<GroceryAdapter.MyViewHolder> /*implements Filterable*/ {

    private ArrayList<GroceryDo> listGroceryDos;
    private String imageURL;
    private Context context;
    private UpdateCartListener listener;
    private PreferenceUtils preferenceUtils;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvProductName, tvPrice, tvDescription,tvNumber;
        public ImageView ivProductImage;
        public RelativeLayout rlRemove,rlAdd;

        public MyViewHolder(View view) {
            super(view);
            tvProductName   = (TextView) view.findViewById(R.id.tvItemName);
            tvPrice         = (TextView) view.findViewById(R.id.tvPrice);
            tvDescription   = (TextView) view.findViewById(R.id.tvItemDescription);
            ivProductImage  = (ImageView)view.findViewById(R.id.ivItem);
            rlRemove        = (RelativeLayout) view.findViewById(R.id.rlRemove);
            rlAdd           = (RelativeLayout) view.findViewById(R.id.rlAdd);
            tvNumber        = (TextView) view.findViewById(R.id.tvNumber);
        }
    }


    public GroceryAdapter(Context context, UpdateCartListener listener, ArrayList<GroceryDo> listGroceryDos, String imageURL) {
        this.context = context;

        this.imageURL = imageURL;
        this.listener = listener;
        preferenceUtils = new PreferenceUtils(context);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.category_data, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final GroceryDo GroceryDo = AppConstants.listGrocery.get(position);
        holder.tvProductName.setText(GroceryDo.productName);
        holder.tvPrice.setText("$"+GroceryDo.price);
        holder.tvDescription.setText(GroceryDo.description);
        holder.tvNumber.setText(""+GroceryDo.itemCount);
        Glide.with(context)
                .load(GroceryDo.uploadImage)
                .into(holder.ivProductImage);

        holder.rlAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AppConstants.listGrocery.get(position).itemCount = AppConstants.listGrocery.get(position).itemCount + 1;
//                GroceryDo.itemCount = GroceryDo.itemCount + 1;
                holder.tvNumber.setText(""+AppConstants.listGrocery.get(position).itemCount);

                int cartCount = getCartCount();
                preferenceUtils.saveInt(PreferenceUtils.CART_COUNT,cartCount);
//                BaseActivity.shoppingCartTotalNumber.setText(""+cartCount);
                listener.updateCartCount();

            }
        });
        holder.rlRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(AppConstants.listGrocery.get(position).itemCount > 0){
                    AppConstants.listGrocery.get(position).itemCount = AppConstants.listGrocery.get(position).itemCount - 1;
                    holder.tvNumber.setText(""+AppConstants.listGrocery.get(position).itemCount);
                }

                int cartCount = getCartCount();
                preferenceUtils.saveInt(PreferenceUtils.CART_COUNT,cartCount);
                listener.updateCartCount();

            }
        });

    }

    @Override
    public int getItemCount() {
        return AppConstants.listGrocery.size();
    }

    private int getCartCount(){

        int cartCount = 0;
        for(GroceryDo GroceryDo : AppConstants.listGrocery){
            if(GroceryDo.itemCount > 0){
                cartCount = cartCount + 1;
            }
        }
        return cartCount;
    }
}

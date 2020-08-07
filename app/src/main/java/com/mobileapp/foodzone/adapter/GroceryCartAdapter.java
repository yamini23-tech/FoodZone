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
import com.foodzone.common.AppConstants;
import com.foodzone.listeners.UpdateTotalPriceListener;
import com.foodzone.model.GroceryDo;
import com.foodzone.utills.PreferenceUtils;

import java.util.ArrayList;

public class GroceryCartAdapter extends RecyclerView.Adapter<GroceryCartAdapter.MyViewHolder> /*implements Filterable*/ {

    private ArrayList<GroceryDo> listGroceryDos;
    private String imageURL;
    private Context context;
    private UpdateTotalPriceListener listener;
    private PreferenceUtils preferenceUtils;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvProductName, tvPrice, tvDescription,tvNumber;
        public ImageView ivProductImage;
        private RelativeLayout rlRemove,rlAdd;

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


    public GroceryCartAdapter(Context context, UpdateTotalPriceListener listener, ArrayList<GroceryDo> listGroceryDos, String imageURL) {
        this.context = context;
//        this.listGroceryDos = listGroceryDos;
//        this.listGroceryDos = AppConstants.listCartGrocery;
        this.imageURL = imageURL;
        this.listener = listener;
        preferenceUtils = new PreferenceUtils(context);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.order_data, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final GroceryDo GroceryDo = AppConstants.listCartGrocery.get(position);
        holder.tvProductName.setText(GroceryDo.productName);
        holder.tvPrice.setText("$"+(Double.parseDouble(String.format("%.2f",GroceryDo.price * GroceryDo.itemCount))));
        holder.tvNumber.setText(""+GroceryDo.itemCount);

        Glide.with(context)
                .load(GroceryDo.uploadImage)
                .into(holder.ivProductImage);

        holder.rlAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AppConstants.listCartGrocery.get(position).itemCount = AppConstants.listCartGrocery.get(position).itemCount + 1;
//                GroceryDo.itemCount = GroceryDo.itemCount + 1;
                holder.tvNumber.setText(""+AppConstants.listCartGrocery.get(position).itemCount);
                holder.tvPrice.setText("$"+(Double.parseDouble(String.format("%.2f", GroceryDo.price * GroceryDo.itemCount))));
                listener.updateTotalPrice();



            }
        });
        holder.rlRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(AppConstants.listCartGrocery.get(position).itemCount == 1) {

                    for(GroceryDo GroceryDo1 : AppConstants.listGrocery){
                         if(AppConstants.listCartGrocery.get(position).id == GroceryDo1.id){
                             GroceryDo1.itemCount = 0;
                         }
                    }
                    AppConstants.listCartGrocery.remove(position);
                    int cartCount = preferenceUtils.getIntFromPreference(PreferenceUtils.CART_COUNT,0);
                    preferenceUtils.saveInt(PreferenceUtils.CART_COUNT,cartCount - 1);
                    notifyDataSetChanged();

                }else if(AppConstants.listCartGrocery.get(position).itemCount > 1){
                    AppConstants.listCartGrocery.get(position).itemCount = AppConstants.listCartGrocery.get(position).itemCount - 1;
                    holder.tvNumber.setText(""+AppConstants.listCartGrocery.get(position).itemCount);
                }
                 holder.tvPrice.setText("$"+(Double.parseDouble(String.format("%.2f", GroceryDo.price * GroceryDo.itemCount))));
                 listener.updateTotalPrice();
            }
        });

//        holder.ivProductImage.setImageResource(GroceryDo.uploadImage);

    }

    @Override
    public int getItemCount() {
        return AppConstants.listCartGrocery.size();
    }
}

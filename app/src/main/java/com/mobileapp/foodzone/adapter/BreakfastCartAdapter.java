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
import com.mobileapp.foodzone.common.AppConstants;
import com.mobileapp.foodzone.listeners.UpdateTotalPriceListener;
import com.mobileapp.foodzone.model.BreakfastDo;
import com.mobileapp.foodzone.utills.PreferenceUtils;

import java.util.ArrayList;

public class BreakfastCartAdapter extends RecyclerView.Adapter<BreakfastCartAdapter.MyViewHolder> /*implements Filterable*/ {

    private ArrayList<BreakfastDo> listBreakfastDos;
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


    public BreakfastCartAdapter(Context context, UpdateTotalPriceListener listener, ArrayList<BreakfastDo> listBreakfastDos, String imageURL) {
        this.context = context;
//        this.listBreakfastDos = listBreakfastDos;
//        this.listBreakfastDos = AppConstants.listCartBreakfast;
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
        final BreakfastDo BreakfastDo = AppConstants.listCartBreakfast.get(position);
        holder.tvProductName.setText(BreakfastDo.productName);
        holder.tvPrice.setText("$"+(Double.parseDouble(String.format("%.2f",BreakfastDo.price * BreakfastDo.itemCount))));
        holder.tvNumber.setText(""+BreakfastDo.itemCount);

        Glide.with(context)
                .load(BreakfastDo.uploadImage)
                .into(holder.ivProductImage);

        holder.rlAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AppConstants.listCartBreakfast.get(position).itemCount = AppConstants.listCartBreakfast.get(position).itemCount + 1;
//                BreakfastDo.itemCount = BreakfastDo.itemCount + 1;
                holder.tvNumber.setText(""+AppConstants.listCartBreakfast.get(position).itemCount);
                holder.tvPrice.setText("$"+(Double.parseDouble(String.format("%.2f", BreakfastDo.price * BreakfastDo.itemCount))));
                listener.updateTotalPrice();



            }
        });
        holder.rlRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(AppConstants.listCartBreakfast.get(position).itemCount == 1) {

                    for(BreakfastDo BreakfastDo1 : AppConstants.listBreakfast){
                         if(AppConstants.listCartBreakfast.get(position).id == BreakfastDo1.id){
                             BreakfastDo1.itemCount = 0;
                         }
                    }
                    AppConstants.listCartBreakfast.remove(position);
                    int cartCount = preferenceUtils.getIntFromPreference(PreferenceUtils.CART_COUNT,0);
                    preferenceUtils.saveInt(PreferenceUtils.CART_COUNT,cartCount - 1);
                    notifyDataSetChanged();

                }else if(AppConstants.listCartBreakfast.get(position).itemCount > 1){
                    AppConstants.listCartBreakfast.get(position).itemCount = AppConstants.listCartBreakfast.get(position).itemCount - 1;
                    holder.tvNumber.setText(""+AppConstants.listCartBreakfast.get(position).itemCount);
                }
                 holder.tvPrice.setText("$"+(Double.parseDouble(String.format("%.2f", BreakfastDo.price * BreakfastDo.itemCount))));
                 listener.updateTotalPrice();
            }
        });

//        holder.ivProductImage.setImageResource(BreakfastDo.uploadImage);

    }

    @Override
    public int getItemCount() {
        return AppConstants.listCartBreakfast.size();
    }
}

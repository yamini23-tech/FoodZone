package com.mobileapp.foodzone.adapter;



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
import com.mobileapp.foodzone.model.LunchDo;
import com.mobileapp.foodzone.utills.PreferenceUtils;

import java.util.ArrayList;

public class LunchCartAdapter extends RecyclerView.Adapter<LunchCartAdapter.MyViewHolder> /*implements Filterable*/ {

    private ArrayList<LunchDo> listLunchDos;
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


    public LunchCartAdapter(Context context, UpdateTotalPriceListener listener, ArrayList<LunchDo> listLunchDos, String imageURL) {
        this.context = context;
//        this.listLunchDos = listLunchDos;
//        this.listLunchDos = AppConstants.listCartLunch;
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
        final LunchDo lunchDo = AppConstants.listCartLunch.get(position);
        holder.tvProductName.setText(lunchDo.productName);
        holder.tvPrice.setText("$"+(Double.parseDouble(String.format("%.2f",lunchDo.price * lunchDo.itemCount))));
        holder.tvNumber.setText(""+lunchDo.itemCount);

        Glide.with(context)
                .load(lunchDo.uploadImage)
                .into(holder.ivProductImage);

        holder.rlAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AppConstants.listCartLunch.get(position).itemCount = AppConstants.listCartLunch.get(position).itemCount + 1;
//                lunchDo.itemCount = lunchDo.itemCount + 1;
                holder.tvNumber.setText(""+AppConstants.listCartLunch.get(position).itemCount);
                holder.tvPrice.setText("$"+(Double.parseDouble(String.format("%.2f", lunchDo.price * lunchDo.itemCount))));
                listener.updateTotalPrice();



            }
        });
        holder.rlRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(AppConstants.listCartLunch.get(position).itemCount == 1) {

                    for(LunchDo lunchDo1 : AppConstants.listLunch){
                         if(AppConstants.listCartLunch.get(position).id == lunchDo1.id){
                             lunchDo1.itemCount = 0;
                         }
                    }
                    AppConstants.listCartLunch.remove(position);
                    int cartCount = preferenceUtils.getIntFromPreference(PreferenceUtils.CART_COUNT,0);
                    preferenceUtils.saveInt(PreferenceUtils.CART_COUNT,cartCount - 1);
                    notifyDataSetChanged();

                }else if(AppConstants.listCartLunch.get(position).itemCount > 1){
                    AppConstants.listCartLunch.get(position).itemCount = AppConstants.listCartLunch.get(position).itemCount - 1;
                    holder.tvNumber.setText(""+AppConstants.listCartLunch.get(position).itemCount);
                }
                 holder.tvPrice.setText("$"+(Double.parseDouble(String.format("%.2f", lunchDo.price * lunchDo.itemCount))));
                 listener.updateTotalPrice();
            }
        });

//        holder.ivProductImage.setImageResource(lunchDo.uploadImage);

    }

    @Override
    public int getItemCount() {
        return AppConstants.listCartLunch.size();
    }
}

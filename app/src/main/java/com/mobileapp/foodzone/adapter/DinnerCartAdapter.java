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
import com.mobileapp.foodzone.model.DinnerDo;
import com.mobileapp.foodzone.utills.PreferenceUtils;

import java.util.ArrayList;

public class DinnerCartAdapter extends RecyclerView.Adapter<DinnerCartAdapter.MyViewHolder> /*implements Filterable*/ {

    private ArrayList<DinnerDo> listDinnerDos;
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


    public DinnerCartAdapter(Context context, UpdateTotalPriceListener listener, ArrayList<DinnerDo> listDinnerDos, String imageURL) {
        this.context = context;
//        this.listDinnerDos = listDinnerDos;
//        this.listDinnerDos = AppConstants.listCartDinner;
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
        final DinnerDo DinnerDo = AppConstants.listCartDinner.get(position);
        holder.tvProductName.setText(DinnerDo.productName);
        holder.tvPrice.setText("$"+(Double.parseDouble(String.format("%.2f",DinnerDo.price * DinnerDo.itemCount))));
        holder.tvNumber.setText(""+DinnerDo.itemCount);

        Glide.with(context)
                .load(DinnerDo.uploadImage)
                .into(holder.ivProductImage);

        holder.rlAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AppConstants.listCartDinner.get(position).itemCount = AppConstants.listCartDinner.get(position).itemCount + 1;
//                DinnerDo.itemCount = DinnerDo.itemCount + 1;
                holder.tvNumber.setText(""+AppConstants.listCartDinner.get(position).itemCount);
                holder.tvPrice.setText("$"+(Double.parseDouble(String.format("%.2f", DinnerDo.price * DinnerDo.itemCount))));
                listener.updateTotalPrice();



            }
        });
        holder.rlRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(AppConstants.listCartDinner.get(position).itemCount == 1) {

                    for(DinnerDo DinnerDo1 : AppConstants.listDinner){
                         if(AppConstants.listCartDinner.get(position).id == DinnerDo1.id){
                             DinnerDo1.itemCount = 0;
                         }
                    }
                    AppConstants.listCartDinner.remove(position);
                    int cartCount = preferenceUtils.getIntFromPreference(PreferenceUtils.CART_COUNT,0);
                    preferenceUtils.saveInt(PreferenceUtils.CART_COUNT,cartCount - 1);
                    notifyDataSetChanged();

                }else if(AppConstants.listCartDinner.get(position).itemCount > 1){
                    AppConstants.listCartDinner.get(position).itemCount = AppConstants.listCartDinner.get(position).itemCount - 1;
                    holder.tvNumber.setText(""+AppConstants.listCartDinner.get(position).itemCount);
                }
                 holder.tvPrice.setText("$"+(Double.parseDouble(String.format("%.2f", DinnerDo.price * DinnerDo.itemCount))));
                 listener.updateTotalPrice();
            }
        });

//        holder.ivProductImage.setImageResource(DinnerDo.uploadImage);

    }

    @Override
    public int getItemCount() {
        return AppConstants.listCartDinner.size();
    }
}

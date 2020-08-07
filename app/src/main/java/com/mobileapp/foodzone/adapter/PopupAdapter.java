
package com.mobileapp.foodzone.adapter;
/*
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.foodzone.R;
import com.foodzone.widget.Dish;
import com.foodzone.widget.Cart;
import com.foodzone.widget.ShopCartImp;

import java.util.ArrayList;


public class PopupAdapter extends RecyclerView.Adapter {

    private static String TAG = "PopupAdapter";
    private Cart shopCart;
    private Context context;
    private int itemCount;
    private ArrayList<Dish> dishList;
    private ShopCartImp shopCartImp;

    public PopupAdapter(Context context, Cart shopCart) {
        this.shopCart   = shopCart;
        this.context    = context;
        this.itemCount  = shopCart.getDishAccount();
        this.dishList   = new ArrayList<>();
        dishList.addAll(shopCart.getShoppingSingleMap().keySet());
        Log.e(TAG, "PopupAdapter: " + this.itemCount);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_data, parent, false);
        DishViewHolder viewHolder = new DishViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final DishViewHolder dishholder = (DishViewHolder) holder;
        final Dish dish = getDishByPosition(position);
        if (dish != null) {
            dishholder.tvItemName.setText(dish.getDishName());
            dishholder.tvPrice.setText("$"+(int) dish.getDishPrice() + "");
            dishholder.ivItem.setImageResource(dish.getmDrawable());
            int num = shopCart.getShoppingSingleMap().get(dish);
            dishholder.tvNumber.setText(num + "");

            dishholder.ivAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (shopCart.addShoppingSingle(dish)) {
                        notifyItemChanged(position);
                        if (shopCartImp != null)
                            shopCartImp.add(view, position);
                    }
                }
            });

            dishholder.ivRemove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (shopCart.subShoppingSingle(dish)) {
                        dishList.clear();
                        dishList.addAll(shopCart.getShoppingSingleMap().keySet());
                        itemCount = shopCart.getDishAccount();
                        ;
                        notifyDataSetChanged();
                        if (shopCartImp != null)
                            shopCartImp.remove(view, position);
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return this.itemCount;
    }

    public Dish getDishByPosition(int position) {
        return dishList.get(position);
    }

    public ShopCartImp getShopCartImp() {
        return shopCartImp;
    }

    public void setShopCartImp(ShopCartImp shopCartImp) {
        this.shopCartImp = shopCartImp;
    }

    private class DishViewHolder extends RecyclerView.ViewHolder {
        private TextView tvItemName;
        private TextView tvPrice;
        private LinearLayout llItem;
        private ImageView ivRemove;
        private ImageView ivItem;
        private ImageView ivAdd;
        private TextView tvNumber;


        public DishViewHolder(View itemView) {
            super(itemView);
            tvItemName = (TextView) itemView.findViewById(R.id.tvItemName);
            tvPrice = (TextView) itemView.findViewById(R.id.tvPrice);
            llItem = (LinearLayout) itemView.findViewById(R.id.llItem);
            ivRemove = (ImageView) itemView.findViewById(R.id.ivRemove);
            ivAdd = (ImageView) itemView.findViewById(R.id.ivAdd);
            ivItem = (ImageView) itemView.findViewById(R.id.ivItem);
            tvNumber = (TextView) itemView.findViewById(R.id.tvNumber);

        }

    }
}*/

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
import com.mobileapp.foodzone.listeners.UpdateCartListener;
import com.mobileapp.foodzone.model.DinnerDo;
import com.mobileapp.foodzone.utills.PreferenceUtils;

import java.util.ArrayList;

/**
 * Adapter class for dinner related items
 */
public class DinnerAdapter extends RecyclerView.Adapter<DinnerAdapter.MyViewHolder> /*implements Filterable*/ {

    private ArrayList<DinnerDo> listDinnerDos;
    private String imageURL;
    private Context context;
    private UpdateCartListener listener;
    private PreferenceUtils preferenceUtils;


    /**
     * The view that need to be st to recycler view
     */
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


    public DinnerAdapter(Context context, UpdateCartListener listener, ArrayList<DinnerDo> listDinnerDos, String imageURL) {
        this.context = context;

        this.imageURL = imageURL;
        this.listener = listener;
        preferenceUtils = new PreferenceUtils(context);
    }

    /**
     * Called when RecyclerView needs a new RecyclerView.ViewHolder of the given type to represent an item.
     * @param parent The ViewGroup into which the new View will be added after it is bound to an adapter position.
     * @param viewType The view type of the new View.
     * @return Itemholder instance
     */
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.category_data, parent, false);

        return new MyViewHolder(itemView);
    }

    /**
     *Called by RecyclerView to display the data at the specified position.
     * @param holder The ViewHolder which should be updated to represent the contents of the item at the given position
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final DinnerDo lunchDo = AppConstants.listDinner.get(position);
        holder.tvProductName.setText(lunchDo.productName);
        holder.tvPrice.setText("$"+lunchDo.price);
        holder.tvDescription.setText(lunchDo.description);
        holder.tvNumber.setText(""+lunchDo.itemCount);
        Glide.with(context)
                .load(lunchDo.uploadImage)
                .into(holder.ivProductImage);

        holder.rlAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AppConstants.listDinner.get(position).itemCount = AppConstants.listDinner.get(position).itemCount + 1;
//                lunchDo.itemCount = lunchDo.itemCount + 1;
                holder.tvNumber.setText(""+AppConstants.listDinner.get(position).itemCount);

                int cartCount = getCartCount();
                preferenceUtils.saveInt(PreferenceUtils.CART_COUNT,cartCount);
//                BaseActivity.shoppingCartTotalNumber.setText(""+cartCount);
                listener.updateCartCount();

            }
        });
        holder.rlRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(AppConstants.listDinner.get(position).itemCount > 0){
                    AppConstants.listDinner.get(position).itemCount = AppConstants.listDinner.get(position).itemCount - 1;
                    holder.tvNumber.setText(""+AppConstants.listDinner.get(position).itemCount);
                }

                int cartCount = getCartCount();
                preferenceUtils.saveInt(PreferenceUtils.CART_COUNT,cartCount);
                listener.updateCartCount();
//                BaseActivity.shoppingCartTotalNumber.setText(""+cartCount);

            }
        });
//        holder.ivProductImage.setImageResource(lunchDo.uploadImage);

    }

    /**
     * This method returns the count of items
     * @return No of items
     */
    @Override
    public int getItemCount() {
        return AppConstants.listDinner.size();
    }

    /**
     * This method returns the count of items in cart
     * @return No of items in cart
     */
    private int getCartCount(){

        int cartCount = 0;
        for(DinnerDo lunchDo : AppConstants.listDinner){
            if(lunchDo.itemCount > 0){
                cartCount = cartCount + 1;
            }
        }
        return cartCount;
    }
}

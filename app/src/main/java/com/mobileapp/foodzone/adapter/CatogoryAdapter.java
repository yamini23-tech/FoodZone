package com.mobileapp.foodzone.adapter;//package com.foodzone.adapter;
//
//import android.content.Context;
//import android.support.v7.widget.RecyclerView;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Button;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.TextView;
//
//
//import com.foodzone.R;
//import com.foodzone.activities.CatogoryActivity;
//import com.foodzone.model.LunchDo;
//import com.foodzone.widget.Dish;
//import com.foodzone.widget.DishMenu;
//import com.foodzone.widget.Cart;
//import com.foodzone.widget.ShopCartImp;
//
//import java.util.ArrayList;
//
//
//public class CatogoryAdapter extends RecyclerView.Adapter {
//    private final int MENU_TYPE = 0;
//    private final int DISH_TYPE = 1;
//    private final int HEAD_TYPE = 2;
//
//    private Context mContext;
//    private ArrayList<LunchDo> listLunchDOs;
//    private String imageURL;
//
//    public CatogoryAdapter(Context mContext, ArrayList<LunchDo> listLunchDOs) {
//        this.mContext   = mContext;
//        this.listLunchDOs  = listLunchDOs;
//    }
//    @Override
//    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//
//
//            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_data, parent, false);
//            DishViewHolder viewHolder = new DishViewHolder(view);
//            return viewHolder;
//
//    }
//
//
//    private class DishViewHolder extends RecyclerView.ViewHolder {
//        private TextView tvItemName;
//        private TextView tvPrice;
//        private LinearLayout llItem;
//        private ImageView ivAdd;
//        private ImageView ivItem;
//        private ImageView ivRemove;
//        private TextView tvItemDescrption;
//        private TextView tvNumber;
//
//        public DishViewHolder(View itemView) {
//            super(itemView);
//
//            tvItemDescrption = (TextView) itemView.findViewById(R.id.tvItemDescription);
//            tvItemName       = (TextView) itemView.findViewById(R.id.tvItemName);
//            tvPrice          = (TextView) itemView.findViewById(R.id.tvPrice);
//            llItem           = (LinearLayout) itemView.findViewById(R.id.llItem);
//            ivRemove         = (ImageView) itemView.findViewById(R.id.ivRemove);
//            ivAdd            = (ImageView) itemView.findViewById(R.id.ivAdd);
//            tvNumber         = (TextView) itemView.findViewById(R.id.tvNumber);
//            ivItem           = (ImageView) itemView.findViewById(R.id.ivItem);
//        }
//
//    }
//
//
//    @Override
//    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
//
//
////        if (getItemViewType(position) == MENU_TYPE) {
////            MenuViewHolder menuholder = (MenuViewHolder) holder;
////            if (menuholder != null) {
////                menuholder.right_menu_title.setText("");
////                menuholder.right_menu_layout.setContentDescription(position + "");
////            }
////        } else {
//            final DishViewHolder dishholder = (DishViewHolder) holder;
//            if (dishholder != null) {
//
////                final Dish dish = getDishByPosition(position);
//                dishholder.tvItemName.setText(dish.getDishName());
//                dishholder.tvPrice.setText("$" + " " + dish.getDishPrice());
//                dishholder.ivItem.setImageResource(dish.getmDrawable());
//
//                dishholder.tvItemDescrption.setText(dish.getDishDescrption());
//                int count = 0;
////                if (shopCart.getShoppingSingleMap().containsKey(dish)) {
////                    count = shopCart.getShoppingSingleMap().get(dish);
////                }
//                if (count <= 0) {
//                    dishholder.ivRemove.setVisibility(View.GONE);
//                    dishholder.tvNumber.setVisibility(View.GONE);
//                } else {
//                    dishholder.ivRemove.setVisibility(View.VISIBLE);
//                    dishholder.tvNumber.setVisibility(View.VISIBLE);
//                    dishholder.tvNumber.setText(count + "");
//                }
////                dishholder.ivAdd.setOnClickListener(new View.OnClickListener() {
////                    @Override
////                    public void onClick(View view) {
////                        if (shopCart.addShoppingSingle(dish)) {
////                            notifyItemChanged(position);
////                            if (shopCartImp != null)
////                                shopCartImp.add(view, position);
////                        }
////                    }
////                });
//
////                dishholder.ivRemove.setOnClickListener(new View.OnClickListener() {
////                    @Override
////                    public void onClick(View view) {
////                        if (shopCart.subShoppingSingle(dish)) {
////                            notifyItemChanged(position);
////                            if (shopCartImp != null)
////                                shopCartImp.remove(view, position);
////                        }
////                    }
////                });
//
//
//            }
//        }
//    }
//
////    public DishMenu getMenuByPosition(int position) {
////        int sum = 0;
////        for (DishMenu menu : mMenuList) {
////            if (position == sum) {
////                return menu;
////            }
////            sum += menu.getDishList().size() + 1;
////        }
////        return null;
////    }
//
////    public Dish getDishByPosition(int position) {
////        for (DishMenu menu : mMenuList) {
////            if (position > 0 && position <= menu.getDishList().size()) {
////                return menu.getDishList().get(position - 1);
////            } else {
////                position -= menu.getDishList().size() + 1;
////            }
////        }
////        return null;
////    }
//
//    public DishMenu getMenuOfMenuByPosition(int position) {
////        for (DishMenu menu : mMenuList) {
////            if (position == 0) return menu;
////            if (position > 0 && position <= menu.getDishList().size()) {
////                return menu;
////            } else {
////                position -= menu.getDishList().size() + 1;
////            }
////        }
////        return null;
////    }
//
//    @Override
//    public int getItemCount() {
//        return mItemCount;
//    }
//
////    public ShopCartImp getShopCartImp() {
////        return shopCartImp;
////    }
//
////    public void setShopCartImp(ShopCartImp shopCartImp) {
////        this.shopCartImp = shopCartImp;
////    }
//
//    private class MenuViewHolder extends RecyclerView.ViewHolder {
//        private LinearLayout right_menu_layout;
//        private TextView right_menu_title;
//
//        public MenuViewHolder(View itemView) {
//            super(itemView);
//            right_menu_layout = (LinearLayout) itemView.findViewById(R.id.right_menu_item);
//            right_menu_title = (TextView) itemView.findViewById(R.id.right_menu_tv);
//
//        }
//    }
//
//}

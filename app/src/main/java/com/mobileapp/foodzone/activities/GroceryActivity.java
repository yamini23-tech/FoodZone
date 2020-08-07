package com.mobileapp.foodzone.activities;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.foodzone.R;
import com.foodzone.adapter.GroceryAdapter;
import com.foodzone.common.AppConstants;
import com.foodzone.listeners.UpdateCartListener;
import com.foodzone.model.GroceryDo;
import com.foodzone.utills.PreferenceUtils;

import java.util.ArrayList;

public class GroceryActivity extends BaseActivity implements UpdateCartListener {
    private RecyclerView recycleview;
    private GroceryAdapter groceryAdapter;
    private TextView tvCartTotal, tvNumber;
    public RelativeLayout rlProceed, help;
    int categoryId = 0;
    String imageURL;


    private RelativeLayout llCategory;

    @Override
    public void initialize() {
        llCategory = (RelativeLayout) getLayoutInflater().inflate(R.layout.lunch_screen, null);
        llBody.addView(llCategory, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        initializeControls();

        if (getIntent().hasExtra("CategoryId")) {
            categoryId = getIntent().getExtras().getInt("CategoryId");
        }

        //First time cart count.
        preferenceUtils.saveInt(PreferenceUtils.CART_COUNT, 0);
        AppConstants.listLunch = new ArrayList<>();


        llshoppingCartLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AppConstants.listCartGrocery = new ArrayList<>();
                int cartCount = preferenceUtils.getIntFromPreference(PreferenceUtils.CART_COUNT, 0);
                if (cartCount > 0) {
                    for (GroceryDo GroceryDo : AppConstants.listGrocery) {
                        if (GroceryDo.itemCount != 0) {
                            AppConstants.listCartGrocery.add(GroceryDo);
                        }
                    }

                    Intent intent = new Intent(GroceryActivity.this, GroceryCartActivity.class);
                    intent.putExtra("CategoryId", categoryId);
                    intent.putExtra("imageURL", imageURL);
                    startActivity(intent);

                } else {
                    showAppCompatAlert("Alert!", "Your cart is Empty.Please Add Products", "Ok", "", "", true);
                }

            }


        });

        tvScreenTitle.setText("Grocery Delivery");
        rlProceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                llshoppingCartLayout.performClick();
            }
        });

        AppConstants.listGrocery = getGroceryDoS();
    }


    @Override
    public void initializeControls() {
        recycleview = (RecyclerView) findViewById(R.id.recycleview);
        tvNumber = (TextView) findViewById(R.id.tvNumber);
        rlProceed = (RelativeLayout) findViewById(R.id.rlProceed);
        help = (RelativeLayout) findViewById(R.id.rlHelp);

    }


    private void lunchAdapter(Context context, ArrayList<GroceryDo> listGroceryDos, String imageURL) {
        groceryAdapter = new GroceryAdapter(context, GroceryActivity.this, listGroceryDos, imageURL);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recycleview.setLayoutManager(linearLayoutManager);

        recycleview.setAdapter(groceryAdapter);
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (categoryId == 4) {
            lunchAdapter(GroceryActivity.this, AppConstants.listGrocery, imageURL);
        }

        updateCartCount();

    }

    private ArrayList<GroceryDo> getGroceryDoS() {
        ArrayList<GroceryDo> GroceryDos = new ArrayList<>();
        GroceryDo p = new GroceryDo();
        p.id = "1";
        p.description = "Apricots dry fruits";
        p.productName = "Apricots";
        p.itemCount = 0;
        p.price = 50.0;
        p.uploadImage = R.drawable.apricot;

        GroceryDos.add(p);

        p = new GroceryDo();

        p.id = "2";
        p.description = "Dry fruits";
        p.productName = "Mixed Dry fruits";
        p.itemCount = 0;
        p.price = 25.0;
        p.uploadImage = R.drawable.dryfruit;
        GroceryDos.add(p);

        p = new GroceryDo();

        p.id = "3";
        p.description = "Masala maggie";
        p.productName = "Maggie";
        p.itemCount = 0;
        p.price = 32.0;
        p.uploadImage = R.drawable.maggie;
        GroceryDos.add(p);
        p = new GroceryDo();

        p.id = "4";
        p.description = "Cooking oil";
        p.productName = "Noor Cooking Oil";
        p.itemCount = 0;
        p.price = 80.0;
        p.uploadImage = R.drawable.noor;
        GroceryDos.add(p);

        p = new GroceryDo();

        p.id = "5";
        p.description = "Oats";
        p.productName = "Plain Oats";
        p.itemCount = 0;
        p.price = 90.0;
        p.uploadImage = R.drawable.oats;
        GroceryDos.add(p);

        p = new GroceryDo();

        p.id = "6";
        p.description = "Cooking Oil";
        p.productName = "Safola Oil";
        p.itemCount = 0;
        p.price = 10.0;
        p.uploadImage = R.drawable.safola;
        GroceryDos.add(p);

        p = new GroceryDo();

        p.id = "7";
        p.description = "Cooking Salt";
        p.productName = "Salt";
        p.itemCount = 0;
        p.price = 20.0;
        p.uploadImage = R.drawable.salt;
        GroceryDos.add(p);

        p = new GroceryDo();

        p.id = "8";
        p.description = "Sugar";
        p.productName = "Sugar";
        p.itemCount = 0;
        p.price = 20.0;
        p.uploadImage = R.drawable.sugar;
        GroceryDos.add(p);

        p = new GroceryDo();

        p.id = "9";
        p.description = "Tide";
        p.productName = "Tide";
        p.itemCount = 0;
        p.price = 20.0;
        p.uploadImage = R.drawable.tide;
        GroceryDos.add(p);

        p = new GroceryDo();

        p.id = "10";
        p.description = "Walnuts";
        p.productName = "Walnuts";
        p.itemCount = 0;
        p.price = 20.0;
        p.uploadImage = R.drawable.walnuts;
        GroceryDos.add(p);


        return GroceryDos;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

//
//    @Override
//    public void dataRetreived(Response data) {
//        hideLoader();
//
//        if (data.method != null && data.method == ServiceMethods.WS_LUNCH) {
//
//            if (data.data != null && data.data instanceof LunchMainDO) {
//                LunchMainDO lunchMainDO = (LunchMainDO) data.data;
//                int status = lunchMainDO.status;
//                imageURL = lunchMainDO.imageURL;
//                ArrayList<GroceryDo> listGroceryDos = lunchMainDO.listGroceryDos;
//                if (status == 1) {
//                    AppConstants.listLunch = listGroceryDos;
//                    lunchAdapter(LunchActivity.this, listGroceryDos, imageURL);
//
//                    // Toast.makeText(LunchActivity.this,"success",Toast.LENGTH_SHORT).show();
//
//                } else {
//                    // TODO: 3/3/2018 show error message.
//                }
//            }
//
//        }
//    }

    @Override
    public void onBackPressed() {
        // close search view on back button pressed
        if (!svSearch.isIconified()) {
            svSearch.setIconified(true);
            return;
        }
        super.onBackPressed();
    }

    @Override
    public void updateCartCount() {
        int cartCount = preferenceUtils.getIntFromPreference(PreferenceUtils.CART_COUNT, 0);
        if (cartCount > 0) {
            rlProceed.setVisibility(View.VISIBLE);
        } else {
            rlProceed.setVisibility(View.GONE);
        }
        updateCartSize();
    }
}

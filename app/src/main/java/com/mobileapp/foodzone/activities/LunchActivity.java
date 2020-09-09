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
import com.mobileapp.foodzone.common.AppConstants;
import com.mobileapp.foodzone.listeners.UpdateCartListener;
import com.mobileapp.foodzone.model.LunchDo;
import com.mobileapp.foodzone.utills.PreferenceUtils;

import java.util.ArrayList;

public class LunchActivity extends BaseActivity implements UpdateCartListener {
    private RecyclerView recycleview;
    private LunchAdapter lunchAdapter;
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

                AppConstants.listCartLunch = new ArrayList<>();
                int cartCount = preferenceUtils.getIntFromPreference(PreferenceUtils.CART_COUNT, 0);
                if (cartCount > 0) {
                    for (LunchDo lunchDo : AppConstants.listLunch) {
                        if (lunchDo.itemCount != 0) {
                            AppConstants.listCartLunch.add(lunchDo);
                        }
                    }

                    Intent intent = new Intent(LunchActivity.this, LunchCartActivity.class);
                    intent.putExtra("CategoryId", categoryId);
                    intent.putExtra("imageURL", imageURL);
                    startActivity(intent);

                } else {
                    showAppCompatAlert("Alert!", "Your cart is Empty.Please Add Products", "Ok", "", "", true);
                }

            }


        });

        tvScreenTitle.setText("Lunch Delivery");
        rlProceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                llshoppingCartLayout.performClick();
            }
        });

        AppConstants.listLunch = getLunchDOS();
//                showLoader();
//                new CommonBL(LunchActivity.this, LunchActivity.this).lunch(1);



    }


    @Override
    public void initializeControls() {
        recycleview = (RecyclerView) findViewById(R.id.recycleview);
        tvNumber = (TextView) findViewById(R.id.tvNumber);
        rlProceed = (RelativeLayout) findViewById(R.id.rlProceed);
        help = (RelativeLayout) findViewById(R.id.rlHelp);

    }


    private void lunchAdapter(Context context, ArrayList<LunchDo> listLunchDos, String imageURL) {
        lunchAdapter = new LunchAdapter(context, LunchActivity.this, listLunchDos, imageURL);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recycleview.setLayoutManager(linearLayoutManager);

        recycleview.setAdapter(lunchAdapter);
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (categoryId == 1) {
            lunchAdapter(LunchActivity.this, AppConstants.listLunch, imageURL);
        }

        updateCartCount();

    }

    private ArrayList<LunchDo> getLunchDOS() {
        ArrayList<LunchDo> LunchDos = new ArrayList<>();
        LunchDo p = new LunchDo();
        p.id = "1";
        p.description = "Spicy Chicken 65";
        p.productName = "Chicken 65";
        p.itemCount = 0;
        p.price = 20.0;
        p.uploadImage = R.drawable.chicken65;

        LunchDos.add(p);

        p = new LunchDo();

        p.id = "2";
        p.description = "Special Chicken biryani";
        p.productName = "Chicken biryani";
        p.itemCount = 0;
        p.price = 25.0;
        p.uploadImage = R.drawable.chickenbiryani;
        LunchDos.add(p);

        p = new LunchDo();

        p.id = "3";
        p.description = "Spicy Chicken Curry";
        p.productName = "Chicken Curry";
        p.itemCount = 0;
        p.price = 32.0;
        p.uploadImage = R.drawable.chickencurry;
        LunchDos.add(p);
        p = new LunchDo();

        p.id = "4";
        p.description = "Chicken Mejestic";
        p.productName = "Chicken Mejestic";
        p.itemCount = 0;
        p.price = 80.0;
        p.uploadImage = R.drawable.mejestic;
        LunchDos.add(p);

        p = new LunchDo();

        p.id = "5";
        p.description = "Mutton Biryani cheff special";
        p.productName = "Mutton Biryani";
        p.itemCount = 0;
        p.price = 90.0;
        p.uploadImage = R.drawable.muutonbiryani;
        LunchDos.add(p);

        p = new LunchDo();

        p.id = "6";
        p.description = "Butter Naan";
        p.productName = "Naan";
        p.itemCount = 0;
        p.price = 10.0;
        p.uploadImage = R.drawable.naan;
        LunchDos.add(p);

        p = new LunchDo();

        p.id = "7";
        p.description = "Paneer pulav";
        p.productName = "Veg Paneer Pulao";
        p.itemCount = 0;
        p.price = 30.0;
        p.uploadImage = R.drawable.paneerpulao;
        LunchDos.add(p);

        p = new LunchDo();

        p.id = "8";
        p.description = "Plain rice";
        p.productName = "Plain Rice";
        p.itemCount = 0;
        p.price = 10.0;
        p.uploadImage = R.drawable.plainrice;
        LunchDos.add(p);

        p = new LunchDo();

        p.id = "9";
        p.description = "Butter Tandoori Roti";
        p.productName = "Tandoori Roti";
        p.itemCount = 0;
        p.price = 25.0;
        p.uploadImage = R.drawable.tandooriroti;
        LunchDos.add(p);

        p = new LunchDo();

        p.id = "10";
        p.description = "Veg Biryani";
        p.productName = "Veg Biryani";
        p.itemCount = 0;
        p.price = 22.0;
        p.uploadImage = R.drawable.vegbiryani;
        LunchDos.add(p);


        return LunchDos;
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
//                ArrayList<LunchDo> listLunchDOs = lunchMainDO.listLunchDOs;
//                if (status == 1) {
//                    AppConstants.listLunch = listLunchDOs;
//                    lunchAdapter(LunchActivity.this, listLunchDOs, imageURL);
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

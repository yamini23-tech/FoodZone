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
import com.mobileapp.foodzone.listeners.UpdateCartListener;
import com.mobileapp.foodzone.model.DinnerDo;
import com.mobileapp.foodzone.utills.PreferenceUtils;

import java.util.ArrayList;

public class DinnerActivity extends BaseActivity implements UpdateCartListener {
    private RecyclerView recycleview;
    private DinnerAdapter dinnerAdapter;
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
        AppConstants.listDinner = new ArrayList<>();


        llshoppingCartLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AppConstants.listCartDinner = new ArrayList<>();
                int cartCount = preferenceUtils.getIntFromPreference(PreferenceUtils.CART_COUNT, 0);
                if (cartCount > 0) {
                    for (DinnerDo dinnerDo : AppConstants.listDinner) {
                        if (dinnerDo.itemCount != 0) {
                            AppConstants.listCartDinner.add(dinnerDo);
                        }
                    }

                    Intent intent = new Intent(DinnerActivity.this, DinnerCartActivity.class);
                    intent.putExtra("CategoryId", categoryId);
                    intent.putExtra("imageURL", imageURL);
                    startActivity(intent);

                } else {
                    showAppCompatAlert("Alert!", "Your cart is Empty.Please Add Products", "Ok", "", "", true);
                }

            }


        });

        tvScreenTitle.setText("Dinner Delivery");
        rlProceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                llshoppingCartLayout.performClick();
            }
        });

        AppConstants.listDinner = getDinnerDOS();


    }


    @Override
    public void initializeControls() {
        recycleview = (RecyclerView) findViewById(R.id.recycleview);
        tvNumber = (TextView) findViewById(R.id.tvNumber);
        rlProceed = (RelativeLayout) findViewById(R.id.rlProceed);
        help = (RelativeLayout) findViewById(R.id.rlHelp);

    }


    private void dinnerAdapter(Context context, ArrayList<DinnerDo> listDinnerDos, String imageURL) {
        dinnerAdapter = new DinnerAdapter(context, DinnerActivity.this, listDinnerDos, imageURL);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recycleview.setLayoutManager(linearLayoutManager);

        recycleview.setAdapter(dinnerAdapter);
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (categoryId == 2) {
            dinnerAdapter(DinnerActivity.this, AppConstants.listDinner, imageURL);
        }

        updateCartCount();

    }

    private ArrayList<DinnerDo> getDinnerDOS() {
        ArrayList<DinnerDo> DinnerDos = new ArrayList<>();
        DinnerDo p = new DinnerDo();
        p.id = "1";

        p.description = "Chicken Mejestic";
        p.productName = "Chicken Mejestic";
        p.itemCount = 0;
        p.price = 80.0;
        p.uploadImage = R.drawable.mejestic;
        DinnerDos.add(p);

        p = new DinnerDo();

        p.id = "2";


        p.description = "Veg Biryani";
        p.productName = "Veg Biryani";
        p.itemCount = 0;
        p.price = 22.0;
        p.uploadImage = R.drawable.vegbiryani;
        DinnerDos.add(p);

        p = new DinnerDo();

        p.id = "3";
        p.description = "Spicy Chicken Curry";
        p.productName = "Chicken Curry";
        p.itemCount = 0;
        p.price = 32.0;
        p.uploadImage = R.drawable.chickencurry;
        DinnerDos.add(p);
        p = new DinnerDo();

        p.id = "4";
        p.description = "Spicy Chicken 65";
        p.productName = "Chicken 65";
        p.itemCount = 0;
        p.price = 20.0;
        p.uploadImage = R.drawable.chicken65;

        DinnerDos.add(p);

        p = new DinnerDo();

        p.id = "5";
        p.description = "Mutton Biryani cheff special";
        p.productName = "Mutton Biryani";
        p.itemCount = 0;
        p.price = 90.0;
        p.uploadImage = R.drawable.muutonbiryani;
        DinnerDos.add(p);

        p = new DinnerDo();

        p.id = "6";
        p.description = "Butter Naan";
        p.productName = "Naan";
        p.itemCount = 0;
        p.price = 10.0;
        p.uploadImage = R.drawable.naan;
        DinnerDos.add(p);

        p = new DinnerDo();

        p.id = "7";
        p.description = "Paneer pulav";
        p.productName = "Veg Paneer Pulao";
        p.itemCount = 0;
        p.price = 30.0;
        p.uploadImage = R.drawable.paneerpulao;
        DinnerDos.add(p);

        p = new DinnerDo();

        p.id = "8";
        p.description = "Plain rice";
        p.productName = "Plain Rice";
        p.itemCount = 0;
        p.price = 10.0;
        p.uploadImage = R.drawable.plainrice;
        DinnerDos.add(p);

        p = new DinnerDo();

        p.id = "9";
        p.description = "Butter Tandoori Roti";
        p.productName = "Tandoori Roti";
        p.itemCount = 0;
        p.price = 25.0;
        p.uploadImage = R.drawable.tandooriroti;
        DinnerDos.add(p);

        p = new DinnerDo();
        p.description = "Special Chicken biryani";
        p.productName = "Chicken biryani";
        p.itemCount = 0;
        p.price = 25.0;
        p.uploadImage = R.drawable.chickenbiryani;
        p.id = "10";

        DinnerDos.add(p);



        return DinnerDos;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


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

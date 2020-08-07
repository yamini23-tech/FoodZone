package com.mobileapp.foodzone.activities;

import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.foodzone.R;
import com.foodzone.utills.PreferenceUtils;

;

public class MainActivity extends BaseActivity {
    private ImageView ivLunch, ivDinner, ivParty, ivGrocerry,ivLunchDelivery;

    private LinearLayout llMain;
    private String userId;
    private PreferenceUtils preferenceUtils;
    @Override
    public void initialize() {
        llMain = (LinearLayout) getLayoutInflater().inflate(R.layout.main_screen, null);
        llBody.addView(llMain, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        initializeControls();
        preferenceUtils = new PreferenceUtils(MainActivity.this);
        userId =  preferenceUtils.getStringFromPreference(PreferenceUtils.USER_ID,"");

        llshoppingCartLayout.setVisibility(View.GONE);
        ivLunchDelivery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, LunchActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("CategoryId", 1);
                startActivity(intent);
            }
        });

        ivDinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//            showToast("coming soon");
                Intent intent = new Intent(MainActivity.this, DinnerActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("CategoryId", 2);
                startActivity(intent);
            }
        });
        ivParty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                showToast("coming soon");

                Intent intent = new Intent(MainActivity.this, BreakfastActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("CategoryId", 3);
                startActivity(intent);
            }
        });
        ivGrocerry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                showToast("coming soon");

                Intent intent = new Intent(MainActivity.this, GroceryActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("CategoryId", 4);
                startActivity(intent);
            }
        });
    }

    @Override
    public void initializeControls() {


        tvScreenTitle.setText(getResources().getString(R.string.app_name));
        ivLunch         = (ImageView) findViewById(R.id.ivLunch);
        ivDinner        = (ImageView) findViewById(R.id.ivDinner);
        ivParty         = (ImageView) findViewById(R.id.ivParty);
        ivGrocerry      = (ImageView) findViewById(R.id.ivGrocery);

        ivLunchDelivery = (ImageView) findViewById(R.id.ivLunchDelivery);

    }



    @Override
    public void onButtonYesClick(String from) {
        super.onButtonYesClick(from);

        if("SUBMIT_CART".equals(from)){

            Intent intent = new Intent(MainActivity.this,MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);

        }
        if("ERROR".equals(from)){

            finishAffinity();

        }
    }
    @Override
    protected void onResume() {
        super.onResume();

        setMenuItems();

    }
}

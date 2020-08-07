package com.mobileapp.foodzone.activities;

import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.foodzone.R;

public class AboutActivity extends BaseActivity {

   private LinearLayout llAboutUs;

    @Override
    public void initialize() {

        llAboutUs = (LinearLayout) getLayoutInflater().inflate(R.layout.about_screen, null);
        llBody.addView(llAboutUs, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        initializeControls();
        llshoppingCartLayout.setVisibility(View.GONE);

    }

    @Override
    public void initializeControls() {

        //Testing.
        tvScreenTitle.setText("About Us");

    }




}

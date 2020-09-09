package com.mobileapp.foodzone.activities;

import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.foodzone.R;

/**
 * This class deals with about information for application
 */
public class AboutActivity extends BaseActivity {

   private LinearLayout llAboutUs;

    /**
     * This method is used to initialize the view with about us screen
     */
    @Override
    public void initialize() {

        llAboutUs = (LinearLayout) getLayoutInflater().inflate(R.layout.about_screen, null);
        llBody.addView(llAboutUs, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        initializeControls();
        llshoppingCartLayout.setVisibility(View.GONE);

    }

    /**
     * This method is used to set the title for the screen
     */
    @Override
    public void initializeControls() {

        //Testing.
        tvScreenTitle.setText("About Us");

    }




}

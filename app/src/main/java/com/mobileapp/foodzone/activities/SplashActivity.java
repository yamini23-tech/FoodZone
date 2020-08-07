package com.mobileapp.foodzone.activities;

import android.content.Intent;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.foodzone.R;

public class SplashActivity extends BaseActivity {

    RelativeLayout llSplash;

    @Override
    public void initialize() {

        llSplash = (RelativeLayout) getLayoutInflater().inflate(R.layout.splash_screen, null);
        llBody.addView(llSplash, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        initializeControls();

        toolbar.setVisibility(View.GONE);
        flToolbar.setVisibility(View.GONE);


    }

    @Override
    public void initializeControls() {


        new Handler().postDelayed(new Runnable() {


            @Override
            public void run() {
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
                finish();
            }

        }, 5000);

    }


}

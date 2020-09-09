package com.mobileapp.foodzone.activities;

import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.foodzone.R;
import com.google.firebase.firestore.FirebaseFirestore;
import com.mobileapp.foodzone.utills.PreferenceUtils;

;

/**
 * This class deals with dashboard after login screen
 */
public class MainActivity extends BaseActivity {
    private ImageView ivLunch, ivDinner, ivParty, ivGrocerry,ivLunchDelivery;
    FirebaseFirestore firebaseFirestore;
    private LinearLayout llMain;
    private String userId;
    private PreferenceUtils preferenceUtils;
    /**
     * Initialize with default values
     */
    @Override
    public void initialize() {
        llMain = (LinearLayout) getLayoutInflater().inflate(R.layout.main_screen, null);
        llBody.addView(llMain, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        initializeControls();
        preferenceUtils = new PreferenceUtils(MainActivity.this);
        userId =  preferenceUtils.getStringFromPreference(PreferenceUtils.USER_ID,"");
        loadAllItems();
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

    private void loadAllItems() {

    }

    /**
     * Initialize with references and functionalities
     */
    @Override
    public void initializeControls() {


        tvScreenTitle.setText(getResources().getString(R.string.app_name));
        ivLunch         = (ImageView) findViewById(R.id.ivLunch);
        ivDinner        = (ImageView) findViewById(R.id.ivDinner);
        ivParty         = (ImageView) findViewById(R.id.ivParty);
        ivGrocerry      = (ImageView) findViewById(R.id.ivGrocery);

        ivLunchDelivery = (ImageView) findViewById(R.id.ivLunchDelivery);
        firebaseFirestore = FirebaseFirestore.getInstance();
    }



    /**
     * Method to override postive button functionality of an alert
     * @param from the screen it is called from
     */
    @Override
    public void onButtonYesClick(String from) {
        super.onButtonYesClick(from);

        if("SUBMIT_CART".equals(from)){

            Intent intent = new Intent(MainActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);

        }
        if("ERROR".equals(from)){

            finishAffinity();

        }
    }
    /**
     * This is an indicator that the activity became active and ready to receive input. It is on top of an activity stack and visible to user.
     */
    @Override
    protected void onResume() {
        super.onResume();

        setMenuItems();

    }
}

package com.mobileapp.foodzone.activities;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.foodzone.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.mobileapp.foodzone.adapter.BreakfastAdapter;
import com.mobileapp.foodzone.common.AppConstants;
import com.mobileapp.foodzone.listeners.UpdateCartListener;
import com.mobileapp.foodzone.model.BreakfastDo;
import com.mobileapp.foodzone.model.LunchDo;
import com.mobileapp.foodzone.utills.PreferenceUtils;

import java.util.ArrayList;

/**
 * This class manages breakfast related items
 */
public class BreakfastActivity extends BaseActivity implements UpdateCartListener {
    private RecyclerView recycleview;
    private BreakfastAdapter BreakfastAdapter;
    private TextView tvCartTotal, tvNumber;
    public RelativeLayout rlProceed, help;
    int categoryId = 0;
    String imageURL;
    FirebaseFirestore firebaseFirestore;

    private RelativeLayout llCategory;

    /**
     * Initialize with default values
     */
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
        AppConstants.listBreakfast = new ArrayList<>();


        llshoppingCartLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AppConstants.listCartBreakfast = new ArrayList<>();
                int cartCount = preferenceUtils.getIntFromPreference(PreferenceUtils.CART_COUNT, 0);
                if (cartCount > 0) {
                    for (BreakfastDo BreakfastDo : AppConstants.listBreakfast) {
                        if (BreakfastDo.itemCount != 0) {
                            AppConstants.listCartBreakfast.add(BreakfastDo);
                        }
                    }

                    Intent intent = new Intent(BreakfastActivity.this, BreakfastCartActivity.class);
                    intent.putExtra("CategoryId", categoryId);
                    intent.putExtra("imageURL", imageURL);
                    startActivity(intent);

                } else {
                    showAppCompatAlert("Alert!", "Your cart is Empty.Please Add Products", "Ok", "", "", true);
                }

            }


        });

        tvScreenTitle.setText("Breakfast Delivery");
        rlProceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                llshoppingCartLayout.performClick();
            }
        });

        AppConstants.listBreakfast = getBreakfastDOS();
//                showLoader();
//                new CommonBL(BreakfastActivity.this, BreakfastActivity.this).Breakfast(1);
    }


    /**
     * Initialize with references and functionalities
     */
    @Override
    public void initializeControls() {
        recycleview = (RecyclerView) findViewById(R.id.recycleview);
        tvNumber = (TextView) findViewById(R.id.tvNumber);
        rlProceed = (RelativeLayout) findViewById(R.id.rlProceed);
        help = (RelativeLayout) findViewById(R.id.rlHelp);
        firebaseFirestore = FirebaseFirestore.getInstance();
    }


    /**
     * Set the adapter for recyclerview
     * @param context Application context
     * @param listBreakfastDos List of breakfast products
     * @param imageURL Image URL
     */
    private void BreakfastAdapter(Context context, ArrayList<BreakfastDo> listBreakfastDos, String imageURL) {
        BreakfastAdapter = new BreakfastAdapter(context, BreakfastActivity.this, listBreakfastDos, imageURL);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recycleview.setLayoutManager(linearLayoutManager);

        recycleview.setAdapter(BreakfastAdapter);
    }


    /**
     * This is an indicator that the activity became active and ready to receive input. It is on top of an activity stack and visible to user.
     */
    @Override
    protected void onResume() {
        super.onResume();
        if (categoryId == 3) {
            BreakfastAdapter(BreakfastActivity.this, AppConstants.listBreakfast, imageURL);
        }

        updateCartCount();

    }

    /**
     * Fetch breakfast related details from database
     * @return list of breakfast items
     */
    private ArrayList<BreakfastDo> getBreakfastDOS() {
        final ArrayList<BreakfastDo> BreakfastDos = new ArrayList<>();

        firebaseFirestore.collection("Breakfast").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful())
                    for (DocumentSnapshot documentSnapshot : task.getResult()) {
                        BreakfastDo breakfastDo = documentSnapshot.toObject(BreakfastDo.class);
                        BreakfastDos.add(breakfastDo);
                    }
                BreakfastAdapter(BreakfastActivity.this, BreakfastDos, imageURL);

            }
        });

    /*    BreakfastDo p = new BreakfastDo();
        p.id = "1";
        p.description = "Chapathi with curry";
        p.productName = "Chapathi";
        p.itemCount = 0;
        p.price = 20.0;
        p.uploadImage = R.drawable.chapathi;

        BreakfastDos.add(p);

        p = new BreakfastDo();

        p.id = "2";
        p.description = "Dosa with chutney";
        p.productName = "Dosa";
        p.itemCount = 0;
        p.price = 25.0;
        p.uploadImage = R.drawable.dosa;
        BreakfastDos.add(p);

        p = new BreakfastDo();

        p.id = "3";
        p.description = "Idly with chutney";
        p.productName = "Idly";
        p.itemCount = 0;
        p.price = 32.0;
        p.uploadImage = R.drawable.idly;
        BreakfastDos.add(p);
        p = new BreakfastDo();

        p.id = "4";
        p.description = "Poha with raitha";
        p.productName = "Poha";
        p.itemCount = 0;
        p.price = 80.0;
        p.uploadImage = R.drawable.poha;
        BreakfastDos.add(p);

        p = new BreakfastDo();

        p.id = "5";
        p.description = "Poori with curry";
        p.productName = "Poori";
        p.itemCount = 0;
        p.price = 90.0;
        p.uploadImage = R.drawable.poori;
        BreakfastDos.add(p);

        p = new BreakfastDo();

        p.id = "6";
        p.description = "Upma with chutney";
        p.productName = "Upma";
        p.itemCount = 0;
        p.price = 10.0;
        p.uploadImage = R.drawable.upma;
        BreakfastDos.add(p);

        p = new BreakfastDo();

        p.id = "7";
        p.description = "Bonda with chutney";
        p.productName = "Bonda";
        p.itemCount = 0;
        p.price = 20.0;
        p.uploadImage = R.drawable.bonda;
        BreakfastDos.add(p);

        p = new BreakfastDo();

        p.id = "8";
        p.description = "Ponganlu with chutney";
        p.productName = "Ponganalu";
        p.itemCount = 0;
        p.price = 20.0;
        p.uploadImage = R.drawable.ponganalu;
        BreakfastDos.add(p);

        p = new BreakfastDo();

        p.id = "9";
        p.description = "Rava Dosa with chutney";
        p.productName = "Rava Dosa";
        p.itemCount = 0;
        p.price = 20.0;
        p.uploadImage = R.drawable.ravadosa;
        BreakfastDos.add(p);

        p = new BreakfastDo();

        p.id = "10";
        p.description = "Pesarattu with chutney";
        p.productName = "Pesarattu";
        p.itemCount = 0;
        p.price = 20.0;
        p.uploadImage = R.drawable.pesarattu;
        BreakfastDos.add(p);

*/

        return BreakfastDos;
    }

    /**
     * This method is called to perform any final cleanup before an activity is destroyed
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

//
//    @Override
//    public void dataRetreived(Response data) {
//        hideLoader();
//
//        if (data.method != null && data.method == ServiceMethods.WS_Breakfast) {
//
//            if (data.data != null && data.data instanceof BreakfastMainDO) {
//                BreakfastMainDO BreakfastMainDO = (BreakfastMainDO) data.data;
//                int status = BreakfastMainDO.status;
//                imageURL = BreakfastMainDO.imageURL;
//                ArrayList<BreakfastDo> listBreakfastDOs = BreakfastMainDO.listBreakfastDOs;
//                if (status == 1) {
//                    AppConstants.listBreakfast = listBreakfastDOs;
//                    BreakfastAdapter(BreakfastActivity.this, listBreakfastDOs, imageURL);
//
//                    // Toast.makeText(BreakfastActivity.this,"success",Toast.LENGTH_SHORT).show();
//
//                } else {
//                    // TODO: 3/3/2018 show error message.
//                }
//            }
//
//        }
//    }

    /**
     * Method to define what happens when native back is pressed
     */
    @Override
    public void onBackPressed() {
        // close search view on back button pressed
        if (!svSearch.isIconified()) {
            svSearch.setIconified(true);
            return;
        }
        super.onBackPressed();
    }

    /**
     * Updates cart size with no of products in cart
     */
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

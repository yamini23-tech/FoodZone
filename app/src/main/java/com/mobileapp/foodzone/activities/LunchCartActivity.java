package com.mobileapp.foodzone.activities;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.foodzone.R;
import com.mobileapp.foodzone.adapter.LunchCartAdapter;
import com.mobileapp.foodzone.common.AppConstants;
import com.mobileapp.foodzone.listeners.UpdateTotalPriceListener;
import com.mobileapp.foodzone.model.LunchDo;
import com.mobileapp.foodzone.utills.PreferenceUtils;

import java.util.ArrayList;

public class LunchCartActivity extends BaseActivity implements UpdateTotalPriceListener {
    private boolean isFocused;

    private LinearLayout llOrders;
    private TextView tvCartTotal;
    private double total;
    private double tax;
    private TextView tvDiscount;
    private TextView tvTotalAmount;
    private TextView tvSubTotal;
    private TextView tvTax;
    private RecyclerView cartRecyclerView;
    private String comment;
    private EditText etTip, etSuggestion;
    private TextView tvLogin;
    private TextView tvRegister;
    private TextView tvQueryEmail;
    private TextView tvQueryCall;
    private TextView tvContinue;

    private double productPrice = 0.00;
    int categoryId = 0;
    String imageURL = "";
    private LunchCartAdapter lunchCartAdapter;


    @Override
    public void initialize() {
        llOrders = (LinearLayout) getLayoutInflater().inflate(R.layout.lunch_cart_screen, null);
        llBody.addView(llOrders, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        initializeControls();

        if (getIntent().hasExtra("CategoryId")) {
            categoryId = getIntent().getExtras().getInt("CategoryId");
        }
        if (getIntent().hasExtra("imageURL")) {
            imageURL = getIntent().getExtras().getString("imageURL");
        }


        cartRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        if(categoryId==1){


            setCartAdapter(LunchCartActivity.this, AppConstants.listCartLunch, imageURL);
        }


        //calculate total price.
        if(categoryId==1){

            for (LunchDo lunchDo : AppConstants.listCartLunch) {
                productPrice = Double.parseDouble(String.format("%.2f", (productPrice + (lunchDo.price * lunchDo.itemCount))));

            }
        }
        updateTotalPrice();
        llshoppingCartLayout.setVisibility(View.GONE);
        preferenceUtils        = new PreferenceUtils(LunchCartActivity.this);
         isLoggedIn = preferenceUtils.getbooleanFromPreference(PreferenceUtils.IS_LOGIN, false);

        if (isLoggedIn) {

            //Lunch flow.

            tvLogin.setVisibility(View.VISIBLE);
            ivAdd.setVisibility(View.GONE);
            tvRegister.setVisibility(View.VISIBLE);
            tvLogin.setText("Pay After Delivery");
            tvRegister.setText("Pay Now");
            tvQueryEmail.setVisibility(View.VISIBLE);
            tvQueryCall.setVisibility(View.VISIBLE);


            //Pay After Delivery case.
            tvLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // new CommonBL(LunchCartActivity.this,LunchCartActivity.this).submitCart(userId, categoryId, subTotal, totalPrice, paymentType, deliveryCharges, discountCharges, serviceTax, tip,comment, device, address, transactionId, name, email, mobileNumber, cardId, transactionTag, rememberToken, addressType, orderDetails);
//                    showLoader();
                    showAppCompatAlert("Thank You","Order has been placed Successfully..!!","OK","","SUBMIT_CART",false);

//                    new CommonBL(LunchCartActivity.this, LunchCartActivity.this).submitCart(userId, String.valueOf(categoryId),
//                            String.valueOf(productPrice), String.valueOf(total), "1", "0.0", String.valueOf(discount),
//                            String.valueOf(tax), "0.0", comment, AppConstants.IOS, locationName, "", userName, emailId, mobileNumber, cardId,
//                            "", rememberToken, "2", "", AppConstants.listCartLunch, 101);

                }
            });
            //Pay Now case.
            tvRegister.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    showAlert("coming soon");

                    Intent intent = new Intent(LunchCartActivity.this, AddingCardActivity.class);
                    intent.putExtra("CategoryId", categoryId);
                    startActivity(intent);
                }
            });


        }
        else {

            tvLogin.setText("Login");
            tvRegister.setText("Register");
            tvLogin.setVisibility(View.VISIBLE);
            tvRegister.setVisibility(View.VISIBLE);
            tvContinue.setVisibility(View.VISIBLE);
            tvLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(LunchCartActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
            });
            tvRegister.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(LunchCartActivity.this, RegisterActivity.class);
                    startActivity(intent);
                }
            });
        }

    }




    @Override
    public void initializeControls() {
        tvScreenTitle.setText("Cart");
        tvQueryCall       = (TextView) findViewById(R.id.tvQueryCall);

        tvQueryEmail      = (TextView) findViewById(R.id.tvQueryEmail);
        tvSubTotal        = (TextView) findViewById(R.id.tvSubTotal);
        etSuggestion      = (EditText) findViewById(R.id.etSuggestion);
        cartRecyclerView  = (RecyclerView) findViewById(R.id.recycleview);
        tvLogin           = (TextView) findViewById(R.id.tvLogin);
        tvContinue        = (TextView) findViewById(R.id.tvContinue);
       tvRegister        = (TextView) findViewById(R.id.tvRegister);

        tvSubTotal        = (TextView) findViewById(R.id.tvSubTotal);
        tvTax             = (TextView) findViewById(R.id.tvTax);
        tvTotalAmount     = (TextView) findViewById(R.id.tvTotalAmount);

    }


//
//    @Override
//    public void dataRetreived(Response data) {
//        hideLoader();
//        if (data.method != null && data.method == ServiceMethods.WS_SUBMIT_CART) {
//            if (data.data != null && data.data instanceof SubmitCartDO) {
//                SubmitCartDO submitCartDO = (SubmitCartDO) data.data;
//                int status = submitCartDO.status;
//                if (status == 1) {
//                    showAppCompatAlert("Thank You",submitCartDO.message,"OK","","SUBMIT_CART",false);
//
//
//                } else
//                    showAppCompatAlert("Alert!",submitCartDO.message,"OK","","ERROR",false);
//
//            }
//        }
//
//
//    }

    @Override
    protected void onResume() {

        super.onResume();
    }
    @Override
    public void onButtonYesClick(String from) {
        super.onButtonYesClick(from);

        if("SUBMIT_CART".equals(from)){



            Intent intent = new Intent(LunchCartActivity.this,MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);

        }

    }
    //===============
    private void setCartAdapter(Context context, ArrayList<LunchDo> listLunchDos, String imageURL) {
        lunchCartAdapter = new LunchCartAdapter(context, LunchCartActivity.this, listLunchDos, imageURL);
        cartRecyclerView.setAdapter(lunchCartAdapter);
    }

    @Override
    public void updateTotalPrice() {

        productPrice = 0.00;
        if(categoryId==1){

            for (LunchDo lunchDo : AppConstants.listCartLunch) {
                productPrice = Double.parseDouble(String.format("%.2f", (productPrice + (lunchDo.price * lunchDo.itemCount))));

            }
        }



            tvSubTotal.setText("$" + productPrice);

            tax = Double.parseDouble(String.format("%.2f", (productPrice / 100) * 7));

            tvTax.setText("$" + tax);

            total = Double.parseDouble(String.format("%.2f", productPrice + tax));
            tvTotalAmount.setText("$" + total);



    }

}

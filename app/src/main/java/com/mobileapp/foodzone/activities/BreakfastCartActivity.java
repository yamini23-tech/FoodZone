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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.mobileapp.foodzone.listeners.UpdateTotalPriceListener;
import com.mobileapp.foodzone.model.BreakfastDo;
import com.mobileapp.foodzone.utills.PreferenceUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * This class manages breakfast items added to cart
 */
public class BreakfastCartActivity extends BaseActivity implements UpdateTotalPriceListener {
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
    private BreakfastCartAdapter cartAdapter;
    FirebaseFirestore firebaseFirestore;

    /**
     * Initialize with default values
     */
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
        if(categoryId==3){


            setCartAdapter(BreakfastCartActivity.this, AppConstants.listCartBreakfast, imageURL);
        }


        //calculate total price.
        if(categoryId==3){

            for (com.mobileapp.foodzone.model.BreakfastDo BreakfastDo : AppConstants.listCartBreakfast) {
                productPrice = Double.parseDouble(String.format("%.2f", (productPrice + (BreakfastDo.price * BreakfastDo.itemCount))));

            }
        }
        updateTotalPrice();
        llshoppingCartLayout.setVisibility(View.GONE);
        preferenceUtils        = new PreferenceUtils(BreakfastCartActivity.this);
         isLoggedIn = preferenceUtils.getbooleanFromPreference(PreferenceUtils.IS_LOGIN, false);

        if (isLoggedIn) {

            //Breakfast flow.

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
                    // new CommonBL(BreakfastCartActivity.this,BreakfastCartActivity.this).submitCart(userId, categoryId, subTotal, totalPrice, paymentType, deliveryCharges, discountCharges, serviceTax, tip,comment, device, address, transactionId, name, email, mobileNumber, cardId, transactionTag, rememberToken, addressType, orderDetails);
//                    showLoader();
                    String date = new SimpleDateFormat("dd-MM-yy HH:mm").format(new Date());

                    for (com.mobileapp.foodzone.model.BreakfastDo BreakfastDo : AppConstants.listCartBreakfast) {
                        BreakfastDo.date = date;
                        firebaseFirestore.collection("Orders")
                                .add(BreakfastDo)
                                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                    @Override
                                    public void onSuccess(DocumentReference documentReference) {
                                        showAppCompatAlert("Thank You","Order has been placed Successfully..!!","OK","","SUBMIT_CART",false);
                                    }
                                });
                    }
                    //showAppCompatAlert("Thank You","Order has been placed Successfully..!!","OK","","SUBMIT_CART",false);

//                    new CommonBL(BreakfastCartActivity.this, BreakfastCartActivity.this).submitCart(userId, String.valueOf(categoryId),
//                            String.valueOf(productPrice), String.valueOf(total), "1", "0.0", String.valueOf(discount),
//                            String.valueOf(tax), "0.0", comment, AppConstants.IOS, locationName, "", userName, emailId, mobileNumber, cardId,
//                            "", rememberToken, "2", "", AppConstants.listCartBreakfast, 101);

                }
            });
            //Pay Now case.
            tvRegister.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    showAlert("coming soon");

                    Intent intent = new Intent(BreakfastCartActivity.this, AddingCardActivity.class);
                    intent.putExtra("CategoryId", categoryId);
                    intent.putExtra("type", "breakfast");
//                    ArrayList cartData = AppConstants.listCartBreakfast;
//                    intent.putExtra("cartData", cartData);
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
                    Intent intent = new Intent(BreakfastCartActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
            });
            tvRegister.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(BreakfastCartActivity.this, RegisterActivity.class);
                    startActivity(intent);
                }
            });
        }

    }




    /**
     * Initialize with references and functionalities
     */
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
        firebaseFirestore = FirebaseFirestore.getInstance();
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

    /**
     * This is an indicator that the activity became active and ready to receive input. It is on top of an activity stack and visible to user.
     */
    @Override
    protected void onResume() {

        super.onResume();
    }

    /**
     * Method to override postive button functionality of an alert
     * @param from the screen it is called from
     */
    @Override
    public void onButtonYesClick(String from) {
        super.onButtonYesClick(from);

        if("SUBMIT_CART".equals(from)){



            Intent intent = new Intent(BreakfastCartActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);

        }

    }

    /**
     * set cart adapter to recycler view
     * @param context Application context
     * @param listBreakfastDos List of breakfast products
     * @param imageURL Image URL
     */
    private void setCartAdapter(Context context, ArrayList<BreakfastDo> listBreakfastDos, String imageURL) {
        cartAdapter = new BreakfastCartAdapter(context, BreakfastCartActivity.this, listBreakfastDos, imageURL);
        cartRecyclerView.setAdapter(cartAdapter);
    }

    /**
     * Updates total price of cart according to items added
     */
    @Override
    public void updateTotalPrice() {

        productPrice = 0.00;
        if(categoryId==3){

            for (com.mobileapp.foodzone.model.BreakfastDo BreakfastDo : AppConstants.listCartBreakfast) {
                productPrice = Double.parseDouble(String.format("%.2f", (productPrice + (BreakfastDo.price * BreakfastDo.itemCount))));

            }
        }



            tvSubTotal.setText("$" + productPrice);

            tax = Double.parseDouble(String.format("%.2f", (productPrice / 100) * 7));

            tvTax.setText("$" + tax);

            total = Double.parseDouble(String.format("%.2f", productPrice + tax));
            tvTotalAmount.setText("$" + total);



    }

}



package com.mobileapp.foodzone.activities;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.foodzone.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.mobileapp.foodzone.model.DinnerDo;
import com.mobileapp.foodzone.model.GroceryDo;
import com.mobileapp.foodzone.model.LunchDo;
import com.mobileapp.foodzone.utills.PreferenceUtils;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * This class manages details of card added by user.
 */
public class AddingCardActivity extends BaseActivity {
    private ScrollView llMain;
    private String userId, rememberToken;
    PreferenceUtils preferenceUtils;
    private EditText etName, etCardNumber, etExpiryDate, etCVV;
    private Button btnPay;
    private CheckBox cbSaveCard;
    private Calendar c;
    private int cday, cmonth, cyear;
    static final int DATE_DIALOG_ID = 1;
    private int mYear;
    private int mMonth;
    private int mDay;
    DatePickerDialog.OnDateSetListener date;
    private String emailId;
    private String mobileNumber;
    private String userName;
    private String cardId;
    private String subTotal;
    private String total;
    private String tax;
    private String discount;
    private String comment;
    private String tip;
    private String deliveryCharges;
    private String locationName;
    private int categoryId;
    FirebaseFirestore firebaseFirestore;

    private String cardHolderName, cardNumber, expiryDate, cvv, cardType;

    /**
     * Initialize with default values
     */
    @Override
    public void initialize() {
        llMain = (ScrollView) getLayoutInflater().inflate(R.layout.adding_card_screen, null);
        llBody.addView(llMain, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        initializeControls();

        preferenceUtils = new PreferenceUtils(AddingCardActivity.this);

        emailId = preferenceUtils.getStringFromPreference(PreferenceUtils.EMAILID, "");
        mobileNumber = preferenceUtils.getStringFromPreference(PreferenceUtils.MOBILENUMBER, "");
        userName = preferenceUtils.getStringFromPreference(PreferenceUtils.USERNAME, "");
        cardId = preferenceUtils.getStringFromPreference(PreferenceUtils.CARD_ID, "");
        subTotal = preferenceUtils.getStringFromPreference(PreferenceUtils.PRODUCT_PRICE, "");
        tax = preferenceUtils.getStringFromPreference(PreferenceUtils.TAX, "");
        discount = preferenceUtils.getStringFromPreference(PreferenceUtils.DISCOUNT, "");
        tip = preferenceUtils.getStringFromPreference(PreferenceUtils.TIP, "");
        comment = preferenceUtils.getStringFromPreference(PreferenceUtils.COMMENT, "");
        deliveryCharges = preferenceUtils.getStringFromPreference(PreferenceUtils.DELIVERY_CHARGES, "");
        locationName = preferenceUtils.getStringFromPreference(PreferenceUtils.LOCATION_NAME, "");


        llshoppingCartLayout.setVisibility(View.GONE);

        c = Calendar.getInstance();
        userId = preferenceUtils.getStringFromPreference(PreferenceUtils.USER_ID, "");
        rememberToken = preferenceUtils.getStringFromPreference(PreferenceUtils.REMEMBER_TOKEN, "");
/*
        etName.setText("DalayyaKalla");
        etCardNumber.setText("4012000033330026");
        etExpiryDate.setText("0318");
        etCVV.setText("123");*/

        btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cardHolderName = etName.getText().toString().trim();
                cardNumber = etCardNumber.getText().toString().trim();
                expiryDate = etExpiryDate.getText().toString().trim().replace("/", "");
                cvv = etCVV.getText().toString().trim();

                String errorMsg = validatePayeezyCard(cardHolderName, cardNumber, expiryDate, cvv);
                if (errorMsg.isEmpty()) {
                    cardType = getCardType(cardNumber);
                    preferenceUtils.saveString(PreferenceUtils.CARD_TYPE, cardType);
                    preferenceUtils.saveString(PreferenceUtils.CARD_HOLDER_NAME, cardHolderName);
                    preferenceUtils.saveString(PreferenceUtils.CARD_NUMBER, cardNumber);
                    preferenceUtils.saveString(PreferenceUtils.EXPIRY_DATE, expiryDate);
//                    firebaseFirestore.collection("Orders").add(AppConstants.listCartDinner).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
//                        @Override
//                        public void onSuccess(DocumentReference documentReference) {
                   // ArrayList cartData = getIntent().getStringArrayListExtra("cartData");
                    String type = getIntent().getStringExtra("type");
                    if (type.equals("breakfast"))
                    {
                        orderBreakfast();
                    }
                    else if (type.equals("lunch"))
                    {
                        orderLunch();
                    }
                    else if (type.equals("dinner"))
                    {
                        orderDinner();
                    }
                    else
                        {
                            orderGroceries();
                        }

                           // showAppCompatAlert("Success","Order has been placed Successfully..!!", "OK", "", "SUBMIT_CART", false);
//                        }
//                    });

                } else {
                    showSnackbar(llMain, errorMsg);
                }

            }
        });


    }

    /**
     * This method is used to order the breakfast
     */
    private void orderBreakfast() {
        String date = new SimpleDateFormat("dd-MM-yy HH:mm").format(new Date());

        for (BreakfastDo breakfastDo : AppConstants.listCartBreakfast) {
            breakfastDo.date = date;
            firebaseFirestore.collection("Orders")
                    .add(breakfastDo)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            showAppCompatAlert("Success","Order has been placed Successfully..!!", "OK", "", "SUBMIT_CART", false);
                        }
                    });
        }
    }
    /**
     * This method is used to order the lunch
     */
    private void orderLunch() {
        String date = new SimpleDateFormat("dd-MM-yy HH:mm").format(new Date());

        for (LunchDo lunchDo : AppConstants.listCartLunch) {
            lunchDo.date = date;
            firebaseFirestore.collection("Orders")
                    .add(lunchDo)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            showAppCompatAlert("Success","Order has been placed Successfully..!!", "OK", "", "SUBMIT_CART", false);
                        }
                    });
        }
    }
    /**
     * This method is used to order the dinner
     */
    private void orderDinner() {
        String date = new SimpleDateFormat("dd-MM-yy HH:mm").format(new Date());

        for (DinnerDo dinnerDo : AppConstants.listCartDinner) {
            dinnerDo.date = date;
            firebaseFirestore.collection("Orders")
                    .add(dinnerDo)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            showAppCompatAlert("Success","Order has been placed Successfully..!!", "OK", "", "SUBMIT_CART", false);
                        }
                    });
        }
    }
    /**
     * This method is used to order the grocceries
     */
    private void orderGroceries() {
        String date = new SimpleDateFormat("dd-MM-yy HH:mm").format(new Date());

        for (GroceryDo groceryDo : AppConstants.listCartGrocery) {
            groceryDo.date = date;
            firebaseFirestore.collection("Orders")
                    .add(groceryDo)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            showAppCompatAlert("Success","Order has been placed Successfully..!!", "OK", "", "SUBMIT_CART", false);
                        }
                    });
        }
    }

    /**
     *This method is used to get the type of card
     * @param cardNumber  Unique card number
     * @return Returns the type of card as a String value
     */
    private String getCardType(String cardNumber) {
        String cardType = "";

        String visaPattern = "^4[0-9]{12}(?:[0-9]{3})?$";
        String masterPattern = "^5[1-5][0-9]{14}$";
        String expressPattern = "^3[47][0-9]{13}$";
        String dinarsPattern = "^3(?:0[0-5]|[68][0-9])[0-9]{11}$";
        String discoversPattern = "^6(?:011|5[0-9]{2})[0-9]{12}$";
        String jcbPattern = "^(?:2131|1800|35\\d{3})\\d{11}$";

        if (cardNumber.matches(visaPattern))
            return "visa";
        else if (cardNumber.matches(masterPattern))
            return "master";
        else if (cardNumber.matches(expressPattern))
            return "aexpress";
        else if (cardNumber.matches(dinarsPattern))
            return "diners";
        else if (cardNumber.matches(discoversPattern))
            return "discovers";
        else if (cardNumber.matches(jcbPattern))
            return "jcb";
        else
            return "invalid";

    }

    /**
     * Validations for card details
     * @param name Card holder name
     * @param cardNumber Card number
     * @param expiryDate Expiry date of card
     * @param cvv Unique 3/4 digit code of card
     * @return Returns error message if any else returns empty String.
     */
    private String validatePayeezyCard(String name, String cardNumber, String expiryDate, String cvv) {
        String errorMsg = "";
        if (name.isEmpty()) {
            errorMsg = "Please Enter Name";
        } else if (cardNumber.isEmpty()) {
            errorMsg = "Please Enter CardNumber";
        } else if (expiryDate.isEmpty()) {
            errorMsg = "Please Enter Date";
        } else if (cvv.isEmpty()) {
            errorMsg = "Please Enter CVV";

        }
        return errorMsg;
    }

    /**
     * Initializing with backend and assigning all related functionalities
     */
    @Override
    public void initializeControls() {
        tvScreenTitle.setText("Enter Card Details");
        etName = (EditText) findViewById(R.id.etName);
        etCardNumber = (EditText) findViewById(R.id.etCardNumber);
        etExpiryDate = (EditText) findViewById(R.id.etExpiryDate);
        etCVV = (EditText) findViewById(R.id.etCVV);
        cbSaveCard = (CheckBox) findViewById(R.id.cbSaveCard);
        btnPay = (Button) findViewById(R.id.btnPay);

        firebaseFirestore = FirebaseFirestore.getInstance();

        etExpiryDate.setCursorVisible(false);
        etExpiryDate.setFocusableInTouchMode(true);
        etExpiryDate.setFocusable(false);
        etExpiryDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*new DatePickerDialog(AddingCardActivity.this,date,
                        c.get(Calendar.YEAR), c.get(Calendar.MONTH), c
                        .get(Calendar.DAY_OF_MONTH)).show();*/

                showDialog(DATE_DIALOG_ID);

            }
        });
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
    }

    DatePickerDialog.OnDateSetListener mDateSetListner = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {

            mYear = year;
            mMonth = monthOfYear;
            mDay = dayOfMonth;
            updateDate();
        }
    };

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_DIALOG_ID:
                /*
                 * return new DatePickerDialog(this, mDateSetListner, mYear, mMonth,
                 * mDay);
                 */
                DatePickerDialog datePickerDialog = this.customDatePicker();
                return datePickerDialog;
        }
        return null;
    }

    /**
     * This method is used to change the format how date look
     */
    protected void updateDate() {
        int localMonth = (mMonth + 1);
        String monthString = localMonth < 10 ? "0" + localMonth : Integer
                .toString(localMonth);
        String localYear = Integer.toString(mYear).substring(2);
        etExpiryDate.setText(new StringBuilder()
                // Month is 0 based so add 1
                .append(monthString).append("/").append(localYear).append(" "));
        showDialog(DATE_DIALOG_ID);
    }

    /**
     * This method shows a custom date picker dialog
     * @return instance of {@link DatePickerDialog}
     */
    private DatePickerDialog customDatePicker() {
        DatePickerDialog dpd = new DatePickerDialog(this, mDateSetListner,
                mYear, mMonth, mDay);
        try {

            Field[] datePickerDialogFields = dpd.getClass().getDeclaredFields();
            for (Field datePickerDialogField : datePickerDialogFields) {
                if (datePickerDialogField.getName().equals("mDatePicker")) {
                    datePickerDialogField.setAccessible(true);
                    DatePicker datePicker = (DatePicker) datePickerDialogField
                            .get(dpd);
                    Field datePickerFields[] = datePickerDialogField.getType()
                            .getDeclaredFields();
                    for (Field datePickerField : datePickerFields) {
                        if ("mDayPicker".equals(datePickerField.getName())
                                || "mDaySpinner".equals(datePickerField
                                .getName())) {
                            datePickerField.setAccessible(true);
                            Object dayPicker = new Object();
                            dayPicker = datePickerField.get(datePicker);
                            ((View) dayPicker).setVisibility(View.GONE);
                        }
                    }
                }

            }
        } catch (Exception ex) {
        }
        return dpd;
    }

    /**
     * This method succesfully adds the card and proceeds to next screen
     * @param from String value to know if card is submitted
     */
    @Override
    public void onButtonYesClick(String from) {
        super.onButtonYesClick(from);

        if ("SUBMIT_CART".equals(from)) {

            Intent intent = new Intent(AddingCardActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);

        }
    }
}

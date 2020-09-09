package com.mobileapp.foodzone.activities;


import android.content.Intent;
import android.util.Patterns;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.drawerlayout.widget.DrawerLayout;

import com.foodzone.R;
import com.mobileapp.foodzone.database.StorageManager;
import com.mobileapp.foodzone.model.RegisterDO;
import com.mobileapp.foodzone.utills.PreferenceUtils;

/**
 * This class deals with user registration
 */
public class RegisterActivity extends BaseActivity {


    private Button btnRegister;
    private EditText etName, etEmail, etPassword, etConfirmPasword, etPhoneNumber, etLocation;
    private TextView tvLogin;
    private String MobilePattern = "[0-9]{10}";
    ScrollView llRegister;
    boolean isTermsChecked = false;
    boolean isTermsAccepted = false;
    private boolean terms = false;

    int count;
    private TextView tvTerms;
    private CheckBox cbTermsConditions;


    /**
     * Initialize with default values
     */
    @Override
    public void initialize() {
        llRegister = (ScrollView) getLayoutInflater().inflate(R.layout.register_screen, null);
        llBody.addView(llRegister, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        toolbar.setVisibility(View.GONE);
        flToolbar.setVisibility(View.GONE);
        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED, Gravity.LEFT);

        initializeControls();
        PreferenceUtils preferenceUtils = new PreferenceUtils(RegisterActivity.this);
        terms = preferenceUtils.getbooleanFromPreference(PreferenceUtils.IS_TERMS_ACCEPT, false);

        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });


        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = etName.getText().toString().trim();
                String email = etEmail.getText().toString().trim();
                String mobileNumber = etPhoneNumber.getText().toString().trim();
                String password = etPassword.getText().toString().trim();
                String confirmPassword = etConfirmPasword.getText().toString().trim();
                //  int locationid         = StringUtils.getInt(etLocation.getText().toString().trim());
                String locationid = etLocation.getText().toString().trim();


                String errorMsg = validateRegisteration(name, email, password, confirmPassword, mobileNumber, isTermsChecked);

                if (errorMsg.isEmpty()) {
                    RegisterDO registerDO = new RegisterDO();
                    registerDO.email = email;
                    registerDO.name = name;
                    registerDO.mobileNumber = mobileNumber;
                    registerDO.password = confirmPassword;


                    StorageManager.getInstance(RegisterActivity.this).saveRegisterDO(RegisterActivity.this, registerDO);
                    showToast("Registered Successfully...");
                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                } else {
                    showSnackbar(llRegister, errorMsg);
                }


            }
        });

    }

    /**
     * Initialize with references and functionalities
     */
    @Override
    public void initializeControls() {
        tvScreenTitle.setText("Register");
        etName = (EditText) findViewById(R.id.etName);
        tvTerms = (TextView) findViewById(R.id.tvTerms);
        etEmail = (EditText) findViewById(R.id.etEmail);
        etPhoneNumber = (EditText) findViewById(R.id.etPhoneNumber);
        etPassword = (EditText) findViewById(R.id.etPassword);
        etConfirmPasword = (EditText) findViewById(R.id.etConfirmPasword);
        etLocation = (EditText) findViewById(R.id.etLocation);
        btnRegister = (Button) findViewById(R.id.btnRegister);
        tvLogin = (TextView) findViewById(R.id.tvLogin);
        cbTermsConditions = (CheckBox) findViewById(R.id.cbTermsConditions);
//        tvTerms.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(RegisterActivity.this, TermsAndConditionsActivity.class);
//                startActivity(intent);
//            }
//        });

        cbTermsConditions.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (cbTermsConditions.isChecked()) {
                    isTermsChecked = true;

                    preferenceUtils.saveBoolean(PreferenceUtils.IS_TERMS_CHECKED, isTermsChecked);
                } else {
                    isTermsChecked = false;
                    preferenceUtils.saveBoolean(PreferenceUtils.IS_TERMS_CHECKED, isTermsChecked);


                }
            }
        });


    }

    /**
     * Validate all registration fields
     * @param userName
     * @param emailId
     * @param password
     * @param confirmPassword
     * @param phoneNumber
     * @param isTermsChecked
     * @return error message if any else returns empty string
     */
    private String validateRegisteration(String userName, String emailId, String password, String confirmPassword, String phoneNumber, boolean isTermsChecked) {
        String errorMsg = "";
        if (userName.isEmpty()) {
            errorMsg = "Please Enter UserName";
        } else if (emailId.isEmpty()) {
            errorMsg = "Please Enter EmailId";
        } else if (!isEmailIdValid(emailId)) {
            errorMsg = "Please Enter Valid EmailId";
        } else if (password.isEmpty()) {
            errorMsg = "Please Enter Password";

        } else if (confirmPassword.isEmpty()) {
            errorMsg = "Please ReEnter Password";

        } else if (!password.equals(confirmPassword)) {
            errorMsg = "Please Enter Correct Password";

        } else if (phoneNumber.isEmpty()) {
            errorMsg = "Please Enter PhoneNumber";

        } else if (phoneNumber.length() != 10) {
            errorMsg = "Please Enter Correct PhoneNumber";

        } else if (isTermsChecked == false) {
            errorMsg = "Please Accept Terms & Conditons";
        }

        return errorMsg;
    }

    private boolean isEmailIdValid(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
//
//    @Override
//    public void dataRetreived(Response data) {
//        hideLoader();
//        if (data.method != null && data.method == ServiceMethods.WS_REGISTER) {
//            if (data.data != null && data.data instanceof RegisterDO) {
//                RegisterDO registerDO = (RegisterDO) data.data;
//                int status = registerDO.status;
//                String message = registerDO.statusMessage;
//                if (status == 1) {
//                    hideLoader();
//                    preferenceUtils = new PreferenceUtils(RegisterActivity.this);
//
//                    showAlert(message);
//                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
//                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                    startActivity(intent);
//                } else {
//                    showAlert(message);
//                }
//            }
//        } else if (data.method != null && data.method == ServiceMethods.WS_LOCATION) {
//            if (data.data != null && data.data instanceof ArrayList) {
//                ArrayList<LocationDo> locationDos = (ArrayList<LocationDo>) data.data;
//          // show list in popup
//                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                startActivity(intent);
//            } else if (data.data != null && data.data instanceof CommonDO) {
//                CommonDO commonDO = (CommonDO) data.data;
//             }
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
        isTermsChecked = preferenceUtils.getbooleanFromPreference(PreferenceUtils.IS_TERMS_ACCEPT, isTermsAccepted);

        if (isTermsChecked == true) {
            cbTermsConditions.setChecked(true);
            isTermsChecked = true;

        } else {
            cbTermsConditions.setChecked(false);
            isTermsChecked = false;
        }
    }
}

























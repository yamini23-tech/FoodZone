package com.mobileapp.foodzone.activities;

import android.content.Intent;
import android.util.Patterns;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.foodzone.R;
import com.foodzone.utills.PreferenceUtils;


public class UpdateAccountActivity extends BaseActivity {
    private EditText etName;
    private EditText etEmail;
    private EditText etMobileNumber;
    private Button btnSubmit;
    private Button btnChangePassword;
    private Button userCheck;
    private Button restaurent;
    private String userId;
    private String rememberToken;
    PreferenceUtils preferenceUtils;
    private Button orderHistory;
    private LinearLayout llUpdateAccount;

    @Override
    public void initialize() {
        llUpdateAccount = (LinearLayout) getLayoutInflater().inflate(R.layout.update_account_screen, null);
        llBody.addView(llUpdateAccount, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        initializeControls();
        llshoppingCartLayout.setVisibility(View.GONE);

        preferenceUtils = new PreferenceUtils(UpdateAccountActivity.this);
        userId = preferenceUtils.getStringFromPreference(PreferenceUtils.USER_ID, "");
        rememberToken = preferenceUtils.getStringFromPreference(PreferenceUtils.REMEMBER_TOKEN, "");

        String email = preferenceUtils.getStringFromPreference(PreferenceUtils.EMAILID, "");
        String mblNumber = preferenceUtils.getStringFromPreference(PreferenceUtils.MOBILENUMBER, "");
//        etName.setHint(preferenceUtils.getStringFromPreference(PreferenceUtils.USERNAME, ""));
        etEmail.setText(email);
        etMobileNumber.setText(mblNumber);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = etName.getText().toString();

                preferenceUtils = new PreferenceUtils(UpdateAccountActivity.this);
                String errorMsg = validateUpdateAccount(name);

                if (errorMsg.isEmpty()) {
                    preferenceUtils.saveString(PreferenceUtils.USERNAME, name);
//                    preferenceUtils.saveString(PreferenceUtils.EMAILID, email);
//                    preferenceUtils.saveString(PreferenceUtils.MOBILENUMBER, mobileNumber);
                    Toast.makeText(UpdateAccountActivity.this, "Account Updated Successfully", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(UpdateAccountActivity.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    showToast(errorMsg);
                }


            }
        });
//        btnChangePassword.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(UpdateAccountActivity.this, ChangePasswordActivity.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                startActivity(intent);
//            }
//        });
    }

    private boolean isEmailIdValid(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private String validateUpdateAccount(String name) {
        String errorMsg = "";
        if (name.isEmpty()) {
            errorMsg = "Please Enter Name";
        }
//        else if (email.isEmpty()) {
//            errorMsg = "Please Enter EmailId";
//
//        } else if (!isEmailIdValid(email)) {
//            errorMsg = "Please Enter Valid EmailId";
//        } else if (mobileNumber.isEmpty()) {
//            errorMsg = "Please Enter PhoneNumber";
//
//        } else if (mobileNumber.length() != 10) {
//            errorMsg = "Please Enter Correct PhoneNumber";
//
//        }
        return errorMsg;
    }

    @Override
    public void initializeControls() {
        tvScreenTitle.setText("Update Account");
        etName = (EditText) findViewById(R.id.etName);
        etEmail = (EditText) findViewById(R.id.etEmail);
        etMobileNumber = (EditText) findViewById(R.id.etMobileNumber);
        btnSubmit = (Button) findViewById(R.id.btnSubmit);
        btnChangePassword = (Button) findViewById(R.id.btnChangePassword);

        restaurent = (Button) findViewById(R.id.restaurent);

    }

}

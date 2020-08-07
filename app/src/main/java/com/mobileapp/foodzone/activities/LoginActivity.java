package com.mobileapp.foodzone.activities;

import android.content.Intent;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.drawerlayout.widget.DrawerLayout;

import com.foodzone.R;
import com.mobileapp.foodzone.database.StorageManager;
import com.mobileapp.foodzone.model.LoginDo;
import com.mobileapp.foodzone.model.RegisterDO;
import com.mobileapp.foodzone.utills.PreferenceUtils;

public class LoginActivity extends BaseActivity {
    private EditText etPassword, etEmail;
    private Button btnLogin;
    private TextView tvRegister, tvForgotPaswword;
    private PreferenceUtils preferenceUtils;
    private int id;
    ScrollView llLogin;
    private String imageURL;

    @Override
    public void initialize() {

        llLogin = (ScrollView) getLayoutInflater().inflate(R.layout.login_screen, null);
        llBody.addView(llLogin, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        toolbar.setVisibility(View.GONE);
        flToolbar.setVisibility(View.GONE);
        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED, Gravity.LEFT);
        initializeControls();

        if (getIntent().hasExtra("login")) {
            id = getIntent().getExtras().getInt("login");
        }
        if (getIntent().hasExtra("imageURL")) {
            imageURL = getIntent().getExtras().getString("imageURL");
        }
  /*      etEmail.setText("adityab@dsquarelabs.com");
        etPassword.setText("123456");*/

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = etEmail.getText().toString().trim();
                String password = etPassword.getText().toString().trim();

                String errorMsg = validateLogin(email, password);
                if (errorMsg.isEmpty()) {
                    RegisterDO registerDO = StorageManager.getInstance(LoginActivity.this).getRegisterDO(LoginActivity.this);
                    if (registerDO.email.equalsIgnoreCase(email) && registerDO.password.equalsIgnoreCase(password)) {
                        LoginDo loginDo = new LoginDo();
                        loginDo.email=registerDO.email;
                        loginDo.mobileNumber=registerDO.mobileNumber;
                        loginDo.name=registerDO.name;
                        preferenceUtils=new PreferenceUtils(LoginActivity.this);
                        preferenceUtils.saveString(PreferenceUtils.USER_ID, registerDO.name);
                        preferenceUtils.saveBoolean(PreferenceUtils.IS_LOGIN, true);
                        String userId = preferenceUtils.getStringFromPreference(PreferenceUtils.USER_ID, "");
//                    showAlert(message);
                        preferenceUtils.saveString(PreferenceUtils.USERNAME, registerDO.name);
                        preferenceUtils.saveString(PreferenceUtils.EMAILID, registerDO.email);
                        preferenceUtils.saveString(PreferenceUtils.MOBILENUMBER, registerDO.mobileNumber);
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                        showToast("Logged in Successfully...");
                    } else {
                        showToast("Please enter Valid Credentials");

                    }

//                    showLoader();
//                    new CommonBL(LoginActivity.this,LoginActivity.this).login(email,password);
                } else {
                    showSnackbar(llLogin, errorMsg);
                }
            }
        });
        tvForgotPaswword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ForgotPasswordActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

    }

    private String validateLogin(String username, String password) {

        String errorMsg = "";
        if (username.isEmpty()) {
            errorMsg = "Please eneter username";
        } else if (password.isEmpty()) {
            errorMsg = "Please enter password";
        }
        return errorMsg;
    }

    @Override
    public void initializeControls() {
        etEmail = (EditText) findViewById(R.id.etEmail);
        etPassword = (EditText) findViewById(R.id.etPassword);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        tvRegister = (TextView) findViewById(R.id.tvRegister);
        tvForgotPaswword = (TextView) findViewById(R.id.tvForgotPaswword);

    }

    //Step 4: Response using.
//    @Override
//    public void dataRetreived(Response data) {
//
//        hideLoader();
//        if (data.method != null && data.method == ServiceMethods.WS_LOGIN) {
//            if (data.data != null && data.data instanceof LoginDo) {
//                LoginDo loginDO = (LoginDo) data.data;
//                int status = loginDO.status;
//                String message = loginDO.statusMessage;
//                preferenceUtils  = new PreferenceUtils(LoginActivity.this);
//
//                if (status == 1) {
//                    preferenceUtils.saveString(PreferenceUtils.USER_ID,loginDO.id);
//                    preferenceUtils.saveString(PreferenceUtils.REMEMBER_TOKEN,loginDO.rememberToken);
//                    preferenceUtils.saveBoolean(PreferenceUtils.IS_LOGIN,true);
//                     String userId= preferenceUtils.getStringFromPreference(PreferenceUtils.USER_ID,"");
////                    showAlert(message);
//                    preferenceUtils.saveString(PreferenceUtils.USERNAME, loginDO.name);
//                    preferenceUtils.saveString(PreferenceUtils.EMAILID, loginDO.email);
//                    preferenceUtils.saveString(PreferenceUtils.MOBILENUMBER, loginDO.mobileNumber);
//
//                    if(id==1){
//                        Intent intent = new Intent(LoginActivity.this, LunchCartActivity.class);
//                        intent.putExtra("CategoryId", 1);
//                        intent.putExtra("imageURL",imageURL);
//                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                        startActivity(intent);
//                    }else  if(id==2){
//                        Intent intent = new Intent(LoginActivity.this, DinnerCartActivity.class);
//                        intent.putExtra("CategoryId", 2);
//                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                        startActivity(intent);
//                    }else  if(id==3){
//                        Intent intent = new Intent(LoginActivity.this, PartyCartActivity.class);
//                        intent.putExtra("CategoryId", 3);
//                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                        startActivity(intent);
//                    }else  if(id==4){
//                        Intent intent = new Intent(LoginActivity.this, GroceryCartActivity.class);
//                        intent.putExtra("CategoryId", 4);
//                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                        startActivity(intent);
//                    }else {
//                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                        startActivity(intent);
//                        finish();
//
//                    }
//
//                } else {
//                    showAlert(message);
//                }
//            }
//
//        }
//        else  if (data.method != null && data.method == ServiceMethods.WS_GROCERY_SESSION_RESPONSE) {
//            if (data.data != null && data.data instanceof GrocerySessionDataStoreMainDo) {
//                GrocerySessionDataStoreMainDo grocerySessionDataStoreMainDo = (GrocerySessionDataStoreMainDo) data.data;
//
//                if (grocerySessionDataStoreMainDo.status == 1) {
//                    AppConstants.listGrocerySessionResponse = grocerySessionDataStoreMainDo.listGrocerySessionResponse;
//                    //  Toast.makeText(GroceryCartActivity.this, grocerySessionDataStoreDo.status, Toast.LENGTH_SHORT).show();
//                }
//
//            } else if (data.data != null && data.data instanceof CommonDO) {
//                CommonDO commonDO = (CommonDO) data.data;
//            }
//
//        }
//
//    }
}












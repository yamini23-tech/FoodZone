package com.mobileapp.foodzone.activities;

import android.util.Patterns;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.foodzone.R;

public class ForgotPasswordActivity extends BaseActivity  {
private Button btnSend;
private EditText etEmail;
private LinearLayout llForgotPassword;



    @Override
    public void initialize() {
        llForgotPassword = (LinearLayout) getLayoutInflater().inflate(R.layout.forgot_password, null);
        llBody.addView(llForgotPassword, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        initializeControls();
        llshoppingCartLayout.setVisibility(View.GONE);

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mail = etEmail.getText().toString().trim();
                String errorMsg = validateForgotPassword(mail);

                if(errorMsg.isEmpty()){
                    showAppCompatAlert("Thank You","Email Sent Successfully..!!","OK","","SUCCESS",false);

//                    showLoader();
//                    new CommonBL(ForgotPasswordActivity.this,ForgotPasswordActivity.this).forgotPassword(mail);
                }else{
                    showSnackbar(llForgotPassword,errorMsg);
                }
            }
        });
    }

    @Override
    public void initializeControls() {
        etEmail = (EditText)findViewById(R.id.etEmail);
        btnSend = (Button)findViewById(R.id.btnSend);
    }
    private boolean isEmailIdValid(String email){
        return  Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
    private String validateForgotPassword(String emailId) {
        String errorMsg ="";
       if(emailId.isEmpty()){
            errorMsg = "Please Enter EmailId";
        }else if(!isEmailIdValid(emailId)){
            errorMsg ="Please Enter Valid EmailId";
        }

        return errorMsg;
    }
//    @Override
//    public void dataRetreived(Response data) {
//        if (data.method != null && data.method == ServiceMethods.WS_FORGOT_PASSWORD) {
//            if (data.data != null && data.data instanceof CommonDO) {
//                CommonDO commonDO = (CommonDO) data.data;
//                int status = commonDO.status;
//                String message = commonDO.statusMessage;
//                if (status == 1) {
//                    showAlert(message);
//                    Intent intent = new Intent(ForgotPasswordActivity.this, MainActivity.class);
//                    startActivity(intent);
//                } else {
//                    showAlert(message);
//                }
//            }
//        }
//    }
}

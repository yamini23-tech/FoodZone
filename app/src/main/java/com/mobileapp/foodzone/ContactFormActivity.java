package com.mobileapp.foodzone;

import android.content.Intent;
import android.util.Patterns;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

public class ContactFormActivity extends BaseActivity  {
    private Button btnSendMessage;
    private EditText etName, etEmail, etPhoneNumber, etSubject,etMessage;

    LinearLayout llContactFrom;

    @Override
    public void initialize() {

        llContactFrom = (LinearLayout) getLayoutInflater().inflate(R.layout.contactform_screen, null);
        llBody.addView(llContactFrom, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        initializeControls();
        llshoppingCartLayout.setVisibility(View.GONE);



        tvScreenTitle.setText("Contact Form");

        btnSendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name          = etName.getText().toString().trim();
                String email         = etEmail.getText().toString().trim();
                String mobileNumber  = etPhoneNumber.getText().toString().trim();
                String subject       = etSubject.getText().toString().trim();
                String message       = etMessage.getText().toString().trim();
                String errorMsg = validateContactForm(name,email,mobileNumber,subject,message);

                if(errorMsg.isEmpty()){
                    showToast("Message sent Successfully");
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
//                    showLoader();
//                    new CommonBL(ContactFormActivity.this, ContactFormActivity.this).needHelp(name, email,mobileNumber,subject,message);
                }else{
                    showSnackbar(llContactFrom,errorMsg);
                }



            }
        });

    }
    private boolean isEmailIdValid(String email){
        return  Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
    private String validateContactForm(String name, String email, String mobileNumber, String subject, String message) {
        String errorMsg ="";
        if(name.isEmpty()){
            errorMsg ="Please Enter Name";
        }else if(email.isEmpty()){
            errorMsg ="Please Enter Email";

        }else if(!isEmailIdValid(email)){
            errorMsg ="Please Enter Valid EmailId";
        }
        else if(mobileNumber.isEmpty()){
            errorMsg ="Please Enter MobileNumber";

        }else if(mobileNumber.length()!=10){
            errorMsg ="Please Enter Correct PhoneNumber";

        }else if(subject.isEmpty()){
            errorMsg ="Please Enter Subject";

        }else if(message.isEmpty()){
            errorMsg ="Please Enter Message";

        }
        return  errorMsg;
    }
    @Override
    public void initializeControls() {
        etName        = (EditText) findViewById(R.id.etName);
        etEmail       = (EditText) findViewById(R.id.etEmail);
        etPhoneNumber = (EditText) findViewById(R.id.etMobileNumber);
        etSubject     = (EditText) findViewById(R.id.etSubject);
        etMessage     = (EditText) findViewById(R.id.etMessage);

        btnSendMessage =(Button)findViewById(R.id.btnSend);
    }
    @Override
    public void onBackPressed() {
//        Intent intent = new Intent(AboutActivity.this,MainActivity.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        startActivity(intent);
        super.onBackPressed();
    }
//    @Override
//    public void dataRetreived(Response data) {
//        if (data.method != null && data.method == ServiceMethods.WS_NEED_HELP) {
//            if (data.data != null && data.data instanceof CommonDO) {
//                CommonDO commonDO = (CommonDO) data.data;
//                int status = commonDO.status;
//                String message = commonDO.statusMessage;
//                if (status == 1) {
//                    showAlert(message);
//                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//                    startActivity(intent);
//                } else {
//                    showAlert(message);
//                }
//            }
//        }
//    }
}

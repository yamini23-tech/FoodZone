package com.mobileapp.foodzone.activities;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.foodzone.R;
import com.google.android.material.snackbar.Snackbar;
import com.mobileapp.foodzone.adapter.MenuAdapter;
import com.mobileapp.foodzone.common.AppConstants;
import com.mobileapp.foodzone.model.MenuDO;
import com.mobileapp.foodzone.utills.ActionBarDrawerToggle;
import com.mobileapp.foodzone.utills.DrawerArrowDrawable;
import com.mobileapp.foodzone.utills.PreferenceUtils;

import java.util.ArrayList;

/**
 * This class acts like a base class for other activities
 */
public abstract class BaseActivity extends AppCompatActivity {

    public Toolbar toolbar;
    public LinearLayout llBody;
    public LinearLayout llFooter;
//    public LayoutInflater inflater;
    public TextView tvRestaurant, tvPartnerName, tvScreenTitle;
    protected Dialog dialogLoader;
    private boolean isCancelableLoader;
    //Navigation drawer.
    public DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private Context context;
    private String userId,orderCode;
    PreferenceUtils preferenceUtils;
    private String orderType;

    public FrameLayout flToolbar;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerArrowDrawable drawerArrow;
    private String number = "";
    public Button btnSettings;
    public TextView tvUsername;
    private long mLastClickTime = 0;
    public TextView tvUser;
    public ImageView ivDelete;
    public ImageView ivSearch;
    public SearchView svSearch;
    public ImageView ivAdd;
    public boolean  isLoggedIn;
    public LinearLayout llSearch;
    public RelativeLayout llshoppingCartLayout;
    private String userName;
    public ImageView ivEtSearch;
    public EditText etSearch;
    public TextView shoppingCartTotalNumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base_screen);

        context = BaseActivity.this;
//        inflater = this.getLayoutInflater();
        preferenceUtils = new PreferenceUtils(context);
        userId    = preferenceUtils.getStringFromPreference(PreferenceUtils.USER_ID, "");
        orderCode = preferenceUtils.getStringFromPreference(PreferenceUtils.ORDER_CODE, "");
        userName  = preferenceUtils.getStringFromPreference(PreferenceUtils.USERNAME,"");
        baseInitializeControls();
        setMenuItems();

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setHomeButtonEnabled(true);
        setSupportActionBar(toolbar);

        drawerArrow = new DrawerArrowDrawable(this) {

            @Override
            public boolean isLayoutRtl() {
                return false;
            }
        };
        toolbar.setNavigationIcon(drawerArrow);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close, drawerArrow) {

            @Override
            public void onDrawerOpened(View drawerView) {
                invalidateOptionsMenu();
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                invalidateOptionsMenu();
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
            }

            @Override
            public void onDrawerStateChanged(int newState) {
                super.onDrawerStateChanged(newState);
            }
        };
        mDrawerLayout.addDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menuOperates();
            }
        });

        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                switch (position) {
                    case AppConstants.NEED_HELP:
                        menuOperates();


                        if(!(context instanceof ContactFormActivity)){
                            Intent contactFormIntent = new Intent(context, ContactFormActivity.class);
                            contactFormIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(contactFormIntent);
                        }
                        break;


                    case AppConstants.ABOUT_US:
                        menuOperates();
                        if(!(context instanceof AboutActivity)){
                            Intent aboutIntent = new Intent(context, AboutActivity.class);
                            aboutIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(aboutIntent);
                        }
                        break;
                    case AppConstants.LOGIN_OR_LOGOUT:
                        menuOperates();
                        boolean isLoggedIn = preferenceUtils.getbooleanFromPreference(PreferenceUtils.IS_LOGIN,false);
                        if(isLoggedIn){
                            showAppCompatAlert("Success","You have successfully Logged out","OK","","LOGOUT",false);
                        }else{
                            Intent logoutIntent = new Intent(context, LoginActivity.class);
                            logoutIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK| Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(logoutIntent);
                        }

                        break;
                    default:
                        break;
                }
            }
        });

            initialize();
    }
        /*} catch (JSONException e) {
            e.printStackTrace();
        }*/


    /**
     * This is an indicator that the activity became active and ready to receive input. It is on top of an activity stack and visible to user.
     */
    @Override
    protected void onResume() {
        super.onResume();

        setMenuItems();
    }

    /**
     * This method operates navigation drawer
     */
    public void menuOperates() {
        hideKeyBoard(llBody);
        if (mDrawerLayout.isDrawerOpen(Gravity.LEFT)) {
            mDrawerLayout.closeDrawer(Gravity.LEFT);
        } else {
            mDrawerLayout.openDrawer(Gravity.LEFT);
        }
    }






    protected void disableMenuWithBackButton() {
        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED, Gravity.LEFT);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    public abstract void initialize();

    public abstract void initializeControls();

    /**
     * Initializing all the view references and title.
     */
    private void baseInitializeControls() {
        tvUser              = (TextView) findViewById(R.id.tvUser);
        flToolbar            = (FrameLayout) findViewById(R.id.flToolbar);
        llBody               = (LinearLayout) findViewById(R.id.llBody);
        toolbar              = (Toolbar) findViewById(R.id.toolbar);
        tvScreenTitle        = (TextView) findViewById(R.id.tvScreenTitle);
        ivDelete             = (ImageView) findViewById(R.id.ivDelete);
        svSearch             = (SearchView) findViewById(R.id.svSearch);
        ivSearch             = (ImageView) findViewById(R.id.ivSearch);
        ivAdd                = (ImageView) findViewById(R.id.ivPlus);
        tvPartnerName        = (TextView) findViewById(R.id.tvUser);
        mDrawerLayout        = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList          = (ListView) findViewById(R.id.left_drawer);
        llshoppingCartLayout = (RelativeLayout) findViewById(R.id.llshoppingCartLayout);
        shoppingCartTotalNumber = (TextView) findViewById(R.id.shoppingCartTotalNumber);
         llSearch           = (LinearLayout) findViewById(R.id.llSearch);
        ivEtSearch             = (ImageView) findViewById(R.id.ivSearch);
        etSearch             = (EditText) findViewById(R.id.etSearch);


        toolbar.setTitle("");
        tvUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isLoggedIn = preferenceUtils.getbooleanFromPreference(PreferenceUtils.IS_LOGIN, false);

                if(isLoggedIn==true){
                    Intent intent = new Intent(BaseActivity.this, UpdateAccountActivity.class);
                    startActivity(intent);
                }

            }
        });

    }

    /**
     * Method to Show loader without text
     */
    public void showLoader() {
        runOnUiThread(new RunShowLoader("Loading..."));
    }

    /**
     * Method to show loader with text
     * @param msg Text message to show with loader
     */
    public void showLoader(String msg) {
        runOnUiThread(new RunShowLoader(msg));
    }

    /**
     * This method is used to show the text message
     * @param strMsg Message to be shown as toast
     */
    public void showToast(String strMsg) {
        if (!strMsg.isEmpty()) {
            Toast.makeText(BaseActivity.this, strMsg, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * This method is used to show android native alert message
     * @param strMsg Message to be shown as alert message
     */
    public void showAlert(String strMsg) {
        if (!strMsg.isEmpty()) {
             showAppCompatAlert("Alert!",strMsg,"OK","","",true);
        }
    }

    /**
     * This method is used to show android native snack bar
     * @param view The view to find a parent from.
     * @param errorMsg Message to be shown
     */
    public void showSnackbar(View view, String errorMsg) {

        Snackbar snackbar = Snackbar.make(view, errorMsg, Snackbar.LENGTH_SHORT);
        snackbar.show();

    }



    public AlertDialog.Builder alertDialog;

    /**
     * Showing alert message using alert dialog builder
     * @param mTitle Title of alert message
     * @param mMessage Message to be shown on alert
     * @param posButton Positive button message
     * @param negButton Negative button message
     * @param from String value to know from where it is called
     * @param isCancelable boolean value to enable or disable cancelling of alert by clicking anywhere outside of alert
     */
    public void showAppCompatAlert(String mTitle, String mMessage, String posButton, String negButton, final String from, boolean isCancelable) {
        if (alertDialog == null)
            alertDialog = new AlertDialog.Builder(BaseActivity.this, R.style.AppCompatAlertDialogStyle);
        alertDialog.setTitle(mTitle);
        alertDialog.setMessage(mMessage);
        number = mMessage;
        alertDialog.setPositiveButton(posButton, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                onButtonYesClick(from);
            }
        });
        if (negButton != null && !negButton.equalsIgnoreCase(""))
            alertDialog.setNegativeButton(negButton, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    onButtonNoClick(from);
                }
            });
        alertDialog.setCancelable(false);
        alertDialog.show();
    }

    public void onButtonNoClick(String from) {

    }

    public void onButtonYesClick(String from) {

        if("LOGOUT".equalsIgnoreCase(from)){
            preferenceUtils.saveBoolean(PreferenceUtils.IS_LOGIN,false);
            tvUser.setText("Welcome Guest");
            setMenuItems();
            if(!(context instanceof MainActivity)){
                Intent logoutIntent = new Intent(context, MainActivity.class);
                logoutIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(logoutIntent);
            }
        }

    }

    /**
     * For hiding progress dialog (Loader ).
     **/
    public void hideLoader() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    if (dialogLoader != null && dialogLoader.isShowing())
                        dialogLoader.dismiss();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }


    /**
     * This method hides the keyboard from screen
     * @param view View on which keyboard should be hidden
     */
    public void hideKeyBoard(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    /**
     * This method shows the keyboard on screen
     * @param view View on which keyboard should be shown
     */
    public void showSoftKeyboard(View view) {
        if (view.requestFocus()) {
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
        }
    }

    /**
     * set a particular typeface font for a view group
     * @param group {@link ViewGroup} to which font should be set
     * @param tf font to be set
     */
    public void setFont(ViewGroup group, Typeface tf) {
        int count = group.getChildCount();
        View v;
        for (int i = 0; i < count; i++) {
            v = group.getChildAt(i);
            if (v instanceof TextView || v instanceof Button /* etc. */)
                ((TextView) v).setTypeface(tf);
            else if (v instanceof ViewGroup)
                setFont((ViewGroup) v, tf);
        }

    }

    private class RunShowLoader implements Runnable {
        private String strMsg;

        public RunShowLoader(String strMsg) {
            this.strMsg = strMsg;
        }

        @Override
        public void run() {
            try {
                dialogLoader = new Dialog(BaseActivity.this);
                dialogLoader.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialogLoader.setCancelable(false);
                dialogLoader.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                dialogLoader.setContentView(R.layout.loader_custom);
                if (!dialogLoader.isShowing())
                    dialogLoader.show();
            } catch (Exception e) {
                dialogLoader = null;
            }
        }
    }

    /**
     * This method updates the cart size with current total of products
     */
    public void updateCartSize() {
         int cartCount = preferenceUtils.getIntFromPreference(PreferenceUtils.CART_COUNT, 0);

        if (cartCount > 0) {
            shoppingCartTotalNumber.setVisibility(View.VISIBLE);
            shoppingCartTotalNumber.setText("" + cartCount);


        } else{
            shoppingCartTotalNumber.setVisibility(View.GONE);
        }

    }

    /**
     * This method sets items in menu
     */
    public void setMenuItems(){

        boolean isLoggediIn = preferenceUtils.getbooleanFromPreference(PreferenceUtils.IS_LOGIN,false);
        if(isLoggediIn){
            tvUser.setText("Hi, "+preferenceUtils.getStringFromPreference(PreferenceUtils.USERNAME,""));
        }else{
            tvUser.setText("Welcome Guest");
        }

        ArrayList<MenuDO> listMenu = new ArrayList<>();
        for (int i = 0; i < AppConstants.menuTitles.length ; i++) {
            MenuDO menuDO = new MenuDO();
            menuDO.name = AppConstants.menuTitles[i];
            menuDO.icon = AppConstants.menuIcons[i];
            listMenu.add(menuDO);
            //if it is logged in it should be "Logged out" other wise it should be "Login/Register".
            if (i == AppConstants.menuTitles.length -1){
                if(isLoggediIn){
                   menuDO.name = "Logout";
                }else{
                    menuDO.name = "Login or Register";
                    tvUser.setText("Welcome Guest");
                    tvUser.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                }

            }
        }
        MenuAdapter menuAdapter = new MenuAdapter(BaseActivity.this, listMenu);
        mDrawerList.setAdapter(menuAdapter);
    }

}

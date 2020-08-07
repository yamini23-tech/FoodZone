package com.mobileapp.foodzone.utills;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;


public class PreferenceUtils {

    public static String ACCEPT_TERMS = "ACCEPT_TERMS";
    public static String EDIT_ORDER_TOTAL ="EDIT_ORDER_TOTAL" ;
    public static String DELIVERY_TYPE ="DELIVERY_TYPE" ;
    public static String CODE ="CODE" ;
    public static String UPDATE_NAME = "NAME";
    public static String UPDATE_EMAIL ="EMAIL";
    public static String UPDATE_NUMBER="NUMBER";
    private SharedPreferences preferences;
    private SharedPreferences.Editor edit;
    public static String USERNAME = "user_name";
    public static String EMAILID = "email_id";
    public static String MOBILENUMBER = "mobile_number";
    public static String USER_ID = "user_id";
    public static String OLD_APP_USER_ID = "userId";
    public static int COUNT = 0;
    public static String REORDER_TOTAL ="reorder_total";
    public static String SAVE_ADDRESS = "save_address";
    public static String PRODUCT_PRICE = "product_price";
    public static String TAX = "tax";
    public static String DISCOUNT = "DISCOUNT";
    public static String TIP = "tip";
    public static String DELIVERY_CHARGES = "delivery charges";
    public static String COMMENT = "comment";
    public static String CARD_TYPE = "card_type";
    public static String CARD_HOLDER_NAME = "card_holder_name";
    public static String CARD_NUMBER = "card_number";
    public static String EXPIRY_DATE = "expiry_date";


    public static String LUNCH_TOTAL = "total";
    public static String DINNER_TOTAL = "total";
    public static String GROCERY_TOTAL = "total";

    public static String TOTAL_AMOUNT = "total";

    public static String ORDER_CODE = "order_code";
    public static String PAYMENT_TYPE = "payment_type";
    public static String LOCATION_NAME = "location_name";
    public static String REMEMBER_TOKEN = "remember_token";
    public static String LOCAL_ADDRESS = "local_address";
    public static String CARD_ID = "card_id";
    public static String ID = "id";
    public static String IS_LOGIN = "is_login";
    public static String CART_COUNT = "cart_count";
    public static String IS_TERMS_CHECKED = "is_checked";
    public static String IS_TERMS_ACCEPT = "is_checked";



    public PreferenceUtils(Context context) {
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        edit = preferences.edit();
    }

    public void saveString(String strKey, String strValue) {
        edit.putString(strKey, strValue);
        edit.commit();
    }

    public void saveInt(String strKey, int value) {
        edit.putInt(strKey, value);
        edit.commit();
    }


    public void saveLong(String strKey, Long value) {
        edit.putLong(strKey, value);
        edit.commit();
    }

    public void saveFloat(String strKey, float value) {
        edit.putFloat(strKey, value);
        edit.commit();
    }

    public void saveDouble(String strKey, Double value) {
        edit.putString(strKey, "" + value);
        edit.commit();
    }

    public void saveBoolean(String strKey, boolean value) {
        edit.putBoolean(strKey, value);
        edit.commit();
    }

    public void removeFromPreference(String strKey) {
        edit.remove(strKey);
    }

    public String getStringFromPreference(String strKey, String defaultValue) {
        return preferences.getString(strKey, defaultValue);
    }

    public boolean getbooleanFromPreference(String strKey, boolean defaultValue) {
        return preferences.getBoolean(strKey, defaultValue);
    }

    public int getIntFromPreference(String strKey, int defaultValue) {
        return preferences.getInt(strKey, defaultValue);
    }

    public long getLongFromPreference(String strKey) {
        return preferences.getLong(strKey, 0);
    }

    public float getFloatFromPreference(String strKey, float defaultValue) {
        return preferences.getFloat(strKey, defaultValue);
    }

    public double getDoubleFromPreference(String strKey, double defaultValue) {
        return Double.parseDouble(preferences.getString(strKey, "" + defaultValue));
    }
}

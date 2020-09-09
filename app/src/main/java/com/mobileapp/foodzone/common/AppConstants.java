package com.mobileapp.foodzone.common;

import com.foodzone.R;
import com.mobileapp.foodzone.model.DinnerDo;

import java.util.ArrayList;

/**
 * This class manages all constants used by application
 */
public class AppConstants {

    public static final int[] menuIcons = {R.drawable.help_icon, R.drawable.rate_icon,
            R.drawable.share_icon, R.drawable.about_icon, R.drawable.logout_icon};
    public static final String[] menuTitles = {"Need Help?", "About Us", "Logout"};

    public static final int NEED_HELP = 0;
    public static final int ABOUT_US = 1;
    public static final int LOGIN_OR_LOGOUT = 2;
    public static String DATABASE_NAME = "generic.sqlite";
    public static String DATABASE_PATH = "";

    public static ArrayList<LunchDo> listLunch = new ArrayList<>();
    public static ArrayList<LunchDo> listCartLunch = new ArrayList<>();


    public static ArrayList<DinnerDo> listDinner = new ArrayList<>();
    public static ArrayList<DinnerDo> listCartDinner = new ArrayList<>();


    public static ArrayList<GroceryDo> listGrocery = new ArrayList<>();
    public static ArrayList<GroceryDo> listCartGrocery = new ArrayList<>();



    public static ArrayList<BreakfastDo> listBreakfast = new ArrayList<>();
    public static ArrayList<BreakfastDo> listCartBreakfast = new ArrayList<>();

}

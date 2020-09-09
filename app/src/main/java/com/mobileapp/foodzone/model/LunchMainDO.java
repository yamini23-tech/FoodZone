package com.mobileapp.foodzone.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Model file
 */
public class LunchMainDO implements Serializable {

    public int status = 0;
    public String imageURL = "";

    public ArrayList<LunchDo> listLunchDOs = new ArrayList<>();

}

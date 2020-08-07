package com.mobileapp.foodzone.model;

import java.io.Serializable;

/**
 * Created by sandy on 2/3/2018.
 */

public class LoginDo implements Serializable {

    public int status = 0;
    public String statusMessage = "";

    public String id = "";
    public String name            = "";
    public String email           = "";
    public String mobileNumber    = "";
    public String rememberToken   = "";
    public int    locationName;


}

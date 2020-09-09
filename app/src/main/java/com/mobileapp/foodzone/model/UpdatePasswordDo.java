package com.mobileapp.foodzone.model;

import java.io.Serializable;

/**
 * Model file
 */
public class UpdatePasswordDo implements Serializable {
    public int status = 0;
    public String statusMessage = "";
    public String name            = "";
    public String email   = "";
    public String mobileNumber   = "";
}

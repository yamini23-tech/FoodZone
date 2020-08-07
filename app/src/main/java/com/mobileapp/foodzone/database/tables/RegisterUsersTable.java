package com.mobileapp.foodzone.database.tables;

import android.database.sqlite.SQLiteDatabase;

/**
 * Created by VenuAppasani on 12/12/18.
 * Copyright (C) 2018 TBS - All Rights Reserved
 */
public class RegisterUsersTable {

    public static final String TABLE_USERS_LIST = "users_list";

    public static final String COLUMN_ID = "id"; //Auto increment
    public static final String COLUMN_USER_ID = "user_id";
    public static final String COLUMN_EMAIL = "email_id";
    public static final String COLUMN_PASSWORD = "pswrd_desc";
    public static final String COLUMN_MOBILE = "mobile_number";

    private static final String CREATE_QUERY = "CREATE TABLE " + TABLE_USERS_LIST + " (" +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_USER_ID + " TEXT, " +
            COLUMN_EMAIL + " TEXT, " +
            COLUMN_PASSWORD + " TEXT, " +
            COLUMN_MOBILE + " TEXT)";


    public static void create(SQLiteDatabase database) {
        database.execSQL(CREATE_QUERY);

    }

    public static void upgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        //TODO: write upgrade query here
        //TODO: should be written in v2 if required
    }
}
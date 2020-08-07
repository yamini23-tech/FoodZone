package com.mobileapp.foodzone.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.mobileapp.foodzone.database.tables.RegisterUsersTable;


/**
 * Created by VenuAppasani on 11/12/2018.
 * Copyright (C) 2018 TBS - All Rights Reserved
 */
public class TBSDatabaseHelper extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    public static final String DATABASE_NAME = "food.db";

    public TBSDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        RegisterUsersTable.create(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        RegisterUsersTable.upgrade(db, oldVersion, newVersion);
    }
}

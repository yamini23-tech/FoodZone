package com.mobileapp.foodzone.database;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.mobileapp.foodzone.model.RegisterDO;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Manages storage related functions
 */

public class StorageManager {
    private static final String TAG = "StorageManager";

    private static TBSDatabaseHelper sDatabaseHelper;
    private static StorageManager sStorageManager;

    private StorageManager(Context context) {
        sDatabaseHelper = new TBSDatabaseHelper(context);
    }

    public static synchronized StorageManager getInstance(Context context) {
        if (sStorageManager == null) {
            sStorageManager = new StorageManager(context);
        }
        return sStorageManager;
    }

  
    public void saveRegisterDO(Activity activity, RegisterDO registerDO) {
        try {
            File file = new File(activity.getApplication().getFilesDir().toString() + "/", "RegisterDO.txt");
            FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream os = new ObjectOutputStream(fos);
            os.writeObject(registerDO);
            os.close();
            fos.close();
        } catch (Exception e) {
            Log.e(TAG, "saveregisterDO() : " + e.getMessage());
        }
    }

    public void deleteRegisterDO(Activity activity) {
        try {
            File file = new File(activity.getApplication().getFilesDir().toString() + "/", "RegisterDO.txt");
            if (file.exists())
                file.delete();
        } catch (Exception e) {
            Log.e(TAG, "saveRegisterDO() : " + e.getMessage());
        }
    }

    public RegisterDO getRegisterDO(Context context) {
        try {
            File file = new File(((Activity) context).getApplication().getFilesDir().toString() + "/", "RegisterDO.txt");
            if (file.exists()) {
                FileInputStream fis = new FileInputStream(file);
                ObjectInputStream is = new ObjectInputStream(fis);
                RegisterDO RegisterDO = (com.mobileapp.foodzone.model.RegisterDO) is.readObject();
                is.close();
                fis.close();
                return RegisterDO;
            }
        } catch (Exception e) {
            Log.e("StorageManager", "getRegisterDO() : " + e.getMessage());
            return new RegisterDO();
        }
        return new RegisterDO();
    }

}

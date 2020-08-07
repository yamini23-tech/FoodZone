package com.mobileapp.foodzone.database.tables;

import android.content.Context;
import android.os.Environment;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Creating Application Level Storage Directories
 * eg: storing images,caching data etc
 * */
public class FilesStorage
{
	public static String SDCARD_ROOT 	;

	public static String ROOT_DIR 			;

	public static void CreateStorageDirs(Context context)
	{
		if(Environment.getExternalStorageState().equalsIgnoreCase(Environment.MEDIA_REMOVED))
			SDCARD_ROOT = context.getFilesDir().toString()+"/";
		else
			SDCARD_ROOT = Environment.getExternalStorageDirectory().toString() + "/";
		ROOT_DIR 			 = SDCARD_ROOT  + "Food/";
		new File(ROOT_DIR).mkdirs();
	}

	public static void clearDir(String dirPath)
	{
		try
		{
			File delDir = new File(dirPath);

			File[] filesList = delDir.listFiles();
			for(int i=0;i<filesList.length;i++)
			{
				try
				{
					filesList[i].delete();
				}
				catch (Exception e)
				{
				}
			}
		}
		catch(Exception e)
		{
		}
	}

	public static void copy(String source, String destination) throws IOException
	{

		 BufferedInputStream bis = new BufferedInputStream(new FileInputStream(source));
		 FileOutputStream fos = new FileOutputStream(destination);
		 BufferedOutputStream bos = new BufferedOutputStream(fos);
        byte[] byt = new byte[8192];
		 int noBytes;
		 while((noBytes = bis.read(byt)) != -1)
			 bos.write(byt,0,noBytes);

		 bos.flush();
		 bos.close();
		 fos.close();
		 bis.close();
	 }

	public static String getRootDirector() {
		 SDCARD_ROOT = Environment.getExternalStorageDirectory().toString() + "/generic/";
		return ROOT_DIR;
	}
}

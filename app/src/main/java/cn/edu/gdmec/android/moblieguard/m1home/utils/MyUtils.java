package cn.edu.gdmec.android.moblieguard.m1home.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/**
 * Created by asus-pc on 2017/10/21.
 */

public class MyUtils {
    public static String getVersion(Context context) {
        PackageManager packageManager = context.getPackageManager();
        try{
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(),0);
            return packageInfo.versionName;
        }catch (PackageManager.NameNotFoundException e){
            e.printStackTrace();
            return "";
        }
    }
}

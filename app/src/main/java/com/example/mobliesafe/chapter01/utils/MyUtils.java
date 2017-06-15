package com.example.mobliesafe.chapter01.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/**
 * 作者：国富小哥
 * 日期：2017/6/15
 * Created by Administrator
 * 获取版本号和安装apk
 */

public class MyUtils {

    /**
     * 获取本地apk版本号
     * */
    public static String getVersion(Context context){
        //PackageManager可以获取清单文件中的所有信息
        PackageManager manager=context.getPackageManager();
        try {
            //获取当前应用程序的包名
            PackageInfo info=manager.getPackageInfo(context.getPackageName(),0);
            //截取版本号
            String versionName=info.versionName;
            return versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }


   
}

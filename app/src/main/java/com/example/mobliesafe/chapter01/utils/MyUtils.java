package com.example.mobliesafe.chapter01.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;

import java.io.File;

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


    /**
     * 安装新版本
     * */
    public static void installApk(Activity activity){
        Intent intent=new Intent("android.intent.action.VIEW");
        //添加默认分类
        intent.addCategory("android.intent.category.DEFAULT");
        //设置数据和类型
        intent.setDataAndType(Uri.fromFile(new File("/mnt/sdcard/mobilesafe2.0.apk")),"application/vnd.android.package-archive");
        activity.startActivityForResult(intent,0);
    }

}

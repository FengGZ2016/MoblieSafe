package com.example.mobliesafe.chapter02.utils;

import java.security.MessageDigest;

/**
 * 作者：国富小哥
 * 日期：2017/7/30
 * Created by Administrator
 *
 * MD5加密算法的工具类
 */

public class MD5Utils {

    public static String encode(String text){
        try {
            MessageDigest digest=MessageDigest.getInstance("md5");
            byte[] result=digest.digest(text.getBytes());
            StringBuilder builder=new StringBuilder();
            //遍历
            for (byte b:result){
                int number=b&0xff;
                String hex=Integer.toHexString(number);
                if (hex.length()==1){
                    builder.append("0"+hex);
                }else {
                    builder.append(hex);
                }
            }
                return builder.toString();

        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}

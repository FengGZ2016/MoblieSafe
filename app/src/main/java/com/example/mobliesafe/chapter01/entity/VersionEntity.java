package com.example.mobliesafe.chapter01.entity;

/**
 * 作者：国富小哥
 * 日期：2017/6/15
 * Created by Administrator
 * 版本信息的实体类
 */

public class VersionEntity {
    //版本号
    private   String versionCode;
    //版本描述
    private  String description;
    //下载地址
    private  String apkurl;

    public  String getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(String versionCode) {
        this.versionCode = versionCode;
    }

    public  String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public  String getApkurl() {
        return apkurl;
    }

    public void setApkurl(String apkurl) {
        this.apkurl = apkurl;
    }
}

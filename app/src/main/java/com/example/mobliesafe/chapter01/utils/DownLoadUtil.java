package com.example.mobliesafe.chapter01.utils;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;

import java.io.File;

/**
 * 作者：国富小哥
 * 日期：2017/6/15
 * Created by Administrator
 * 下载文件的工具类
 */

public class DownLoadUtil {

    /**
     * 用来监听下载状态的接口
     * */
    interface MyCallBack{
        //下载成功
        void onSuccess(ResponseInfo<File> arg0);
        //下载失败
        void onFailure(HttpException arg0,String arg1);
        //下载中
        void onLoadding(long total,long current,boolean isUploading);
    }

    /**
     * 下载apk的方法
     * */
    public void downLoadapk(String url,String targerFile,final  MyCallBack myCallBack){
        //创建HttpUtils对象
        HttpUtils httpUtils=new HttpUtils();
        //调用download方法（url地址，下载文件的本地路径，监听下载状态的接口）
        httpUtils.download(url, targerFile, new RequestCallBack<File>() {
            @Override
            public void onSuccess(ResponseInfo<File> responseInfo) {
                //下载成功
                myCallBack.onSuccess(responseInfo);
            }

            @Override
            public void onFailure(HttpException e, String s) {
                //下载失败
                myCallBack.onFailure(e,s);
            }

            @Override
            public void onLoading(long total, long current, boolean isUploading) {
                super.onLoading(total, current, isUploading);
                //下载中
                myCallBack.onLoadding(total,current,isUploading);
            }
        });
    }

}

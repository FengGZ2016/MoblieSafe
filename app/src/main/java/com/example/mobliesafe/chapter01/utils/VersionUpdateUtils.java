package com.example.mobliesafe.chapter01.utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import com.example.mobliesafe.MainActivity;
import com.example.mobliesafe.R;
import com.example.mobliesafe.chapter01.entity.VersionEntity;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 作者：国富小哥
 * 日期：2017/6/15
 * Created by Administrator
 * 版本更新提醒工具类（服务器版本号和本地版本号对比）
 */

public class VersionUpdateUtils {
    /**
     * 用于更新UI
     * */
    private Handler mHandler=new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case MESSAGE_IO_EEOR:
                    Toast.makeText(context, "IO异常", Toast.LENGTH_SHORT).show();
                    enterHome();
                    break;
                case MESSAGE_JSON_EEOR:
                    Toast.makeText(context, "JSON解析异常", Toast.LENGTH_SHORT).show();
                    enterHome();
                    break;
                case MESSAGE_NET_EEOR:
                    Toast.makeText(context, "网络异常", Toast.LENGTH_SHORT).show();
                    enterHome();
                    break;
                case MESSAGE_SHOEW_DIALOG:
                    showUpdateDialog(mVersionEntity);
                    break;
                case MESSAGE_ENTERHOME:
                    Intent intent = new Intent(context,MainActivity.class);
                    context.startActivity(intent);
                    context.finish();
                    break;
            }

            return false;
        }
    });



    private String mVersion;//本地版本号
    private Activity context;
    private ProgressDialog mProgressDialog;//进度条对话框
    private VersionEntity mVersionEntity;//服务器版本号

    private static final int MESSAGE_NET_EEOR = 101;
    private static final int MESSAGE_IO_EEOR = 102;
    private static final int MESSAGE_JSON_EEOR = 103;
    private static final int MESSAGE_SHOEW_DIALOG = 104;
    protected static final int MESSAGE_ENTERHOME = 105;

    public VersionUpdateUtils(String version,Activity activity){
        mVersion=version;
        context=activity;
    }

    /**
     * 获取服务器版本号
     * */
    public void getCloudVersion(){
        OkHttpClient client=new OkHttpClient();
        Request request=new Request.Builder().url("http://192.168.234.1:8080/updateinfo.html").build();
        Call call=client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                //异常
                mHandler.sendEmptyMessage(MESSAGE_NET_EEOR);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //成功
                String responseData=response.body().string();
                try {
                    //创建JsonObject对象
                    JSONObject jsonObject=new JSONObject(responseData);
                    VersionEntity versionEntity=new VersionEntity();
                    String versionCode=jsonObject.getString("code");
                    String description=jsonObject.getString("des");
                    String apkurl=jsonObject.getString("apkurl");
                    mVersionEntity=new VersionEntity();
                    mVersionEntity.setVersionCode(versionCode);
                    mVersionEntity.setDescription(description);
                    mVersionEntity.setApkurl(apkurl);

                    if (!mVersion.equals(mVersionEntity.getDescription())) {
                        // 如果本地版本号与服务器版本号不一致，就显示更新对话框
                        mHandler.sendEmptyMessage(MESSAGE_SHOEW_DIALOG);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    mHandler.sendEmptyMessage(MESSAGE_JSON_EEOR);
                }
            }
        });

    }


    /**
     *进入主界面
     * */
    private void enterHome() {
        mHandler.sendEmptyMessageDelayed(MESSAGE_ENTERHOME, 2000);
    }


    /**
     * 显示版本更新的对话框
     * */
    private void showUpdateDialog(final VersionEntity versionEntity) {
        //创建dialog
        AlertDialog.Builder builder=new AlertDialog.Builder(context);
        //版本号
        builder.setTitle("检测到新版本："+versionEntity.getVersionCode());
        //版本描述
        builder.setMessage(versionEntity.getDescription());
        //设置不能点击手机返回按钮隐藏对话框
        builder.setCancelable(false);
        //设置对话框图标
       builder.setIcon(R.mipmap.ic_launcher);
        //设置立即升级按钮的点击事件
        builder.setPositiveButton("立即升级", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
           //初始化进度条对话框
                initProgressDialog();
                //下载新版本
                downloadNewApk(versionEntity.getApkurl());
            }
        });
        //设置暂不升级按钮的点击事件
        builder.setNegativeButton("暂不升级", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //隐藏版本更新的对话框
                dialog.dismiss();
                //进入主界面
                enterHome();
            }
        });
        builder.show();

    }



    /**
     * 初始化进度条对话框
     * */
    private void initProgressDialog() {
        mProgressDialog=new ProgressDialog(context);
        mProgressDialog.setMessage("准备下载...");
        //设置样式
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        mProgressDialog.show();
    }


    /**
     * 下载新版本
     *
     * @param apkurl*/
    private void downloadNewApk(String apkurl) {
        DownLoadUtil downLoadUtil=new DownLoadUtil();
        downLoadUtil.downLoadapk(apkurl, "/mnt/sdcard/mobilesafe2.0.apk", new DownLoadUtil.MyCallBack() {
            @Override
            public void onSuccess(ResponseInfo<File> arg0) {
                //下载成功,隐藏进度条
                mProgressDialog.dismiss();
                //开始安装新版本
                MyUtils.installApk(context);
            }

            @Override
            public void onFailure(HttpException arg0, String arg1) {
                //下载失败
                mProgressDialog.setMessage("下载失败");
                //隐藏进度条
                mProgressDialog.dismiss();
                //进入主界面
                enterHome();

            }

            @Override
            public void onLoadding(long total, long current, boolean isUploading) {
                //正在下载
                mProgressDialog.setMax((int) total);
                mProgressDialog.setMessage("正在下载...");
                mProgressDialog.setProgress((int) current);

            }
        });


    }
}

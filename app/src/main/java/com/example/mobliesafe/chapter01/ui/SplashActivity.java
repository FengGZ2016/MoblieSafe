package com.example.mobliesafe.chapter01.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;

import com.example.mobliesafe.R;
import com.example.mobliesafe.chapter01.utils.MyUtils;
import com.example.mobliesafe.chapter01.utils.VersionUpdateUtils;

public class SplashActivity extends AppCompatActivity {
    //应用的版本号
    private TextView mTextView_version;
    //本地的版本号
    private String mVersion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //去掉标题栏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash);
        initView();
        getUpdateVersion();
    }



    private void initView() {
        mTextView_version= (TextView) findViewById(R.id.textview_splash_version);
        mVersion= MyUtils.getVersion(getApplicationContext());
        mTextView_version.setText("版本号 "+mVersion);
    }


    private void getUpdateVersion() {
        final VersionUpdateUtils updateUtils=new VersionUpdateUtils(mVersion,SplashActivity.this);
        new Thread(){
            @Override
            public void run() {
               //获取服务器版本号
                updateUtils.getCloudVersion();
            }
        }.start();
    }
}

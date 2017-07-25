package com.example.mobliesafe;

import android.content.Intent;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.example.mobliesafe.chapter01.adapter.HomeAdapter;
import com.example.mobliesafe.chapter01.ui.AdvancedToolsActivity;
import com.example.mobliesafe.chapter01.ui.AppManagerActivity;
import com.example.mobliesafe.chapter01.ui.CacheClearListActivity;
import com.example.mobliesafe.chapter01.ui.ProcessManagerActivity;
import com.example.mobliesafe.chapter01.ui.SecurityPhoneActivity;
import com.example.mobliesafe.chapter01.ui.SettingsActivity;
import com.example.mobliesafe.chapter01.ui.TrafficMonitoringActivity;
import com.example.mobliesafe.chapter01.ui.VirusScanActivity;

public class MainActivity extends AppCompatActivity {

    private GridView mGridView;
    private HomeAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //去掉标题栏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        mGridView= (GridView) findViewById(R.id.gridview_home);
        mAdapter=new HomeAdapter(this);
        //设置适配器
        mGridView.setAdapter(mAdapter);
        //设置item的点击事件
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        //手机防盗
                        if (isSetUpPassword()){
                            //如果已经设置过防盗密码，就弹出输入密码的对话框
                            showInterPasswordDialog();
                        }else {
                            //如果没有设置过防盗密码，就弹出设置密码的对话框
                            showSetUpPasswordDialog();
                        }
                        break;
                    case 1:
                        //通讯卫士
                        startActivity(SecurityPhoneActivity.class);

                        break;
                    case 2:
                        //软件管家
                        startActivity(AppManagerActivity.class);

                        break;
                    case 3:
                        //病毒查杀
                        startActivity(VirusScanActivity.class);

                        break;
                    case 4:
                        //缓存清理
                        startActivity(CacheClearListActivity.class);

                        break;
                    case 5:
                        //进程管理
                        startActivity(ProcessManagerActivity.class);

                        break;
                    case 6:
                        //流量统计
                        startActivity(TrafficMonitoringActivity.class);

                        break;
                    case 7:
                        //高级工具
                        startActivity(AdvancedToolsActivity.class);

                        break;
                    case 8:
                        //设置中心
                        startActivity(SettingsActivity.class);
                        break;
                }
            }
        });

    }


    /**
     * 打开新的Activity，不关闭自己
     * */
    private void startActivity(Class<?> activity) {
        Intent intent=new Intent(this,activity);
        startActivity(intent);

    }


    /**
     * 设置密码的对话框
     * */
    private void showSetUpPasswordDialog() {

    }


    /**
     * 输入密码的对话框
     * */
    private void showInterPasswordDialog() {
    }


    /**
     * 判断用户是否设置防盗密码
     * */
    private boolean isSetUpPassword() {
        return false;
    }


    private long mExitTime;
    /**
     * 按两次返回键退出程序
     * */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode==KeyEvent.KEYCODE_BACK){
            if (SystemClock.currentThreadTimeMillis()-mExitTime<2000){
                System.exit(0);
            }else {
                Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                mExitTime=System.currentTimeMillis();
            }

            return true;
        }

        return super.onKeyDown(keyCode, event);
    }
}

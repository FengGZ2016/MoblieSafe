package com.example.mobliesafe;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
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
import com.example.mobliesafe.chapter02.dialog.InterPasswordDialog;
import com.example.mobliesafe.chapter02.dialog.SetUpPasswordDialog;
import com.example.mobliesafe.chapter02.ui.LostFindActivity;
import com.example.mobliesafe.chapter02.utils.MD5Utils;

public class MainActivity extends AppCompatActivity {

    private GridView mGridView;
    private HomeAdapter mAdapter;
    /** 存储手机防盗密码的sp */
    private SharedPreferences msharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //去掉标题栏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        msharedPreferences=getSharedPreferences("config",MODE_PRIVATE);
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
        //引入对话框
        final SetUpPasswordDialog setUpPasswordDialog=new SetUpPasswordDialog(MainActivity.this);
        setUpPasswordDialog.setCallBack(new SetUpPasswordDialog.MyCallBack() {
            @Override
            public void ok() {
                String firstPassword=setUpPasswordDialog.getFirstPassword();
                String  affirmPassword=setUpPasswordDialog.getAffirmPassword();

                if (!TextUtils.isEmpty(firstPassword)
                        && !TextUtils.isEmpty(affirmPassword)) {
                    if (firstPassword.equals(affirmPassword)) {
                        // 两次密码一致,存储密码
                        savePswd(affirmPassword);
                        setUpPasswordDialog.dismiss();
                        // 显示输入密码对话框
                        showInterPasswordDialog();
                    } else {
                        Toast.makeText(MainActivity.this, "两次密码不一致！", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "密码不能为空！", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void cancle() {
                setUpPasswordDialog.dismiss();
            }
        });

        //设置返回键可以隐藏对话框
        setUpPasswordDialog.setCancelable(true);
        setUpPasswordDialog.show();
    }


    /**
     * 储存密码
     * */
    private void savePswd(String affirmPassword) {
        SharedPreferences.Editor editor=msharedPreferences.edit();
        // 为了防止用户隐私被泄露，因此需要加密密码
        editor.putString("PhoneAntiTheftPWD", MD5Utils.encode(affirmPassword));
        editor.commit();

    }


    /**
     * 输入密码的对话框
     * */
    private void showInterPasswordDialog() {
        //获取已经设置好的密码
        final String spPassword=getPassword();
        //引入对话框
        final InterPasswordDialog mInterPasswordDialog=new InterPasswordDialog(MainActivity.this);
        //获取用户输入的密码
        final String interPassword=mInterPasswordDialog.getPassword();
        mInterPasswordDialog.setCallBack(new InterPasswordDialog.MyCallBack() {
            @Override
            public void confirm() {
                if (TextUtils.isEmpty(interPassword)){
                    Toast.makeText(MainActivity.this, "密码不能为空！", Toast.LENGTH_SHORT).show();
                }else if (spPassword.equals(MD5Utils.encode(interPassword))){
                    //用户输入的密码与设置好的密码一致，进入防盗主界面
                    mInterPasswordDialog.dismiss();
                    startActivity(LostFindActivity.class);
                }else {
                    mInterPasswordDialog.dismiss();
                    Toast.makeText(MainActivity.this, "密码有误，请从新输入", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void cancle() {
                mInterPasswordDialog.dismiss();
            }
        });
        mInterPasswordDialog.setCancelable(true);
        // 让对话框显示
        mInterPasswordDialog.show();
    }


    /**
     * 获取已经设置好的密码
     * */
    private String getPassword() {
        String password=msharedPreferences.getString("PhoneAntiTheftPWD",null);
        if (TextUtils.isEmpty(password)){
            return "";
        }
        return password;
    }


    /**
     * 判断用户是否设置防盗密码
     * */
    private boolean isSetUpPassword() {
        String password=msharedPreferences.getString("PhoneAntiTheftPWD",null);
        if (TextUtils.isEmpty(password)){
            return false;
        }
        return true;
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

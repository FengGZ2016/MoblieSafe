package com.example.mobliesafe.chapter02.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.mobliesafe.R;

/**
 * 作者：国富小哥
 * 日期：2017/7/27
 * Created by Administrator
 * 自定义对话框
 * 用于设置密码
 */

public class SetUpPasswordDialog extends Dialog{

    private TextView mTextView_title;
    private EditText mEditText_first_password;
    private EditText mEditText_affirm_password;
    private MyCallBack mMyCallBack;
    private Button mButton_ok;
    private Button mButton_cancle;


    public interface MyCallBack{
        void ok();
        void cancle();
    }

    public SetUpPasswordDialog(@NonNull Context context) {
        /*引入自定义对话框的样式*/
        super(context, R.style.dialog_custom);
    }

    /**
     * 设置回调接口
     * */
    public void setCallBack(MyCallBack mMyCallBack){
        this.mMyCallBack=mMyCallBack;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setup_password_dialog);
        initView();
    }

    private void initView() {
        mTextView_title= (TextView) findViewById(R.id.textview_setuppassword_title);
        mEditText_first_password= (EditText) findViewById(R.id.editText_firstpassword);
        mEditText_affirm_password= (EditText) findViewById(R.id.editText_affirmpassword);
        mButton_ok= (Button) findViewById(R.id.btn_ok);
        mButton_cancle= (Button) findViewById(R.id.btn_cancle);

        mButton_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMyCallBack.ok();
            }
        });

        mButton_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMyCallBack.cancle();
            }
        });


    }

    public String getFirstPassword(){
        String firstPassword=mEditText_first_password.getText().toString().trim();
        return firstPassword;
    }

    public String getAffirmPassword(){
        String affirmPassword=mEditText_affirm_password.getText().toString().trim();
        return affirmPassword;
    }


    /**
     * 设置对话框的标题栏
     * */
    public void setTitle(String title){
        if (!TextUtils.isEmpty(title)){
            mTextView_title.setText(title);
        }

    }
}

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
 * 日期：2017/8/1
 * Created by Administrator
 */

public class InterPasswordDialog extends Dialog{

    /**对话框标题*/
    private TextView mTextView_title;
    /**输入密码文本框*/
    private EditText mEditText_inter_password;
    /**确认按钮*/
    private Button mButton_comfirm;
    /**取消按钮*/
    private Button mButton_dismiss;
    /**回调接口*/
    private MyCallBack mMyCallBack;

    private Context context;

    public InterPasswordDialog(@NonNull Context context) {
        super(context, R.style.dialog_custom);

        this.context=context;
    }

    public void setCallBack(MyCallBack mMyCallBack){
        this.mMyCallBack = mMyCallBack;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inter_password_dialog);

        initView();
    }

    private void initView() {
        mTextView_title= (TextView) findViewById(R.id.textview_interpassword_title);
        mEditText_inter_password= (EditText) findViewById(R.id.eidt_inter_password);
        mButton_comfirm= (Button) findViewById(R.id.btn_comfirm);
        mButton_dismiss= (Button) findViewById(R.id.btn_dismiss);

        mButton_comfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        mButton_dismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    public String getPassword(){
        String password=mEditText_inter_password.getText().toString().trim();
        return password;
    }

    /***
     * 设置对话框标题
     * @param title
     */
    public void setTitle(String title){
        if(!TextUtils.isEmpty(title)){
            mTextView_title.setText(title);
        }
    }

    /**
     * 回调接口
     * @author admin
     */
    public interface MyCallBack{
        void confirm();
        void cancle();
    }
}

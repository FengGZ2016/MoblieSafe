package com.example.mobliesafe.chapter01.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mobliesafe.R;

/**
 * 作者：国富小哥
 * 日期：2017/7/25
 * Created by Administrator
 *
 * 主界面GridView的适配器
 */

public class HomeAdapter  extends BaseAdapter{

    //功能图标数组
    int[] imageId={R.drawable.safe,R.drawable.callmsgsafe,R.drawable.app,
    R.drawable.trojan,R.drawable.sysoptimize,R.drawable.taskmanager,
            R.drawable.netmanager,R.drawable.atools,R.drawable.settings
    };
    //功能名字数组
    String[] names={
        "手机防盗","通讯卫士","软件管家","手机杀毒","缓存清理","进程管理","流量统计","高级工具","设置中心"};

    private Context mContext;
    public HomeAdapter(Context mContext){
        this.mContext=mContext;
    }

    @Override
    public int getCount() {
        return names.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View mView;
        ViewHolder mViewHolder;
        if (convertView==null){
            mView= LayoutInflater.from(mContext).inflate(R.layout.item_home,parent,false);
            mViewHolder=new ViewHolder();
            mViewHolder.mImageView_icon= (ImageView) mView.findViewById(R.id.imageview_icon);
            mViewHolder.mTextView_name= (TextView) mView.findViewById(R.id.textview_name);
            //将ViewHolder存储在view中
            mView.setTag(mViewHolder);
        }else {
            mView=convertView;
            mViewHolder= (ViewHolder) mView.getTag();
        }

        mViewHolder.mImageView_icon.setImageResource(imageId[position]);
        mViewHolder.mTextView_name.setText(names[position]);

        return mView;
    }

    class ViewHolder{
        ImageView mImageView_icon;
        TextView mTextView_name;
    }
}

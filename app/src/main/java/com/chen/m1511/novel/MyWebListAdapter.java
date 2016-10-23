package com.chen.m1511.novel;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by m1511 on 2016/7/12.
 */
public class MyWebListAdapter extends BaseAdapter {

    private List<WebBean> mWebBeanList;
    private LayoutInflater mLayoutInflater;
    private Context mContext;

    public void bindData(Context mContext, List<WebBean> mWebBeanList){
        this.mContext = mContext;
        this.mLayoutInflater = LayoutInflater.from(mContext);
        this.mWebBeanList = mWebBeanList;
    }

    @Override
    public int getCount() {
        return mWebBeanList.size();
    }

    @Override
    public Object getItem(int position) {
        return mWebBeanList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder mViewHolder = null;
        if (convertView == null){
            mViewHolder = new ViewHolder();
            convertView = mLayoutInflater.inflate(R.layout.web_list_item, null);
            mViewHolder.mImageView = (ImageView) convertView.findViewById(R.id.web_Image);
            mViewHolder.mTextView = (TextView) convertView.findViewById(R.id.web_Text);
            convertView.setTag(mViewHolder);
        }else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }

        WebBean mWebBean = mWebBeanList.get(position);
        Glide.with(mContext).load(mWebBean.web_ImageView).placeholder(R.mipmap.ic_launcher).into(mViewHolder.mImageView);
        mViewHolder.mTextView.setText(mWebBean.web_TextView);

        return convertView;
    }

    class ViewHolder{
        public ImageView mImageView;
        public TextView mTextView;
    }
}

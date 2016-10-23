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
public class MyListAdapter extends BaseAdapter {

    private List<NewsBean> mNewsBeanList;
    private LayoutInflater mLayoutInflater;
    private Context mContext;

    public void bindData(Context mContext, List<NewsBean> mNewsBeanList){
        this.mContext = mContext;
        this.mLayoutInflater = LayoutInflater.from(mContext);
        this.mNewsBeanList = mNewsBeanList;
    }

    @Override
    public int getCount() {
        return mNewsBeanList.size();
    }

    @Override
    public Object getItem(int position) {
        return mNewsBeanList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder mViewHolder;
        if (convertView == null){
            mViewHolder = new ViewHolder();
            convertView = mLayoutInflater.inflate(R.layout.list_item, null);
            mViewHolder.mImageView = (ImageView) convertView.findViewById(R.id.image_item);
            mViewHolder.mTextView = (TextView) convertView.findViewById(R.id.text_item);
            convertView.setTag(mViewHolder);
        }else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }

        NewsBean mNewsBean = mNewsBeanList.get(position);
        //使用了Glide框架加载图片
        Glide.with(mContext).load(mNewsBean.news_ImageView).placeholder(R.mipmap.ic_launcher).into(mViewHolder.mImageView);
        mViewHolder.mTextView.setText(mNewsBean.news_TextView);

        return convertView;
    }

    class ViewHolder{
        public ImageView mImageView;
        public TextView mTextView;
    }
}

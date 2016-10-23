package com.chen.m1511.novel;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    //URL的分段
    private static final String URL_PATH1 = "http://route.showapi.com/231-1?num=";
    private int num = 20;
    private static final String URL_PATH2 = "&page=";
    private int page = 1;
    private static final String URL_PATH3 = "&showapi_appid=21755&showapi_timestamp=";
    private String showapi_timestamp = String.valueOf(System.currentTimeMillis());
    private static final String URL_PATH4 = "&showapi_sign=a701942eb1474c68820f41172ca01e74";

    private ListView mListView;
    private List<NewsBean> mList = new ArrayList<>();     //当前page的list
    private boolean isLast;     //是否滑动到底部的标志位
    private MyListAdapter mMyListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mListView = (ListView) findViewById(R.id.listView);

        mMyListAdapter = new MyListAdapter();       //MyAdapter对象只需在这里创建一次
        MyAsyncTask mMyAsyncTask = new MyAsyncTask();
        mMyAsyncTask.execute(URL_PATH1 + num + URL_PATH2 + page + URL_PATH3 + showapi_timestamp + URL_PATH4);


        //列表的滚动监听
        mListView.setOnScrollListener(new AbsListView.OnScrollListener() {

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                isLast = (totalItemCount == firstVisibleItem + visibleItemCount);     //判断是否滑倒底部

            }

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                //当滑动到底部 且 手指离开屏幕时 确定为需要加载分页
                if (isLast && scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                    new MyAsyncTask().execute(URL_PATH1 + num + URL_PATH2 + page + URL_PATH3 + showapi_timestamp + URL_PATH4);
                }
            }
        });


        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent mIntent = new Intent(MainActivity.this, WebActivity.class);
                Bundle mBundle = new Bundle();
                mBundle.putCharSequence("url", mList.get(position).news_Url);
                mIntent.putExtras(mBundle);
                startActivity(mIntent);
            }
        });

    }


    class MyAsyncTask extends AsyncTask<String, Void, List<NewsBean>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected List<NewsBean> doInBackground(String... params) {

            try {
                JSONObject mJSONObject = GetJsonObject.getJsonObject(params[0]).getJSONObject("showapi_res_body");
                JSONArray mJSONArray = mJSONObject.getJSONArray("newslist");

                for (int i = 0; i < mJSONArray.length(); i++) {
                    JSONObject mJSONObject1 = mJSONArray.getJSONObject(i);
                    NewsBean mNewsBean = new NewsBean();
                    mNewsBean.news_ImageView = mJSONObject1.getString("picUrl");
                    mNewsBean.news_TextView = mJSONObject1.getString("title");
                    mNewsBean.news_Url = mJSONObject1.getString("url");
                    mList.add(mNewsBean);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return mList;
        }

        @Override
        protected void onPostExecute(List<NewsBean> strings) {
            super.onPostExecute(strings);
            mMyListAdapter.bindData(MainActivity.this, strings);
            //只有当加载入第一页时才设置一次适配器
            if (page == 1) {
                mListView.setAdapter(mMyListAdapter);
            }
            mMyListAdapter.notifyDataSetChanged();
            page++;

        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }
}

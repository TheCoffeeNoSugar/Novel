package com.chen.m1511.novel;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by m1511 on 2016/7/12.
 */
public class WebActivity extends AppCompatActivity {

    private String web_Content;
    private String web_ImageUrl;
    private int pageNum = 2;
    private String url;
    private static final int TIME_OUT = 10000;
    private Handler mHandler;
    private Document mDocument;
    private Elements mElements;
    private Elements mElements_var;
    private List<WebBean> mWebBeanList = new ArrayList<>();
    private MyWebListAdapter mMyWebListAdapter;
    private ListView mListView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

        mHandler = new Handler();

        Bundle mBundle = getIntent().getExtras();
        url = mBundle.getString("url");
        Log.i("TAG", "url= " + url);

        mListView = (ListView) findViewById(R.id.web_ListView);
        mMyWebListAdapter = new MyWebListAdapter();


        new Thread(new Runnable() {
            @Override
            public void run() {
                getHtmlData("index");       //第一页是index.html
                try {
                    //判断网页是否存在
                    while ((Jsoup.connect(url + pageNum +".html").timeout(TIME_OUT).get()) != null){
                        getHtmlData(pageNum);
                        pageNum++;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {


                            mMyWebListAdapter.bindData(WebActivity.this, mWebBeanList);
                            mListView.setAdapter(mMyWebListAdapter);
                        }
                    });
            }
        }).start();
    }

    private void getHtmlData(Object type){
        switch(type.getClass().getSimpleName()){
            case "String":
                //Jsoup解析html必须在子线程中进行，不能在主线程里
                try {
                    mDocument = Jsoup.connect(url + type +".html").timeout(TIME_OUT).get();
                    mElements = mDocument.getElementsByClass("reads");
                    web_ImageUrl = mDocument.select("img").attr("src");
                    web_Content = mElements.text();
                    Log.i("TAG", "pageNum= " + type);
                    Log.i("TAG", "web_ImageUrl= " + web_ImageUrl);
                    Log.i("TAG", "web_Content= " + web_Content);

                    WebBean mWebBean = new WebBean();
                    mWebBean.web_ImageView = web_ImageUrl;
                    mWebBean.web_TextView = web_Content;
                    mWebBeanList.add(mWebBean);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;

            default:
                try {
                    int pageNum = (int)type;
                    mDocument = Jsoup.connect(url + pageNum +".html").timeout(TIME_OUT).get();
                    mElements = mDocument.getElementsByClass("reads");
                    web_ImageUrl = mDocument.select("img").attr("src");
                    web_Content = mElements.text();
                    Log.i("TAG", "pageNum= " + pageNum);
                    Log.i("TAG", "web_ImageUrl= " + web_ImageUrl);
                    Log.i("TAG", "web_Content= " + web_Content);

                    WebBean mWebBean = new WebBean();
                    mWebBean.web_ImageView = web_ImageUrl;
                    mWebBean.web_TextView = web_Content;
                    mWebBeanList.add(mWebBean);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;

        }
    }

}
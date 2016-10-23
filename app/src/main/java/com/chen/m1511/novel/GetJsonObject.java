package com.chen.m1511.novel;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by m1511 on 2016/7/10.
 */
public class GetJsonObject {
    public static JSONObject getJsonObject(String url) {
        JSONObject mJsonObject = null;

        try {
            URL mURL = new URL(url);
            HttpURLConnection mHttpURLConnection = (HttpURLConnection) mURL.openConnection();
            mHttpURLConnection.setRequestMethod("GET");
            mHttpURLConnection.setReadTimeout(5000);
            mHttpURLConnection.setDoInput(true);

            StringBuffer mStringBuffer = new StringBuffer();
            String str;
            BufferedReader mBufferedReader = new BufferedReader(new InputStreamReader(mHttpURLConnection.getInputStream()));
            while ((str = mBufferedReader.readLine()) != null) {
                mStringBuffer.append(str);
            }

            mJsonObject = new JSONObject(String.valueOf(mStringBuffer));
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }
        return mJsonObject;
    }
}

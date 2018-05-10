package com.example.day02;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView listview;
    private String myurl="http://v.juhe.cn/toutiao/index?type=top&key=2f092bd9ce76c0257052d6d3c93c11b4";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listview = findViewById(R.id.listview);
        RaMaxt();
    }

    private void RaMaxt() {
        MyData myData = new MyData();
        myData.execute(myurl);
    }

    private class MyData extends AsyncTask<String,Integer,String > {
        @Override
        protected String doInBackground(String... strings) {
            try {
                URL url = new URL(strings[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setReadTimeout(5000);
                connection.setConnectTimeout(5000);
                if(connection.getResponseCode()==200){
                    InputStream stream = connection.getInputStream();
                    String s=StreamToString(stream);
                    return  s;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Gson gson = new Gson();
            Lei lei = gson.fromJson(s, Lei.class);
            List<Lei.ResultBean.DataBean> data = lei.getResult().getData();
            MyAdapter myAdapter = new MyAdapter(data,MainActivity.this);
            listview.setAdapter(myAdapter);
        }
    }


    private String StreamToString(InputStream stream) {
        StringBuilder stringBuilder = new StringBuilder();
        String str;
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        try {
            while((str=reader.readLine())!=null){
                stringBuilder.append(str);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  stringBuilder.toString();
    }
}

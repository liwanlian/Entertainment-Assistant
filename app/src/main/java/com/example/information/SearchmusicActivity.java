package com.example.information;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.information.Adapter.LrcInfoAdapter;
import com.example.information.Adapter.SearchmusicInfoAdapter;
import com.example.information.Bean.Bean_lrc;
import com.example.information.Bean.Bean_playmusic;
import com.example.information.Bean.Bean_rankinglist;
import com.example.information.Bean.Bean_searchmusic;
import com.example.information.Configure.exitsystem;
import com.google.gson.Gson;
import com.nostra13.universalimageloader.utils.L;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class SearchmusicActivity extends exitsystem {

   private EditText et_search;
   private TextView tv_sure;
   private RecyclerView recyclerView;
    List<Bean_searchmusic.SearchData> list_searchresult;
    LinearLayoutManager mlinear;
    private long exitTime=0;
    private Thread thread1;
    private ImageView search_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchmusic);
        initView();
    }
    private void initView(){
        et_search=(EditText)findViewById(R.id.et_searchcontent);
        tv_sure=(TextView)findViewById(R.id.searchmusic_tv);
        recyclerView=(RecyclerView)findViewById(R.id.search_music);
        search_back=(ImageView) findViewById(R.id.search_back);
        search_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(SearchmusicActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
        tv_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s_content=et_search.getText().toString();
                if (s_content==null){
                    Toast.makeText(SearchmusicActivity.this,"搜索的内容不能为空",Toast.LENGTH_LONG).show();
                    recyclerView.setVisibility(View.INVISIBLE);
                }
                else{
                    Searchmusic(s_content);
                }
            }
        });
    }
    //搜索歌曲
    private void Searchmusic(String songname){
        //http://tingapi.ting.baidu.com/v1/restserver/ting?format=json&calback=&from=webapp_music&method=baidu.ting.search.catalogSug&query=%E8%B5%B7%E9%A3%8E%E4%BA%86
       thread1= new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection;
                URL url = null;

                try {
                    url = new URL("http://tingapi.ting.baidu.com/v1/restserver/ting?format=json&calback=&from=webapp_music&method=baidu.ting.search.catalogSug&query="+songname);

                    connection = (HttpURLConnection) url.openConnection();
                    connection.setConnectTimeout(60*1000);
                    connection.setReadTimeout(60*1000);
                    connection.connect();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    String s;
                    s=reader.readLine();

                    Gson gson=new Gson();
                    Bean_searchmusic bean_searchmusic =new Bean_searchmusic();
                    bean_searchmusic =gson.fromJson(s,Bean_searchmusic.class);
                    Message msg=new Message();
                    if (bean_searchmusic.getError_code()==22000){
                     msg.what=1;
                     msg.obj=bean_searchmusic;
                    }
                    else{
                        msg.what=2;
                        msg.obj=null;
                    }
                    handler_searchmusic.sendMessage(msg);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
        });
       thread1.start();
    }
    Handler handler_searchmusic=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what==1){
                Bean_searchmusic bean_searchmusic=(Bean_searchmusic)msg.obj;
                list_searchresult=bean_searchmusic.getSong();
                if (list_searchresult.size()>0) {
                    mlinear = new LinearLayoutManager(SearchmusicActivity.this);
                    recyclerView.setLayoutManager(mlinear);
                    SearchmusicInfoAdapter searchmusicInfoAdapter=new SearchmusicInfoAdapter(list_searchresult);
                    recyclerView.setAdapter(searchmusicInfoAdapter);
                    recyclerView.setVisibility(View.VISIBLE);
                }
                else{
                    recyclerView.setVisibility(View.INVISIBLE);
                }
            }
            else{
                Toast.makeText(SearchmusicActivity.this,"请检查你当前的网络是否正常",Toast.LENGTH_LONG).show();
            }
        }
    };
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){
        if(keyCode==KeyEvent.KEYCODE_BACK){
            exit();
            return false;
        }
        return super.onKeyDown(keyCode,event);
    }

    private void exit() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            Toast.makeText(getApplicationContext(),
                    "再按一次退出程序", Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        }
        else{
            Intent intent = new Intent();
            intent.setAction(exitsystem.SYSTEM_EXIT);
            sendBroadcast(intent);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}

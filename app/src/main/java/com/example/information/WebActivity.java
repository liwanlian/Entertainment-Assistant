package com.example.information;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.information.Configure.exitsystem;


public class WebActivity extends exitsystem {
    private WebView webView;
    private final  static int SEARCH_MOHU =1;
    private ImageView web_back;
    String url;
    private long exitTime=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        webView = (WebView) findViewById(R.id.webView);
        web_back=(ImageView)findViewById(R.id.web_back);
    }
    @Override
    protected void onStart() {
        super.onStart();

        web_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(WebActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
        url = getIntent().getStringExtra("url");
        //显示JavaScript页面
        WebSettings settings = webView.getSettings();

        settings.setJavaScriptEnabled(true);
        settings.setSupportZoom(true); //支持缩放，默认为true。是下面那个的前提。
        settings.setBuiltInZoomControls(true); //设置内置的缩放控件。若为false，则该WebView不可缩放
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setUseWideViewPort(true);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        settings.setLoadWithOverviewMode(true);
        /*settings.setDisplayZoomControls(false);*/
        settings.setDefaultTextEncodingName("utf-8");
        webView.loadUrl(url);
    }
    //web视图
    private class webviewclient extends WebViewClient{
        public boolean shouldoveriverdurlloading(WebView webView,String url){
            webView.loadUrl(url);
            return true;
        }

    }
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
}

package com.example.information;



import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.information.Adapter.Adapter_news;

public class WebActivity extends AppCompatActivity {
    private WebView webView;
    private final  static int SEARCH_MOHU =1;
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        webView = (WebView) findViewById(R.id.webView);
    }
    @Override
    protected void onStart() {
        super.onStart();
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
}

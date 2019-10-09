package com.example.information.Fragment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;


import com.example.information.Adapter.Adapter_news;
import com.example.information.Bean.Bean_news;
import com.example.information.R;
import com.example.information.WebActivity;
import com.google.gson.Gson;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.BufferedSink;

public class NewsFragment extends Fragment {

    private ListView listView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private List<Bean_news.ResultBean.DataBean> list;
    private static final int UPNEWS_INSERT = 0;
    private int page =0,row =10;
    private static final int SELECT_REFLSH = 1;
    String  responseDate;
    Dialog mDialog;
    Bean_news listDataBean=new Bean_news() ;
    @SuppressLint("HandlerLeak")
    private Handler newsHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case UPNEWS_INSERT:
                    list = ((Bean_news) msg.obj).getResult().getData();

//                    Adapter_news adapter = new Adapter_news(getActivity().getApplicationContext(),list);
//                    listView.setAdapter(adapter);
//                    adapter.notifyDataSetChanged();
//                    String rr=(String)msg.obj;
                   System.out.println("kkkk="+getActivity().getApplicationContext());
                  //  Toast.makeText(getActivity().getApplicationContext(),"rr="+list.toString(),Toast.LENGTH_LONG).show();
                    break;
                case SELECT_REFLSH:
                    list =((Bean_news) msg.obj).getResult().getData();
//                    Adapter_news myTabAdapter = new Adapter_news(getActivity(),list);
//                    listView.setAdapter(myTabAdapter);
//                    myTabAdapter.notifyDataSetChanged();
//                    if (swipeRefreshLayout.isRefreshing()){
//                        swipeRefreshLayout.setRefreshing(false);//设置不刷新
//                 //       DialogUtil.closeDialog(mDialog);
//                    }
//                    String rr1=(String)msg.obj;
                    System.out.println("kkkk1="+list);
                 //   Toast.makeText(getActivity().getApplicationContext(),"rr1"+list.toString(),Toast.LENGTH_LONG).show();
                    break;
                default:
                    break;
            }
        }
    };
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_item,container,false);
        listView = (ListView) view.findViewById(R.id.listView);

        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh);
        return view;
    }
    @SuppressLint("HandlerLeak")
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        onAttach(getActivity());
        //获取传递的值getImportantForAccessibility
        Bundle bundle = getArguments();
        final String data = bundle.getString("name");
        getDataFromNet(data,UPNEWS_INSERT);
        //下拉刷新
//        swipeRefreshLayout.setColorSchemeResources(R.color.colorRed);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getDataFromNet(data,SELECT_REFLSH);
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //获取点击条目的路径，传值显示webview页面
                String url = list.get(position).getUrl();
                String uniquekey = list.get(position).getUniquekey();
                Intent intent = new Intent(getActivity(), WebActivity.class);
                intent.putExtra("url",url);
                intent.putExtra("uniquekey",uniquekey);
                startActivity(intent);
            }
        });
    }
    //异步加载数据   聚合平台的新闻接口的key 是 8a959eee5938e6fee92bf636f3a7a6c4
    private void getDataFromNet(final String data, final int flag){
        final String path = "http://v.juhe.cn/toutiao/index?type="+data+"&key=8a959eee5938e6fee92bf636f3a7a6c4";
        new Thread(new Runnable() {
            @Override
            public void run() {
              Bean_news resultbean=new Bean_news();
              OkHttpClient okHttpClient=new OkHttpClient();
//              //若是使用post方式请求 则需要将请求的内容写好
//                RequestBody requestBody=new FormBody().Builder()
//                        .add("name","kkk")
//                        .add("password","1234567");
                Request request = new Request.Builder()
                        .url(path)
                        .build();
                 try {
                     Response response=okHttpClient.newCall(request).execute();
                     String jsondata=response.body().string();
                     System.out.println("json="+jsondata);
                     Gson gson=new Gson();
                     Bean_news bean_news=gson.fromJson(jsondata,Bean_news.class);
                     if (bean_news.getError_code()==0){
                        resultbean=bean_news;
                        listDataBean=resultbean;
                     }//正常的返回数据
                     else{
                        resultbean=listDataBean;
                     }

                     Message msg = newsHandler.obtainMessage();
                     msg.what = flag;
                     msg.obj = resultbean;
                     newsHandler.sendMessage(msg);
                 }
                 catch (IOException e) {
                     e.printStackTrace();
                 }


            }

        }).start();
    }
}

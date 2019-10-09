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
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.information.Adapter.NewsInfoAdapter;
import com.example.information.Bean.Bean_news;
import com.example.information.R;
import com.example.information.WebActivity;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class BaseFragment extends Fragment {

    private SwipeRefreshLayout swipeRefreshLayout;
    private List<Bean_news.ResultBean.DataBean> list;
    private static final int UPNEWS_INSERT = 0;
    private int page =0,row =10;
    private static final int SELECT_REFLSH = 1;
    Thread thread1;

    /**
     * ProgressBar原型进度条 让用户知道正在加载数据
     */
    private ProgressBar progressBar;
    /**
     * RecyclerView控件显示多条新闻数据
     * 初始隐藏，加载到数据后才显示
     */
    private RecyclerView recyclerView;
    /**
     * TextView控件请求失败的时显示
     * 初始隐藏，加载到数据失败才显示，
     * 这3个控件对应3种不同的状态，所以只有页面只会出现一个
     */
    private TextView textView;

    Bean_news listDataBean=new Bean_news() ;
    @SuppressLint("HandlerLeak")
    private Handler newsHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case UPNEWS_INSERT:
                    list = ((Bean_news) msg.obj).getResult().getData();
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                    recyclerView.setLayoutManager(linearLayoutManager);
                    NewsInfoAdapter newsInfoAdapter = new NewsInfoAdapter(list);
                    recyclerView.setAdapter(newsInfoAdapter);
                    //一切正常，拿到数据后显示数据，同时也隐藏进度条
                    recyclerView.setVisibility(View.VISIBLE);
                    swipeRefreshLayout.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);

//                    Adapter_news adapter = new Adapter_news(getActivity().getApplicationContext(),list);
//                    listView.setAdapter(adapter);
//                    adapter.notifyDataSetChanged();
//                    String rr=(String)msg.obj;
                    System.out.println("kkkk="+getActivity().getApplicationContext());
                    //  Toast.makeText(getActivity().getApplicationContext(),"rr="+list.toString(),Toast.LENGTH_LONG).show();
//                    Thread.currentThread().stop();
                    break;
                case SELECT_REFLSH:
                    list =((Bean_news) msg.obj).getResult().getData();
                    LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(getActivity());
                    recyclerView.setLayoutManager(linearLayoutManager1);
                    NewsInfoAdapter newsInfoAdapter1 = new NewsInfoAdapter(list);
                    recyclerView.setAdapter(newsInfoAdapter1);
                    if (swipeRefreshLayout.isRefreshing()){
                        swipeRefreshLayout.setRefreshing(false);//设置不刷新
                    }
                    Toast.makeText(getActivity().getApplicationContext(),"刷新成功",Toast.LENGTH_LONG).show();
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
                  //  Thread.currentThread().stop();
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
        View view = inflater.inflate(R.layout.page,container,false);
        recyclerView = view.findViewById(R.id.rv_news);
        textView = view.findViewById(R.id.tv_result_error);
        progressBar = view.findViewById(R.id.pb_loading);
        swipeRefreshLayout=view.findViewById(R.id.swipe_refresh1);
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
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
               // getDataFromNet(data,SELECT_REFLSH);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getDataFromNet(data,SELECT_REFLSH);
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }, 3000);
            }
        });
    }
    //异步加载数据   聚合平台的新闻接口的key 是 8a959eee5938e6fee92bf636f3a7a6c4
    private void getDataFromNet(final String data, final int flag){
        final String path = "http://v.juhe.cn/toutiao/index?type="+data+"&key=8a959eee5938e6fee92bf636f3a7a6c4";
    thread1=    new Thread(new Runnable() {
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
                        Message msg = newsHandler.obtainMessage();
                        msg.what = flag;
                        msg.obj = resultbean;
                        newsHandler.sendMessage(msg);
                    }//正常的返回数据
                    else{
//                        if (listDataBean==null){
//                            progressBar.setVisibility(View.GONE);
//                            textView.setText("请求失败");
//                        }
//                        else
//                        {
//                            resultbean=listDataBean;
//                            Message msg = newsHandler.obtainMessage();
//                            msg.what = flag;
//                            msg.obj = resultbean;
//                            newsHandler.sendMessage(msg);
//                        }
                        Message mm=new Message();
                        mm.obj=bean_news.getError_code();
                        newsHandler1.sendMessage(mm);
                    }


                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    thread1.start();

    }
private Runnable mrunnable=new Runnable() {
    @Override
    public void run() {

    }
};
    @SuppressLint("HandlerLeak")
    private Handler newsHandler1 = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            int code=(int)msg.obj;
            progressBar.setVisibility(View.GONE);
            textView.setVisibility(View.VISIBLE);
            if (code==10012)
                textView.setText("超过每日可允许请求次数!");
            else
                textView.setText("请检查你当前的网络是否正常！");
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        thread1.destroy();
    }
}

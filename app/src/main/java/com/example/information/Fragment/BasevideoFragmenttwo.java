package com.example.information.Fragment;

import android.annotation.SuppressLint;
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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.information.Adapter.MusicInfoAdapter;
import com.example.information.Bean.Bean_rankinglist;
import com.example.information.R;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class BasevideoFragmenttwo extends Fragment {

    private SwipeRefreshLayout swipeRefreshLayout;
    private List<Bean_rankinglist.SongList> list;
    private static final int UPNEWS_INSERT = 0;
    private int page =0,row =10;
    private static final int SELECT_REFLSH = 1;
    private TextView textView;

    /**
     * ProgressBar原型进度条 让用户知道正在加载数据
     */
    private ProgressBar progressBar;

    private RecyclerView recyclerView;

    Bean_rankinglist listDataBean=new Bean_rankinglist() ;

    private Handler newsHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case UPNEWS_INSERT:
                    list = ((Bean_rankinglist) msg.obj).getSong_list();
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                    recyclerView.setLayoutManager(linearLayoutManager);
                    MusicInfoAdapter musicInfoAdapter = new MusicInfoAdapter(list);
                    recyclerView.setAdapter(musicInfoAdapter);
                    //一切正常，拿到数据后显示数据，同时也隐藏进度条
                    recyclerView.setVisibility(View.VISIBLE);
                    swipeRefreshLayout.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                    for (int i=0;i<list.size();i++){
                        System.out.println("ids="+list.get(i).getSong_id());
                    }
//                    Adapter_news adapter = new Adapter_news(getActivity().getApplicationContext(),list);
//                    listView.setAdapter(adapter);
//                    adapter.notifyDataSetChanged();
//                    String rr=(String)msg.obj;
                    System.out.println("kkkk="+getActivity().getApplicationContext());
                    //  Toast.makeText(getActivity().getApplicationContext(),"rr="+list.toString(),Toast.LENGTH_LONG).show();

                    break;
                case SELECT_REFLSH:
                    list = ((Bean_rankinglist) msg.obj).getSong_list();
                    LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(getActivity());
                    recyclerView.setLayoutManager(linearLayoutManager1);
                    MusicInfoAdapter musicInfoAdapter1 = new MusicInfoAdapter(list);
                    recyclerView.setAdapter(musicInfoAdapter1);
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
        View view = inflater.inflate(R.layout.page_music,container,false);
        recyclerView = view.findViewById(R.id.rv_music);
        progressBar = view.findViewById(R.id.pb_loadingmusic);
        swipeRefreshLayout=view.findViewById(R.id.swipe_refreshmusic);
        textView=view.findViewById(R.id.tv_result_errormusic);
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
//http://tingapi.ting.baidu.com/v1/restserver/ting?from=android&version=4.9.2.0&method=baidu.ting.billboard.billList&format=json&&type=1&size=10&offset=0
    //http://music.baidu.com/data/music/fmlink?rate=320&songIds=242078437&type=&callback=&_t=1468380564513&format=json
    private void getDataFromNet(final String data, final int flag){
//        final String path = "http://tingapi.ting.baidu.com/v1/restserver/ting?format=json&calback=&from=webapp_music&method=baidu.ting.billboard.billList&type=1&size=10&offset=0";
//        new Thread(new Runnable() {
//            @Override
//            public void run() {

//                OkHttpClient okHttpClient=new OkHttpClient();
////              //若是使用post方式请求 则需要将请求的内容写好
////                RequestBody requestBody=new FormBody().Builder()
////                        .add("name","kkk")
////                        .add("password","1234567");
//                Request request = new Request.Builder()
//                        .url(path)
//                        .build();
//                try {
//                    Response response=okHttpClient.newCall(request).execute();
//                    String jsondata=response.body().string();
//                    System.out.println("json="+jsondata);

////
//
//                }
//                catch (IOException e) {
//                    e.printStackTrace();
//                }
//
//
//            }
//
//        }).start();
       new Thread(new Runnable() {
           @Override
           public void run() {
               Bean_rankinglist resultbean=new Bean_rankinglist();
               HttpURLConnection connection;
               URL url = null;
               try {
                url = new URL("http://tingapi.ting.baidu.com/v1/restserver/ting?format=json&calback=&from=webapp_music&method=baidu.ting.billboard.billList&type="+data+"&size=30&offset=0");

               //    url = new URL("http://music.baidu.com/data/music/fmlink?rate=320&songIds=242078437&type=&callback=&_t=&format=json");
                   connection = (HttpURLConnection) url.openConnection();
                   connection.setConnectTimeout(60*1000);
                   connection.setReadTimeout(60*1000);
                   connection.connect();
                   BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                   String s;
                   s=reader.readLine();

                   Gson gson=new Gson();
                    Bean_rankinglist bean_news =new Bean_rankinglist();
                   bean_news =gson.fromJson(s,Bean_rankinglist.class);
                    if (bean_news.getError_code()==22000){
                        resultbean=bean_news;
                        listDataBean=new Bean_rankinglist();
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
               } catch (MalformedURLException e) {
                   e.printStackTrace();

//                   Gson gson=new Gson();
//                   Bean_playmusic bean_playmusic=gson.fromJson(s,Bean_playmusic.class);
//                  if (bean_playmusic.getErrorCode()==22000){
//                      Message mk=new Message();
//                      mk.obj=bean_playmusic.getData().getSongList().get(0).getSongLink();
//                      System.out.println("link="+mk.obj);
//                      newsHandler1.sendMessage(mk);
//                  }
               } catch (IOException e) {
                   e.printStackTrace();
               }
         }
       }).start();


    }
    @SuppressLint("HandlerLeak")
    private Handler newsHandler1 = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            progressBar.setVisibility(View.GONE);
               // textView.setText("请检查你当前的网络是否正常！");
            String rrr=(String)msg.obj;
            Toast.makeText(getActivity().getApplicationContext(),rrr,Toast.LENGTH_LONG).show();
        }
    };
}

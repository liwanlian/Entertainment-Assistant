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
import com.example.information.Adapter.VideoInfoAdapter1;
import com.example.information.Adapter.VideoInfoAdapter2;
import com.example.information.Bean.Bean_new;
import com.example.information.Bean.Bean_rankinglist;
import com.example.information.Bean.Bean_ubox;
import com.example.information.Bean.Bean_video;
import com.example.information.Bean.Bean_weekly;
import com.example.information.R;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BasevideoFragment extends Fragment {

    private SwipeRefreshLayout swipeRefreshLayout;
    private List<Bean_video.Subjectdata> list;
    private List<Bean_video.Subjectdata> list1;
    private List<Bean_video.Subjectdata> list2;
    private List<Bean_ubox.subjectsdatauxbox> list4;


    private List<Map<String,String>> list_uxbox;
    private static final int UPNEWS_INSERT = 0;

    private static final int SELECT_REFLSH = 1;
    private TextView textView;
       String videotype;

    /**
     * ProgressBar原型进度条 让用户知道正在加载数据
     */
    private ProgressBar progressBar;

    private RecyclerView recyclerView;

    Bean_video listDataBean=new Bean_video() ;
    Bean_new  listDataBean2=new Bean_new();
    Bean_weekly listDataBean3 =new Bean_weekly();


    private Handler newsHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case UPNEWS_INSERT:
                    if (videotype.equals("正在热映") || videotype.equals("即将上映") || videotype.equals("top100")){{
                        list = ((Bean_video) msg.obj).getSubjects();
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                        recyclerView.setLayoutManager(linearLayoutManager);
                        VideoInfoAdapter1 videoInfoAdapter1=new VideoInfoAdapter1(list);
                        recyclerView.setAdapter(videoInfoAdapter1);
                    }}
                    else if (videotype.equals("新片榜")){
                        list1=((Bean_new)msg.obj).getSubjects();
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                        recyclerView.setLayoutManager(linearLayoutManager);
                        VideoInfoAdapter1 videoInfoAdapter1=new VideoInfoAdapter1(list1);
                        recyclerView.setAdapter(videoInfoAdapter1);
                    }
                    else if (videotype.equals("口碑榜")){
                        list2=new ArrayList<>();
                        List<Bean_weekly.subjectsdata> list3=((Bean_weekly)msg.obj).getSubjects();
                        for (int i=0;i<list3.size();i++)
                        {
                            list2.add(list3.get(i).getSubject());
                        }
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                        recyclerView.setLayoutManager(linearLayoutManager);
                        VideoInfoAdapter1 videoInfoAdapter1=new VideoInfoAdapter1(list2);
                        recyclerView.setAdapter(videoInfoAdapter1);
                    }
                   else if (videotype.equals("北美榜")){
                       List<Bean_ubox.subjectsdatauxbox> list_ub=new ArrayList<>();
                        list_ub=((Bean_ubox)msg.obj).getSubjects();
                        list4=new ArrayList<>();
                        for (int k=0;k<list_ub.size();k++){
                          list4.add(list_ub.get(k));
                        }
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                        recyclerView.setLayoutManager(linearLayoutManager);
                        VideoInfoAdapter2 videoInfoAdapter2=new VideoInfoAdapter2(list4);
                        recyclerView.setAdapter(videoInfoAdapter2);
                    }

                    //一切正常，拿到数据后显示数据，同时也隐藏进度条
                    recyclerView.setVisibility(View.VISIBLE);
                    swipeRefreshLayout.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);

                    break;
                case SELECT_REFLSH:
                    if (videotype.equals("正在热映") || videotype.equals("即将上映") || videotype.equals("top100")){{
                        list = ((Bean_video) msg.obj).getSubjects();
                        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(getActivity());
                        recyclerView.setLayoutManager(linearLayoutManager1);
                        VideoInfoAdapter1 videoInfoAdapter1=new VideoInfoAdapter1(list);
                        recyclerView.setAdapter(videoInfoAdapter1);
                    }}
                    else if (videotype.equals("新片榜")){
                        list1=((Bean_new)msg.obj).getSubjects();
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                        recyclerView.setLayoutManager(linearLayoutManager);
                        VideoInfoAdapter1 videoInfoAdapter1=new VideoInfoAdapter1(list1);
                        recyclerView.setAdapter(videoInfoAdapter1);
                    }
                    else if (videotype.equals("口碑榜")){
                        list2=new ArrayList<>();
                        List<Bean_weekly.subjectsdata> list3=((Bean_weekly)msg.obj).getSubjects();
                        for (int i=0;i<list3.size();i++)
                        {
                            list2.add(list3.get(i).getSubject());
                        }
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                        recyclerView.setLayoutManager(linearLayoutManager);
                        VideoInfoAdapter1 videoInfoAdapter1=new VideoInfoAdapter1(list2);
                        recyclerView.setAdapter(videoInfoAdapter1);
                    }
                    else if (videotype.equals("北美榜")){
                        List<Bean_ubox.subjectsdatauxbox> list_ub=new ArrayList<>();
                        list_ub=((Bean_ubox)msg.obj).getSubjects();
                        list4=new ArrayList<>();
                        for (int k=0;k<list_ub.size();k++){
                            list4.add(list_ub.get(k));
                        }
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                        recyclerView.setLayoutManager(linearLayoutManager);
                        VideoInfoAdapter2 videoInfoAdapter2=new VideoInfoAdapter2(list4);
                        recyclerView.setAdapter(videoInfoAdapter2);
                    }

                    if (swipeRefreshLayout.isRefreshing()){
                        swipeRefreshLayout.setRefreshing(false);//设置不刷新
                    }
                    Toast.makeText(getActivity().getApplicationContext(),"刷新成功",Toast.LENGTH_LONG).show();

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
        View view = inflater.inflate(R.layout.page_videoone,container,false);
        recyclerView = view.findViewById(R.id.rv_video);
        progressBar = view.findViewById(R.id.pb_loadingvideo);
        swipeRefreshLayout=view.findViewById(R.id.swipe_refreshvideo);
        textView=view.findViewById(R.id.tv_result_errorvideo);
        return view;
    }
    @SuppressLint("HandlerLeak")
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        onAttach(getActivity());
        //获取传递的值getImportantForAccessibility
        Bundle bundle = getArguments();
        final String data = bundle.getString("url");
          videotype=bundle.getString("videotype");
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

    private void getDataFromNet(final String data, final int flag){

       new Thread(new Runnable() {
           @Override
           public void run() {
               HttpURLConnection connection;
               URL url = null;
               try {
                url = new URL(data);

                   connection = (HttpURLConnection) url.openConnection();
                   connection.setConnectTimeout(60*1000);
                   connection.setReadTimeout(60*1000);
                   connection.connect();
                   BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                   String s;
                   s=reader.readLine();
                   Message msg=new Message();
                   msg.obj=s;
                   handler_data.sendMessage(msg);
               } catch (MalformedURLException e) {
                   e.printStackTrace();

               } catch (IOException e) {
                   e.printStackTrace();
               }
         }
       }).start();


    }
    //处理访问api之后的数据
    Handler handler_data=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String resultjsondata=(String)msg.obj;//
            if (videotype.equals("正在热映") || videotype.equals("即将上映") || videotype.equals("top100")){
                Gson gson=new Gson();
                Bean_video bean_video=new Bean_video();
                bean_video=gson.fromJson(resultjsondata,Bean_video.class);
                listDataBean=new Bean_video();
                listDataBean=bean_video;
                  Message message = newsHandler.obtainMessage();
                message.what = UPNEWS_INSERT;
                message.obj = bean_video;
                    newsHandler.sendMessage(message);
            }
            else if (videotype.equals("新片榜")){
                Gson gson=new Gson();
                Bean_new bean_new=new Bean_new();
                bean_new=gson.fromJson(resultjsondata,Bean_new.class);
                listDataBean2=new Bean_new();
                listDataBean2=bean_new;
                Message message = newsHandler.obtainMessage();
                message.what = UPNEWS_INSERT;
                message.obj = bean_new;
                newsHandler.sendMessage(message);
            }
            else if (videotype.equals("口碑榜")){
                Gson gson=new Gson();
                Bean_weekly bean_weekly=new Bean_weekly();
                bean_weekly=gson.fromJson(resultjsondata,Bean_weekly.class);
                listDataBean3=new Bean_weekly();
                listDataBean3=bean_weekly;
                Message message = newsHandler.obtainMessage();
                message.what = UPNEWS_INSERT;
                message.obj = bean_weekly;
                newsHandler.sendMessage(message);
            }

            else if (videotype.equals("北美榜")){

                Gson gson=new Gson();
                Bean_ubox bean_ubox=new Bean_ubox();
                bean_ubox=gson.fromJson(resultjsondata,Bean_ubox.class);
                Message message = newsHandler.obtainMessage();
                message.what = UPNEWS_INSERT;
                message.obj = bean_ubox;
                newsHandler.sendMessage(message);
            }

        }
    };
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

package com.example.information;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewParent;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.information.Bean.Bean_video;
import com.example.information.Bean.Bean_videodetail;
import com.example.information.Configure.exitsystem;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class PlayvideooneActivity extends exitsystem {
//界面控件
    ImageView videoplayfirst_back;
    ImageView videoplayfirst_image;
    TextView videoplayfirst_title,videoplayfirst_type,videoplayfirst_year,videoplayfirst_daoyan,videoplayfirst_zhuyan,videoplayfirst_pinfen,videoplayfirst_pubtime;
   TextView videoplayfirst_lantime;
    TextView videoplayfirst_summary;
    LinearLayout linearone,linearonez;//导演
    LinearLayout lineartwo,lineartwoz;//主演
    LinearLayout linearthree,linearthreez;//预告片
    LinearLayout linearfour;//剧照

    //传送过来的videoid
    private String videoid;
    private String photourl;
    private Bean_videodetail bean_videodetail;
    private long exitTime=0;
    private Thread thread1,thread2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playvideoone);

        initView();

    }
    @Override
    protected void onStart() {
        super.onStart();
        initData();
    }
    private void initView(){
        videoplayfirst_back=(ImageView)findViewById(R.id.videoplayfirst_back);
        videoplayfirst_image=(ImageView)findViewById(R.id.videoplayfirst_image);
        videoplayfirst_title=(TextView)findViewById(R.id.videoplayfirst_title);
        videoplayfirst_type=(TextView)findViewById(R.id.videoplayfirst_type);
        videoplayfirst_year=(TextView)findViewById(R.id.videoplayfirst_year);
        videoplayfirst_daoyan=(TextView)findViewById(R.id.videoplayfirst_daoyan);
        videoplayfirst_zhuyan=(TextView)findViewById(R.id.videoplayfirst_zhuyan);
        videoplayfirst_pinfen=(TextView)findViewById(R.id.videoplayfirst_pinfen);
        videoplayfirst_pubtime=(TextView)findViewById(R.id.videoplayfirst_pubtime);
        videoplayfirst_summary=(TextView)findViewById(R.id.videoplayfirst_summary);
        videoplayfirst_lantime=(TextView)findViewById(R.id.videoplayfirst_lantime);
        linearone=(LinearLayout)findViewById(R.id.linearone);
        linearonez=(LinearLayout)findViewById(R.id.linearonez);
        lineartwo=(LinearLayout)findViewById(R.id.lineartwo);
        lineartwoz=(LinearLayout)findViewById(R.id.lineartwoz);
        linearthree=(LinearLayout)findViewById(R.id.linearthree);
        linearthreez=(LinearLayout)findViewById(R.id.linearthreez);
        linearfour=(LinearLayout)findViewById(R.id.linearfour);

        videoplayfirst_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(PlayvideooneActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }
    private void initData(){
        Bundle bundle=getIntent().getBundleExtra("bundle");
        videoid=bundle.getString("videoid");
        photourl=bundle.getString("photourl");
        subjectfromid(videoid);
    }
    //将传送的id访问对应的api
    //http://api.douban.com/v2/movie/subject/27119724?apikey=0b2bdeda43b5688921839c8ecb20399b&city=%E5%8C%97%E4%BA%AC&client=&udid=
    private void subjectfromid(String id){
       thread1= new Thread(new Runnable() {
            @Override
            public void run() {
                String path="http://api.douban.com/v2/movie/subject/"+id+"?apikey=0b2bdeda43b5688921839c8ecb20399b&city=%E5%8C%97%E4%BA%AC&client=&udid=";
                HttpURLConnection connection;
                URL url;
                try {
                    url=new URL(path);
                    connection=(HttpURLConnection)url.openConnection();
                    connection.setConnectTimeout(60*1000);
                    connection.setReadTimeout(60*1000);
                    connection.connect();
                    BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    String s=bufferedReader.readLine();
                    Message msg=new Message();
                    msg.obj=s;
                    handler_subject.sendMessage(msg);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
       thread1.start();
    }
    Handler handler_subject=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String resultdata=(String)msg.obj;
            Gson gson=new Gson();
            bean_videodetail=new Bean_videodetail();
            bean_videodetail=gson.fromJson(resultdata,Bean_videodetail.class);
            //数据渲染
            videoplayfirst_title.setText(bean_videodetail.getTitle());

            String[] videotypes=bean_videodetail.getGenres();
            StringBuffer sb_type=new StringBuffer();
            for (int i=0;i<videotypes.length;i++){
                sb_type.append(videotypes[i]);
                if (i==videotypes.length-1);
                else
                    sb_type.append(",");
            }
            String string_types=sb_type.toString();
            videoplayfirst_type.setText("类型："+string_types);
            videoplayfirst_year.setText("年份："+bean_videodetail.getYear());

            List<Bean_video.Subjectdata.castsdata> directora=bean_videodetail.getDirectors();
            StringBuffer sb_director=new StringBuffer();
            sb_director.append("导演：");
            for (int i=0;i<directora.size();i++){
                sb_director.append(directora.get(i).getName());
                if (i==directora.size()-1);
                else
                    sb_director.append(",");
            }
            String string_director=sb_director.toString();
            videoplayfirst_daoyan.setText(string_director);

            List<Bean_video.Subjectdata.castsdata> castsda=bean_videodetail.getCasts();
            StringBuffer sb_castas=new StringBuffer();
            sb_castas.append("主演：");
            for (int i=0;i<castsda.size();i++){
                sb_castas.append(castsda.get(i).getName());
                if (i==castsda.size()-1);
                else
                    sb_castas.append(",");
            }
            String string_cast=sb_castas.toString();
            videoplayfirst_zhuyan.setText(string_cast);

            String[] lang=bean_videodetail.getLanguages();
            String[] durction=bean_videodetail.getDurations();
            StringBuffer sb_lanyu=new StringBuffer();
            sb_lanyu.append("语言与片长：");
            for(int i=0;i<lang.length;i++){
                sb_lanyu.append(lang[i]);
                if (i==lang.length-1);
                else
                    sb_lanyu.append(",");
            }
            sb_lanyu.append("[");
            for (int y=0;y<durction.length;y++){
                sb_lanyu.append(durction[y]);
                if (y==durction.length-1);
                else
                    sb_lanyu.append("/");
            }
            sb_lanyu.append("]");
            String lanyu=sb_lanyu.toString();
            videoplayfirst_lantime.setText(lanyu);
            Glide.with(PlayvideooneActivity.this).load(photourl).into(videoplayfirst_image);
            videoplayfirst_pinfen.setText("评分："+String.valueOf(bean_videodetail.getRating().getAverage()));
            String[] pubdates=bean_videodetail.getPubdates();
            videoplayfirst_pubtime.setText("上映时间："+pubdates[0]);
            videoplayfirst_summary.setText("主要简介如下："+"\n"+"  "+bean_videodetail.getSummary());
            addview_zhudao(bean_videodetail);
        }
    };
    //动态添加view
    private void addview_zhudao(Bean_videodetail bean){
        //动态添加导演展示

      List<Bean_video.Subjectdata.castsdata> directors =bean.getDirectors();
      for (int i=0;i<directors.size();i++){
          View view1 = View.inflate(PlayvideooneActivity.this, R.layout.zhuyanitem, null);
          System.out.println("view1"+view1);
          ImageView zhuyan_avator=(ImageView)view1.findViewById(R.id.zhuyanavtator);
          TextView zhuyan_name=(TextView)view1.findViewById(R.id.zhuyanname);
            String phuri=null;
            if(directors.get(i).getAvatars()==null){

            }
           else{
                if (directors.get(i).getAvatars().getSmall()==null){
                    if (directors.get(i).getAvatars().getMedium()==null){
                        if (directors.get(i).getAvatars().getLarge()==null);
                        else
                            phuri=directors.get(i).getAvatars().getLarge();
                    }
                    else
                        phuri=directors.get(i).getAvatars().getMedium();
                }
                else{
                    phuri=directors.get(i).getAvatars().getSmall();
                }

            }
           if (phuri!=null)
           {
               System.out.println("lll="+phuri);
              Glide.with(PlayvideooneActivity.this).load(phuri).into(zhuyan_avator);
           }
           else;
            StringBuffer stringBuffer=new StringBuffer();
            stringBuffer.append(bean.getDirectors().get(i).getName()).append("(").append(bean.getDirectors().get(i).getName_en()).append(")");
            String ss=stringBuffer.toString();
            zhuyan_name.setText(ss);

//                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
//                        LinearLayout.LayoutParams.WRAP_CONTENT);
//                lp.setMargins(18,0,0,0);
//                view1.setLayoutParams(lp);
//                ViewParent viewParent =linearone.getParent();
//                System.out.println("pa="+viewParent);
//               // linearone.addView(view1);
            if (i<4)
          linearone.addView(view1);
            else
                linearonez.addView(view1);
        }

      //动态添加主演部分
        List<Bean_video.Subjectdata.castsdata> casets=bean.getCasts();
        for (int i=0;i<casets.size();i++){
            View view1 = View.inflate(PlayvideooneActivity.this, R.layout.zhuyanitem, null);

            ImageView zhuyan_avator=(ImageView)view1.findViewById(R.id.zhuyanavtator);
            TextView zhuyan_name=(TextView)view1.findViewById(R.id.zhuyanname);
            TextView zhuyansummary=(TextView)view1.findViewById(R.id.zhuyansummary);
            String phuri=null;
            if (casets.get(i).getAvatars().getSmall()==null){
                if (casets.get(i).getAvatars().getMedium()==null){
                    if (casets.get(i).getAvatars().getLarge()==null);
                    else
                        phuri=casets.get(i).getAvatars().getLarge();
                }
                else
                    phuri=casets.get(i).getAvatars().getMedium();
            }
            else{
                phuri=casets.get(i).getAvatars().getSmall();
            }

            if (phuri!=null)
            {
                System.out.println("lll="+phuri);
                Glide.with(PlayvideooneActivity.this).load(phuri).into(zhuyan_avator);
            }
            else;
            StringBuffer stringBuffer=new StringBuffer();
            stringBuffer.append(bean.getCasts().get(i).getName()).append("(").append(bean.getCasts().get(i).getName_en()).append(")");
            String ss=stringBuffer.toString();
            zhuyan_name.setText(ss);

            if (i<4)
                lineartwo.addView(view1);
            else
                lineartwoz.addView(view1);
        }
        //剧照  iv_juzhao
        List<Bean_videodetail.photodata> photos=bean.getPhotos();
        for (int i=0;i<photos.size();i++){
            View view2=View.inflate(PlayvideooneActivity.this,R.layout.juzhaoitem,null);
            ImageView juzhao=(ImageView)view2.findViewById(R.id.iv_juzhao);
            Glide.with(this).load(photos.get(i).getThumb()).into(juzhao);
            linearfour.addView(view2);
        }

        //预告片 tv_yuyao
        String[] boourl=bean.getBlooper_urls();
        String[] trails=bean.getTrailer_urls();
        int num=boourl.length+trails.length;
        String[] jugaourl=new String[num];
        for (int i=0;i<boourl.length;i++){
            jugaourl[i]=boourl[i];
        }
        int k=boourl.length;

        for (int y=0;y<trails.length;y++){
            jugaourl[k+y]=trails[y];
        }
        System.out.println("changdu="+num);
        int posi=0;
        for (int z=0;z<num;z++){
            posi=z;
            View view3=View.inflate(PlayvideooneActivity.this,R.layout.yugaoitem,null);
            TextView tv_yuyao=(TextView)view3.findViewById(R.id.tv_yuyao);
            tv_yuyao.setText("预告"+String.valueOf(z+1));

            int finalPosi = posi;
            tv_yuyao.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (jugaourl[finalPosi]==null){
                       Toast.makeText(PlayvideooneActivity.this,"当前片段不能播放",Toast.LENGTH_LONG).show();
                    }
                    else{
                        Bundle bundle=new Bundle();
                        bundle.putString("videourl",jugaourl[finalPosi]);
                        bundle.putString("videoid",videoid);
                        bundle.putString("photourl",photourl);
                        Intent intent=new Intent(PlayvideooneActivity.this,PlayvideotwoActivity.class);
                        intent.putExtra("bundle",bundle);
                        startActivity(intent);
                    }
                }
            });
            if (z<5){
                if(z==0);
                else{
                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT);
                    lp.setMargins(23,0,0,0);
                    view3.setLayoutParams(lp);
                }
                linearthree.addView(view3);
            }
            else{
                if (z==5);
                else{
                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT);
                    lp.setMargins(23,0,0,0);
                    view3.setLayoutParams(lp);
                }
                linearthreez.addView(view3);
            }

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

package com.example.information;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.example.information.Adapter.Adapter_historyitem;
import com.example.information.Adapter.LrcInfoAdapter;
import com.example.information.Bean.Bean_lrc;
import com.example.information.Bean.Bean_playmusic;
import com.example.information.Configure.DBManager;
import com.example.information.Configure.exitsystem;
import com.google.gson.Gson;
import com.google.gson.JsonArray;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
import java.util.Timer;
import java.util.TimerTask;

public class PlayMusicActivity extends exitsystem implements View.OnClickListener  {

    //界面控件
    Button bt_back;
  ImageView pm_background;
    RecyclerView lrc_recycle;
    Button bt_pre,bt_onff,bt_next,bt_musiclist;
    SeekBar seekBar;
    TextView pm_timing,pm_totaltime;
    ImageView iv_exchange;
    //传递过来的id值
    String songsid;
    int durction;
    private MediaPlayer mediaPlayer;
    String tseturi;
    //暂停的标志
    boolean pauseflag=false;
    private Timer timer;
    List<String> list_lrc;
    int pause_imageview;
  Thread thread1;
  Thread thread2;
    LinearLayoutManager mlinear;
    //记录歌词显示在第几毫秒
    long[] long_lrc;
    int pisition_flag=0;
    private DBManager db;
    //当前停留的这界面的歌曲能否能播的标志
    boolean flag_playsong=false;
    int onclickposition=0;//历史记录按下的标记
    String[] ids2;
    boolean flag_lrc=false;
    AlertDialog   dialog;
    private long exitTime=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_music);
        initView();
        db=new DBManager(this);
    }
    private void initView(){
       bt_back=(Button)findViewById(R.id.bt_back);
       pm_background=(ImageView)findViewById(R.id.pm_background);
        iv_exchange=(ImageView)findViewById(R.id.iv_exchange);
        bt_pre=(Button)findViewById(R.id.bt_pre);
        bt_onff=(Button)findViewById(R.id.bt_onff);
        bt_next=(Button)findViewById(R.id.bt_next);
        bt_musiclist=(Button)findViewById(R.id.bt_musiclist);
        seekBar=(SeekBar)findViewById(R.id.pm_seekbar);
        pm_timing=(TextView)findViewById(R.id.pm_timing);
        pm_totaltime=(TextView)findViewById(R.id.pm_totaltime);
        lrc_recycle=(RecyclerView)findViewById(R.id.lrc_music);
        mediaPlayer=new MediaPlayer();
        bt_back.setOnClickListener(this);
        bt_onff.setOnClickListener(this);
        pm_background.setOnClickListener(this);


        Glide.with(PlayMusicActivity.this).load(R.mipmap.labixiaoxin).apply(RequestOptions.circleCropTransform()).into(iv_exchange);
        iv_exchange.setOnClickListener(this);
        bt_pre.setOnClickListener(this);
        bt_next.setOnClickListener(this);
        bt_musiclist.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.bt_back:
                    mediaPlayer.stop();
                    mediaPlayer.release();
                    musicsongtwo.removeCallbacks(runnable);
                Intent intent=new Intent(PlayMusicActivity.this,MainActivity.class);
                startActivity(intent);
                break;
            case R.id.bt_onff:
                if (mediaPlayer.isPlaying()){
                    mediaPlayer.pause();
                    bt_onff.setBackgroundResource(R.drawable.stop);
                    pauseflag=true;
                }
               else{
                    bt_onff.setBackgroundResource(R.drawable.musicing);
                    mediaPlayer.start();
                    pauseflag=false;
                }
                break;
            case R.id.iv_exchange:
                pause_imageview++;
                if (pause_imageview%2==0){
                    pm_background.setVisibility(View.VISIBLE);
                    lrc_recycle.setVisibility(View.INVISIBLE);
                }
                else{
                    pm_background.setVisibility(View.INVISIBLE);
                    if (flag_lrc)
                    lrc_recycle.setVisibility(View.VISIBLE);
                    else
                        lrc_recycle.setVisibility(View.INVISIBLE);
                }
                break;
            case R.id.bt_pre:
                String[] ids=db.receiveids();
                if (flag_playsong)
                {
                    mediaPlayer.stop();
                    mediaPlayer.release();
                    mediaPlayer=new MediaPlayer();
                    int id=db.returnid(songsid);
                    if (id==1){
                        id=db.countid()-2;
                    }
                    else{
                        id=id-2;
                    }
                    String cid=ids[id];
                    String songlink=db.returnsonglink(cid);
                    System.out.println("前一首="+songlink+"  id="+cid);
                    String picuri=db.returnuri(id+1);
                    List<Map<String,Object>> maps=db.getlist_songart();
                    Map<String,Object> map=maps.get(id);
                    String songname=map.get("songname").toString();
                    String artist=map.get("songartist").toString();
                    playmusic(cid,songlink,picuri,songname,artist);

                    thread_recevielrc(cid);
//                    thread2.start();
                }
                else{
                    String cid=ids[0];
                    int id=db.returnid(cid);
                    String songlink=db.returnsonglink(cid);
                    String picuri=db.returnuri(id+1);
                    List<Map<String,Object>> maps=db.getlist_songart();
                    Map<String,Object> map=maps.get(id);
                    String songname=map.get("songname").toString();
                    String artist=map.get("songartist").toString();
                    playmusic(cid,songlink,picuri,songname,artist);

                    thread_recevielrc(cid);

                }
                flag_playsong=true;
                break;//前一首
            case R.id.bt_next:
                String[] ids1=db.receiveids();
                if (flag_playsong)
                {
                    mediaPlayer.stop();
                    mediaPlayer.release();
                    mediaPlayer=new MediaPlayer();
                    int id=db.returnid(songsid);
                    if (id==db.countid()-1){
                        id=0;
                    }
                    else{
                        id=id;
                    }
                    String cid=ids1[id];
                    String songlink=db.returnsonglink(cid);
                    String picuri=db.returnuri(id+1);
                    List<Map<String,Object>> maps=db.getlist_songart();
                    Map<String,Object> map=maps.get(id);
                    String songname=map.get("songname").toString();
                    String artist=map.get("songartist").toString();
                    playmusic(cid,songlink,picuri,songname,artist);

                    thread_recevielrc(cid);

                }
                else{
                    String cid=ids1[0];
                    int id=db.returnid(cid);
                    String songlink=db.returnsonglink(cid);
                    String picuri=db.returnuri(id+1);
                    List<Map<String,Object>> maps=db.getlist_songart();
                    Map<String,Object> map=maps.get(id);
                    String songname=map.get("songname").toString();
                    String artist=map.get("songartist").toString();


                    playmusic(cid,songlink,picuri,songname,artist);
                    thread_recevielrc(cid);
                }
                flag_playsong=true;
                break;//后一首
            case  R.id.bt_musiclist:
                ids2=db.receiveids();
                for (int i=0;i<ids2.length;i++){
                    System.out.println(i+"="+ids2[i]);
            }
                List<String> history=new ArrayList<>();
                List<Map<String,Object>> contets=db.getlist_songart();
                for (int i=0;i<contets.size();i++){
                    Map<String,Object> mapitem=new HashMap<>();
                    mapitem=contets.get(i);
                    StringBuffer sb=new StringBuffer();
                    sb.append(mapitem.get("songname").toString()).append("----").append(mapitem.get("songartist").toString());
                    String ss=sb.toString();
                    history.add(ss);
                }


                LayoutInflater inflater = getLayoutInflater();
                View view1 = inflater.inflate(R.layout.listview_history, null);
                ListView listView=(ListView)view1.findViewById(R.id.lv_history) ;
                Adapter_historyitem  adapter_historyitem=new Adapter_historyitem(PlayMusicActivity.this,R.layout.listview_historyitem,history);
                listView.setAdapter(adapter_historyitem);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                        onclickposition=i;
                        mediaPlayer.stop();
                        mediaPlayer.release();
                        mediaPlayer=new MediaPlayer();
                        String cid=ids2[onclickposition];
                        int id=db.returnid(ids2[onclickposition]);
                        String songlink=db.returnsonglink(cid);
                        String picuri=db.returnuri(id);
                        List<Map<String,Object>> maps=db.getlist_songart();
                        Map<String,Object> map=maps.get(onclickposition);
                        String songname=map.get("songname").toString();
                        String artist=map.get("songartist").toString();
                        playmusic(cid,songlink,picuri,songname,artist);

                        thread_recevielrc(cid);
                        dialog.dismiss();
                    }
                });
                AlertDialog.Builder builder=   new AlertDialog.Builder(PlayMusicActivity.this)
                        .setTitle("历史播放记录如下：")
                        .setView(view1)
                       ;
                   dialog=builder.create();
                Window window = dialog.getWindow();
                window.setGravity(Gravity.BOTTOM);
                WindowManager m = getWindowManager();
                Display d = m.getDefaultDisplay(); //为获取屏幕宽、高
                WindowManager.LayoutParams p = dialog.getWindow().getAttributes(); //获取对话框当前的参数值
                p.width = d.getWidth()+20; //宽度设置为屏幕
                p.height=d.getDisplayId()/2;
                dialog.getWindow().setAttributes(p); //设置生效
                dialog.show();
                break;//查看播放记录
            default:
                break;
        }
    }
    @Override
    protected void onStart() {
        super.onStart();
      //  songsid=getIntent().getStringExtra("songid");
        //http://music.baidu.com/data/music/fmlink?rate=320&songIds=242078437&type=&=&_t=&format=json
        //在线解析lrc的歌词  method=baidu.ting.song.lry&songid=877578

        Bundle bundle=getIntent().getBundleExtra("bundle");
        songsid=bundle.getString("songid");
        durction=bundle.getInt("durction");
        System.out.println("拿到的id"+songsid);
        pm_timing.setText("0:00");
        pm_totaltime.setText(String.valueOf(durction/60)+":"+String.valueOf(durction%60));
        seekBar.setMax(durction);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if (b)
                    mediaPlayer.seekTo(i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mediaPlayer.pause();
                mediaPlayer.seekTo(seekBar.getProgress()*1000);
                mediaPlayer.start();
                musicsongtwo.post(runnable);

                //获取进度条的进度
            }
        });
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                bt_onff.setBackgroundResource(R.drawable.stop);
                pauseflag=true;
            }
        });
        thread_receivesonglink(songsid);
        thread_recevielrc(songsid);
    }
    //获取歌词和播放连接的线程
    private void thread_receivesonglink(String currentid){
        thread1=  new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection;
                URL url = null;
                try {
                    url = new URL("http://music.baidu.com/data/music/fmlink?rate=320&songIds="+currentid+"&type=&callback=&_t=&format=json");//670251925
                    // url = new URL("http://music.baidu.com/data/music/fmlink?rate=320&songIds=588259036&type=&callback=&_t=&format=json");//605563712
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setConnectTimeout(60*1000);
                    connection.setReadTimeout(60*1000);
                    connection.connect();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    String s;
                    s=reader.readLine();

                    Message msg=new Message();
                    msg.obj=s;
                    hdt.sendMessage(msg);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        thread1.start();
    }
    private void thread_recevielrc(String currentid){
        //解析获取歌词
        thread2= new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connectionlrc;
                URL url_lrc = null;
                try {
                    url_lrc = new URL("http://tingapi.ting.baidu.com/v1/restserver/ting?format=json&calback=&from=webapp_music&method=baidu.ting.song.lry&songid="+currentid);//670251925
                    connectionlrc = (HttpURLConnection) url_lrc.openConnection();
                    connectionlrc.setConnectTimeout(60*1000);
                    connectionlrc.setReadTimeout(60*1000);
                    connectionlrc.connect();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(connectionlrc.getInputStream()));
                    String s1;
                    s1=reader.readLine();
                    System.out.println("geci="+s1);
                    Bean_lrc bean_lrc=new Bean_lrc();
                    Gson gson=new Gson();
                    bean_lrc=gson.fromJson(s1,Bean_lrc.class);
                    Message mjson=new Message();
                    mjson.obj=bean_lrc;
                    handler_lrc.sendMessage(mjson);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();

                }
            }
        });
        thread2.start();
    }
    Handler hdt=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String kk=(String)msg.obj;
            try {
                List<Object> lb=new ArrayList<>();
                JSONObject jsonObject=new JSONObject(kk);
                JSONObject j1=jsonObject.getJSONObject("data");
                JSONArray jsonArray=j1.getJSONArray("songList");
                JSONObject j2=jsonArray.getJSONObject(0);
                if (j2.getString("songLink").equals("")){
                   Toast.makeText(PlayMusicActivity.this,"当前歌曲不能播放",Toast.LENGTH_LONG).show();
                }
                else{
                    Gson gson=new Gson();
                    Bean_playmusic bean_playmusic=gson.fromJson(kk,Bean_playmusic.class);
                    System.out.println("link="+bean_playmusic.getData().getSongList().get(0).getShowLink());

                    if (bean_playmusic.getErrorCode()==22000){
                        String songlink=bean_playmusic.getData().getSongList().get(0).getSongLink();
                        String picuri=bean_playmusic.getData().getSongList().get(0).getSongPicRadio();
                        String songid=String.valueOf(bean_playmusic.getData().getSongList().get(0).getSongId());
                        String songname=bean_playmusic.getData().getSongList().get(0).getSongName();
                        String artistname=bean_playmusic.getData().getSongList().get(0).getArtistName();
                        playmusic(songid,songlink,picuri,songname,artistname);

//                        thread_recevielrc(songid);


                    }
                    else{
                        Toast.makeText(PlayMusicActivity.this,"当前歌曲不能在线播放",Toast.LENGTH_LONG).show();
                    }

//                    Message msgk=new Message();
//                    msgk.obj=bean_playmusic;
//                    handler_musicsong.sendMessage(msg);
                   // thread2.start();
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };
    //显示歌词
    Handler handler_lrc=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bean_lrc tv_lrc=(Bean_lrc) msg.obj;
            if(tv_lrc.getLrcContent()==null){
                Toast.makeText(PlayMusicActivity.this,"该曲没有歌词",Toast.LENGTH_LONG).show();
                flag_lrc=false;
                lrc_recycle.setVisibility(View.INVISIBLE);
            }
            else{
                flag_lrc=true;
                String string_lrc=tv_lrc.getLrcContent().toString();

                String[] splitlrc=string_lrc.split("\n");
                list_lrc=new ArrayList<>();
                for (int y=0;y<splitlrc.length;y++){
                    splitlrc[y]= splitlrc[y].replace("[","");
                    list_lrc.add(splitlrc[y]);
                }
                long_lrc=new long[list_lrc.size()];

                if (long_lrc.length>1){
                    for (int i=0;i<long_lrc.length;i++){
                        if (list_lrc.get(i).length()<1){
                            long_lrc[i]=0;
                        }
                      else{
                            String zz=list_lrc.get(i).substring(0,1);
                            String s_listlrc=list_lrc.get(i);
                            if (zz.equals("0")||zz.equals("1")||zz.equals("2")||zz.equals("3")||zz.equals("4")||zz.equals("5")||zz.equals("6")||zz.equals("7")||zz.equals("8")||zz.equals("9")){
                                String s_fen=s_listlrc.substring(0,2);
                                long long_fen=Long.valueOf(s_fen);
                                String s_miao=s_listlrc.substring(3,5);
                                long long_miao=Long.valueOf(s_miao);
                                String s_hao=s_listlrc.substring(6,8);
                                long long_hao=Long.valueOf(s_hao);
                                long total=long_fen*1000*60+long_miao*1000+long_hao;
                                long_lrc[i]=total;
                            }
                            else{
                                long_lrc[i]=0;
                            }
                        }
                    }
                }
                else{
                    flag_lrc=false;
                    Toast.makeText(PlayMusicActivity.this,"该曲没有歌词",Toast.LENGTH_LONG).show();
                    lrc_recycle.setVisibility(View.INVISIBLE);
                }

                if (list_lrc.size()>1){

                    mlinear = new LinearLayoutManager(PlayMusicActivity.this);
                    lrc_recycle.setLayoutManager(mlinear);
                    LrcInfoAdapter lrcInfoAdapter=new LrcInfoAdapter(list_lrc);
                    lrc_recycle.setAdapter(lrcInfoAdapter);
                }
            }



        }
    };
   private void playmusic(String songidc,String songlink,String picuri,String songnamec,String artistnamec){
       //播放地址
       tseturi=songlink;

       Glide.with(PlayMusicActivity.this).load(picuri).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(pm_background);

        songsid=songidc;
       if (tseturi==null){
           Toast.makeText(PlayMusicActivity.this,"当前音乐不能在线播放",Toast.LENGTH_LONG).show();
           bt_onff.setBackgroundResource(R.drawable.stop);
           mediaPlayer.release();
           mediaPlayer=new MediaPlayer();
           flag_playsong=false;
       }
       else{
           //使用media播放歌曲
           try {
               seekBar.setProgress(0);
               bt_onff.setBackgroundResource(R.drawable.musicing);
               mediaPlayer.release();
               mediaPlayer=new MediaPlayer();
               mediaPlayer.setDataSource(tseturi);
               mediaPlayer.prepare();
               mediaPlayer.start();
               musicsongtwo.post(runnable);
               flag_playsong=true;

           } catch (IOException e) {
               e.printStackTrace();
           }
       }
       //将当前歌曲放入播放记录 若sql已存在 不执行下面操作
       if (db.search_songid(songsid)){

       }
       else{
           int id=db.countid();
           String songname=songnamec;
           String songart=artistnamec;
           System.out.println("存入的link"+tseturi);
           db.adddata(id,songsid,mediaPlayer.getCurrentPosition(),tseturi,songname,songart,picuri);
       }

   }
    Runnable runnable=new Runnable() {
        @Override
        public void run() {
          seekBar.setProgress(mediaPlayer.getCurrentPosition()/1000);//音乐播放器当前的值

            setTime();
            musicsongtwo.postDelayed(runnable,200);

        }
    };
    Handler musicsongtwo=new Handler();
    //设置进度条的时间
    private void setTime(){
        int now=mediaPlayer.getCurrentPosition();//获取当前的播放进度
        int count=0;
        if (durction!=0)
        {
             count=durction;//歌曲的总长度
        }
        else
        {
            count=mediaPlayer.getDuration()/1000;
        }
        int seccond=now/1000;//当前有多少秒
        int csecound=count;//总共有多少秒

        seekBar.setMax(count);

        pm_timing.setText(String.valueOf(seccond/60)+":"+String.valueOf(seccond%60));
        pm_totaltime.setText(String.valueOf(csecound/60)+":"+String.valueOf(csecound%60));

        if (flag_lrc){
            for (int i=0;i<long_lrc.length;i++){
                if (i==long_lrc.length-1){

                }
                else{
                    if (now>long_lrc[i]&&now<long_lrc[i+1]){
                        dolrc(i);
                    }
                }

            }
        }
    }
    //对指定行的歌词做操作
    private void dolrc(int positon){

        if (pisition_flag==positon){

        }
        else{

            View childview1=lrc_recycle.getLayoutManager().findViewByPosition(pisition_flag);
            if (childview1!=null){
                TextView tt1=(TextView)childview1.findViewById(R.id.tv_musiclrc);

//        TextView tt=(TextView)mlinear.findViewByPosition(10);
                tt1.setTextColor(0xffffffff);
                tt1.setTextSize(18);
            }
            View childView = lrc_recycle.getLayoutManager().findViewByPosition(positon);
            if (childView!=null){
                TextView tt=(TextView)childView.findViewById(R.id.tv_musiclrc);

//        TextView tt=(TextView)mlinear.findViewByPosition(10);
                tt.setTextColor(0xffFF4500);
                tt.setTextSize(23);
            }
//            mlinear = new LinearLayoutManager(this);
//            lrc_recycle.setLayoutManager(mlinear);
            mlinear.scrollToPositionWithOffset(positon, 8);
            pisition_flag=positon;
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
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mediaPlayer.stop();
        mediaPlayer.release();
        musicsongtwo.removeCallbacks(runnable);
        thread1.destroy();
        thread2.destroy();

    }
}

package com.example.information;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.example.information.Configure.exitsystem;
import com.example.information.Fragment.Fragment_music;
import com.example.information.Fragment.Fragment_news;
import com.example.information.Fragment.Fragment_video;

public class MainActivity extends exitsystem implements View.OnClickListener {

    //底部菜单栏的三个布局
    private LinearLayout ll_news;
    private LinearLayout ll_music;
    private LinearLayout ll_video;
    //底部菜单栏的三个imageview
    private ImageView iv_news;
    private ImageView iv_music;
    private ImageView iv_videos;
    //底部菜单栏的三个textview
    private TextView tv_news;
    private TextView tv_music;
    private TextView tv_video;

    //三个fragment
    private Fragment_news fragment_news;
    private Fragment_music fragment_music;
    private Fragment_video fragment_video;
    private long exitTime=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        control_listener();
        initViewpage(0);
    }
    //界面初始化
    private void initView(){
        ll_news=(LinearLayout)findViewById(R.id.ll_news);
        ll_music=(LinearLayout)findViewById(R.id.ll_music);
        ll_video=(LinearLayout)findViewById(R.id.ll_video);

        iv_news=(ImageView)findViewById(R.id.iv_news);
        iv_music=(ImageView)findViewById(R.id.iv_music);
        iv_videos=(ImageView)findViewById(R.id.iv_video);

        tv_news=(TextView)findViewById(R.id.tv_news);
        tv_music=(TextView)findViewById(R.id.tv_music);
        tv_video=(TextView)findViewById(R.id.tv_video);
    }
//控件监听
    private void control_listener(){
        iv_news.setOnClickListener(this);
        iv_music.setOnClickListener(this);
        iv_videos.setOnClickListener(this);
        tv_news.setOnClickListener(this);
        tv_music.setOnClickListener(this);
        tv_video.setOnClickListener(this);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_news:
            case R.id.tv_news:
                restartbackground();
                iv_news.setImageResource(R.drawable.news_on);
                tv_news.setTextColor(0xff5ce1e6);
                initViewpage(0);
                break;
            case R.id.iv_music:
            case R.id.tv_music:
                restartbackground();
                iv_music.setImageResource(R.drawable.music_on);
                tv_music.setTextColor(0xff5ce1e6);
                initViewpage(1);
                break;
            case R.id.iv_video:
            case R.id.tv_video:
                restartbackground();
                iv_videos.setImageResource(R.drawable.video_on);
                tv_video.setTextColor(0xff5ce1e6);
                initViewpage(2);
                break;
            default:break;
        }
    }
    //让底部菜单栏的控件背景变灰
    private void restartbackground(){
        //imageview 设置为灰色
        iv_news.setImageResource(R.drawable.news_off);
        iv_music.setImageResource(R.drawable.music_off);
        iv_videos.setImageResource(R.drawable.video_off);
        //textview 字体颜色变灰
        tv_news.setTextColor(0xffd9d9d9);
        tv_music.setTextColor(0xffd9d9d9);
        tv_video.setTextColor(0xffd9d9d9);
    }
    //初始化页面
    private void initViewpage(int i){
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();// 创建一个事务
        hideFragment(transaction);// 先把所有的Fragment隐藏了，然后下面再开始处理具体要显示的Fragment
        switch (i) {
            case 0:
                if (fragment_news == null) {
                    fragment_news = new Fragment_news();
                    transaction.add(R.id.id_content, fragment_news,Fragment_news.class.getName());// 将Fragment添加到Activity中
                } else {
                    transaction.show(fragment_news);
                }
                //transaction.add(R.id.id_content, fragment_home,Fragment_home.class.getName());// 将Fragment添加到Activity中
                break;
            case 1:
                if (fragment_music == null) {
                    fragment_music = new Fragment_music();
                    transaction.add(R.id.id_content, fragment_music,Fragment_music.class.getName());
                } else {
                    transaction.show(fragment_music);
                }
                //   transaction.add(R.id.id_content, fragment_address,Fragment_address.class.getName());
                break;
            case 2:
                if (fragment_video == null) {
                    fragment_video = new Fragment_video();
                    transaction.add(R.id.id_content, fragment_video, Fragment_video.class.getName());
                } else {
                    transaction.show(fragment_video);
                }
                //transaction.add(R.id.id_content, fragment_buSu,Fragment_BuSu.class.getName());
                break;
            default:
                break;
        }
        //  transaction.addToBackStack(null);
        transaction.commit();// 提交事务
    }
    //隐藏fragment
    private void hideFragment(FragmentTransaction transaction){
        if (fragment_news != null) {
            transaction.hide(fragment_news);
        }
        if (fragment_music != null) {
            transaction.hide(fragment_music);
        }
        if (fragment_video != null) {
            transaction.hide(fragment_video);
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
